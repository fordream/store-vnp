using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;
using Microsoft.VisualBasic.FileIO;

namespace KimCuong
{
    public partial class FormMain
    {
        private void SaveGame(object sender, EventArgs e)
        {
            string save = 0 + "\r\n";
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                {
                    save = save + " " + mokimcuong.kimcuong[i, j].SoMau + " " + mokimcuong.kimcuong[i, j].XuLy + "\r\n";
                }
            save = save + diem + "\r\n" + level + "\r\n" + progressBar1.Value + "\r\n";

            File.WriteAllText(System.Environment.CurrentDirectory + "\\save.txt", save);


        }
    }
}
