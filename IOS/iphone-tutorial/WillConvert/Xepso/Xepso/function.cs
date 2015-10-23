using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization;
using System.Collections;


namespace Xepso
{

    [Serializable()]
    class function
    {
        public static string filename = "score.bin";
        public static playerlist readhigh()
        {
            if (!File.Exists(filename)) writedefault();
            Stream file = File.Open(filename, FileMode.Open);
            BinaryFormatter b = new BinaryFormatter();
            playerlist pl = (playerlist)b.Deserialize(file);
            file.Close();
            return pl;


        }
        public static void write(playerlist pl1)
        {
            Stream s = File.Open(filename,FileMode.Create);
            BinaryFormatter b= new BinaryFormatter();
            b.Serialize(s, pl1);
            s.Close();
        }
        public static void writedefault()
        {
            player[] p = new player[10];
            for (int i = 0; i < 10; i++)
            {
                p[i] = new player("Victory", 10, 0, 1, 0);
            }
            write(new playerlist(p));
        }
        public static void insert(player p)
        {
            playerlist pl = readhigh();
            int i, j;
            for (i = 0; i < 10; i++)
                if (player.sapxep(p, pl.list[i])) break;
            for (j = 9; j >= i + 1; j--)
                pl.list[j] = pl.list[j - 1];
            pl.list[i] = p;
            write(pl);
        }
    }
}
