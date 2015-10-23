using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Media;
using System.Threading;

namespace KimCuong
{
    public partial class FormMain
    {
        public int level = 0;
        public int speed = 0;
        private void NewGame(object sender, EventArgs e)
        {
            cout = 0;
            //AM THANH GET READY
            Thread a_getready = new Thread(new ThreadStart(A_getreadly));
            a_getready.Start();
          
            foreach (KimCuong kc in mokimcuong.kimcuong)
            {
                kc.Enabled = true;
            }
            label3.Text = 0 + "";
            textBox1.Text = ("0");
            diem = 0;
            progressBar1.Value = 180;
            // tg_progress.Start();
            Random t = new Random();
            int x = 0;
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                {
                    x = t.Next(1, 8);
                    mokimcuong.kimcuong[i, j].SoMau = x;
                    mokimcuong.kimcuong[i, j].XuLy = 0;
                    mokimcuong.kimcuong[i, j].TrangThai = false;
                    XuatHinh();
                }
            bool a;
            do
            {
                a = false;

                #region xu ly trung hang ngang
                for (int hng = 0; hng < 8; hng++)
                {
                    for (int j = 1; j < 7; j++)
                    {
                        if (mokimcuong.kimcuong[j - 1, hng].SoMau == mokimcuong.kimcuong[j, hng].SoMau && mokimcuong.kimcuong[j, hng].SoMau == mokimcuong.kimcuong[j + 1, hng].SoMau)
                        {
                            if (hng == 0)//neu nhu la hang ngang dau tien
                            {
                                for (int z = 1; z < 8; z++)
                                {
                                    if (mokimcuong.kimcuong[j - 1, hng].SoMau != z && mokimcuong.kimcuong[j + 1, hng].SoMau != z && mokimcuong.kimcuong[j, hng + 1].SoMau != z)
                                    {
                                        mokimcuong.kimcuong[j, hng].SoMau = z;
                                        mokimcuong.kimcuong[j, hng].Image = hinh[z];
                                        break;
                                    }
                                }
                            }
                            if (hng == 7)//neu nhu la hang ngang cuoi cung
                            {
                                for (int z = 1; z < 8; z++)
                                {
                                    if (mokimcuong.kimcuong[j - 1, hng].SoMau != z && mokimcuong.kimcuong[j + 1, hng].SoMau != z && mokimcuong.kimcuong[j, hng - 1].SoMau != z)
                                    {
                                        mokimcuong.kimcuong[j, hng].SoMau = z;
                                        mokimcuong.kimcuong[j, hng].Image = hinh[z];
                                        break;
                                    }
                                }
                            }
                            if (hng > 0 && hng < 7)
                            {
                                for (int z = 1; z < 8; z++)
                                {
                                    if (mokimcuong.kimcuong[j - 1, hng].SoMau != z && mokimcuong.kimcuong[j + 1, hng].SoMau != z && mokimcuong.kimcuong[j, hng - 1].SoMau != z && mokimcuong.kimcuong[j, hng + 1].SoMau != z)
                                    {
                                        mokimcuong.kimcuong[j, hng].SoMau = z;
                                        mokimcuong.kimcuong[j, hng].Image = hinh[z];
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                #endregion

                #region xu ly trung hang doc
                for (int hdoc = 0; hdoc < 8; hdoc++)
                {
                    for (int i = 1; i < 7; i++)
                    {
                        if (mokimcuong.kimcuong[hdoc, i - 1].SoMau == mokimcuong.kimcuong[hdoc, i].SoMau && mokimcuong.kimcuong[hdoc, i].SoMau == mokimcuong.kimcuong[hdoc, i + 1].SoMau)
                        {
                            if (hdoc == 0)//neu la hang doc dau tien
                            {
                                for (int z = 1; z < 8; z++)
                                {
                                    if (mokimcuong.kimcuong[hdoc, i].SoMau != z && mokimcuong.kimcuong[hdoc, i + 1].SoMau != z)
                                    {
                                        mokimcuong.kimcuong[hdoc, i].SoMau = z;
                                        mokimcuong.kimcuong[hdoc, i].Image = hinh[z];
                                        break;
                                    }
                                }
                            }
                            if (hdoc == 7)
                            {
                                for (int z = 1; z < 8; z++)
                                {
                                    if (mokimcuong.kimcuong[hdoc, i].SoMau != z && mokimcuong.kimcuong[hdoc, i - 1].SoMau != z)
                                    {
                                        mokimcuong.kimcuong[hdoc, i].SoMau = z;
                                        mokimcuong.kimcuong[hdoc, i].Image = hinh[z];
                                        break;
                                    }
                                }
                            }
                            if (hdoc > 0 && hdoc < 7)
                            {
                                for (int z = 1; z < 8; z++)
                                {
                                    if (mokimcuong.kimcuong[hdoc - 1, i].SoMau != z && mokimcuong.kimcuong[hdoc, i - 1].SoMau != z && mokimcuong.kimcuong[hdoc, i + 1].SoMau != z && mokimcuong.kimcuong[hdoc + 1, i].SoMau != z)
                                    {
                                        mokimcuong.kimcuong[hdoc, i].SoMau = z;
                                        mokimcuong.kimcuong[hdoc, i].Image = hinh[z];
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                #endregion

                #region kiem tra trung
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 1; j < 7; j++)
                    {
                        if (mokimcuong.kimcuong[i, j].SoMau == mokimcuong.kimcuong[i, j - 1].SoMau && mokimcuong.kimcuong[i, j].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                        {
                            a = true;
                            MessageBox.Show("Trung mau hang ngang", "Thong Bao");
                        }
                        if (mokimcuong.kimcuong[j, i].SoMau == mokimcuong.kimcuong[j - 1, i].SoMau && mokimcuong.kimcuong[j, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                        {
                            a = true;
                            MessageBox.Show("Trung mau hang doc", "Thong Bao");
                        }
                    }
                }
                #endregion

            } while (a == true);


        }


    }
}
