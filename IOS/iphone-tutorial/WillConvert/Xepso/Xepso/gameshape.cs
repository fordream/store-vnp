using System;
using System.Collections.Generic;
using System.Text;

namespace Xepso
{
    [Serializable()]
    class gameshape
    {
        public  sohoc[] array;
        public  int hours, milis, seconds;
        public  int mov;
        public  int len;
        public gameshape()
        {
        }
        public gameshape(sohoc[] mang,int dem, int g, int p, int s,int l)
        {
            array = mang;
            this.len = l;
            this.hours = g;
            this.milis = p;
            this.seconds = s;
            this.mov = dem;
        }
    }
}
