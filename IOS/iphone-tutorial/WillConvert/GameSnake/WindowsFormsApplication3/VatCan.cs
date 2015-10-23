using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;
using System.IO;

namespace GamesSnake
{
    public class VatCan
    {
        Point[] vc = new Point[50 * 50];
        int max=0;
        Graphics g;
        public VatCan(Graphics gr)
        {
            g = gr;
        }
        public Point[] vitri { get { return vc; } }
      // string path = "level2.lev";
       public void DocFile(string path)
        {
            try
            {
                using (StreamReader sr = new StreamReader(path))
                {
                    for (int i = 0; i < 50; i++)
                    {

                        for (int j = 0; j < 50; j++)
                            if (sr.Read() == 49)
                            {
                                vc[max] = new Point(j, i);
                                max++;
                            }
                        sr.Read(); sr.Read();
                    }
                }
            }catch
            {
                System.Windows.Forms.MessageBox.Show("tải level thất bại :(");
            }
        }
        public void draw()
        {
            for (int i = 0; i < max; i++)
                render.Draw(g, new Point(vc[i].X*10,vc[i].Y*10), Pens.LightCyan, Brushes.MediumSeaGreen);
        }
    }
}
