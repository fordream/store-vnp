using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KimCuong
{
    [Serializable()]
    class Player
    {
        public string name;
        public int scores;
        public Player(string name, int scores)
        {
            this.name = name;
            this.scores = scores;
        }
        //ham so sanh diem so giua hai nguoi choi
        //ham so sanh nay co kieu bool
        public static bool less(Player p1, Player p2)
        {
            if (p1.scores != p2.scores)
                return p1.scores < p2.scores;
            return p1.scores < p2.scores;
        }
        //ta dua tat ca thong tin cua nguoi choi vao 1 mang cac chuoi
        public string[] convert(int index)
        {
            string[] s = new string[3];
            //moi thong tin cua nguoi choi la mot chuoi
            s[0] = index.ToString();
            s[1] = name;
            s[2] = scores.ToString();
            return s;
        }
    }
}
