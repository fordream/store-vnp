using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;

namespace WindowsFormsApplication9
{

    class player_volume
    {
        private WindowsFormsApplication9.Form1 win9 = new WindowsFormsApplication9.Form1();
        [DllImport("winmm.dll")]
        public static extern int waveOutGetVolume(IntPtr hwo, out uint dwVolume);
        [DllImport("winmm.dll")]
        public static extern int waveOutSetVolume(IntPtr hwo, uint dwVolume);

        public player_volume()
        {
            get_volume();
        }

        public void get_volume()
        {
            uint CurrVol = 0;
            waveOutGetVolume(IntPtr.Zero, out CurrVol);
            ushort CalcVol = (ushort)(CurrVol & 0x0000ffff);
            win9.trackWave.Value = CalcVol / (ushort.MaxValue / 10);
        }

        public void set_volume()
        {
            int NewVolume = ((ushort.MaxValue / 10) * win9.trackWave.Value);
            uint NewVolumeAllChannels = (((uint)NewVolume & 0x0000ffff) | ((uint)NewVolume << 16));
            waveOutSetVolume(IntPtr.Zero, NewVolumeAllChannels); 
        }
    }


}
