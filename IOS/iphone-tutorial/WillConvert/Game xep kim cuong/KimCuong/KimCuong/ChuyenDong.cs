using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Threading;

namespace KimCuong
{
    public partial class FormMain
    {
        public int int_tg_cd = 0;
        public int int_tg_ah = 0;
        public int int_tg_hint = 0;

        public void Chuyendong(object sender, EventArgs e)//timer chuyen dong
        {
            foreach (KimCuong kc in mokimcuong.kimcuong)
            {
                if (kc.TrangThai == true && kc.XuLy <= 2)
                {
                    kc.Image = bitmap[int_tg_cd, 0];
                }
            }
            int_tg_cd++;
            if (int_tg_cd == 19)
            {
                int_tg_cd = 0;
            }
        }//timer chuyen dong

        private void timer_picturebox_Tick(object sender, EventArgs e)
        {
            if (v < 10)
            {
                temp = 0;
                label2.Top -= 4;
                v++;
            }
            else
            {
                v = 0;
                label2.Visible = false;
                timer_picturebox.Stop();
                return;
            }
        }

        private void tg_progress_Tick(object sender, EventArgs e)//su kien thanh progress bar
        {
            if (progressBar1.Value > 0)
                progressBar1.Value--;
            if (progressBar1.Value == 0)
            {
                tg_progress.Stop();
                ////////////////////////////////////////////////////////////AM THANH GAME OVER
                Thread a_gameover = new Thread(new ThreadStart(A_Gameover));
                a_gameover.Start();
                //foreach (KimCuong kc in mokimcuong.kimcuong)
                //{
                //    kc.Enabled = false;
                //}
                cout = 3;
                tg_kt.Stop();
                if (MessageBox.Show("Game Over", "Thong Bao", MessageBoxButtons.OK, MessageBoxIcon.Stop) == DialogResult.OK)
                {
                    Name f = new Name(diem);
                    f.Show();
                }
            }
        }

        private void tg_kt_Tick(object sender, EventArgs e)//timer doi cho
        {
            tg_cd.Stop();
            KimCuong temp = new KimCuong();
            if (lan == 1)//doi cho lan thu nhat
            {
                point[2] = point[0];
                point[3] = point[1];
                if (mokimcuong.kimcuong[point[1].X, point[1].Y].XuLy == 3)//neu nhu point 1 la o dac biet
                {
                    foreach (KimCuong kc1 in mokimcuong.kimcuong)
                    {
                        if (kc1.SoMau == mokimcuong.kimcuong[point[0].X, point[0].Y].SoMau)
                        {
                            kc1.XuLy = 1;
                        }
                    }
                    Hinhnen[mokimcuong.kimcuong[point[0].X, point[0].Y].SoMau].bm = clip(hinh_an[mokimcuong.kimcuong[point[0].X, point[0].Y].SoMau], 4, 1);
                    Hinhnen[mokimcuong.kimcuong[point[1].X, point[1].Y].SoMau].bm = clip(hinh_an[mokimcuong.kimcuong[point[1].X, point[1].Y].SoMau], 4, 1);
                    mokimcuong.kimcuong[point[1].X, point[1].Y].XuLy = 1;
                    tg_anhinh.Start();
                    tg_kt.Stop();
                    return;
                }
                if (mokimcuong.kimcuong[point[0].X, point[0].Y].XuLy == 3)//neu nhu point 0 la o dac biet
                {
                    foreach (KimCuong kc1 in mokimcuong.kimcuong)
                    {
                        if (kc1.SoMau == mokimcuong.kimcuong[point[1].X, point[1].Y].SoMau)
                        {
                            kc1.XuLy = 1;
                        }
                    }
                    Hinhnen[mokimcuong.kimcuong[point[0].X, point[0].Y].SoMau].bm = clip(hinh_an[mokimcuong.kimcuong[point[0].X, point[0].Y].SoMau], 4, 1);
                    Hinhnen[mokimcuong.kimcuong[point[1].X, point[1].Y].SoMau].bm = clip(hinh_an[mokimcuong.kimcuong[point[1].X, point[1].Y].SoMau], 4, 1);
                    mokimcuong.kimcuong[point[0].X, point[0].Y].XuLy = 1;
                    tg_anhinh.Start();
                    tg_kt.Stop();
                    return;
                }
                if (point[0].X == point[1].X)//neu la hang ngang
                {
                    if (point[0].Y > point[1].Y)
                    {
                        mokimcuong.kimcuong[point[0].X, point[0].Y].Left = mokimcuong.kimcuong[point[0].X, point[0].Y].Left - 17;
                        mokimcuong.kimcuong[point[1].X, point[1].Y].Left = mokimcuong.kimcuong[point[1].X, point[1].Y].Left + 17;
                    }
                    if (point[1].Y > point[0].Y)
                    {
                        mokimcuong.kimcuong[point[0].X, point[0].Y].Left = mokimcuong.kimcuong[point[0].X, point[0].Y].Left + 17;
                        mokimcuong.kimcuong[point[1].X, point[1].Y].Left = mokimcuong.kimcuong[point[1].X, point[1].Y].Left - 17;
                    }

                }
                if (point[0].Y == point[1].Y)
                {
                    if (point[0].X < point[1].X)
                    {
                        mokimcuong.kimcuong[point[0].X, point[0].Y].Top = mokimcuong.kimcuong[point[0].X, point[0].Y].Top + 17;
                        mokimcuong.kimcuong[point[1].X, point[1].Y].Top = mokimcuong.kimcuong[point[1].X, point[1].Y].Top - 17;
                    }
                    if (point[1].X < point[0].X)
                    {
                        mokimcuong.kimcuong[point[0].X, point[0].Y].Top = mokimcuong.kimcuong[point[0].X, point[0].Y].Top - 17;
                        mokimcuong.kimcuong[point[1].X, point[1].Y].Top = mokimcuong.kimcuong[point[1].X, point[1].Y].Top + 17;
                    }
                }
                i++;
                if (i == 5)
                {
                    i = 0;
                    temp = mokimcuong.kimcuong[point[0].X, point[0].Y];
                    mokimcuong.kimcuong[point[0].X, point[0].Y] = mokimcuong.kimcuong[point[1].X, point[1].Y];
                    mokimcuong.kimcuong[point[1].X, point[1].Y] = temp;
                    tg_kt.Stop();
                    KiemTra(ref mokimcuong);

                    if (kiemtratrung(ref mokimcuong) == false)
                    {
                        Point a = new Point();
                        a = point[0];
                        point[0] = point[1];
                        point[1] = a;
                        kt = 1;
                        tg_kt.Start();
                        lan = 2;

                    }

                }
            }
            if (lan == 2)//xu ly nguoc lai neu ko an dc
            {
                point[0] = point[2]; point[1] = point[3];
                if (point[0].X == point[1].X)//neu la hang ngang
                {
                    if (point[0].Y > point[1].Y)
                    {
                        mokimcuong.kimcuong[point[0].X, point[0].Y].Left = mokimcuong.kimcuong[point[0].X, point[0].Y].Left - 17;
                        mokimcuong.kimcuong[point[1].X, point[1].Y].Left = mokimcuong.kimcuong[point[1].X, point[1].Y].Left + 17;
                    }
                    if (point[1].Y > point[0].Y)
                    {
                        mokimcuong.kimcuong[point[0].X, point[0].Y].Left = mokimcuong.kimcuong[point[0].X, point[0].Y].Left + 17;
                        mokimcuong.kimcuong[point[1].X, point[1].Y].Left = mokimcuong.kimcuong[point[1].X, point[1].Y].Left - 17;
                    }

                }
                if (point[0].Y == point[1].Y)
                {
                    if (point[0].X < point[1].X)
                    {
                        mokimcuong.kimcuong[point[0].X, point[0].Y].Top = mokimcuong.kimcuong[point[0].X, point[0].Y].Top + 17;
                        mokimcuong.kimcuong[point[1].X, point[1].Y].Top = mokimcuong.kimcuong[point[1].X, point[1].Y].Top - 17;
                    }
                    if (point[1].X < point[0].X)
                    {
                        mokimcuong.kimcuong[point[0].X, point[0].Y].Top = mokimcuong.kimcuong[point[0].X, point[0].Y].Top - 17;
                        mokimcuong.kimcuong[point[1].X, point[1].Y].Top = mokimcuong.kimcuong[point[1].X, point[1].Y].Top + 17;
                    }
                }
                i++;
                if (i == 5)
                {
                    i = 0;
                    temp = mokimcuong.kimcuong[point[0].X, point[0].Y];
                    mokimcuong.kimcuong[point[0].X, point[0].Y] = mokimcuong.kimcuong[point[1].X, point[1].Y];
                    mokimcuong.kimcuong[point[1].X, point[1].Y] = temp;
                    kt = 0;

                    //AM THANH KO DI CHUYEN
                    Thread a_cntmove = new Thread(new ThreadStart(A_cantmove));
                    a_cntmove.Start();

                    tg_kt.Stop();
                    lan = 1;
                }

            }
        }

        public void AnHinh(object sender, EventArgs e)//timer an hinh
        {
            //tg_kt.Stop();
            kt = 1;
            tg_cd.Stop();
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    if (mokimcuong.kimcuong[i, j].XuLy > 0 && mokimcuong.kimcuong[i, j].XuLy <3)
                        mokimcuong.kimcuong[i, j].Image = Hinhnen[mokimcuong.kimcuong[i, j].SoMau].bm[int_tg_ah, 0];

            int_tg_ah++;
            if (int_tg_ah == 4)
            {
                int_tg_ah = 0;
                tg_anhinh.Stop();
                kt = 0;
                AnKimCuong(ref mokimcuong);
            }
        }

        class hinhnen
        {
            public Bitmap[,] bm { get; set; }
        }
    }
}
