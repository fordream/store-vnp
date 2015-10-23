using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using DevComponents.DotNetBar;
using System.Net;
using System.IO;
using AxWMPLib;
using WMPLib;

namespace dean
{
    class ham
    {
        public SaveFileDialog savelist
        {
            get;
            set;
        }

        public OpenFileDialog openlist
        {
            get;
            set;
        }

        public AxWindowsMediaPlayer ax2
        {
            get;
            set;
        }

        public ListView danhsach
        {
            get;
            set;
        }

        public ListBox l1
        {
            get;
            set;
        }

        //tao playlist
        public void tao_playlist()
        {
            if (danhsach.Items.Count == 0)
            {
                MessageBox.Show("Chưa có bài hát");
                return;
            }
            else
            {
                try
                {
                    savelist = new SaveFileDialog();
                    savelist.Title = "Lưu Danh Sách";
                    savelist.InitialDirectory = "C:";
                    savelist.Filter = "(*.m3u)|*.m3u";
                    if (savelist.ShowDialog() == DialogResult.OK)
                    {

                        string filename = savelist.FileName;
                        if (filename != "")
                            using (StreamWriter sw = new StreamWriter(filename))
                            {
                                for (int i = 0; i < danhsach.Items.Count; i++)
                                    sw.WriteLine(ax2.currentPlaylist.get_Item(i).sourceURL.ToString());
                            }
                    }
                }
                catch (Exception)
                {
                    MessageBox.Show("Lỗi khi tạo Playlist");
                }
            }
        }

        //mo playlist
        public void mo_playlist()
        {
            openlist.Title = "Open PlayList";
            openlist.Filter = "(*.m3u)|*.m3u";
            
            int kt = 0;
            if (openlist.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    if (openlist.SafeFileName != null)
                    {
                        String line;
                        string myPlaylist = openlist.SafeFileName;
                        WMPLib.IWMPPlaylist pl1;
                        WMPLib.IWMPMedia m11;
                        pl1 = ax2.playlistCollection.newPlaylist(myPlaylist);
                        danhsach.Items.Clear();
                        using (StreamReader sr = new StreamReader(openlist.FileName))
                        {
                            while ((line = sr.ReadLine()) != null)
                            {
                                l1.Items.Add(line);
                                m11 = ax2.newMedia(line.Trim());
                                pl1.appendItem(m11);
                                ax2.currentPlaylist = pl1;
                                string[] ite = new string[] {ax2.currentPlaylist.get_Item(kt).getItemInfo("Name").ToString()};
                                ListViewItem items = new ListViewItem(ite);
                                danhsach.Items.Add(items);
                                kt++;
                            }
                        }
                    }
                    ax2.Ctlcontrols.play();
                }
                catch
                {
                    MessageBox.Show("Lỗi file !!");
                }
            }
        }
    }
}
