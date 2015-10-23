package wyf.ytl;
//Import reference package
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class ChessActivity extends Activity {
	boolean isSound = true;															//Volume on or off
	MediaPlayer startSound;															//Music of start and menu
	MediaPlayer gamesound;															//Game sound
	
	Handler myHandler = new Handler(){												//The plug-in of updating the UI thread
        public void handleMessage(Message msg) {
        	if(msg.what == 1){														//Swith to MenuView when WelcomeView or HelpView or GameView triggers
        		initMenuView();														//Initialization of menu screen
        	}
        	else if(msg.what == 2){													//Swith to GameView when MenuView triggers
        		initGameView();														//Initialization of game screen
        	}
        	else if(msg.what == 3){													//Switch to HelpView when MenuView triggers
        		initHelpView();														//Initialization of help screen
        	}
        }
	}; 	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		startSound = MediaPlayer.create(this, R.raw.startsound);					//Load welcome sound
		startSound.setLooping(true);												//Loop playing the welcome sound 
		gamesound  = MediaPlayer.create(this, R.raw.gamesound);						//Load background sound
		gamesound.setLooping(true);													//Loop playing the background sound 
        this.initWelcomeView();														//Initialization of welcome screen
    }
	
    public void initWelcomeView(){													//Initialization of welcome screen 
		this.setContentView(new WelcomeView(this,this));							//Switch to welcome screen
    	if(isSound){
    		startSound.start();														//Volume on
		}
    }
    
    public void initGameView(){														//Initialization of game screen
    	this.setContentView(new GameView(this,this)); 								//Swith to GameView
    }
    
    public void initMenuView(){														//Initialization of menu screen
    	if(startSound != null){														//Stop
    		startSound.stop();														//Stop sound
    		startSound = null;
    	}
    	if(this.isSound){
    		gamesound.start();														//Playing sound
    	}
    	this.setContentView(new MenuView(this,this));								//Swith to MenuView
    } 
	
    public void initHelpView(){														//Initialization of help screen
    	this.setContentView(new HelpView(this,this));								//Switch to HelpView
    }
}