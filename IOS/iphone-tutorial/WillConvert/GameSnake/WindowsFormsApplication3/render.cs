using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;
using System.Drawing.Drawing2D;

namespace GamesSnake
{
    static class render
    {
        /// <summary>
        /// độ rộng của mổi nút và góc làm tròn
        /// </summary>
         private const int width = 10;
         private const int radius = 2;
       /// <summary>
         /// vẽ các đường thân rắn dưới dạng hình  vuông làm tròn góc
       /// </summary>
       /// <param name="g">nơi dùng để vẽ lên</param>
       /// <param name="p">điểm vẽ hình </param>
       /// <param name="pen">màu sắc khung</param>
       /// <param name="brush">màu sắc tô bên trong</param>
         public static void Draw(Graphics g, Point p, Pen pen,Brush brush)
         {
             try
             {
                 GraphicsPath gp = new GraphicsPath();
                 gp.AddLine(p.X + radius, p.Y, p.X + width - (radius * 2), p.Y);
                 gp.AddArc(p.X + width - (radius * 2), p.Y, radius * 2, radius * 2, 270, 90);
                 gp.AddLine(p.X + width, p.Y + radius, p.X + width, p.Y + width - (radius * 2));
                 gp.AddArc(p.X + width - (radius * 2), p.Y + width - (radius * 2), radius * 2, radius * 2, 0, 90);
                 gp.AddLine(p.X + width - (radius * 2), p.Y + width, p.X + radius, p.Y + width);
                 gp.AddArc(p.X, p.Y + width - (radius * 2), radius * 2, radius * 2, 90, 90);
                 gp.AddLine(p.X, p.Y + width - (radius * 2), p.X, p.Y + radius);
                 gp.AddArc(p.X, p.Y, radius * 2, radius * 2, 180, 90);
                 gp.CloseFigure();
                 g.FillPath(brush, gp);
                 g.DrawPath(pen, gp);
                 gp.Dispose();
             }
             catch { }
         }
    }
}
