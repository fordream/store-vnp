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
using System.Threading;
using System.Runtime.InteropServices;
using System.Management;
using System.Text.RegularExpressions;
using Microsoft.Win32;
using System.Diagnostics;

namespace dean
{
    public partial class Form1 : Office2007RibbonForm
    {
        ListBox lb1 = new ListBox();
        ListBox lb2 = new ListBox();
        int n = -1;
        int so = 0;
        int Time = 0;
        int so1;
        string str1 = "";
        tkonline tk;
        ham h = new ham();
        ListBox[] list;
        ToolStripMenuItem[] temp;
        [DllImport("user32.dll")]
        public static extern int ExitWindowsEx(int a, int b);
        String path = Application.StartupPath + @"\Recently.css";

        public Form1()
        {
            InitializeComponent();
            listView2.View = View.Details;
            listView1.View = View.Details;
            listView1.Columns.Add("Song", Convert.ToInt32(listView1.Width * 36 / 100));
            listView1.Columns.Add("Artis", Convert.ToInt32(listView1.Width * 21 / 100));
            listView1.Columns.Add("Album", Convert.ToInt32(listView1.Width * 22 / 100));
            listView1.Columns.Add("Date", Convert.ToInt32(listView1.Width * 21 / 100));
            listView2.Columns.Add("Đang Chơi", Convert.ToInt32(listView2.Width * 1));
        }

        private void set_tk()
        {
            tk = new tkonline();
            tk.dstk = listViewEx1;
            tk.s = so;
            tk.ax = axWindowsMediaPlayer1;
            tk.dl = down;
        }

        private void set_ham()
        {
            h.ax2 = axWindowsMediaPlayer1;
            h.danhsach = ds;
            h.savelist = savelist;
            h.openlist = openpl;
            h.l1 = lb2;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            set_ham();
            set_tk();
            if (!texttimkiem.Focused)
            {
                texttimkiem.Text = "Nhập Nội Dung";
                texttimkiem.Font = new Font("Arial", 8, FontStyle.Regular);
            }
            play.Show();
            pause.Hide();
            shuffle.Hide();
            shuffleoff.Show();
            list = new ListBox[4];
            temp = new ToolStripMenuItem[10];
            mute.Show();
            muteoff.Hide();
            shuffleoff.Show();
            shuffle.Hide();
            repeat.Show();
            repeat_on.Hide();
            axWindowsMediaPlayer1.settings.setMode("loop", false);
        }


        //tao list nhac va open list

        private void buttonItem4_Click(object sender, EventArgs e)
        {
            h.tao_playlist();
            taoNode(h.savelist.FileName);
        }


        private void mods_Click(object sender, EventArgs e)
        {
            listView2.Items.Clear();
            listView1.Items.Clear();
            lb2.Items.Clear();
            h.mo_playlist();
            taoNode(h.openlist.FileName);
            string s = "";
            for (int i = 0; i < lb2.Items.Count; i++)
            {
                s = lb2.Items[i].ToString();
                listView2.Items.Add(Path.GetFileNameWithoutExtension(s));
            }

        }

        //
        //volume
        private void volume_ValueChanged(object sender, EventArgs e)
        {
            axWindowsMediaPlayer1.settings.volume = Convert.ToInt32(volume.Value);
            muteoff.Hide();
            mute.Show();
        }


        //tim kiem tren mang

        private void texttimkiem_MouseClick(object sender, MouseEventArgs e)
        {
            texttimkiem.Text = "";
        }

        private void listViewEx1_MouseDoubleClick_1(object sender, MouseEventArgs e)
        {
            axWindowsMediaPlayer1.URL = listViewEx1.Items[listViewEx1.SelectedIndices[0]].SubItems[5].Text;
            axWindowsMediaPlayer1.Ctlcontrols.play();
        }

        private void timkiem_Click(object sender, EventArgs e)
        {
            //
            listViewEx1.Items.Clear();
            if (comboBox2.Text.Equals("Bài hát")) { so = 1; }
            if (comboBox2.Text.Equals("Ca sỹ")) { so = 2; }
            tk.settext = texttimkiem.Text;
            tk.Searchnhac();
        }

        //download file ve may
        private void dlfile_Click(object sender, EventArgs e)
        {
            tk.download();
        }

        //het

        //Mo file       
        public string myPlaylist = "UIT_K3C1";
        public WMPLib.IWMPPlaylist pl;
        public WMPLib.IWMPPlaylistArray plItems;
        public WMPLib.IWMPMedia m1;

        private void CreatePlayLis(OpenFileDialog open, ListView lv)
        {
            plItems = axWindowsMediaPlayer1.playlistCollection.getByName(myPlaylist);

            if (plItems.count == 0)
                pl = axWindowsMediaPlayer1.playlistCollection.newPlaylist(myPlaylist);
            else
                pl = plItems.Item(0);

            //them vao listview va thu vien WMP

            int i = 0;
            foreach (string file in open.FileNames)
            {
                ListViewItem item = new ListViewItem(open.FileNames[i]);
                lv.Items.Add(Path.GetFileNameWithoutExtension(item.ToString()));
                i++;
                m1 = axWindowsMediaPlayer1.newMedia(file);
                pl.appendItem(m1);
            }

            //choi nhac
            axWindowsMediaPlayer1.currentPlaylist = pl;
            axWindowsMediaPlayer1.Ctlcontrols.play();
            axWindowsMediaPlayer1.playlistCollection.remove(pl);
        }

        private void MoFile_Click(object sender, EventArgs e)
        {
            OpenFileDialog op2 = new OpenFileDialog();
            op2.Multiselect = true;
            if (op2.ShowDialog() == DialogResult.OK)
            {
                ds.Items.Clear();
                listView2.Items.Clear();
                lb2.Items.Clear();
                CreatePlayLis(op2, ds);
                CreatePlayLis(op2, listView2);
                foreach (string s in op2.FileNames)
                    lb2.Items.Add(s);
            }

            play.Hide();
            pause.Show();
        }

        //chuc nag keo tha
        private void ds_DragDrop(object sender, DragEventArgs e)
        {
            try
            {
                ds.Items.Clear();
                //tao playlist chua cac bai hat duoc chon
                plItems = axWindowsMediaPlayer1.playlistCollection.getByName(myPlaylist);
                pl = axWindowsMediaPlayer1.playlistCollection.newPlaylist(myPlaylist);

                //them vao listview va thu vien WMP

                string[] s = (string[])e.Data.GetData(DataFormats.FileDrop, false);
                FileInfo f;
                for (int i = 0; i < s.Length; i++)
                {
                    f = new FileInfo(s[i]);
                    ds.Items.Add(f.Name);
                    ListViewItem item = new ListViewItem(s[i]);
                    lb2.Items.Add(f.FullName);

                    m1 = axWindowsMediaPlayer1.newMedia(s[i]);
                    pl.appendItem(m1);
                }
                play.Hide();
                pause.Show();
                axWindowsMediaPlayer1.currentPlaylist = pl;
                axWindowsMediaPlayer1.Ctlcontrols.play();
                axWindowsMediaPlayer1.playlistCollection.remove(pl);
            }
            catch (Exception)
            {
                //MessageBox.Show("Không Cho Phép Kéo Thả");
            }
        }

        private void ds_DragEnter(object sender, DragEventArgs e)
        {
            if (e.Data.GetDataPresent(DataFormats.FileDrop))
                e.Effect = DragDropEffects.All;
            else
                e.Effect = DragDropEffects.None;
        }

        //double chuot vao danh sach nhac

        private void ds_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            try
            {
                IWMPMedia med = axWindowsMediaPlayer1.currentPlaylist.get_Item(ds.SelectedIndices[0]);
                // ListViewItem sel = ds.SelectedItems[0];
                axWindowsMediaPlayer1.Ctlcontrols.playItem(med);
            }
            catch (Exception)
            {
            }
        }

        //CAC PHIM TAT CHO CHUONG TRINH


        private void ds_KeyDown(object sender, KeyEventArgs e)
        {
            //play bai hat khi nhan enter

            if (e.KeyCode == Keys.Enter)
            {
                try
                {
                    IWMPMedia med = axWindowsMediaPlayer1.currentPlaylist.get_Item(ds.SelectedIndices[0]);
                    ListViewItem sel = ds.SelectedItems[0];
                    axWindowsMediaPlayer1.Ctlcontrols.playItem(med);
                }
                catch (Exception)
                {
                }
            }

            //xoa bai hat khoi ds khi nhan delete

            if (e.KeyCode == Keys.Delete)
            {
                try
                {
                    IWMPMedia med = axWindowsMediaPlayer1.currentPlaylist.get_Item(ds.SelectedIndices[0]);
                    ListViewItem sel = ds.SelectedItems[0];
                    axWindowsMediaPlayer1.currentPlaylist.removeItem(med);
                    ds.Items.Remove(sel);
                    axWindowsMediaPlayer1.Ctlcontrols.play();
                }
                catch (Exception)
                {
                }
            }
        }


        //hen gio tat may
        private void ok_Click(object sender, EventArgs e)
        {
            if (comboBoxEx1.Text != "")
            {
                Time = ((int)gio.Value) * 3600 + ((int)phut.Value) * 60 + ((int)giay.Value);
                hg.Enabled = true;
                ok.Enabled = false;
                cancel.Enabled = true;
                giay.Enabled = false;
                gio.Enabled = false;
                phut.Enabled = false;
                comboBoxEx1.Enabled = false;
                lbComment.Text = "Còn lại " + Time.ToString() + " giây";
            }
            else
            {
                MessageBox.Show("Bạn Chưa Chọn Thao Tác");
            }
        }

        private void hg_Tick(object sender, EventArgs e)
        {
            if (Time > 0)
            {
                Time--;
                lbComment.Text = "Còn lại " + Time.ToString() + " giây";
            }
            else
            {
                hg.Enabled = false;
                if (comboBoxEx1.Text == "Standby")
                {
                    Application.SetSuspendState(PowerState.Suspend, true, true);
                }
                if (comboBoxEx1.Text == "Hibernate")
                {
                    Application.SetSuspendState(PowerState.Hibernate, true, true);
                }
                if (comboBoxEx1.Text == "Logoff")
                {
                    ExitWindowsEx(0, 0);
                }
                if (comboBoxEx1.Text == "Restart")
                {
                    ExitWindowsEx(2, 0);
                }
                if (comboBoxEx1.Text == "Shutdown")
                {
                    ManagementBaseObject mboShutdown = null;
                    ManagementClass mcWin32 = new ManagementClass("Win32_OperatingSystem");
                    mcWin32.Get();
                    mcWin32.Scope.Options.EnablePrivileges = true;
                    ManagementBaseObject mboShutdownParams = mcWin32.GetMethodParameters("Win32Shutdown");
                    mboShutdownParams["Flags"] = "1";
                    mboShutdownParams["Reserved"] = "0";
                    foreach (ManagementObject manObj in mcWin32.GetInstances())
                    {
                        mboShutdown = manObj.InvokeMethod("Win32Shutdown", mboShutdownParams, null);
                    }
                }
            }
        }

        private void cancel_Click(object sender, EventArgs e)
        {
            hg.Enabled = false;
            ok.Enabled = true;
            cancel.Enabled = false;
            gio.Enabled = true;
            phut.Enabled = true;
            giay.Enabled = true;
            comboBoxEx1.Enabled = true;
            lbComment.Text = "";
        }
        //

        //cac button choi nhac

        private void axWindowsMediaPlayer1_DoubleClickEvent(object sender, _WMPOCXEvents_DoubleClickEvent e)
        {
            axWindowsMediaPlayer1.fullScreen = true;
        }

        private void next_Click(object sender, EventArgs e)
        {
            axWindowsMediaPlayer1.Ctlcontrols.next();
        }

        private void back_Click(object sender, EventArgs e)
        {
            axWindowsMediaPlayer1.Ctlcontrols.previous();
        }

        private void pause_Click(object sender, EventArgs e)
        {
            axWindowsMediaPlayer1.Ctlcontrols.pause();
            pause.Hide();
            play.Show();
        }


        private void stop_Click(object sender, EventArgs e)
        {
            axWindowsMediaPlayer1.Ctlcontrols.stop();
        }

        private void play_Click(object sender, EventArgs e)
        {
            axWindowsMediaPlayer1.Ctlcontrols.play();
            play.Hide();
            pause.Show();
        }

        //choi nhac o che do random bai hat

        Random rd = new Random(DateTime.Now.Second);
        int vt = 0;

        private void shuffleoff_Click(object sender, EventArgs e)
        {
            if (ds.Items.Count > 0)
            {
                axWindowsMediaPlayer1.playlistCollection.remove(pl);
            }

            pl = axWindowsMediaPlayer1.playlistCollection.newPlaylist(myPlaylist);
            ListViewItem lvi;
            for (int i = 0; i < ds.Items.Count; i++)
            {
                vt = rd.Next(0, ds.Items.Count - 1);
                lvi = new ListViewItem(Path.GetFileNameWithoutExtension(lb2.Items[vt].ToString()));
                ds.Items[i] = lvi;
                m1 = axWindowsMediaPlayer1.newMedia(lb2.Items[vt].ToString());
                pl.appendItem(m1);
            }
            axWindowsMediaPlayer1.currentPlaylist = pl;
            shuffle.Show();
            shuffleoff.Hide();

        }

        private void shuffle_Click(object sender, EventArgs e)
        {
            shuffle.Hide();
            shuffleoff.Show();
        }

        private void repeat_Click(object sender, EventArgs e)
        {
            axWindowsMediaPlayer1.settings.setMode("loop", true);
            repeat_on.Show();
            repeat.Hide();
        }

        private void repeat_on_Click(object sender, EventArgs e)
        {
            axWindowsMediaPlayer1.settings.setMode("loop", false);
            repeat_on.Hide();
            repeat.Show();
        }
        private void mute_Click(object sender, EventArgs e)
        {
            axWindowsMediaPlayer1.settings.mute = true;
            mute.Hide();
            muteoff.Show();
        }

        private void muteoff_Click(object sender, EventArgs e)
        {
            axWindowsMediaPlayer1.settings.mute = false;
            muteoff.Hide();
            mute.Show();
        }

        //tab danh sach
        private void listView2_MouseClick(object sender, MouseEventArgs e)
        {
            for (int i = 0; i < listView2.Items.Count; i++)
                if (listView2.Items[i].Selected)
                {
                    str1 = lb2.Items[i].ToString();
                    so1 = i;
                }
        }

        private void listView2_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            try
            {
                IWMPMedia med = axWindowsMediaPlayer1.currentPlaylist.get_Item(listView2.SelectedIndices[0]);
                ListViewItem sel = listView2.SelectedItems[0];
                axWindowsMediaPlayer1.Ctlcontrols.playItem(med);
            }
            catch (Exception)
            {
            }
        }

        private void taoNode(string path)
        {
            n++;
            list[n] = new ListBox();
            treeView1.Nodes[0].Nodes.Add(Path.GetFileNameWithoutExtension(path));
            for (int i = 0; i < lb2.Items.Count; i++)
            {
                list[n].Items.Add(lb2.Items[i].ToString());
            }
            temp[n] = new ToolStripMenuItem();
            temp[n].Text = Path.GetFileNameWithoutExtension(path);
            addToToolStripMenuItem.DropDownItems.Add(temp[n]);
            temp[n].MergeIndex = n;
            temp[n].Click += new EventHandler(add_click);
        }

        void add_click(object sender, EventArgs e)
        {
            ToolStripMenuItem _temp = (ToolStripMenuItem)sender;           
            for (int i = 0; i <= n; i++)
            {
                if (_temp.MergeIndex == i)
                    list[i].Items.Add(lb2.Items[so1].ToString());
            }

        }

        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            try
            {
                listView1.Items.Clear();
                TreeNode selnode = e.Node;
                int i = selnode.Index;
                for (int j = 0; j < list[i].Items.Count; j++)
                {
                    Info(list[i].Items[j].ToString());
                }
            }
            catch { }
        }

        private void treeView1_NodeMouseDoubleClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            axWindowsMediaPlayer1.Ctlcontrols.stop();

            TreeNode selnode = e.Node;
            int i = selnode.Index;
            string[] s = new string[list[i].Items.Count];
            lb2.Items.Clear();
            listView2.Items.Clear();
            listView1.Items.Clear();
            ds.Items.Clear();

            for (int j = 0; j < list[i].Items.Count; j++)
            {
                s[j] = list[i].Items[j].ToString();
                lb2.Items.Add(s[j]);
                listView2.Items.Add(Path.GetFileNameWithoutExtension(s[j]));
            }

            plItems = axWindowsMediaPlayer1.playlistCollection.getByName(myPlaylist);
            if (plItems.count == 0)
            {
                pl = axWindowsMediaPlayer1.playlistCollection.newPlaylist(myPlaylist);
            }
            else
            {
                pl = plItems.Item(0);
            }
            int q = 0;
            foreach (string file in s)
            {
                ListViewItem item = new ListViewItem(s[q]);
                ds.Items.Add(Path.GetFileNameWithoutExtension(item.ToString()));
                q++;
                m1 = axWindowsMediaPlayer1.newMedia(file);
                pl.appendItem(m1);
            }

            //choi nhac                        
            axWindowsMediaPlayer1.currentPlaylist = pl;
            axWindowsMediaPlayer1.Ctlcontrols.play();
            axWindowsMediaPlayer1.playlistCollection.remove(pl);
        }

        private void Info(string f)
        {
            listView1.Items.Add(new ListViewItem(new string[] { Path.GetFileNameWithoutExtension(f.ToString()) }));
        }
        //menu 

        private void playToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                IWMPMedia med = axWindowsMediaPlayer1.currentPlaylist.get_Item(listView2.SelectedIndices[0]);
                ListViewItem sel = listView2.SelectedItems[0];
                axWindowsMediaPlayer1.Ctlcontrols.playItem(med);
            }
            catch { }
        }

        private void thoToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                int cur = this.listView2.FocusedItem.Index;
                FileInfo f = new FileInfo(lb2.Items[cur].ToString());
                string ten = f.Name;
                string kieu = f.Extension;
                string len = f.Length / 1024 / 1024 + " MB " + "(" + f.Length + " Bytes)";
                string path = f.FullName;
                string thoigian = axWindowsMediaPlayer1.Ctlcontrols.currentItem.durationString;
                thongtin tt = new thongtin();
                tt.set(ten, kieu, len, path, thoigian);
                tt.ShowDialog(this);
            }
            catch { }
        }
        //menu chuot phai tren danh sach
        private void find_Click(object sender, EventArgs e)
        {
            try
            {
                int cur = this.ds.FocusedItem.Index;
                FileInfo f = new FileInfo(lb2.Items[cur].ToString());
                Process.Start(f.Directory.ToString());
            }
            catch { }
        }

        private void playfile_Click(object sender, EventArgs e)
        {
            try
            {
                IWMPMedia med = axWindowsMediaPlayer1.currentPlaylist.get_Item(ds.SelectedIndices[0]);
                ListViewItem sel = ds.SelectedItems[0];
                axWindowsMediaPlayer1.Ctlcontrols.playItem(med);
            }
            catch (Exception)
            {
            }
        }

        private void tt_Click(object sender, EventArgs e)
        {
            try
            {
                int cur = this.ds.FocusedItem.Index;
                FileInfo f = new FileInfo(lb2.Items[cur].ToString());
                string ten = f.Name;
                string kieu = f.Extension;
                string len = f.Length / 1024 / 1024 + " MB " + "(" + f.Length + " Bytes)";
                string path = f.FullName;
                string thoigian = axWindowsMediaPlayer1.Ctlcontrols.currentItem.durationString;
                thongtin tt = new thongtin();
                tt.set(ten, kieu, len, path, thoigian);
                tt.ShowDialog(this);
            }
            catch { }
        }

        private void buttonItem7_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void delete_Click(object sender, EventArgs e)
        {
            try
            {
                IWMPMedia med = axWindowsMediaPlayer1.currentPlaylist.get_Item(ds.SelectedIndices[0]);
                ListViewItem sel = ds.SelectedItems[0];
                axWindowsMediaPlayer1.currentPlaylist.removeItem(med);
                ds.Items.Remove(sel);
                axWindowsMediaPlayer1.Ctlcontrols.play();
            }
            catch (Exception)
            {
            }
        }

        private void xoa_Click(object sender, EventArgs e)
        {
            try
            {
                listViewEx1.Items[listViewEx1.SelectedIndices[0]].Remove();
            }
            catch
            {
                MessageBox.Show("chọn File !");
            }
        }

        //tim loi bai hat
        private void button1_Click(object sender, EventArgs e)
        {
            string t1 = "&title=";
            string artok = "";
            string titleok = "";
            artok = textBox2.Text;
            string art=artok.Replace(" ", "%20");
            titleok = textBox1.Text;
            string title = titleok.Replace(" ", "%20");
            string s = "http://www.lyricsplugin.com/wmplayer03/plugin/?artist=" + art + t1 + title;
            GeneralValidation.IsUrl(s);
            //txtAddress.Text = s;
            try
            {
                webBrowser1.Navigate(s);
            }
            catch (Exception ex)
            {
                throw ex.InnerException;
            }
        }
    }
}
