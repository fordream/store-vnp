

using System;
using System.Text;

namespace Orbz
{

public delegate void GameEventHandler(object sender, EventArgs e);

	public class GameState
	{
		#region Variables
		private static int orbRate = 2;
		private static int score = 0;
		private static int chain = 0;
		private static int chainCount = 0;
		private static int maxChain = 0;
		private static int maxChainCount = 0;
		private static int level = 0;
		private static int difficulty = 0;
		private static int clockSpeed = 800;
		private const int basePoints = 100;
		public static event GameEventHandler LevelUp;
		
		//for logging exceptions
		private static int errorCount = 0;
		private static StringBuilder sb = new StringBuilder();
		
		//points to jump to next level
		private static int[] levelJumps = {12500, 37500, 75000, 125000, 200000,
											250000, 300000, 350000, 400000, 450000,
											500000, 550000, 600000, 650000, 700000,
											750000, 800000, 850000, 900000, 950000};
		#endregion
		
		#region Properties
		//how often orbs appear
		public static int OrbRate
		{
			get
			{
				return orbRate;
			}
		}
		
		//game score
		public static int Score
		{
			get
			{
				return score;
			}
		}
		
		//game difficulty level
		public static int Difficulty
		{
			get
			{
				return difficulty;
			}
		}
		
		//# points in last orb chain
		public static int Chain
		{
			get
			{
				return chain;
			}
			set
			{
				chain = value;
				if(value>maxChain)
					maxChain = value;
			}
		}
		
		//# of blocks in last chain
		public static int ChainCount
		{
			get
			{
				return chainCount;
			}
			set
			{
				chainCount = value;
				if (value>maxChainCount)
					maxChainCount = value;
			}
		}
		
		//# points in largest chain made in game
		public static int MaxChain
		{
			get
			{
				return maxChain;
			}
		}
		
		//#blocks in largetst chain made in game
		public static int MaxChainCount
		{
			get
			{
				return maxChainCount;
			}
		}
		
		//level of difficulty
		public static int Level
		{
			get
			{
				return level;
			}
		}
		
		//how many milliseconds between clock ticks
		public static int ClockSpeed
		{
			get
			{
				return clockSpeed;
			}
		}
		
		//base number of points for a block exploding
		//*for the i'th block i*basePoints will be the number of points assessed
		//though this will be handled in the scoring loop
		public static int BasePoints
		{
			get
			{
				return basePoints;
			}
		}
		
		#endregion

		#region Methods
		//resets back to original game state
		public static void Reset()
		{
			orbRate = 2;
			score = 0;
			chain = 0;
			chainCount = 0;
			maxChain = 0;
			maxChainCount = 0;
			level = 0;
			clockSpeed = 800 - (50*difficulty);
		}
		
		//true to raise difficulty, false to lower it
		public static void AdjustDifficulty( bool bRaise )
		{
			if (bRaise) {
				if (difficulty < 10) {
					difficulty++;
					clockSpeed -= 50;
				}
			}
			else {
				if (difficulty > -5) {
					difficulty--;
					clockSpeed += 50;
				}
			}
		}
		
		//increment the score
		public static void IncrementScore( int points )
		{
			score += points;
			
			while (score >= levelJumps[level] && level < levelJumps.Length)
				IncrementLevel();
		}
		
		//raise the level of difficulty
		private static void IncrementLevel()
		{
			level++;
			if (level < 10)
				clockSpeed -= 70;
			else
				clockSpeed -= 35;
			if (clockSpeed < 30)
				clockSpeed = 30;
			if (level%2==0 && orbRate<6)
				orbRate++;
			
			if (LevelUp != null)
				LevelUp(null,null);
		}
				
		public static void AppendToLog( string str )
		{
			errorCount++;
			sb.Append("Error ");
			sb.Append(errorCount);
			sb.Append("\n\n");
			sb.Append(str);
			sb.Append("\n\n");
		}
		
		public static void LogToClipboard()
		{
			System.Windows.Forms.Clipboard.SetDataObject(sb.ToString(), true);
		}
		#endregion
	}
}
