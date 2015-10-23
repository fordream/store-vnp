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
        WMPLib.WindowsMediaPlayer WindowsMediaPlayer1;
        WMPLib.WindowsMediaPlayer WindowsMediaPlayer2;
        WMPLib.WindowsMediaPlayer WindowsMediaPlayer3;
        WMPLib.WindowsMediaPlayer WindowsMediaPlayer4;
        WMPLib.WindowsMediaPlayer WindowsMediaPlayer5;
        WMPLib.WindowsMediaPlayer WindowsMediaPlayer6;
        WMPLib.WindowsMediaPlayer WindowsMediaPlayer7;
        WMPLib.WindowsMediaPlayer WindowsMediaPlayer8;

        private void definesound()
        {
            WindowsMediaPlayer1 = new WMPLib.WindowsMediaPlayer();
            WindowsMediaPlayer2 = new WMPLib.WindowsMediaPlayer();
            WindowsMediaPlayer3 = new WMPLib.WindowsMediaPlayer();
            WindowsMediaPlayer4 = new WMPLib.WindowsMediaPlayer();
            WindowsMediaPlayer5 = new WMPLib.WindowsMediaPlayer();
            WindowsMediaPlayer6 = new WMPLib.WindowsMediaPlayer();
            WindowsMediaPlayer7 = new WMPLib.WindowsMediaPlayer();
            WindowsMediaPlayer8 = new WMPLib.WindowsMediaPlayer();
        }
        void A_ankc()
        {
            WindowsMediaPlayer1.URL = "ankc.wav";  
        }
        public void A_cantmove()
        {
            WindowsMediaPlayer2.URL = "cantmove.wav";
        }
        public void A_Gameover()
        {
            WindowsMediaPlayer3.URL = "gameover.wav";
        }
        public void A_getreadly()
        {
            WindowsMediaPlayer4.URL = "getready.wav";
        }
        public void A_nomove()
        {
            WindowsMediaPlayer5.URL = "no_more_moves.wav";
        }
        public void A_levelup()
        {
            Thread.Sleep(1500);
            WindowsMediaPlayer6.URL = "levelup.wav";
        }
        public void A_goodjob()
        {
            Thread.Sleep(3000);
            WindowsMediaPlayer7.URL = "goodjob.wav";
        }
    }
}
