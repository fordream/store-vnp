using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Runtime.InteropServices;

namespace WindowsFormsApplication9
{
    class media_player
    {
        public bool fileopenning = false;
        public OpenFileDialog openFileDialog1 = new OpenFileDialog();
        private WindowsFormsApplication9.Form1 win9 = new WindowsFormsApplication9.Form1();

        public media_player()
        {
            //InitializeComponent();
        }

        [DllImport("winmm.dll")]
        public static extern long mciSendString(string stay, StringBuilder strbuilder, int width, IntPtr sign);
        private StringBuilder sbf = new StringBuilder();
        public string Status()
        {
            mciSendString("status mediafile mode", sbf, sbf.Capacity, IntPtr.Zero);
            return sbf.ToString();
        }
        string staying;
        public void play_open(object sender, EventArgs e)
        {
            if (Status() == "playing")
            {
                staying = "close mediafile";
                mciSendString(staying, null, 0, IntPtr.Zero);
                openFileDialog1.Title = "Chon Files nhac (*.mpg,*.avi,*.dat)";
                openFileDialog1.Filter = "Media File(*.mpg,*.dat,*.avi,*.wmv,*.wav,*.mp3)|*.wav;*.mp3;*.mpg;*.dat;*.avi;*.wmv";
                openFileDialog1.ShowDialog();
                fileopenning = true;
            }
            else
            {
                staying = "close mediafile";
                mciSendString(staying, null, 0, IntPtr.Zero);
                openFileDialog1.Title = "Chon Files nhac (*.mpg,*.avi,*.dat)";
                openFileDialog1.Filter = "Media File(*.mpg,*.dat,*.avi,*.wmv,*.wav,*.mp3,*.mkv)|*.wav;*.mp3;*.mpg;*.dat;*.avi;*.wmv;*.mkv";
                openFileDialog1.ShowDialog();
                fileopenning = true;
            }
        }

        public void play_play(object sender, EventArgs e)
        {
            staying = "open \"" + this.openFileDialog1.FileName + "\" type mpegvideo alias mediafile style child parent " + win9.pictureBox1.Handle.ToInt32();
            mciSendString(staying, null, 0, IntPtr.Zero);
            staying = "put mediafile window at 0 0 " + win9.pictureBox1.Width + " " + win9.pictureBox1.Height;
            if (fileopenning)
            {
                //this.Text = openFileDialog1.FileName;
                staying = "play mediafile";
                mciSendString(staying, null, 0, IntPtr.Zero);

                StringBuilder length = new StringBuilder(256);
                mciSendString("status mediafile length", length, 256, IntPtr.Zero);
                win9.play_length.Text = FormatSeconds((long)Math.Floor(Convert.ToDouble(length.ToString().Trim()) / 1000));
            }
            else
            {
                MessageBox.Show("Please chose file!");
            }
            Timer timer = new Timer();
            timer.Interval = 500;
            timer.Start();
            timer.Tick += new EventHandler(timer_Tick);
        }

        public string FormatSeconds(long seconds)
        {
            long minus = 0;
            if (seconds < 3600)
            {
                minus = seconds / 60;
                seconds = seconds % 60;
                return minus.ToString().PadLeft(2, '0')
                + ":" + seconds.ToString().PadLeft(2, '0');
            }
            return string.Empty;
        }

        public static long time = 0;
        public void timer_Tick(object sender, EventArgs e)
        {
            StringBuilder fileleng = new StringBuilder(256);
            mciSendString("status mediafile position", fileleng, 256, IntPtr.Zero);
            try
            {
                win9.play_position.Text =  fileleng.ToString();
            }
            catch {  }
        }        

        public void play_seek(object sender, EventArgs e)
        {
            time = Convert.ToInt32(play_goto.Text);            

            mciSendString("seek mediafile to " + play_goto.Text.ToString(), null, 0, IntPtr.Zero);

            staying = "open \"" + this.openFileDialog1.FileName + "\" type mpegvideo alias mediafile style child parent " + pictureBox1.Handle.ToInt32();
            mciSendString(staying, null, 0, IntPtr.Zero);
            staying = "put mediafile window at 0 0 " + win9.pictureBox1.Width + " " + win9.pictureBox1.Height;
            if (fileopenning)
            {
                staying = "play mediafile";
                mciSendString(staying, null, 0, IntPtr.Zero);

            }
            win9.play_position.Text = time.ToString();
        }

        public void play_stop(object sender, EventArgs e)
        {
            //this.Text = openFileDialog1.FileName;
            staying = "close mediafile";
            mciSendString(staying, null, 0, IntPtr.Zero);
        }

        public void play_pause(object sender, EventArgs e)
        {
            staying = "pause mediafile";
            mciSendString(staying, null, 0, IntPtr.Zero);
        }

        public void Form1_Load(object sender, EventArgs e)
        {
            
        }           
    }
}
