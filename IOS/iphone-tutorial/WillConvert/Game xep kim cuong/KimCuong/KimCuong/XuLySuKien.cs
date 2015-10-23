using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Threading;
using System.Collections;
using Microsoft.VisualBasic.FileIO;
using System.IO;

namespace KimCuong
{
    public partial class FormMain
    {
        public int diem = 0, temp = 0, x = 0, y = 0, mau = 0;
        public Point[] point = new Point[4];
        public int cout = 0;//dem so lan click
        public Bitmap[,] bitmap;
        public int kt = 0;//chan su kien luc dang doi cho
        public ArrayList arr = new ArrayList();//luu cac phan tu dang xu ly

        private void XuLySuKien(object sender, EventArgs e)
        {
            if (cout==3)//chan su kien click khi game over
            {
                return;
            }
            if (kt==1)
            {
                return;
            }
            tg_progress.Start();
            point[0].X = 0; point[0].Y = 0;
            point[1].X = 0; point[1].Y = 0;
            KimCuong kc = new KimCuong();
            kc = (KimCuong)sender;
            kc.TrangThai = true;//trang thai dang kich hoat
            if (kc.SoMau == 0)//neu chua bam new game
            {
                kc.TrangThai = false;
                return;
            }
            if (cout == 0)//click lan dau tien
            {

                tg_kt.Stop();
                bitmap = clip(hinh_cd[kc.SoMau], 20, 1);
                tg_cd.Start();
            }
            if (cout == 1)//click lan thu 2
            {
                int dem = 0;
                for (int i = 0; i < 8; i++)//doi cho 2 o gan nhau
                    for (int j = 0; j < 8; j++)
                    {
                        if (mokimcuong.kimcuong[i, j].TrangThai == true)
                        {
                            if (dem == 0)
                            {
                                point[0].X = i;//luu 2 phan tu dang xu ly
                                point[0].Y = j;
                            }
                            if (dem == 1)
                            {
                                point[1].X = i;
                                point[1].Y = j;
                            }
                            dem++;
                        }
                    }
             
                if (dem == 2)
                {
                    dem = 0;
                    if (point[0].X == point[1].X && (point[0].Y == point[1].Y + 1 || point[0].Y == point[1].Y - 1))
                    {
                        lan = 1;
                        tg_kt.Start();
                    }
                    if (point[0].Y == point[1].Y && (point[0].X == point[1].X + 1 || point[0].X == point[1].X - 1))
                    {
                        lan = 1;
                        tg_kt.Start();
                    }
                }
                foreach (KimCuong kc2 in mokimcuong.kimcuong)
                {
                    kc2.TrangThai = false;
                    XuatHinh();
                }
            }
            
            cout++;
            if (cout == 2)
            {
                cout = 0;
            }
        }//click button

        private void KiemTra(ref MoKimCuong mkc)
        {

            foreach (KimCuong kc in mokimcuong.kimcuong)//gan kc.xuly=3 khi bi mat
            {
                if (kc.SoMau==8)
                {
                    kc.XuLy = 3;
                }
            }
            for (int i = 0; i < 8; i++)//duyet kim cuong
                for (int j = 1; j < 7; j++)
                {
                    if (mkc.kimcuong[i, j].SoMau == mkc.kimcuong[i, j - 1].SoMau && mkc.kimcuong[i, j].SoMau == mkc.kimcuong[i, j + 1].SoMau)
                    {

                        mkc.kimcuong[i, j].XuLy++; 
                        mkc.kimcuong[i, j - 1].XuLy++; 
                        mkc.kimcuong[i, j + 1].XuLy++;
                        Hinhnen[mkc.kimcuong[i, j].SoMau].bm = clip(hinh_an[mkc.kimcuong[i, j].SoMau], 4, 1);
                        arr.Add(new Point(i, j));//luu cac phan tu dang xu ly
                        tg_kt.Stop();
                        tg_anhinh.Start();

                    }
                    if (mkc.kimcuong[j, i].SoMau == mkc.kimcuong[j - 1, i].SoMau && mkc.kimcuong[j, i].SoMau == mkc.kimcuong[j + 1, i].SoMau)
                    {

                        mkc.kimcuong[j, i].XuLy++; mkc.kimcuong[j - 1, i].XuLy++; mkc.kimcuong[j + 1, i].XuLy++;
                        Hinhnen[mkc.kimcuong[j, i].SoMau].bm = clip(hinh_an[mkc.kimcuong[j, i].SoMau], 4, 1);
                        arr.Add(new Point(i,j));//luu cac phan tu dang xu ly
                        tg_kt.Stop();
                        tg_anhinh.Start();
                    }

                }
        }

        private bool kiemtratrung(ref MoKimCuong mck)//kiem tra  kc
        {
            for (int i = 0; i < 8; i++)
                for (int j = 1; j < 7; j++)
                {
                    if (mokimcuong.kimcuong[i, j].SoMau == mokimcuong.kimcuong[i, j - 1].SoMau && mokimcuong.kimcuong[i, j].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                    {
                        return true;
                    }
                    if (mokimcuong.kimcuong[j, i].SoMau == mokimcuong.kimcuong[j - 1, i].SoMau && mokimcuong.kimcuong[j, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                    {
                        return true;
                    }
                }
       
            return false;
        }

        private void AnKimCuong(ref MoKimCuong mkc)
        {
            foreach (Point pp in arr)//truong hop 5 kc thang hang
            {
                if (mokimcuong.kimcuong[pp.X, pp.Y].XuLy == 3)
                {
                    mokimcuong.kimcuong[pp.X, pp.Y].SoMau = 8;
                    mokimcuong.kimcuong[pp.X, pp.Y].Image = hinh[8];
                    diem += 6;
                    temp += 6;
                }
                if (mokimcuong.kimcuong[pp.Y, pp.X].XuLy == 3)
                {
                    mokimcuong.kimcuong[pp.Y, pp.X].SoMau = 8;
                    mokimcuong.kimcuong[pp.Y, pp.X].Image = hinh[8];
                    diem += 6;
                    temp += 6;
                }
            }
            arr.Clear();
            Random t = new Random();
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                {
                    if (mkc.kimcuong[i, j].XuLy >= 1 && mkc.kimcuong[i, j].XuLy <= 2)
                    {
                        #region Tinh diem
                        if (mkc.kimcuong[i, j].XuLy == 1)
                        {
                            diem += 1;
                            temp += 1;
                            x = i;
                            y = j;
                            mau = mkc.kimcuong[i, j].SoMau;
                        }
                        if (mkc.kimcuong[i, j].XuLy == 2)
                        {
                            diem += 3;
                            temp += 3;
                            x = i;
                            y = j;
                          
                        }
                        //if (mkc.kimcuong[i, j].XuLy == 3)
                        //{
                        //    diem += 3;
                        //    temp += 3;
                        //    x = i;
                        //    y = j;
                        //}
                        #endregion

                        #region Tang gia tri progressbar1
                        tg_progress.Stop();
                        if (progressBar1.Value <= 179)
                            progressBar1.Value = progressBar1.Value  + 1;
                        if (progressBar1.Value <=179-level-3)
                        {
                            progressBar1.Value = progressBar1.Value + level + 1;
                        }
                        else
                            progressBar1.Value = progressBar1.Value;
                        tg_progress.Start();

                        #endregion
                        if (i == 0)
                        {
                            mkc.kimcuong[0, j].SoMau = 0;
                            mkc.kimcuong[0, j].XuLy = 0;
                        }
                        for (int z = i; z > 0; z--)
                        {
                            mkc.kimcuong[z, j].SoMau = mkc.kimcuong[z - 1, j].SoMau;
                            mkc.kimcuong[z, j].Image = hinh[mkc.kimcuong[z, j].SoMau];
                            mkc.kimcuong[z, j].XuLy = 0;
                            if (z == 1)
                            {
                                mkc.kimcuong[0, j].SoMau = 0;
                                mkc.kimcuong[0, j].XuLy = 0;
                            }
                           
                        }
                       
                        //WindowsMediaPlayer2.URL = "Marble.wav";
                        //ISoundEngine engine2 = new ISoundEngine();
                        //engine2.Play2D("Marble.wav");
                    }
                    
                    textBox1.Text = ("" + diem);

                    #region tao label chuyen dong khi an kc
                    switch (mau)//gan mau cho label
                    {
                        case 1: label2.BackColor = System.Drawing.Color.Yellow;
                            break;
                        case 2: label2.BackColor = System.Drawing.Color.White;
                            break;
                        case 3: label2.BackColor = System.Drawing.Color.Blue;
                            break;
                        case 4: label2.BackColor = System.Drawing.Color.Red;
                            break;
                        case 5: label2.BackColor = System.Drawing.Color.Pink;
                            break;
                        case 6: label2.BackColor = System.Drawing.Color.Orange;
                            break;
                        case 7: label2.BackColor = System.Drawing.Color.Lime;
                            break;
                    }
                    if (temp == 3)
                    {
                        label2.Text = ("" + temp);
                        label2.Visible = true;
                        label2.Location = new Point(mkc.kimcuong[x, y].Location.X+40, mkc.kimcuong[x, y].Location.Y + 40);
                        timer_picturebox.Start();//goi su kien chuyen dong label
                    }
                    else 
                        if (temp == 6)
                        {
                            label2.Text = ("" + temp);
                            label2.Visible = true;
                            label2.Location = new Point(mkc.kimcuong[x, y].Location.X + 40, mkc.kimcuong[x, y].Location.Y + 40);
                            timer_picturebox.Start();
                        }
                        else
                            if (temp == 12)
                            {
                                label2.Text = ("" + temp);
                                label2.Visible = true;
                                label2.Location = new Point(mkc.kimcuong[x, y].Location.X + 40, mkc.kimcuong[x, y].Location.Y + 40);
                                timer_picturebox.Start();
                            }
                            else
                            {
                                label2.Text = ("" + temp);
                                label2.Visible = true;
                                label2.Location = new Point(mkc.kimcuong[x, y].Location.X + 40, mkc.kimcuong[x, y].Location.Y + 40);
                                timer_picturebox.Start();
                            }

                    #endregion
                }
           
            foreach (KimCuong kc in mkc.kimcuong)
            {
                if (kc.SoMau == 0)
                {
           
                    kc.SoMau = t.Next(1, 8);
                    kc.Image = hinh[kc.SoMau];
                   
                }
            }
            int a=  (diem / (50));
            if (a>level)
            {
                level = a;
                //AM THANH LEVEL UP
                Thread a_levelup = new Thread(new ThreadStart(A_levelup));
                a_levelup.Start();
            }
            label3.Text = level +1+ "";
            tg_progress.Interval = 1000 / (level+1);
            if (temp >= 12)
            {
                //am thanh good job
                Thread a_goodjob = new Thread(new ThreadStart(A_goodjob));
                a_goodjob.Start();
            }
            else if (temp>=0)
            {
            }
            //Am thanh an kim cuong            
            Thread a_ankc = new Thread(new ThreadStart(A_ankc));
            a_ankc.Start();
            KiemTra(ref mkc);//tiep tuc kiem tra trung sau khi ankc
        }

    }
}
