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
	public class SubUnit
	{
		#region Variables, etc...
		//Cr = circle, Sq = square - y,b,etc. = colors (yellow, blue)		
		public enum SubUnitType
		{
			ySq = 10,
			bSq = 9,
			gSq = 8,
			pSq = 7,
			rSq = 6,
			bCr = 4,
			rCr = 1,
			pCr = 2,
			gCr = 3,
			yCr = 5,
			fCr = 0,
			blank = -1,
			clearing = -2
		};
		public Point Location;			//location in the GameGrid
		
		private GameGrid grid;          //reference to the GameGrid
		private SubUnitType type;		//tracks the type of SubUnit represented
		private int countdown;          //for orbs that will count down
		
		//pointers to adjacent SubUnits
		private SubUnit top;
		private SubUnit bottom;
		private SubUnit left;
		private SubUnit right;		
		
		#endregion
		
		#region Constructors/Destructors, etc...		
		public SubUnit( GameGrid g )
		{
			grid = g;
			Location = new Point(0,0);
			type = SubUnitType.blank;
			countdown = -1;
		}
		#endregion
		
		#region Properties
		//What type of SubUnit is this?
		public SubUnitType Type
		{
			get
			{
				return type;
			}
			set
			{
				type = value;
			}
		}
		
		//for orbs that are counting down
		public int OrbCount
		{
			get
			{
				return countdown;
			}
			set
			{
				countdown = value;
			}
		}
		
		//access to top
		public SubUnit Top
		{
			set
			{
				top = value;
			}
		}
		
		//access to bottom
		public SubUnit Bottom
		{
			set
			{
				bottom = value;
			}
		}
		
		//access to right
		public SubUnit Right
		{
			set
			{
				right = value;
			}
		}
		
		//access to left
		public SubUnit Left
		{
			set
			{
				left = value;
			}
		}
		#endregion
		
		#region Public Methods
		//clears all pointers to other SubUnits
		public void Clear()
		{
			this.top = null;
			this.bottom = null;
			this.left = null;
			this.right = null;
		}
		
		//moves one unit left in the grid
		public void ShiftLeft()
		{
			Location.X--;
		}
		
		//moves one unit right in the grid
		public void ShiftRight()
		{
			Location.X++;
		}
		
		//moves the subunit one unit down in the grid
		public void ShiftDown()
		{
			Location.Y++;
		}
	
		//rotates the SubUnit and all attached SubUnits clockwise around their axis
		public void RotateRight()
		{
			SubUnit temp = this.top;
			
			//rotate pointers and adjust locations of attached SubUnits
			this.top = this.left;
			if (this.top != null) {
				this.top.Location.Y = this.Location.Y - 1;
				this.top.Location.X = this.Location.X;
				this.top.RotateRight();
			}
			this.left = this.bottom;
			if (this.left != null) {
				this.left.Location.X = this.Location.X - 1;
				this.left.Location.Y = this.Location.Y;
				this.left.RotateRight();
			}
			this.bottom = this.right;
			if (this.bottom != null) {
				this.bottom.Location.Y = this.Location.Y + 1;
				this.bottom.Location.X = this.Location.X;
				this.bottom.RotateRight();
			}
			this.right = temp;
			if (this.right != null) {
				this.right.Location.X = this.Location.X + 1;
				this.right.Location.Y = this.Location.Y;
				this.right.RotateRight();
			}
		}
		
		//rotates the SubUnit and all attached SubUnits counter-clockwise around their axis
		public void RotateLeft()
		{
			SubUnit temp = this.top;
			
			//rotate pointers
			this.top = this.right;
			if (this.top != null) {
				this.top.Location.Y = this.Location.Y - 1;
				this.top.Location.X = this.Location.X;
				this.top.RotateLeft();
			}
			this.right = this.bottom;
			if (this.right != null) {
				this.right.Location.X = this.Location.X + 1;
				this.right.Location.Y = this.Location.Y;
				this.right.RotateLeft();
			}
			this.bottom = this.left;
			if (this.bottom != null) {
				this.bottom.Location.Y = this.Location.Y + 1;
				this.bottom.Location.X = this.Location.X;
				this.bottom.RotateLeft();
			}
			this.left = temp;
			if (this.left != null) {
				this.left.Location.X = this.Location.X - 1;
				this.left.Location.Y = this.Location.Y;
				this.left.RotateLeft();
			}
		}
		
		#endregion

		#region Private Methods
	
		#endregion
	}
}
