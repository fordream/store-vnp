/*
 * Created by SharpDevelop.
 * User: jroam
 * Date: 5/20/2004
 * Time: 11:36 AM
 * 
 */

using System;


namespace Orbz
{	
	public class GameGrid
	{
	
		#region Variables, etc.
		private System.Drawing.Size size;              //width/height of grid in SubUnits				
		private SubUnit.SubUnitType[][] grid;	      //actual grid spaces
		private int[][] orbtimer;                    //tracks orbs in countdown state
		#endregion
		
		#region Constructors/Destructors, etc..
		public GameGrid( System.Drawing.Size s )
		{
			//set the size of the grid
			size = s;
			grid = new SubUnit.SubUnitType[s.Height][];
			orbtimer = new  int[s.Height][];
			
			//fill the grid with blank space
			for (int i=0; i<grid.Length;i++) {
				grid[i] = new SubUnit.SubUnitType[s.Width];
				orbtimer[i] = new int[s.Width];
				for (int j=0; j<grid[i].Length;j++) {
					grid[i][j] = SubUnit.SubUnitType.blank;
					orbtimer[i][j] = -1;
				}
			}
		}//end Constructor
	
		#endregion
	
		#region Properties
		/// <summary>
		/// Returns a Size object whose width and height represent
		/// the number of SubUnits across and up/down compose the grid
		/// </summary>
		public System.Drawing.Size Size
		{
			get
			{
				return size;
			}
		}

		#endregion
			
		#region Public Methods
		///<summary>
		//returns true if this is a valid location to add a new
		//SubUnit, false otherwise
		//a location is valid if it is in bounds and holds a
		//blank SubUnitType
		///</summary>
		public bool ValidateLocation( int x, int y )
		{
			bool bValid = false;
			
			if (x > -1 && x < size.Width) {
				if (y > -1 && y < size.Height) {
					if (grid[y][x] == SubUnit.SubUnitType.blank)
						bValid = true;
				}//end if
			}//end if
			
			return bValid;
		}
		
		///<summary>
		//Fills a space in the grid
		///</summary>
		public void FillSpace( int oc, SubUnit.SubUnitType s, int x, int y )
		{
			grid[y][x] = s;
			orbtimer[y][x] = oc;
		}

		//returns the type of a space in the grid
		public SubUnit.SubUnitType GetSpace( int x, int y )
		{
			return grid[y][x];
		}

		public int GetOrbCount( int x, int y)
		{
			return orbtimer[y][x];
		}
		
		//makes the grid all blank spaces
		public void Clear()
		{
			for (int i=0; i<grid.Length; i++) {
				for (int j=0; j<grid[i].Length; j++) {
					grid[i][j] = SubUnit.SubUnitType.blank;
					orbtimer[i][j] = -1;
				}
			}
		}
		
		//runs through all the orbs and reduces their counts
		public void ReduceOrbCounts()
		{
			for (int i=0; i<grid.Length; i++)
			{
				for (int j=0; j<grid[i].Length; j++)
				{
					if (orbtimer[i][j]>0)
						orbtimer[i][j]--;
				}
			}
		}
		#endregion
			
		#region Private Methods
			
		#endregion
	}
}
