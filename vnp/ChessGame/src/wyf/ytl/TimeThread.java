package wyf.ytl;
/**
 * 
 * Computing the thinking time,
 * the class will plus 1 when you are thinking
 *
 */
public class TimeThread extends Thread{
	private boolean flag = true;													
	GameView gameView;
	
	public TimeThread(GameView gameView){											//Constructors
		this.gameView = gameView;													
	}
	
	public void setFlag(boolean flag){												
		this.flag = flag;
	}
	
	@Override
	public void run(){																
		while(flag){
			if(gameView.caiPan == false){											//Opponent is black
				gameView.heiTime++;													//Add black time
			}
			else if(gameView.caiPan == true){										//Opponent is red
				gameView.hongTime++;												//Add red time
			}
			try{
				Thread.sleep(1000);													//Milisecond of sleeping, 1 second
			}
			catch(Exception e){														
				e.printStackTrace();												
			}
		}
	}
}