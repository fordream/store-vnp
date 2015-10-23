
namespace Orbz
{
	using System;
	using System.Drawing;
	using System.Windows.Forms;
	
	public class DrawingSurface : System.Windows.Forms.Panel
	{
		
		#region Variables, etc.
		private Graphics surface;
		private Bitmap memImage;
		
		private bool bInit;
		#endregion
		
		#region Constructors/Destructors, etc..		
		public DrawingSurface() : base()
		{			
			bInit = false;
		}
		
		~DrawingSurface() 
		{
			this.surface.Dispose();
			this.memImage.Dispose();
			surface = null;
			memImage = null;
		}
		#endregion
	
		#region Properties
		//allow access to the drawing surface (for drawing of course)
		public Graphics Surface
		{
			get
			{
				return surface;
			}
		}
		#endregion
	
		#region Public Methods
		//sets up our "in-memory" drawing surface so its the same size
		//as the container it will be flushed into - also sets the background color (hard coded for now)
		public void Initialize()
		{
			memImage = new Bitmap(base.ClientRectangle.Width, base.ClientRectangle.Height);
			surface = Graphics.FromImage(memImage);
			bInit = true;
		}
		#endregion 
		
		#region Private Methods
		//override base class logic for painting the background
		protected override void OnPaintBackground( PaintEventArgs pevent )
		{
			//Do nothing.
			//We do not want to paint the background -
			//as we will draw this manually to memImage before it is flushed
			//to the screen.
		}	

		//override base class logic for painting
		//here we call the base (to signal the paint event which will allow
		//for the "in-memory" surface to be drawn too) and then flush the 
		//buffer to the screen.  This will allow us to reduce any "flicker" during
		//times of heavy painting
		protected override void OnPaint( PaintEventArgs e )
		{
			if (bInit)
			{
				//call base logic (fires paint event - where memImage can be modified)
				base.OnPaint( e );
				
				//flush memImage to the screen
				e.Graphics.DrawImage(memImage, base.ClientRectangle, 0, 0, base.ClientRectangle.Width, base.ClientRectangle.Height, GraphicsUnit.Pixel);
			}
			else
				throw new Exception("DrawingSurface.Initialize() must be called before paint may occur.");
		}
		#endregion
	}
}
