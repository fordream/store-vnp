using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;

namespace GamesSnake
{
    public class ThucAn
    {
        private Point toaDo;
        public Point ToaDo { get { return toaDo; } }    
        private Graphics g;
        Random rd=new Random();
        public ThucAn(Graphics gr)
        {
            g = gr;
            toaDo.X = rd.Next(50);
            toaDo.Y = rd.Next(50);
        }
        //public void draw()
        //{
        //    render.Draw(g, new Point(toaDo.X * 10, toaDo.Y * 10), Pens.White, Brushes.White);
        //    toaDo.X = rd.Next(50);
        //    toaDo.Y = rd.Next(50);
        //    render.Draw(g, new Point(toaDo.X*10,toaDo.Y*10), Pens.Orange, Brushes.YellowGreen);
        //}
        public void redraw()
        {
            render.Draw(g, new Point(toaDo.X * 10, toaDo.Y * 10), Pens.Blue, Brushes.Orange);
        }
        public Point TaoDiem()
        {
            toaDo.X = rd.Next(50);
            toaDo.Y = rd.Next(50);
            return toaDo;
        }
    }
}
