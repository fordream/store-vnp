package wyf.ytl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/*******************************
 * 
 * The class is HelpView,
 * One picture and one button
 *
 *******************************/
public class HelpView extends SurfaceView implements SurfaceHolder.Callback {
	ChessActivity activity;
	private TutorialThread thread;
	Bitmap back;
	Bitmap helpBackground;
	
	public HelpView(Context context,ChessActivity activity) {								//Constructors 
		super(context);
		this.activity = activity;
        getHolder().addCallback(this);
        this.thread = new TutorialThread(getHolder(), this);								//Initialization of thread
        initBitmap();//Initialization of background pic
	}
	
	public void initBitmap(){
		back = BitmapFactory.decodeResource(getResources(), R.drawable.back);				//Back button
		helpBackground = BitmapFactory.decodeResource(getResources(), R.drawable.helpbackground);//Initialization of background pic
	}
	
	public void onDraw(Canvas canvas){														//Drawing method
		canvas.drawBitmap(helpBackground, 0, 90, new Paint());								//Drawing background pic
		canvas.drawBitmap(back, 200, 370, new Paint());										//Drawing button
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
	
	public void surfaceCreated(SurfaceHolder holder) {										//Create updating thread
        this.thread.setFlag(true);															//Setting flag
        this.thread.start();//Starting the thread
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {									//Stopping the thread
        boolean retry = true;																//Loop flag
        thread.setFlag(false);																//Setting flag
        while (retry) {
            try {
                thread.join();																//Waiting the thread stops
                retry = false;																//Stop looping
            }catch (InterruptedException e){}												//Loop until thread stopping
        }
	}
	
	public boolean onTouchEvent(MotionEvent event) {										//Monitoring the screen
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(event.getX()>200 && event.getX()<200+back.getWidth() && event.getY()>370 && event.getY()<370+back.getHeight()){//Push the back button
				activity.myHandler.sendEmptyMessage(1);										//Send Handler
			}
		}
		return super.onTouchEvent(event);
	} 
	
	class TutorialThread extends Thread{
		private int span = 1000;															//Milisecond of sleeping 
		private SurfaceHolder surfaceHolder;
		private HelpView helpView;
		private boolean flag = false;														//Looping flag
        public TutorialThread(SurfaceHolder surfaceHolder, HelpView helpView) {				//Constructors
            this.surfaceHolder = surfaceHolder;
            this.helpView = helpView;
        }
		
        public void setFlag(boolean flag) {													//Setting flag
        	this.flag = flag;
        }
		
		public void run() {																	//Run method
			Canvas c;
            while (this.flag) {
                c = null;
                try {
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {										//Synchronize
                    	helpView.onDraw(c);
                    }
                } finally {																	//Guarantee next code execution
                    if (c != null) {														//Refresh screen
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                try{
                	Thread.sleep(span);														//Time of sleeping
                }catch(Exception e){
                	e.printStackTrace();
                }
            }
		}
	}
}