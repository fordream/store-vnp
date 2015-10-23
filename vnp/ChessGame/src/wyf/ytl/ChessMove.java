package wyf.ytl;
/**
 * The movement of a chess
 * Information of the type of chess
 * start position
 * destination 
 * estimate the score
 */
public class ChessMove {
	int ChessID;																	//Type of chess
	int fromX;																		//Start position
	int fromY;
	int toX;																		//Destination
	int toY;
	int score;																		//Estimation
	public ChessMove(int ChessID, int fromX,int fromY,int toX,int toY,int score){	//Constructors
		this.ChessID = ChessID;														
		this.fromX = fromX;															
		this.fromY = fromY;
		this.toX = toX;																
		this.toY = toY;																
		this.score = score;
	}
}