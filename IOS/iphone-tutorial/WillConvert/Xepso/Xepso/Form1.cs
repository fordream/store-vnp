using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Timers;

namespace Xepso
{
    [Serializable()]
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            this.Show();
            this.khoitao(25);
        }
        public static int gio;
        public static int phut;
        public static int giay;
        public static int moves = 0;
        gameshape gshape;
        sohoc[] num;
        public static int m;
        SaveFileDialog save1 = new SaveFileDialog();
        OpenFileDialog open = new OpenFileDialog();
        private void Form1_Load(object sender, EventArgs e)
        {
            this.Text = "GAME";
            lbltool.Text = "Moves :" + moves;
            giay = 0;
            phut = 0;
            gio = 0;
            lbltime.Text = string.Format("{0}:{1}:{2}", gio, phut, giay);
            timer1.Enabled = true;
        }
        public void khoitao(int n)
        {
            m = n;
            this.ClientSize = new Size(64 * 5, 66 * 6);
            bool[] bran;
            bran = new bool[n];
            for (int i = 0; i < n; i++)
            {
                bran[i] = false;
            }
            int nextran;
            int nran;
            num = new sohoc[n];
            Random ran = new Random();
            for (int i = 0; i < n; i++)
            {

                do
                {
                    nextran = ran.Next(0, n);
                    nran = nextran;

                } while (bran[nran] != false);
                num[i] = new sohoc(i);
                num[i].setloc(nextran);
                bran[nextran] = true;
            }


        }
        public int find(int value)
        {
            int[] f;
            f = new int[m];
            for (int i = 0; i < m; i++)
            {
                f[num[i].postion] = i;
            }
            return f[value];
        }
        public void swap(int nguon, int dich)
        {
            int n1 = find(nguon);
            int n2 = find(dich);
            num[n1].setloc(dich);
            num[n2].setloc(nguon);
            pictureBox1.Invalidate();


        }
        public void movenum(int toadox, int toadoy)
        {
            try
            {


                int tang = (int)Math.Sqrt(m);
                int count = 0;
                int so1 = (toadoy * tang) + toadox;
                int toadoy1 = (int)(num[m - 1].postion / tang);
                int toadox1 = (int)num[m - 1].postion - (toadoy1 * tang);
                int so2 = num[m - 1].postion;


                if (toadoy1 == toadoy)
                {

                    if (so2 > so1)
                    {
                        moves += 1;
                        count = so2;
                        this.swap(count, count - 1);

                    }
                    if (so2 < so1)
                    {
                        moves += 1;
                        count = so2;
                        this.swap(count, count + 1);

                    }
                }
                if (toadox == toadox1)
                {

                    if (so2 > so1)
                    {
                        moves += 1;
                        count = so2;
                        this.swap(count, count - tang);
                    }
                    if (so2 < so1)
                    {
                        moves += 1;
                        count = so2;
                        this.swap(count, count + tang);

                    }
                }
                bool win = true;
                for (int i = 0; i < m; i++)
                {
                    if (num[i].postion != i)
                    {
                        win = false;
                        break;

                    }
                }
                if (win)
                {
                    MessageBox.Show(" Ban Da chien thang *-* ", "Winner");
                    timer1.Stop();
                    lbl1.Text = "Thanh Cong";
                    new Name().ShowDialog();

                }
                lbltool.Text = "Moves :" + moves;
                gshape = new gameshape(num, moves, gio, phut, giay,m);
            }
            catch (Exception ex)
            {
                MessageBox.Show("loi:" + ex.ToString());
            }

        }
        private void nToolStripMenuItem_Click(object sender, EventArgs e)
        {
            giay = 0;
            phut = 0;
            gio = 0;
            this.khoitao(m);
            lbltime.Text = string.Format("{0}:{1}:{2}", gio, phut, giay);

        }

        private void closeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Close();
        }
        private void pictureBox1_Paint_1(object sender, PaintEventArgs e)
        {

            for (int i = 0; i < m; i++)
            {
                num[i].draw(e.Graphics);

            }
        }
        private void pictureBox1_MouseUp_1(object sender, MouseEventArgs e)
        {

            int posx = (int)(e.X * 1.06 / 64);
            int posy = (int)(e.Y * 1.06 / 64);
            this.movenum(posx, posy);
        }
        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            DialogResult key = MessageBox.Show(" Ban co muon ket thuc tro choi ko ?", "Confirm", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            e.Cancel = (key == DialogResult.No);
        }

        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            giay = 0;
            phut = 0;
            gio = 0;
            moves = 0;
            int index = cboluachon.SelectedIndex;
            timer1.Enabled = true;
            timer1.Start();
            switch (index)
            {
                case 0:
                    
                    this.khoitao(9);
                    lbltime.Text = string.Format("{0}:{1}:{2}", gio, phut, giay);
                    lbltool.Text = "Moves:" + moves.ToString();
                    lbl1.Text = "Start";
                    this.Refresh();
                    break;
                case 1:
                   
                    this.khoitao(16);
                    lbltime.Text = string.Format("{0}:{1}:{2}", gio, phut, giay);
                    lbltool.Text = "Moves:" + moves.ToString();
                    lbl1.Text = "Start";
                    this.Refresh();
                    break;
                case 2:
                    
                    this.khoitao(25);
                    lbltime.Text = string.Format("{0}:{1}:{2}", gio, phut, giay);
                    lbltool.Text = "Moves:" + moves.ToString();
                    lbl1.Text = "Start";
                    this.Refresh();
                    break;
            }

            this.Refresh();
        }
        private void timer1_Tick(object sender, EventArgs e)
        {
            giay++;
            if (giay >= 60)
            {
                giay = 0;
                phut++;

            }
            if (phut == 60)
            {
                phut = 0;
                gio++;
            }
            lbltime.Text = string.Format("{0}:{1}:{2}", gio, phut, giay);
        }

        private void highScoresToolStripMenuItem_Click(object sender, EventArgs e)
        {
            HighScores h = new HighScores();
            h.ShowDialog();
        }

        private void aboutMeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            
        }
        string currentfile = "saveandload.dat";
        private void saveGameToolStripMenuItem_Click(object sender, EventArgs e)
        {
            
            saveandload.save(gshape,currentfile);
            timer1.Enabled = false;
        }

        private void loadGameToolStripMenuItem_Click(object sender, EventArgs e)
        {
            gameshape s = saveandload.load(currentfile);
            m = s.len;
            num = s.array;
            moves = s.mov;
            timer1.Enabled = true;
            gio = s.hours;
            phut = s.milis;
            giay = s.seconds;
            lbltime.Text = string.Format("{0}:{1}:{2}", s.hours, s.milis, s.seconds);
            lbltool.Text = "Moves:" + s.mov.ToString();
            this.Refresh();
        }
        private void toolStripMenuItem2_Click(object sender, EventArgs e)
        {
            new aboutme().ShowDialog();

        }

        private void toolStripMenuItem4_Click(object sender, EventArgs e)
        {
         
            save1.Filter = "Xep so File (*.vic)|*.vic";
            if (save1.ShowDialog() == DialogResult.OK)
            {
                this.currentfile = save1.FileName;
                saveandload.save(gshape, this.save1.FileName);
                timer1.Enabled = false;
            }
            this.Refresh();
        }

        private void toolStripMenuItem3_Click(object sender, EventArgs e)
        {
            open.Filter = "Xep so File (*.vic)|*.vic";
            timer1.Enabled = true;
            if (open.ShowDialog() == DialogResult.OK)
            {
                gameshape s = saveandload.load(this.open.FileName);
                m = s.len;
                num = s.array;
                moves = s.mov;
                timer1.Enabled = true;
                gio = s.hours;
                phut = s.milis;
                giay = s.seconds;
                lbltime.Text = string.Format("{0}:{1}:{2}", s.hours, s.milis, s.seconds);
                lbltool.Text = "Moves:" + s.mov.ToString();
                
            }
            this.Refresh();
           
        }
        
    }
}