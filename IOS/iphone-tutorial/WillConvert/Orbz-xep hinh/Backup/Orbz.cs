/*
 * Created by SharpDevelop.
 * User: jroam
 * Date: 5/20/2004
 * Time: 11:20 AM
 * 
 */
using System;
using System.Windows.Forms;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Reflection;
using System.Threading;
using System.IO;

[assembly:CLSCompliant(true)]

namespace Orbz
{
	public class Orbz : System.Windows.Forms.Form
	{ 
		private System.ComponentModel.IContainer components;
		private System.Windows.Forms.MenuItem miSndEnable;
		private System.Windows.Forms.MenuItem miDiffUp;
		private System.Windows.Forms.MenuItem miPause;
		private System.Windows.Forms.MenuItem menuItem;
		private System.Windows.Forms.MenuItem menuItem3;
		private System.Windows.Forms.MenuItem menuItem2;
		private System.Windows.Forms.MenuItem menuItem1;
		private System.Windows.Forms.MainMenu mainMenu;
		private System.Windows.Forms.MenuItem menuItem6;
		private System.Windows.Forms.MenuItem menuItem4;
		private System.Windows.Forms.MenuItem miStop;
		private System.Windows.Forms.Timer clock;
		private System.Windows.Forms.MenuItem miStart;
		private System.Windows.Forms.MenuItem miSndMode;
		private System.Windows.Forms.MenuItem miDiffDown;
		private System.Windows.Forms.ImageList ilUnits;
		
		#region Variables (non-Form Generator added)
		
		//Game Variables
		private DrawingSurface srfGrid;  //surface to draw the grid on
		private DrawingSurface srfNext;  //surface to display next unit
		private DrawingSurface srfStatus;//status area
		private GameGrid gameArea;       //actual playing grid
		private GameGrid nextArea;       //stores the next unit to be dropped into the grid
		private Unit activeUnit;         //unit in the grid
		private Unit nextUnit;           //next unit to be added to the grid
		private bool bRunning;           //whether game is running or not
		private bool bPaused;            //paused?
		private bool bGameOver;          //game over?
		private bool bSound;             //play sound?
		
		//Graphics Variables
		private bool isDirty;            //whether grid or active unit has been modifed since last paint
		private LinearGradientBrush gridBackGround;   //for drawing the background of the grid
		private LinearGradientBrush statusBackGround; //for drawing the status area
		private SolidBrush nextBackGround;            //for the next grid
		private Font font;                            //for drawing orb countdown text
		private StringFormat strfmt;                  //for drawing orb countdown text

		//sound variables
		private bool bSndLoopMode = false;
		private int currentTrack = 0;
		private int currentPauseTrack = 0;
		private string[] sndResources = { "Orbz.snd.5","Orbz.snd.1","Orbz.snd.2","Orbz.snd.3","Orbz.snd.4","Orbz.snd.6","Orbz.snd.7"};
		private string[] pauseSndResources = {"Orbz.snd.p2", "Orbz.snd.p", "Orbz.snd.p3" };
		private const string sndGameOver = "Orbz.snd.gameover";
		private const string sndBackGroundTemp = "temp#bg#orbz.temp";
		private const string sndPauseTemp = "temp#p#orbz.tmp";
		private const string sndGameOverTemp = "temp#go#orbz.tmp";
		private byte[] sndLevelUp;   				//level up sound
		#endregion

		#region Constructor/Destructor code
		public Orbz()
		{
			this.bRunning = false;
			this.bPaused = false;
			this.isDirty = true;
			this.bGameOver = false;
			this.bSound = true;
			
			//form generator component init.
			InitializeComponent();
			miStop.Checked = true;
			
			//add game components to the form
			this.gameArea = new GameGrid(new System.Drawing.Size(16, 24));
			this.srfGrid = new DrawingSurface();
			this.nextArea = new GameGrid(new System.Drawing.Size(5, 5));
			this.srfNext = new DrawingSurface();
			this.srfStatus = new DrawingSurface();
			this.nextUnit = new Unit(nextArea);
			this.activeUnit = new Unit(gameArea);
			this.font = new Font("Arial", 8, FontStyle.Bold); 
			this.strfmt = new StringFormat();
			this.strfmt.Alignment = StringAlignment.Center;
			this.strfmt.LineAlignment = StringAlignment.Center;
			clock.Interval = GameState.ClockSpeed;
			this.MakeOrbImages();
			this.SuspendLayout();
			
			//configure game area
			this.Controls.Add(srfGrid);
			this.srfGrid.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
			this.srfGrid.Location = new System.Drawing.Point(8, 10);
			this.srfGrid.Size = new System.Drawing.Size(320, 480);
			this.srfGrid.Paint += new System.Windows.Forms.PaintEventHandler(this.SrfGrid_Paint);
			this.srfGrid.Initialize();
			this.gridBackGround = new LinearGradientBrush(srfGrid.ClientRectangle, Color.Gold, Color.Black, 53, false);
			
			//configure area to display next unit
			this.Controls.Add(srfNext);
			this.srfNext.BorderStyle = System.Windows.Forms.BorderStyle.None;
			this.srfNext.Location = new System.Drawing.Point(srfGrid.Location.X + 30 + srfGrid.Size.Width, 50);
			this.srfNext.Size = new System.Drawing.Size(100, 80);
			this.srfNext.Paint += new System.Windows.Forms.PaintEventHandler(this.SrfNext_Paint);
			this.srfNext.Initialize();
			this.nextBackGround = new SolidBrush(this.BackColor);

			//configure status area
			this.Controls.Add(srfStatus);
			this.srfStatus.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
			this.srfStatus.Location = new System.Drawing.Point(336,264);
			this.srfStatus.Size = new System.Drawing.Size(148,224);
			this.srfStatus.Paint += new System.Windows.Forms.PaintEventHandler(this.SrfStatus_Paint);
			this.statusBackGround = new LinearGradientBrush(srfStatus.ClientRectangle, Color.DarkGoldenrod, Color.DimGray, 50, false);
			this.srfStatus.Initialize();
		
			this.ResumeLayout(true);
		
			//level up sound
			GameState.LevelUp += new GameEventHandler(Level_Up);
			Stream audio = Assembly.GetEntryAssembly().GetManifestResourceStream("Orbz.snd.bloop");
   			this.sndLevelUp = new byte[audio.Length];
		
			for (int i=0; i<audio.Length; i++)
				this.sndLevelUp[i] = (byte)audio.ReadByte();
		}
		
		#endregion
		
		#region Properties
		
		#endregion
		
		#region Event Code
		//draws the grid squares onto the Drawing Surface before it is flashed
		//to the screen
		void SrfGrid_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
		{
			if (isDirty && !bGameOver)
			{
				//draw the background
				srfGrid.Surface.FillRectangle( gridBackGround, srfGrid.ClientRectangle );
				
				//draw the active unit and grid
				if (bRunning) { 
					DrawUnit( activeUnit, srfGrid );
					DrawGrid( gameArea, srfGrid );
				}
				
				//mark graphics context as clean after a paint
				isDirty = false;
			}
		}

		void SrfNext_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
		{
			//draw the background
			srfNext.Surface.FillRectangle( nextBackGround, srfNext.ClientRectangle );
			
			//draw the unit
			if (bRunning)				
				DrawUnit( nextUnit, srfNext );
		}

		void SrfStatus_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
		{
			System.Text.StringBuilder s = new System.Text.StringBuilder();
			
			//draw the background
			srfStatus.Surface.FillRectangle( statusBackGround, srfStatus.ClientRectangle );
			
			s.Append("Level:  ");
			s.Append(GameState.Level);
			s.Append("\n\n\nScore:  \n\n");
			s.Append(GameState.Score);
			s.Append("\n\nLast Chain:  \n\n");
			s.Append("(" + GameState.ChainCount + ") " + GameState.Chain);
			s.Append("\n\n Max Chain:  \n\n");
			s.Append("(" + GameState.MaxChainCount + ") " + GameState.MaxChain);
			if (bSound)
				s.Append("\n\nSound track " + (this.currentTrack + 1) + " of " + this.sndResources.Length);
			else
				s.Append("\n\nSound  is  disabled");
			s.Append("\nDifficulty: ");
			if (GameState.Difficulty > 0)
				s.Append("+");
			s.Append(GameState.Difficulty);
			
			srfStatus.Surface.DrawString(s.ToString(), font, new SolidBrush(Color.Black), new System.Drawing.RectangleF(0,0,srfStatus.ClientRectangle.Width,srfStatus.ClientRectangle.Height),strfmt);
		}

		//handles the keydown event (moves unit within grid)		
		void Orbz_KeyDown(object sender, System.Windows.Forms.KeyEventArgs e)
		{
			if (bRunning && !bPaused)
			{
				bool bValidate = false;
			
				switch(e.KeyCode)
				{
					case Keys.Down:
						//a failed down shift will add the active
						//unit to the grid - so we need to perform all
						//logic associated with this
						if (!activeUnit.ShiftDown())
						{
							this.ProcessGameCycle();
						}
						bValidate = true;
						break;
					case Keys.Right:
						bValidate = activeUnit.ShiftRight();
						break;
					case Keys.Left:
						bValidate = activeUnit.ShiftLeft();
						break;
					case Keys.A:
						bValidate = activeUnit.RotateLeft();
						break;
					case Keys.S:
						bValidate = activeUnit.RotateRight();
						break;
					case Keys.Up:
						bValidate = activeUnit.RotateRight();
						break;
				}
				
				if (bValidate)
				{
					this.isDirty = true;
					srfGrid.Invalidate();
					srfGrid.Update();
				}
			}
			
			if (e.Control && e.Shift && e.KeyCode == Keys.C){
				GameState.LogToClipboard();
			}
		}
		
		//Start menu item clicked
		void Start_Click(object sender, System.EventArgs e)
		{
			if (!bRunning) 
			{
				//load background sound
				if (bSound) {
					Sound.PlayMidi(this.sndResources[this.currentTrack], sndBackGroundTemp, this.bSndLoopMode);
				}

				gameArea.Clear();
				GameState.Reset();
				this.bGameOver = false;
				activeUnit.Create();
				nextUnit.Create();
				this.bRunning = true;
				this.isDirty = true;
				miStart.Checked = true;
				miStop.Checked = false;
				miDiffUp.Enabled = false;
				miDiffDown.Enabled = false;
				srfGrid.Invalidate();
				srfGrid.Update();
				srfNext.Invalidate();
				srfStatus.Invalidate();
				clock.Start();
			}
		}

		//Stop menu item clicked
		void Stop_Click(object sender, System.EventArgs e)
		{
			if (bRunning) {
				clock.Stop();
				this.bRunning = false;
				miStop.Checked = true;
				miStart.Checked = false;
				miDiffUp.Enabled = true;
				miDiffDown.Enabled = true;
				if (bSound)
					Sound.StopMidi();
			}
		}

		//Pause menu item clicked
		void Pause_Click(object sender, System.EventArgs e)
		{
			if (bRunning)
			{
				if (bPaused)
				{
					if (bSound)
						Sound.PlayMidi( this.sndResources[this.currentTrack], sndBackGroundTemp, this.bSndLoopMode );
					miStart.Checked = true;
					miPause.Checked = false;
					Thread.Sleep(100);
					bPaused = false;
				}
				else
				{
					bPaused = true;
					miStart.Checked = false;
					miPause.Checked = true;
					if (bSound) {
						Sound.PlayMidi(this.pauseSndResources[this.currentPauseTrack],sndPauseTemp, this.bSndLoopMode);
						if (!this.bSndLoopMode)
							this.sndPauseArrayIncrementPosition();
					}
				}
			}
		}	

		//game timer
		void Clock_Tick(object sender, System.EventArgs e)
		{
			//graphics logic
			if (bRunning && !bPaused)
			{
				if (!activeUnit.ShiftDown())
				{
					this.ProcessGameCycle();
				}
				this.isDirty = true;
				srfGrid.Invalidate();
				srfGrid.Update();
				clock.Interval = GameState.ClockSpeed;
			}
		
			//sound logic
			if (bRunning && !this.bSndLoopMode) {
				if (!Sound.MidiActive()) {
					if (bPaused) {
						if (bSound) 
						{
							Sound.PlayMidi( this.pauseSndResources[this.currentPauseTrack], sndPauseTemp, this.bSndLoopMode);
							this.sndPauseArrayIncrementPosition();
						}
					}
					else {
						this.sndArrayIncrementPosition(bSound);
					}
					srfStatus.Invalidate();
					srfStatus.Update();
				}
			}
		}

		//toggles to next background sound
		void Toggle_Click(object sender, System.EventArgs e)
		{		
			if (bSound && !bPaused && bRunning) {
				this.sndArrayIncrementPosition(true);
			}
			else
				this.sndArrayIncrementPosition(false);
		}

		//enables and disables sound
		void SndEnable_Click(object sender, System.EventArgs e)
		{
			if (bSound) {
				Sound.StopMidi();
				miSndEnable.Text = "Enable";
				miSndEnable.Checked = false;
				bSound = false;
			}
			else {
				bSound = true;
				miSndEnable.Text = "Disable";
				miSndEnable.Checked = true;
				if (bRunning) {
					if (bPaused) 
						Sound.PlayMidi(this.pauseSndResources[this.currentPauseTrack], sndPauseTemp, false);
					else
						Sound.PlayMidi(this.sndResources[this.currentTrack], sndBackGroundTemp, true);
				}
			}
			srfStatus.Invalidate();
			srfStatus.Update();
		}

		//changes sound mode (loop current track or increment to next track)
		void SndMode_Change(object sender, System.EventArgs e)
		{
			Sound.StopMidi();
			if (this.bSndLoopMode) {
				this.bSndLoopMode = false;
				miSndMode.Text = "Go to Loop Mode";
			}
			else {
				this.bSndLoopMode = true;
				miSndMode.Text = "Go To Pipeline Mode";	
			}
			
			if(bRunning) {
				if (bPaused) {
					Sound.PlayMidi( this.pauseSndResources[this.currentPauseTrack], sndPauseTemp, this.bSndLoopMode);
					this.sndPauseArrayIncrementPosition();					
				}
				else {
					Sound.PlayMidi( this.sndResources[this.currentTrack], sndBackGroundTemp, this.bSndLoopMode);
				}
			}
		}

		//increase difficulty
		void DiffUp_Click(object sender, System.EventArgs e)
		{
			GameState.AdjustDifficulty( true );
			srfStatus.Invalidate();
			srfStatus.Update();
		}
		
		//decreaes difficulty
		void DiffDown_Click(object sender, System.EventArgs e)
		{
			GameState.AdjustDifficulty( false );
			srfStatus.Invalidate();
			srfStatus.Update();
		}

		//launch credits & info etc...dialog/etc...
		void About_Click(object sender, System.EventArgs e)
		{
			MessageBox.Show("            Author - John C. Roam\n\nSound clips are MIDI versions of tracks from the\nfollowing games:  Chrono Trigger, Final Fantasy 7, \nand Secret of Mana - authors unknown.\n\nI know - I'm a freaking geek - get over it.", "About", MessageBoxButtons.OK, MessageBoxIcon.Asterisk, MessageBoxDefaultButton.Button1);
		}

		//exit the application
		void Exit_Click(object sender, System.EventArgs e)
		{
			this.Close();	
		}

		//cleanup when application closes
		void Form_Close(object sender, System.EventArgs e)
		{
			Sound.StopMidi();
			File.Delete(sndBackGroundTemp);
			File.Delete(sndPauseTemp);
			File.Delete(sndGameOverTemp);
		}
		
		//level up logic
		void Level_Up(object sender, System.EventArgs e)
		{
			Thread t = new Thread(new ThreadStart(PlayLevelUpSound));
			t.IsBackground = true;
			t.Start();
			Thread.Sleep(0);
		}
		#endregion
		
		#region Public Methods
		
		#endregion
		
		#region Private Methods
		
		//swaps active unit with next unit
		//ends the game if swap fails (grid is full)
		//basically responsible for everything that happens when
		//a block can't go any further...
		private void ProcessGameCycle()
		{
			clock.Stop();

			//swap out active unit
			this.SwapUnits();

			//blow the orbs and clear out the grids...	
			this.BlowOrbs();
		
			//handle game over possibility
			if (!activeUnit.ValidateLocation()) {
				this.Stop_Click(this, null);
				DrawUnit(activeUnit, srfGrid);
				srfGrid.Surface.DrawString("GAME OVER", new Font("Arial", 35) , new SolidBrush(Color.Black), new System.Drawing.RectangleF(0,0,srfGrid.ClientRectangle.Width,srfGrid.ClientRectangle.Height),strfmt);
				this.bGameOver = true;
				srfGrid.Invalidate();
				srfGrid.Update();
				if (bSound) {
					Sound.PlayMidi(sndGameOver, sndGameOverTemp, true);
				}
			}
			else
				clock.Start();
		}
		
		//loop through the grid and blow all the orbs
		private void BlowOrbs()
		{
			bool bBlow = false;
			int blo, pts;
			for(int y=gameArea.Size.Height-1; y>=0; y--)
			{
				for(int x=0; x<gameArea.Size.Width; x++)
				{
					if (gameArea.GetOrbCount(x,y)==0)
					{
						blo = 0;
						pts = 0;
						this.Blow(x,y,(gameArea.GetSpace(x,y)),ref blo,ref pts);
						GameState.IncrementScore(pts);
						GameState.Chain = pts;
						GameState.ChainCount = blo;
						srfStatus.Invalidate();
						srfStatus.Update();
						bBlow = true;
					}
				}
			}
			if (bBlow)
				this.ClearBlown();
		}
		
		//blow a block in the grid and all bordering blocks that are of the specified
		//type return the amount of points racked up in process
		private void Blow( int x, int y, SubUnit.SubUnitType t, ref int blowcount, ref int pts )
		{
			//for scoring
			pts += (++blowcount)*GameState.BasePoints;

			//clear the current space
			//the update/invalidate and sleep calls make the "flash" occur
			//adjusting the time the thread sleeps speeds up/slows down the "flash"
			gameArea.FillSpace( -1, SubUnit.SubUnitType.clearing, x, y );
			this.isDirty = true;
			srfGrid.Invalidate();
			srfGrid.Update();
			Thread.Sleep(25);
			
			//check and blow surrounding spaces
			if ((y!=0) && ((gameArea.GetSpace(x,y-1)==t) || (gameArea.GetSpace(x,y-1)==t+5)))
				this.Blow(x,y-1,t, ref blowcount, ref pts);
			if ((x!=0) && ((gameArea.GetSpace(x-1,y)==t) || (gameArea.GetSpace(x-1,y)==t+5)))
				this.Blow(x-1,y,t, ref blowcount, ref pts);
			if ((y!=gameArea.Size.Height-1) && ((gameArea.GetSpace(x,y+1)==t) || (gameArea.GetSpace(x,y+1)==t+5)))
				this.Blow(x,y+1,t, ref blowcount, ref pts);
			if ((x!=gameArea.Size.Width-1) && ((gameArea.GetSpace(x+1,y)==t) || (gameArea.GetSpace(x+1,y)==t+5)))
				this.Blow(x+1,y,t, ref blowcount, ref pts);
		}
		
		//clears out all blown blocks
		private void ClearBlown()
		{			
			//mark spaces as fully cleared
			for (int i=0; i<gameArea.Size.Width; i++)
				for (int j=0; j<gameArea.Size.Height; j++)
					if (gameArea.GetSpace(i,j)==SubUnit.SubUnitType.clearing)
						gameArea.FillSpace( -1, SubUnit.SubUnitType.blank, i, j);
			
			//redraw
			this.isDirty = true;
			srfGrid.Invalidate();
			
			//now run through and start downshifting the blocks in all
			//the columns to fill any gaps that are there
			for (int x=0; x<gameArea.Size.Width; x++)
				this.DownShiftColumn(x);
		}	
		
		//shifts all the blocks in a column down one at a time until all 
		//blank gaps are filled and blocks are as far down as they can go
		private void DownShiftColumn( int x )
		{
			int z;
			for (int y=gameArea.Size.Height-2; y>=0; y--)
			{
				z = y+1;
				if (gameArea.GetSpace(x,y) != SubUnit.SubUnitType.blank) 
				{
					while (z<gameArea.Size.Height && gameArea.GetSpace(x,z)==SubUnit.SubUnitType.blank)
					{
						gameArea.FillSpace( gameArea.GetOrbCount(x,z-1), gameArea.GetSpace(x,z-1), x, z );
						gameArea.FillSpace( -1, SubUnit.SubUnitType.blank, x, z-1 );
						z++;
						this.isDirty = true;
						srfGrid.Invalidate();
						srfGrid.Update();
						Thread.Sleep(15);
					}
				}
			}
		}
		
		//swap the active unit with the next unit
		private void SwapUnits()
		{
			Unit t = activeUnit;
			activeUnit = nextUnit;
			nextUnit = t;
			nextUnit.Grid = nextArea;
			nextUnit.Create();
			activeUnit.Grid = gameArea;
			activeUnit.Calibrate();
			srfNext.Invalidate();
			srfNext.Update();		
		}
		
		//draws a unit to a drawing surface
		private void DrawUnit( Unit u, DrawingSurface d)
		{
			SubUnit s;
			for (int i=0; i<4; i++)
			{
				s = u.GetSubUnit(i);
				d.Surface.DrawImage(ilUnits.Images[GetIlIndex(s.Type)], s.Location.X*20, s.Location.Y*20, 20, 20 );
				if (s.OrbCount>-1)
					d.Surface.DrawString(""+s.OrbCount, font, new SolidBrush(Color.Black),(s.Location.X*20)+11, (s.Location.Y*20)+11, strfmt);
			}	
		}
		
		//draws a grid to the drawing surface
		private void DrawGrid( GameGrid g, DrawingSurface s)
		{
			int index;
			
			for( int i=0; i<g.Size.Width; i++ )
			{ 
				for (int j=0; j<g.Size.Height; j++)
				{
					index = GetIlIndex(g.GetSpace(i,j));
					if (index >= 0)
						s.Surface.DrawImage(ilUnits.Images[index], i*20, j*20, 20, 20);
					if (g.GetOrbCount(i,j)>-1)
						s.Surface.DrawString(""+g.GetOrbCount(i,j), font, new SolidBrush(Color.Black),(i*20)+11, (j*20)+11, strfmt);
				}
			}
		}
		
		//determines what image to draw from the image collection
		//based on a SubUnit type
		private int GetIlIndex( SubUnit.SubUnitType s )
		{
			int index;
			
			switch(s)
			{
				case SubUnit.SubUnitType.bSq:
					index = 0;
					break;
				case SubUnit.SubUnitType.gSq:
					index = 1;
					break;
				case SubUnit.SubUnitType.pSq:
					index = 2;
					break;
				case SubUnit.SubUnitType.rSq:
					index = 3;
					break;
				case SubUnit.SubUnitType.ySq:
					index = 4;
					break;
				case SubUnit.SubUnitType.clearing:
					index = 5;
					break;
				case SubUnit.SubUnitType.bCr:
					index = 6;
					break;
				case SubUnit.SubUnitType.gCr:
					index = 7;
					break;
				case SubUnit.SubUnitType.pCr:
					index = 8;
					break;
				case SubUnit.SubUnitType.rCr:
					index = 9;
					break;
				case SubUnit.SubUnitType.yCr:
					index = 10;
					break;
				default:
					index = -1;
					break;
			}
			
			return index;
		}
		
		//make orb images (MS Paint sucks and I'm an artistic cripple incapable of making pretty orbs -- 
		//so I got annoyed and decided to just do it in code since I don't have software to do real graphics
		private void MakeOrbImages()
		{
			Bitmap b = new Bitmap(20,20);
			Color c;
			for (int i=0; i<5; i++)
			{
				Graphics.FromImage(b).FillRectangle(new SolidBrush(Color.Transparent), 0, 0, 20, 20);
				Graphics.FromImage(b).DrawRectangle(new Pen(Color.Black, 2), 1, 1, 19, 19);
				Graphics.FromImage(b).FillEllipse(new SolidBrush(Color.Black), 0, 0, 20, 20);
				switch (i)
				{
					case 0:
						c = Color.DodgerBlue;
						break;
					case 1:
						c = Color.Green;
						break;
					case 2:
						c = Color.Purple;
						break;
					case 3:
						c = Color.Red;
						break;
					case 4:
						c = Color.Yellow;
						break;
					default:
						c = Color.Transparent;
						break;						
				}
				Graphics.FromImage(b).FillEllipse(new SolidBrush(c), 2, 2, 16, 16);
				ilUnits.Images.Add(b);
			}
			b.Dispose();
		}

		private void sndArrayIncrementPosition( bool play )
		{
			this.currentTrack++;
			if (this.currentTrack>=this.sndResources.Length)
				this.currentTrack = 0;
			if (play)
				Sound.PlayMidi(this.sndResources[this.currentTrack], sndBackGroundTemp, this.bSndLoopMode);
			srfStatus.Invalidate();
			srfStatus.Update();
		}
		
		private void sndPauseArrayIncrementPosition()
		{
			this.currentPauseTrack++;
			if (this.currentPauseTrack>=this.pauseSndResources.Length)
				this.currentPauseTrack = 0;
		}

		private void PlayLevelUpSound() {
			Sound.PlayByteArray(this.sndLevelUp, true, false, false, false);
		}
		#endregion
				
		[STAThread]
		public static void Main(string[] args)
		{
			try {	
				Application.Run(new Orbz());
			}
			catch (Exception e) {
				//one of these days i might make this write the error log somewhere....
				GameState.AppendToLog("Full crash to main" + e.Message);
				Sound.StopMidi();
				File.Delete(sndBackGroundTemp);
				File.Delete(sndPauseTemp);
				File.Delete(sndGameOverTemp);
				GameState.LogToClipboard();
			}
		}
		
		#region Windows Forms Designer generated code
		/// <summary>
		/// This method is required for Windows Forms designer support.
		/// Do not change the method contents inside the source code editor. The Forms designer might
		/// not be able to load this method if it was changed manually.
		/// </summary>
		private void InitializeComponent() {
			this.components = new System.ComponentModel.Container();
			System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(Orbz));
			this.ilUnits = new System.Windows.Forms.ImageList(this.components);
			this.miDiffDown = new System.Windows.Forms.MenuItem();
			this.miSndMode = new System.Windows.Forms.MenuItem();
			this.miStart = new System.Windows.Forms.MenuItem();
			this.clock = new System.Windows.Forms.Timer(this.components);
			this.miStop = new System.Windows.Forms.MenuItem();
			this.menuItem4 = new System.Windows.Forms.MenuItem();
			this.menuItem6 = new System.Windows.Forms.MenuItem();
			this.mainMenu = new System.Windows.Forms.MainMenu();
			this.menuItem = new System.Windows.Forms.MenuItem();
			this.miPause = new System.Windows.Forms.MenuItem();
			this.menuItem3 = new System.Windows.Forms.MenuItem();
			this.menuItem2 = new System.Windows.Forms.MenuItem();
			this.miSndEnable = new System.Windows.Forms.MenuItem();
			this.menuItem1 = new System.Windows.Forms.MenuItem();
			this.miDiffUp = new System.Windows.Forms.MenuItem();
			// 
			// ilUnits
			// 
			this.ilUnits.ColorDepth = System.Windows.Forms.ColorDepth.Depth16Bit;
			this.ilUnits.ImageSize = new System.Drawing.Size(20, 20);
			this.ilUnits.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("ilUnits.ImageStream")));
			this.ilUnits.TransparentColor = System.Drawing.Color.Transparent;
			// 
			// miDiffDown
			// 
			this.miDiffDown.Index = 1;
			this.miDiffDown.Shortcut = System.Windows.Forms.Shortcut.F8;
			this.miDiffDown.Text = "Difficulty Down";
			this.miDiffDown.Click += new System.EventHandler(this.DiffDown_Click);
			// 
			// miSndMode
			// 
			this.miSndMode.Index = 2;
			this.miSndMode.Shortcut = System.Windows.Forms.Shortcut.F6;
			this.miSndMode.Text = "Go to Loop Mode";
			this.miSndMode.Click += new System.EventHandler(this.SndMode_Change);
			// 
			// miStart
			// 
			this.miStart.Index = 0;
			this.miStart.Shortcut = System.Windows.Forms.Shortcut.F1;
			this.miStart.Text = "Start";
			this.miStart.Click += new System.EventHandler(this.Start_Click);
			// 
			// clock
			// 
			this.clock.Interval = 1000;
			this.clock.Tick += new System.EventHandler(this.Clock_Tick);
			// 
			// miStop
			// 
			this.miStop.Index = 2;
			this.miStop.Shortcut = System.Windows.Forms.Shortcut.F3;
			this.miStop.Text = "Stop";
			this.miStop.Click += new System.EventHandler(this.Stop_Click);
			// 
			// menuItem4
			// 
			this.menuItem4.Index = 1;
			this.menuItem4.Shortcut = System.Windows.Forms.Shortcut.F5;
			this.menuItem4.Text = "Toggle";
			this.menuItem4.Click += new System.EventHandler(this.Toggle_Click);
			// 
			// menuItem6
			// 
			this.menuItem6.Index = 2;
			this.menuItem6.Shortcut = System.Windows.Forms.Shortcut.F9;
			this.menuItem6.Text = "About";
			this.menuItem6.Click += new System.EventHandler(this.About_Click);
			// 
			// mainMenu
			// 
			this.mainMenu.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					 this.menuItem,
																					 this.menuItem2,
																					 this.menuItem1});
			// 
			// menuItem
			// 
			this.menuItem.Index = 0;
			this.menuItem.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					 this.miStart,
																					 this.miPause,
																					 this.miStop,
																					 this.menuItem3});
			this.menuItem.Text = "Game";
			// 
			// miPause
			// 
			this.miPause.Index = 1;
			this.miPause.Shortcut = System.Windows.Forms.Shortcut.F2;
			this.miPause.Text = "Pause";
			this.miPause.Click += new System.EventHandler(this.Pause_Click);
			// 
			// menuItem3
			// 
			this.menuItem3.Index = 3;
			this.menuItem3.Shortcut = System.Windows.Forms.Shortcut.CtrlX;
			this.menuItem3.Text = "Exit";
			this.menuItem3.Click += new System.EventHandler(this.Exit_Click);
			// 
			// menuItem2
			// 
			this.menuItem2.Index = 1;
			this.menuItem2.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					  this.miSndEnable,
																					  this.menuItem4,
																					  this.miSndMode});
			this.menuItem2.Text = "Cheesy Sound";
			// 
			// miSndEnable
			// 
			this.miSndEnable.Index = 0;
			this.miSndEnable.Shortcut = System.Windows.Forms.Shortcut.F4;
			this.miSndEnable.Text = "Disable";
			this.miSndEnable.Click += new System.EventHandler(this.SndEnable_Click);
			// 
			// menuItem1
			// 
			this.menuItem1.Index = 2;
			this.menuItem1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
																					  this.miDiffUp,
																					  this.miDiffDown,
																					  this.menuItem6});
			this.menuItem1.Text = "Other";
			// 
			// miDiffUp
			// 
			this.miDiffUp.Index = 0;
			this.miDiffUp.Shortcut = System.Windows.Forms.Shortcut.F7;
			this.miDiffUp.Text = "Difficulty Up";
			this.miDiffUp.Click += new System.EventHandler(this.DiffUp_Click);
			// 
			// Orbz
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(64)), ((System.Byte)(64)), ((System.Byte)(64)));
			this.ClientSize = new System.Drawing.Size(492, 516);
			this.MaximizeBox = false;
			this.MaximumSize = new System.Drawing.Size(500, 550);
			this.Menu = this.mainMenu;
			this.MinimumSize = new System.Drawing.Size(500, 550);
			this.Name = "Orbz";
			this.Text = "Orbz";
			this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.Orbz_KeyDown);
			this.Closed += new System.EventHandler(this.Form_Close);

		}
		#endregion										
		
	}
}
