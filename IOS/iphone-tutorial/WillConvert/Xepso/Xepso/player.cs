using System;
using System.Collections.Generic;
using System.Text;

namespace Xepso
{
    [Serializable()]
    class player
    {
        string name;
        int diem;
        int hour;
        int mili;
        int second;
        public player(string n, int d, int h, int m, int s)
        {
            this.name = n.ToString();
            this.diem = d;
            this.hour = h;
            this.mili = m;
            this.second = s;
        }
        public int tinhgio()
        {
            return hour * 3600 + mili * 60 + second;

        }
        public static bool sapxep(player p1, player p2)
        {
            if (p1.diem != p2.diem)
                return p1.diem < p2.diem;
            return p1.tinhgio() < p2.tinhgio();
        }
        public string[] convert(int index)
        {
            string[] s = new string[4];
            s[0] = index.ToString();
            s[1] = name;
            s[2] = diem.ToString(); ;
            s[3] = hour.ToString() + ":";
            if (mili < 10) s[3] += "0";
            s[3] += mili.ToString() + ":";

            if (second < 10) s[3] += "0";
            s[3] += second.ToString();

            return s;
        }

    }
}
