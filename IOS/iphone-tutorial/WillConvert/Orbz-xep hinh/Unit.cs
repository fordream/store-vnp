/*
 * Created by SharpDevelop.
 * User: jroam
 * Date: 5/20/2004
 * Time: 11:35 AM
 * 
 */

using System;
using System.Drawing;

namespace Orbz
{
	public class Unit
	{
		#region Variables, etc...
		private static System.Random rnd;      //used to do random numbers
		private SubUnit[] subunits;            //the subunits
		private GameGrid grid;                 //reference to the grid
		#endregion
		
		#region Constructors/Destructors, etc...
		public Unit( GameGrid g )
		{
			rnd = new System.Random();
			grid = g;
			
			//create SubUnits
			subunits = new SubUnit[4];
			for (int i=0; i<subunits.Length; i++)	
				subunits[i] = new SubUnit( grid );
		}
		#endregion
		
		#region Properties
		//return/set the gamegrid - moves unit to top of whatever
		//grid is assigned
		public GameGrid Grid
		{
			set
			{
				grid = value;
			}
			get
			{
				return grid;
			}
		}
		#endregion
		
		#region Public Methods
		//builds the Unit and sets its location to the top of the grid
		public void Create()
		{
			SubUnit.SubUnitType t1, t2;
			
			//set the types for this unit -- only squares can be used for overall type (6-10)
			t1 = (SubUnit.SubUnitType)(rnd.Next(60,110)/10);
			t2 = (SubUnit.SubUnitType)(rnd.Next(60,110)/10);
			
			//clear SubUnit pointers and set their type
			for (int i=0; i<subunits.Length; i++) {
				subunits[i].Clear();
				if (i<2)
					subunits[i].Type = t1;
				else
					subunits[i].Type = t2;
				subunits[i].OrbCount = -1;
			}
			
			//determine if this unit contains an orb - and if it does
			//place the orb
			if (rnd.Next(0,GameState.OrbRate) == (GameState.OrbRate/2))
			{
				int t = rnd.Next(0,subunits.Length-1);
				subunits[t].Type -= 5;
				subunits[t].OrbCount = rnd.Next(0,6);
			}
			
			//set location of axis
			subunits[0].Location.Y = 1;
			subunits[0].Location.X = grid.Size.Width/2 - 1;		

			//randomly choose a block configuration and then 
			//set the SubUnit pointers/locations to that config.
			switch(rnd.Next(10,80)/10)
			{
				case 1:
					//L shape
					PlaceSubUnits( subunits[0], subunits[1], "Top");
					PlaceSubUnits( subunits[0], subunits[2], "Bottom");
					PlaceSubUnits( subunits[2], subunits[3], "Right");
					break;
				case 2:
					//Backward L shape
					PlaceSubUnits( subunits[0], subunits[1], "Top");
					PlaceSubUnits( subunits[0], subunits[2], "Bottom");
					PlaceSubUnits( subunits[2], subunits[3], "Left");
					break;
				case 3:
					//Straight Line shape
					PlaceSubUnits( subunits[0], subunits[1], "Top");
					PlaceSubUnits( subunits[0], subunits[2], "Bottom");
					PlaceSubUnits( subunits[2], subunits[3], "Bottom");
					break;
				case 4:
					//Block shape
					PlaceSubUnits( subunits[0], subunits[1], "Right");
					PlaceSubUnits( subunits[0], subunits[2], "Top");
					PlaceSubUnits( subunits[1], subunits[3], "Top");
					break;
				case 5:
					//T shape
					PlaceSubUnits( subunits[0], subunits[1], "Right");
					PlaceSubUnits( subunits[0], subunits[2], "Bottom");
					PlaceSubUnits( subunits[0], subunits[3], "Top");
					break;
				case 6:
					//Zig Zag shape
					PlaceSubUnits( subunits[0], subunits[1], "Bottom");
					PlaceSubUnits( subunits[0], subunits[2], "Right");
					PlaceSubUnits( subunits[2], subunits[3], "Top");
					break;
				case 7:
					//Backward Zig Zag shape
					PlaceSubUnits( subunits[0], subunits[1], "Bottom");
					PlaceSubUnits( subunits[0], subunits[2], "Right");
					PlaceSubUnits( subunits[2], subunits[3], "Top");
					break;
				default:					
					break;
			}//end switch
		}
		
		//returns the SubUnit at the specified index
		public SubUnit GetSubUnit( int index )
		{
			return subunits[index];	
		}
		
		//adjusts unit so that it is at the top/center
		//of the grid
		public void Calibrate()
		{
			int x, y;
			x = subunits[0].Location.X;
			y = subunits[0].Location.Y;
			
			//set location of axis
			subunits[0].Location.Y = 1;
			subunits[0].Location.X = grid.Size.Width/2 - 1;
			
			//calculate the movement of the axis
			x -= subunits[0].Location.X;
			y -= subunits[0].Location.Y;
			
			//move the other units
			for (int i=1; i<subunits.Length; i++)
			{
				subunits[i].Location.X -= x;
				subunits[i].Location.Y -= y;
			}
		}
		
		//returns true if the unit is currently in a valid location
		public bool ValidateLocation()
		{
			bool bValid = true;
			
			for (int i=0; i<subunits.Length; i++)
			{
				if(!grid.ValidateLocation(subunits[i].Location.X, subunits[i].Location.Y))
				{
					bValid = false;
					break;
				}
			}
			
			return bValid;
		}
		
		//Shifts the Unit to the left - returns whether or not the shift occured
		public bool ShiftLeft()
		{
			bool bValid = true;
			
			//test each subunit to make sure shift is allowed
			for (int i=0; i<subunits.Length; i++)
			{
				if (!grid.ValidateLocation(subunits[i].Location.X - 1, subunits[i].Location.Y))
				{
					bValid = false;
					break;
				}
			}
			
			//if all subunit shifts are valid then shift the unit
			if (bValid)
			{
				for (int i=0; i<subunits.Length; i++)
					subunits[i].ShiftLeft();
			}
			
			return bValid;
		}
		
		//Shifts the Unit to the right - returns whether or not the shift occured
		public bool ShiftRight()
		{
			bool bValid = true;
			
			//test each subunit to make sure shift is allowed
			for (int i=0; i<subunits.Length; i++)
			{
				if (!grid.ValidateLocation(subunits[i].Location.X + 1, subunits[i].Location.Y))
				{
					bValid = false;
					break;
				}
			}
			
			//if all subunit shifts are valid then shift the unit
			if (bValid)
			{
				for (int i=0; i<subunits.Length; i++)
					subunits[i].ShiftRight();
			}
			
			return bValid;
		}
		
		//Shifte the Unit down in the grid
		//If the Unit cannot go any further then it is added to the grid
		public bool ShiftDown()
		{
			bool bValid = true;
			
			//test each subunit to make sure shift is allowed
			for (int i=0; i<subunits.Length; i++)
			{
				if (!grid.ValidateLocation(subunits[i].Location.X, subunits[i].Location.Y + 1))
				{
					bValid = false;
					break;
				}
			}
			
			//if all subunit shifts are valid then shift the unit
			//otherwise add the unit to the grid
			if (bValid)
			{
				for (int i=0; i<subunits.Length; i++)
					subunits[i].ShiftDown();
			}
			else
				this.AddToGrid();
			
			return bValid;
		}
		
		//right rotates the unit
		public bool RotateRight()
		{
			subunits[0].RotateRight();
			
			for (int i=1; i<subunits.Length; i++)
			{
				if (!grid.ValidateLocation(subunits[i].Location.X, subunits[i].Location.Y))
				{
					subunits[0].RotateLeft();
					return false;
				}
			}
			return true;
		}
		
		//left rotates the unit
		public bool RotateLeft()
		{
			subunits[0].RotateLeft();
			
			for (int i=1; i<subunits.Length; i++)
			{
				if (!grid.ValidateLocation(subunits[i].Location.X, subunits[i].Location.Y))
				{
					subunits[0].RotateRight();
					return false;
				}
			}
			return true;
		}
		#endregion
		
		#region Private Methods		
		//sets the pointers in a to reference Subunit b, and then
		//changes b's location to reflect the change
		private void PlaceSubUnits( SubUnit a, SubUnit b, String s)
		{
			switch(s)
			{
				case "Top":
					a.Top = b;
					b.Location.X = a.Location.X;
					b.Location.Y = a.Location.Y - 1;
					break;
				case "Bottom":
					a.Bottom = b;
					b.Location.X = a.Location.X;
					b.Location.Y = a.Location.Y + 1;
					break;
				case "Left":
					a.Left = b;
					b.Location.X = a.Location.X - 1;
					b.Location.Y = a.Location.Y;
					break;
				case "Right":
					a.Right = b;
					b.Location.X = a.Location.X + 1;
					b.Location.Y = a.Location.Y;
					break;
			}//end switch
		}
		
		//Migrates this Unit into the grid
		private void AddToGrid()
		{
			//all orbs currently in the grid get counted down
			grid.ReduceOrbCounts();
			
			//move each subunit into the grid
			for (int i=0; i<subunits.Length; i++)
			{
				grid.FillSpace( subunits[i].OrbCount, subunits[i].Type, subunits[i].Location.X, subunits[i].Location.Y);
			}
		}
		#endregion
	}
}
