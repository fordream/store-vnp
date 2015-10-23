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
        private void LoadGame(object sender, EventArgs e)
        {
            tg_cd.Stop();
            tg_progress.Stop();
            string load = null;
            TextReader tr = new StreamReader(System.Environment.CurrentDirectory + "\\save.txt");
            load = tr.ReadToEnd();
            string[] split = load.Split(new Char[] { '\n', ' ', '\r' });
            int n = split.Length;
            int j = 1;
            for (int i = 0; i < n; i++)
            {
                if (split[i] == "")
                {
                    for (int z = i; z < n - 1; z++)
                    {
                        split[z] = split[z + 1];
                    }
                    split[n - 1] = 0 + "";
                    n--;
                    i--;
                }
            }
            for (int a = 0; a < 8; a++)
                for (int b = 0; b < 8; b++)
                {
                    mokimcuong.kimcuong[a, b].SoMau = int.Parse(split[j++]);
                    mokimcuong.kimcuong[a, b].XuLy = int.Parse(split[j++]);
                    mokimcuong.kimcuong[a, b].Enabled = true;
                }
            j = 129;
            diem = int.Parse(split[j++]);
            level = int.Parse(split[j++]);
            label3.Text = level + 1 + "";
            textBox1.Text = diem + "";
            progressBar1.Value = int.Parse(split[j++]);
            XuatHinh();
        }
    }
}
