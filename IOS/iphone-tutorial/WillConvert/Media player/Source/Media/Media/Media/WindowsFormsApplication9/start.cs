using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
//using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Drawing.Drawing2D;

namespace WindowsFormsApplication9
{
    public partial class start : Form
    {
        public start()
        {
            InitializeComponent();
            GraphicsPath shape;
            shape = new GraphicsPath();
            
            shape.AddEllipse(20, 43, 230, 230);

            this.Size = new System.Drawing.Size(1000, 1000);
            this.Region = new System.Drawing.Region(shape);
            
            this.Show();
            
            this.BackgroundImage = global::WindowsFormsApplication9.Properties.Resources.Start;
        }
        private void start_Load(object sender, System.EventArgs e)
        {
            this.Location = new Point(SystemInformation.PrimaryMonitorSize.Width/2 - 150, 
                SystemInformation.PrimaryMonitorSize.Height/2 - 150);
        }
    }
}
