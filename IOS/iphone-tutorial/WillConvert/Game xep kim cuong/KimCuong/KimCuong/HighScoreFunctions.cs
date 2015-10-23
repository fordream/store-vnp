using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization.Formatters.Binary;
using System.IO;
using System.Windows.Forms;

namespace KimCuong
{
    class HighScoreFunctions
    {
        public static string filename = "kimcuong.dat";
        public static PlayerList readHighScores()
        {
            if (!File.Exists(filename))
                writeDefaultHighScores();
            //sau khi viet du lieu le filename ta tiep tuc cong viec doc du lieu
            Stream s = File.Open(filename, FileMode.Open);
            BinaryFormatter b = new BinaryFormatter();
            //nho ep kieu 
            PlayerList pl = (PlayerList)b.Deserialize(s);
            s.Close();
            return pl;
        }

        public static void writeDefaultHighScores()
        {
            Player[] p = new Player[10];
            //khoi tao 10 nguoi player ban dau
            for (int i = 0; i < 10; i++)
            {
                p[i] = new Player("An Vu ", 0);
            }
            //goi ham viet danh sach 10 nguoi choi vua khoi tao tren len filename
            //dua mang danh sach 10 nguoi choi ve kieu Layer
            writeHighScores(new PlayerList(p));
        }

        public static void writeHighScores(PlayerList pl)
        {   //khai bao mot luong den file
            Stream s = File.Open(filename, FileMode.Create);
            //tao oi tuong BinaryFormatter de su dung cho qua trinh tuan tu hoa nhi phan
            BinaryFormatter b = new BinaryFormatter();
            //viet len danh sach 10 nguoi choi vua khoi tao o tren len filename
            b.Serialize(s, pl);
            s.Close();
        }
        //ham sap xep
        public static void insert(Player p)
        {
            PlayerList pl = readHighScores();
            int i, j;
            if (p.scores < pl.list[9].scores)
            {
                MessageBox.Show("Diem Cua Ban Khong Du De Vao Top", "Thong Bao", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            for (i = 0; i < 10; i++)
                if (Player.less(pl.list[i], p))
                    break;
            for (j = 9; j >= i+1; j--)
                pl.list[j] = pl.list[j - 1];
            pl.list[i] = p;
            writeHighScores(pl);
        }

    }
}
