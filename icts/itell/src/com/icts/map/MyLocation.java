package com.icts.map;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * This class to get users' location using gps or network.</p>
 * User can customize when getting the location 
 * @author Luong
 *
 */
public class MyLocation {
	// Time to reset timer: 10'
	private final int INTERVAL_TIME = 600000;
	private Context mContext;
    private Timer timer1;
    private LocationManager lm;
    private LocationResult locationResult;
    private boolean gps_enabled=false;
    private boolean network_enabled=false;
    private GetLastLocation timerTask;
    private ProgressDialog mProgressDialog;
    //Listener to handle when user changed location, based on gps
    private LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
        	if (mProgressDialog!=null){
        		mProgressDialog.dismiss();
        	}
        	if (location==null){
        		return;
        	}
        	Log.i("NDT", "go2");
            timer1.cancel();
            locationResult.gotLocation(location);
            lm.removeUpdates(locationListenerGps);
            lm.removeUpdates(locationListenerNetwork);
            resetTimer(false);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    
    // Listener to handle when user changed location, based on network
    private LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
        	if (mProgressDialog!=null){
        		mProgressDialog.dismiss();
        	}
        	if (location==null){
        		return;
        	}
        	Log.i("NDT", "go1");
            timer1.cancel();
            locationResult.gotLocation(location);
            lm.removeUpdates(locationListenerNetwork);
            lm.removeUpdates(locationListenerGps);
            resetTimer(false);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    
    public boolean getLocation(Context context, LocationResult result){
    	mProgressDialog = new ProgressDialog(context);
    	mProgressDialog.setMessage("Got location...");
    	mProgressDialog.show();
    	this.mContext = context;
        //I use LocationResult callback class to pass location value from MyLocation to user code.
        this.locationResult=result;
        if(lm==null){
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        //exceptions will be thrown if provider is not permitted.
        try{
        	gps_enabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        	}
        catch(Exception ex){}
        try{
        	network_enabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        	}
        catch(Exception ex){}

        //don't start listeners if no provider is enabled
        if(!gps_enabled && !network_enabled){
            return false;
        }

        if(gps_enabled){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        }
        //else {
	        if(network_enabled){
	            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
	        }
        //}
        timer1 = new Timer();
        if (timerTask!=null){
        	timerTask.stopTimer();
        }
        timerTask = new GetLastLocation();
        timer1.schedule(timerTask, INTERVAL_TIME);
        return true;
    }
    
    /**
     * Disable timer and remove update users' location
     */
    public void disableTimer(){
    	try{
    		timer1.cancel();
    		if (lm!=null){
    			lm.removeUpdates(locationListenerGps);
    			lm.removeUpdates(locationListenerNetwork);
    		}
            locationResult.gotLocation(null);
    	}
    	catch(Exception ex){
    		
    	}
    }
    
    public boolean resetTimer(boolean c){
    	 if(lm==null){
             lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
         }
    	 
         //exceptions will be thrown if provider is not permitted.
         try{
         	gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
         }
         catch(Exception ex){}
         try{
         	network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
         }
         catch(Exception ex){}
         //don't start listeners if no provider is enabled
         if(!gps_enabled && !network_enabled){
             return false;
         }
         if (c){
	         if(gps_enabled){
	             lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
	         }
	         if(network_enabled){
	             lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 
	            		 				   0, 0, locationListenerNetwork);
	         }
         }
         timer1 = new Timer();
         if (timerTask!=null){
         	timerTask.stopTimer();
         }
         timerTask = new GetLastLocation();
         timer1.schedule(timerTask, INTERVAL_TIME);
         return true;
    }
    
    /**
     * This task to update user's location in INTERVAL_TIME
     * @author TRUNG
     *
     */
    public class GetLastLocation extends TimerTask {
    	private Handler handler = new Handler();
    	private Location location = null;
		private  Runnable gotLocation = new Runnable() {
			public void run() {
				locationResult.gotLocation(location);
				resetTimer(false);
			}
		}; 
        @Override
        public void run() {
        	Log.i("NDT", "go");
        	
        	//Disable update users' location
            lm.removeUpdates(locationListenerGps);
            lm.removeUpdates(locationListenerNetwork);

             Location net_loc=null;
     		 Location gps_loc=null;
             if(gps_enabled){
                 gps_loc=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             }
             //else {
	             if(network_enabled){
	                 net_loc=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	             }
             //}

             //Get location
             //reset timer
             //boolean c = resetTimer();
             //if (!c){
            	 //error 
             //}
             
             //if there are both values use the latest one
             if(gps_loc!=null && net_loc!=null){
                 if(gps_loc.getTime()>net_loc.getTime()){
                	 location = gps_loc;
					 //locationResult.gotLocation(gps_loc);
                	 handler.post(gotLocation);
							
                 }
                 else{
                     //locationResult.gotLocation(net_loc);
                	 location = net_loc;
					 //locationResult.gotLocation(net_loc);
                	 handler.post(gotLocation);
                 }
                 return;
             }

             if(gps_loc!=null){
            	  location = gps_loc;
            	  handler.post(gotLocation);
                 //locationResult.gotLocation(gps_loc);
                 return;
             }
             if(net_loc!=null){
                 //locationResult.gotLocation(net_loc);
                 //resetTimer();
            	 location = net_loc;
            	 handler.post(gotLocation);
                 return;
             }
             location = null;
             //locationResult.gotLocation(null);
             handler.post(gotLocation);
        }
        
        public void stopTimer(){
        	handler.removeCallbacks(gotLocation);
        }
    }

    /**
     * Handle when users got their location
     * @author Luong
     *
     */
    public static abstract class LocationResult{
        public abstract void gotLocation(Location location);
    }
}
