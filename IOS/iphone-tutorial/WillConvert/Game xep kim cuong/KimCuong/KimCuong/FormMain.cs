using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Threading;
using System.Media;
using IrrKlang;

namespace KimCuong
{
    public partial class FormMain : Form
    {
        SoundPlayer spWave;
        public static System.Drawing.Bitmap[] hinh = new Bitmap[9];
        public static System.Drawing.Bitmap[] hinh_an = new Bitmap[9];
        public static System.Drawing.Bitmap[] hinh_cd = new Bitmap[9];
        public static System.Drawing.Bitmap hinh_hint;
        public int btton = 0;
        MoKimCuong mokimcuong = new MoKimCuong();
        hinhnen[] Hinhnen = new hinhnen[9];
        public int v;
        public int i = 0;
        public int lan = 1;

        public FormMain()
        {
            InitializeComponent();
            definesound();//class xu lu am thanh

            for (int a = 0; a < 9; a++)
            {
                Hinhnen[a] = new hinhnen();
            }

            #region Add Resource va Su kien click
            //
            //hinh
            //
            hinh[0] = WindowsFormsApplication1.Properties.Resources.gem_0;
            hinh[1] = WindowsFormsApplication1.Properties.Resources.gem_1;
            hinh[2] = WindowsFormsApplication1.Properties.Resources.gem_2;
            hinh[3] = WindowsFormsApplication1.Properties.Resources.gem_3;
            hinh[4] = WindowsFormsApplication1.Properties.Resources.gem_4;
            hinh[5] = WindowsFormsApplication1.Properties.Resources.gem_5;
            hinh[6] = WindowsFormsApplication1.Properties.Resources.gem_6;
            hinh[7] = WindowsFormsApplication1.Properties.Resources.gem_7;
            hinh[8] = WindowsFormsApplication1.Properties.Resources.gem_8;
            //
            //hinhcd
            //
            hinh_cd[0] = WindowsFormsApplication1.Properties.Resources.hinhcd00;
            hinh_cd[1] = WindowsFormsApplication1.Properties.Resources.hinhcd01;
            hinh_cd[2] = WindowsFormsApplication1.Properties.Resources.hinhcd02;
            hinh_cd[3] = WindowsFormsApplication1.Properties.Resources.hinhcd03;
            hinh_cd[4] = WindowsFormsApplication1.Properties.Resources.hinhcd04;
            hinh_cd[5] = WindowsFormsApplication1.Properties.Resources.hinhcd05;
            hinh_cd[6] = WindowsFormsApplication1.Properties.Resources.hinhcd06;
            hinh_cd[7] = WindowsFormsApplication1.Properties.Resources.hinhcd07;
            hinh_cd[8] = WindowsFormsApplication1.Properties.Resources.hinhcd08;
            //
            //hinhan
            //
            hinh_an[0] = WindowsFormsApplication1.Properties.Resources.anhinh_00;
            hinh_an[1] = WindowsFormsApplication1.Properties.Resources.anhinh_01;
            hinh_an[2] = WindowsFormsApplication1.Properties.Resources.anhinh_02;
            hinh_an[3] = WindowsFormsApplication1.Properties.Resources.anhinh_03;
            hinh_an[4] = WindowsFormsApplication1.Properties.Resources.anhinh_04;
            hinh_an[5] = WindowsFormsApplication1.Properties.Resources.anhinh_05;
            hinh_an[6] = WindowsFormsApplication1.Properties.Resources.anhinh_06;
            hinh_an[7] = WindowsFormsApplication1.Properties.Resources.anhinh_07;
            hinh_an[8] = WindowsFormsApplication1.Properties.Resources.anhinh_08;
            //
            //hinh hint
            //
            hinh_hint = WindowsFormsApplication1.Properties.Resources.Hint;
            foreach (KimCuong kc in mokimcuong.kimcuong)
            {
                this.Controls.Add(kc);
                kc.Click += new EventHandler(this.XuLySuKien);
            }
            #endregion

            this.Size = new Size(950, 760);
            //add control len form
            this.btNewGame.Location = new Point(748, 70);
            this.bt_SaveGame.Location = new Point(748, 210);
            this.bt_LoadGame.Location = new Point(748, 280);
            this.bt_Hint.Location = new Point(748, 140);
            this.bt_HighScore.Location = new Point(748, 350);
            this.progressBar1.Location = new Point(10, 693);
            this.progressBar1.Size = new Size(680, 25);
            this.textBox1.Size = new Size(143, 39);
            this.textBox1.Text = ("" + diem);
            this.groupBox1.Location = new Point(748, 550);
        }
       
        private void FormMain_Load(object sender, EventArgs e)
        {
            spWave = new SoundPlayer(WindowsFormsApplication1.Properties.Resources.Kalimba_2_);
            spWave.PlayLooping();
            tg_kt.Stop();
            btNewGame.MouseEnter += new EventHandler(button1_MouseEnter);
            btNewGame.MouseLeave += new EventHandler(button1_MouseLeave);
            bt_SaveGame.MouseEnter += new EventHandler(button2_MouseEnter);
            bt_SaveGame.MouseLeave += new EventHandler(button2_MouseLeave);
            bt_LoadGame.MouseEnter += new EventHandler(button3_MouseEnter);
            bt_LoadGame.MouseLeave += new EventHandler(button3_MouseLeave);
            bt_Hint.MouseEnter += new EventHandler(button4_MouseEnter);
            bt_Hint.MouseLeave += new EventHandler(button4_MouseLeave);
            bt_HighScore.MouseEnter += new EventHandler(button5_MouseEnter);
            bt_HighScore.MouseLeave += new EventHandler(button5_MouseLeave);

        }

        #region Su kien chuot

        void button5_MouseLeave(object sender, EventArgs e)
        {
            bt_HighScore.Image = WindowsFormsApplication1.Properties.Resources.High_Score;
            timer1.Stop();
        }

        void button5_MouseEnter(object sender, EventArgs e)
        {
            btton = 5;
            timer1.Start();
        }

        void button4_MouseLeave(object sender, EventArgs e)
        {
            bt_Hint.Image = WindowsFormsApplication1.Properties.Resources.Hint1;
            timer1.Stop();
        }

        void button4_MouseEnter(object sender, EventArgs e)
        {
            btton = 4;
            timer1.Start();
        }

        void button3_MouseLeave(object sender, EventArgs e)
        {
            bt_LoadGame.Image = WindowsFormsApplication1.Properties.Resources.Load_Game;
            timer1.Stop();
        }

        void button3_MouseEnter(object sender, EventArgs e)
        {
            btton = 3;
            timer1.Start();
        }

        void button2_MouseLeave(object sender, EventArgs e)
        {
            bt_SaveGame.Image = WindowsFormsApplication1.Properties.Resources.Save_Game;
            timer1.Stop();
        }

        void button2_MouseEnter(object sender, EventArgs e)
        {
            btton = 2;
            timer1.Start();
        }

        void button1_MouseLeave(object sender, EventArgs e)
        {
            btNewGame.Image = WindowsFormsApplication1.Properties.Resources.New_Game;
            timer1.Stop();
        }

        void button1_MouseEnter(object sender, EventArgs e)
        {
            btton = 1;
            timer1.Start();
        }

        #endregion

        private void bt_Hint_Click(object sender, EventArgs e)//su kien click button hint
        {
            try
            {
                Hint();
                if (Hint() == 0)
                {
                    tg_progress.Stop();
                    //AM THANH NOMOVE
                    Thread a_nomove = new Thread(new ThreadStart(A_nomove));
                    a_nomove.Start();

                    if (MessageBox.Show("No Moves =>> Game Over", "Thong Bao", MessageBoxButtons.OK, MessageBoxIcon.Stop) == DialogResult.OK)
                    {

                        //foreach (KimCuong kc in mokimcuong.kimcuong)
                        //{
                        //    kc.Enabled = false;
                        //}
                        cout = 3;
                        Name f = new Name(diem);
                        f.Show();
                    }
                    return;
                }
                if (progressBar1.Value >= 5)
                    progressBar1.Value -= 5;
                if (diem >= 5)
                    diem -= 5;
                textBox1.Text = ("" + diem);
            }
            catch
            {
                MessageBox.Show("Ban Chua Bam New Game");
            }

        }

        private void bt_HighScore_Click(object sender, EventArgs e)//su kien button high score
        {
            FormHighScore f = new FormHighScore();
            f.Show();
        }
        
        public int int_time1 = 0;
        
        private void timer1_Tick_1(object sender, EventArgs e)//su kien chuot button
        {
            Bitmap[,] bm;
            if (btton == 1)
            {
                bm = clip(WindowsFormsApplication1.Properties.Resources.New_Game01, 4, 1);
                btNewGame.Image = bm[int_time1, 0];
            }
            if (btton == 2)
            {
                bm = clip(WindowsFormsApplication1.Properties.Resources.Save_Game01, 4, 1);
                bt_SaveGame.Image = bm[int_time1, 0];
            }
            if (btton == 3)
            {
                bm = clip(WindowsFormsApplication1.Properties.Resources.Load_Game01, 4, 1);
                bt_LoadGame.Image = bm[int_time1, 0];
            }
            if (btton == 4)
            {
                bm = clip(WindowsFormsApplication1.Properties.Resources.Hint01, 4, 1);
                bt_Hint.Image = bm[int_time1, 0];
            }
            if (btton == 5)
            {
                bm = clip(WindowsFormsApplication1.Properties.Resources.High_Score01, 4, 1);
                bt_HighScore.Image = bm[int_time1, 0];
            }
            int_time1++;
            if (int_time1 == 4)
            {
                int_time1 = 0;
            }
        }
        
       

    }
}
