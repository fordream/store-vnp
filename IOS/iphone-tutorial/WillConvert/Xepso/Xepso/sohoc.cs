using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Windows.Forms;
using System.Drawing.Imaging;
namespace Xepso
{
    [Serializable()]
    class sohoc
    {
        public int postion;
        public int nvalue;
        Point p = new Point();
        Bitmap image = new Bitmap(64, 64, PixelFormat.Format32bppArgb);
        int m1;
        public void setloc(int loc)
        {
            m1 = (int)Math.Sqrt(Form1.m); 
            int col = loc / m1;
            int row = loc - (col * m1);
            row *= 64;
            col *= 64;
            p = new Point(row, col);
            postion = loc;
        }
        
        public sohoc() { }
        public sohoc(int so)
        {
            string str = so.ToString();
            Graphics g = Graphics.FromImage(image);
            Font f = new Font("Impact", 15, FontStyle.Bold);
            SizeF s = g.MeasureString(str, f);
            g.FillRectangle(Brushes.Bisque, 0, 0, image.Width, image.Height);
            g.DrawRectangle(Pens.Black, 0, 0, image.Width, image.Height);
            g.DrawString(str, f, Brushes.Black,(image.Width - s.Width) / 2,(image.Height - s.Height) / 2);
            nvalue = so;
            f.Dispose();
            g.Dispose();

        }
        
        public void draw(Graphics g)
        {
            if (nvalue != Form1.m-1)
            {
                g.DrawImage(image, p);
            }
            
        }
        
    }
}
