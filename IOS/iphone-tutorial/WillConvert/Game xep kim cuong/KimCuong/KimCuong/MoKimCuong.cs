using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace KimCuong
{
    class MoKimCuong
    {
        public KimCuong[,] kimcuong = new KimCuong[8, 8];
        public MoKimCuong()
        {
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    kimcuong[i, j] = new KimCuong();
                    kimcuong[i, j].BackColor = Color.Black;
                    kimcuong[i, j].Location = new Point(10 + 85 * j, 10 + 85 * i);
                    kimcuong[i, j].Doc = i;
                    kimcuong[i, j].Ngang = j;
                    kimcuong[i, j].SoMau = 0;
                    kimcuong[i, j].SoTT = i * 8 + j;
                }
            }

        }

    }//MoKimcuong
}
