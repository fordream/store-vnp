using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using PWR_UI;
using System.Collections;
using System.Runtime.InteropServices;
using System.Net;
using System.IO;
using Manina.Windows;
using WindowsFormsApplication3;
using System.Collections;

namespace WindowsFormsApplication9
{
    public partial class Form1 : Form
    {
        private static int flag = 3, vol = 0, button = 3,count_list = 0, count_play = 0,
            monitor_height, monitor_width, pic_max = int.MaxValue;
        private static Stack stack_play;
        private StringBuilder sbf = new StringBuilder();
        bool flagpicture=false, flaglibrary=false;
        ListView ListviewLibrary;
        Panel panel2;
        PWR_UI.MediaButton SearchLibrary;
        ComboBox combombox1;
        ComboBox combobox2;
        TextBox Content;
        Label textsize;
        Label textrender;
        ComboBox render;
        PictureBox ImagePicture;
        Panel panel3 = new Panel();
        private Manina.Windows.Forms.ImageListView imageListView2;
        private PWR_UI.MediaButton mediaButton14;
        private PWR_UI.MediaButton mediaButton17;
        private PWR_UI.MediaButton mediaButton18;
        private PWR_UI.MediaButton mediaButton19;
        private PWR_UI.MediaButton mediaButton20;
        //ToolStrip ts = new ToolStrip();
        private static string staying, str_lyric = string.Empty, str_length, str_play_path;
        private static string[] path = new String[0];
        private static bool fileopenning = false, flag_play = false, flag_full = false, flag_save = false,
            play_random = false, play_repeat = false;
        private static long longlength = 0, posi_min = 0;
        private static System.Windows.Forms.Timer time_main = new System.Windows.Forms.Timer();

        private static PictureBox[] pic;

        private OpenFileDialog openFileDialog1 = new OpenFileDialog(), openFileDialog2 = new OpenFileDialog();
        [DllImport("winmm.dll")]
        public static extern int waveOutGetVolume(IntPtr hwo, out uint dwVolume);
        [DllImport("winmm.dll")]
        public static extern int waveOutSetVolume(IntPtr hwo, uint dwVolume);
        [DllImport("winmm.dll")]
        public static extern long mciSendString(string stay, StringBuilder strbuilder, int width, IntPtr sign);

        public Form1()
        {
            start s = new start();
            System.Threading.Thread.Sleep(1000);
            s.Dispose();

            InitializeComponent();

            constructor();
        }
        public void AddControlPicture()
        {
            // 
            // mediaButton20
            // 
            mediaButton20 = new MediaButton();
            this.mediaButton20.AutoScrollMargin = new System.Drawing.Size(0, 0);
            this.mediaButton20.AutoScrollMinSize = new System.Drawing.Size(0, 0);
            this.mediaButton20.BorderStyle = PWR_UI.BorderStyle.Thin;
            this.mediaButton20.CheckStyle = false;
            this.mediaButton20.ContextMenuStrip = null;
            this.mediaButton20.DisabledForeColor = System.Drawing.Color.DarkGray;
            this.mediaButton20.FocusedForeColor = System.Drawing.Color.WhiteSmoke;
            this.mediaButton20.FocusOnClick = false;
            this.mediaButton20.FocusOnHover = false;
            this.mediaButton20.Image = global::WindowsFormsApplication9.Properties.Resources.galleryview;
            this.mediaButton20.ImageAlign = PWR_UI.MediaButton.Alignment.MiddleCenter;
            this.mediaButton20.ImageFocused = null;
            this.mediaButton20.ImagePadding = new System.Windows.Forms.Padding(8);
            this.mediaButton20.Location = new System.Drawing.Point(525, 37);
            this.mediaButton20.ResizeOnLoad = false;
            this.mediaButton20.Size = new System.Drawing.Size(64, 64);
            this.mediaButton20.Style = PWR_UI.MediaButton.ButtonStyle.MenuButton;
            this.mediaButton20.TabIndex = 1136;
            toolTip1.SetToolTip(mediaButton20, "View_Gallery");
            this.mediaButton20.TextAlign = PWR_UI.MediaButton.Alignment.BottomRight;
            this.mediaButton20.TextPadding = new System.Windows.Forms.Padding(4);
            mediaButton20.Click += new EventHandler(mediaButton20_Click);
            this.Controls.Add(mediaButton20);
            // 
            // mediaButton19
            // 
            mediaButton19 = new MediaButton();
            this.mediaButton19.AutoScrollMargin = new System.Drawing.Size(0, 0);
            this.mediaButton19.AutoScrollMinSize = new System.Drawing.Size(0, 0);
            this.mediaButton19.BorderStyle = PWR_UI.BorderStyle.Thin;
            this.mediaButton19.CheckStyle = false;
            this.mediaButton19.ContextMenuStrip = null;
            this.mediaButton19.DisabledForeColor = System.Drawing.Color.DarkGray;
            this.mediaButton19.FocusedForeColor = System.Drawing.Color.WhiteSmoke;
            this.mediaButton19.FocusOnClick = false;
            this.mediaButton19.FocusOnHover = false;
            this.mediaButton19.Image = global::WindowsFormsApplication9.Properties.Resources.pane;
            this.mediaButton19.ImageAlign = PWR_UI.MediaButton.Alignment.MiddleCenter;
            this.mediaButton19.ImageFocused = null;
            this.mediaButton19.ImagePadding = new System.Windows.Forms.Padding(8);
            this.mediaButton19.Location = new System.Drawing.Point(441, 38);
            this.mediaButton19.ResizeOnLoad = false;
            this.mediaButton19.Size = new System.Drawing.Size(64, 64);
            this.mediaButton19.Style = PWR_UI.MediaButton.ButtonStyle.MenuButton;
            this.mediaButton19.TabIndex = 1135;
            this.mediaButton19.TextAlign = PWR_UI.MediaButton.Alignment.BottomRight;
            this.mediaButton19.TextPadding = new System.Windows.Forms.Padding(4);
            toolTip1.SetToolTip(mediaButton19, "View_Pane");
            mediaButton19.Click += new EventHandler(mediaButton19_Click);
            this.Controls.Add(mediaButton19);
            // 
            // mediaButton18
            // 
            mediaButton18 = new MediaButton();
            this.mediaButton18.AutoScrollMargin = new System.Drawing.Size(0, 0);
            this.mediaButton18.AutoScrollMinSize = new System.Drawing.Size(0, 0);
            this.mediaButton18.BorderStyle = PWR_UI.BorderStyle.Thin;
            this.mediaButton18.CheckStyle = false;
            this.mediaButton18.ContextMenuStrip = null;
            this.mediaButton18.DisabledForeColor = System.Drawing.Color.DarkGray;
            this.mediaButton18.FocusedForeColor = System.Drawing.Color.WhiteSmoke;
            this.mediaButton18.FocusOnClick = false;
            this.mediaButton18.FocusOnHover = false;
            this.mediaButton18.Image = global::WindowsFormsApplication9.Properties.Resources.thumnials1;
            this.mediaButton18.ImageAlign = PWR_UI.MediaButton.Alignment.MiddleCenter;
            this.mediaButton18.ImageFocused = null;
            this.mediaButton18.ImagePadding = new System.Windows.Forms.Padding(8);
            this.mediaButton18.Location = new System.Drawing.Point(362, 36);
            this.mediaButton18.ResizeOnLoad = false;
            this.mediaButton18.Size = new System.Drawing.Size(64, 64);
            this.mediaButton18.Style = PWR_UI.MediaButton.ButtonStyle.MenuButton;
            this.mediaButton18.TabIndex = 1134;
            this.mediaButton18.TextAlign = PWR_UI.MediaButton.Alignment.BottomRight;
            this.mediaButton18.TextPadding = new System.Windows.Forms.Padding(4);
            toolTip1.SetToolTip(mediaButton18, "View_Thumnails");
            mediaButton18.Click += new EventHandler(mediaButton18_Click);
            this.Controls.Add(mediaButton18);
            // 
            // mediaButton17
            // 
            mediaButton17 = new MediaButton();
            this.mediaButton17.AutoScrollMargin = new System.Drawing.Size(0, 0);
            this.mediaButton17.AutoScrollMinSize = new System.Drawing.Size(0, 0);
            this.mediaButton17.BorderStyle = PWR_UI.BorderStyle.Thin;
            this.mediaButton17.CheckStyle = false;
            this.mediaButton17.ContextMenuStrip = null;
            this.mediaButton17.DisabledForeColor = System.Drawing.Color.DarkGray;
            this.mediaButton17.FocusedForeColor = System.Drawing.Color.WhiteSmoke;
            this.mediaButton17.FocusOnClick = false;
            this.mediaButton17.FocusOnHover = false;
            this.mediaButton17.Image = global::WindowsFormsApplication9.Properties.Resources.detailed;
            this.mediaButton17.ImageAlign = PWR_UI.MediaButton.Alignment.MiddleCenter;
            this.mediaButton17.ImageFocused = null;
            this.mediaButton17.ImagePadding = new System.Windows.Forms.Padding(8);
            this.mediaButton17.Location = new System.Drawing.Point(282, 38);
            this.mediaButton17.ResizeOnLoad = false;
            this.mediaButton17.Size = new System.Drawing.Size(64, 64);
            this.mediaButton17.Style = PWR_UI.MediaButton.ButtonStyle.MenuButton;
            this.mediaButton17.TabIndex = 1133;
            this.mediaButton17.TextAlign = PWR_UI.MediaButton.Alignment.BottomRight;
            this.mediaButton17.TextPadding = new System.Windows.Forms.Padding(4);
            toolTip1.SetToolTip(mediaButton17, " View_Details");
            mediaButton17.Click += new EventHandler(mediaButton17_Click);
            this.Controls.Add(mediaButton17);
            // 
            // mediaButton14
            // 
            mediaButton14 = new MediaButton();
            this.mediaButton14.AutoScrollMargin = new System.Drawing.Size(0, 0);
            this.mediaButton14.AutoScrollMinSize = new System.Drawing.Size(0, 0);
            this.mediaButton14.BorderStyle = PWR_UI.BorderStyle.Thin;
            this.mediaButton14.CheckStyle = false;
            this.mediaButton14.ContextMenuStrip = null;
            this.mediaButton14.DisabledForeColor = System.Drawing.Color.DarkGray;
            this.mediaButton14.FocusedForeColor = System.Drawing.Color.WhiteSmoke;
            this.mediaButton14.FocusOnClick = false;
            this.mediaButton14.FocusOnHover = false;
            this.mediaButton14.Image = global::WindowsFormsApplication9.Properties.Resources.add;
            this.mediaButton14.ImageAlign = PWR_UI.MediaButton.Alignment.MiddleCenter;
            this.mediaButton14.ImageFocused = null;
            this.mediaButton14.ImagePadding = new System.Windows.Forms.Padding(8);
            this.mediaButton14.Location = new System.Drawing.Point(197, 40);
            this.mediaButton14.ResizeOnLoad = false;
            this.mediaButton14.Size = new System.Drawing.Size(64, 64);
            this.mediaButton14.Style = PWR_UI.MediaButton.ButtonStyle.MenuButton;
            this.mediaButton14.TabIndex = 1132;
            this.mediaButton14.TextAlign = PWR_UI.MediaButton.Alignment.BottomRight;
            this.mediaButton14.TextPadding = new System.Windows.Forms.Padding(4);
            toolTip1.SetToolTip(mediaButton14, "AddImage");
            mediaButton14.Click += new EventHandler(mediaButton14_Click);
            this.Controls.Add(mediaButton14);
            //
            //combobox2
            //
            combobox2 = new ComboBox();
            combobox2.FormattingEnabled = true;
            combobox2.Items.AddRange(new object[] {
            "48x48",
            "96x96",
            "120x120",
            "150x150",
            "200x200"});
            combobox2.Location = new System.Drawing.Point(685, 44);
            combobox2.Size = new System.Drawing.Size(121, 21);
            combobox2.TabIndex = 1121;
            combobox2.Text = "Select Size";
            combobox2.SelectedIndexChanged += new EventHandler(combobox2_SelectedIndexChanged);
            this.Controls.Add(combobox2);
            //
            //combobox render
            //
            render = new ComboBox();
            render.FormattingEnabled = true;
            render.Items.AddRange(new object[] {
            "DefaultRenderer",
            "NewYear2010Renderer",
            "NoirRenderer",
            "TilesRenderer",
            "XPRenderer",
            "ZoomingRenderer"});
            render.Location = new System.Drawing.Point(685, 70);
            render.Size = new System.Drawing.Size(121, 21);
            render.Text = "Select Renderer";
            render.SelectedIndexChanged += new EventHandler(render_SelectedIndexChanged);
            this.Controls.Add(render);
            //textsize
            //
            textsize = new Label();
            textsize.Font = new System.Drawing.Font("VNI-Fato", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            textsize.ForeColor = System.Drawing.SystemColors.ButtonHighlight;
            textsize.Location = new System.Drawing.Point(598, 39);
            textsize.Size = new System.Drawing.Size(64, 26);
            textsize.TabIndex = 1133;
            textsize.Text = "SIZE";
            this.Controls.Add(textsize);
            //
            //textrender
            // 
            textrender = new Label();
            textrender.Font = new System.Drawing.Font("VNI-Fato", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            textrender.ForeColor = System.Drawing.SystemColors.ButtonHighlight;
            textrender.Location = new System.Drawing.Point(598, 67);
            textrender.Size = new System.Drawing.Size(100, 26);
            textrender.TabIndex = 1133;
            textrender.Text = "RENDER";
            this.Controls.Add(textrender);
            // imageListView1
            // 
            imageListView2 = new Manina.Windows.Forms.ImageListView();
            this.imageListView2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.imageListView2.HeaderFont = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(162)));
            this.imageListView2.Location = new System.Drawing.Point(0, 0);
            this.imageListView2.Size = new System.Drawing.Size(810, 562);
            Manina.Windows.Forms.ImageListViewRenderers.NoirRenderer re = new Manina.Windows.Forms.ImageListViewRenderers.NoirRenderer();
            imageListView2.SetRenderer(re);
            //imageListView2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(8)))), ((int)(((byte)(42)))), ((int)(((byte)(105)))));
            this.imageListView2.TabIndex = 2;
            this.imageListView2.Text = "";
            //
            //panel3
            //
            panel3 = new Panel();
            this.panel3.Controls.Add(this.imageListView2);
            this.panel3.Location = new System.Drawing.Point(192, 132);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(830, 600);
            this.panel3.TabIndex = 1131;
            this.Controls.Add(panel3);
        }
        public void Hidepicture(bool flagpic)
        {
            if (flagpic)
            {
                mediaButton14.Hide();
                mediaButton17.Hide();
                mediaButton18.Hide();
                mediaButton19.Hide();
                mediaButton20.Hide();
                combobox2.Hide();
                panel3.Hide();
                textsize.Hide();
                textrender.Hide();
                render.Hide();
                mediaButton9.Show();
            }
            else
            {
            }
        }

        #region event picture
        private void mediaButton14_Click(object sender, EventArgs e)
        {
            openFileDialog3.Title = "My Picture";
            if (openFileDialog3.ShowDialog() == DialogResult.OK)
            {
                foreach (string list in openFileDialog3.FileNames)
                {
                    imageListView2.Items.Add(list);
                }
            }
        }
        private void mediaButton17_Click(object sender, EventArgs e)
        {
            imageListView2.View = Manina.Windows.Forms.View.Details;
        }
        private void mediaButton20_Click(object sender, EventArgs e)
        {
            imageListView2.View = Manina.Windows.Forms.View.Gallery;
        }
        private void mediaButton19_Click(object sender, EventArgs e)
        {
            imageListView2.View = Manina.Windows.Forms.View.Pane;
        }
        private void mediaButton18_Click(object sender, EventArgs e)
        {
            imageListView2.View = Manina.Windows.Forms.View.Thumbnails;
        }
        private void combobox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (combobox2.SelectedIndex > -1)
            {
                switch (combobox2.SelectedIndex)
                {
                    case 0:
                        {
                            imageListView2.ThumbnailSize = new Size(48, 48);
                            break;
                        }
                    case 1:
                        {
                            imageListView2.ThumbnailSize = new Size(96, 96);
                            break;
                        }
                    case 2:
                        {
                            imageListView2.ThumbnailSize = new Size(120, 120);
                            break;
                        }
                    case 3:
                        {
                            imageListView2.ThumbnailSize = new Size(150, 150);
                            break;
                        }
                    case 4:
                        {
                            imageListView2.ThumbnailSize = new Size(200, 200);
                            break;
                        }
                }
            }
        }
        private void render_SelectedIndexChanged(object sender, EventArgs e)
        {
            switch (render.SelectedIndex)
            {
                case 0:
                    {
                        Manina.Windows.Forms.ImageListViewRenderers.DefaultRenderer re = new Manina.Windows.Forms.ImageListViewRenderers.DefaultRenderer();
                        imageListView2.SetRenderer(re);
                        break;
                    }
                case 1:
                    {
                        Manina.Windows.Forms.ImageListViewRenderers.NewYear2010Renderer re = new Manina.Windows.Forms.ImageListViewRenderers.NewYear2010Renderer();
                        imageListView2.SetRenderer(re);
                        break;
                    }
                case 2:
                    {
                        Manina.Windows.Forms.ImageListViewRenderers.NoirRenderer re = new Manina.Windows.Forms.ImageListViewRenderers.NoirRenderer();
                        imageListView2.SetRenderer(re);
                        break;
                    }
                case 3:
                    {
                        Manina.Windows.Forms.ImageListViewRenderers.TilesRenderer re = new Manina.Windows.Forms.ImageListViewRenderers.TilesRenderer();
                        imageListView2.SetRenderer(re);
                        break;
                    }
                case 4:
                    {
                        Manina.Windows.Forms.ImageListViewRenderers.XPRenderer re = new Manina.Windows.Forms.ImageListViewRenderers.XPRenderer();
                        imageListView2.SetRenderer(re);
                        break;
                    }
                case 5:
                    {
                        Manina.Windows.Forms.ImageListViewRenderers.ZoomingRenderer re = new Manina.Windows.Forms.ImageListViewRenderers.ZoomingRenderer();
                        imageListView2.SetRenderer(re);
                        break;
                    }
            }

        }
        #endregion

        #region Library
        public void AddControlLibary()
        {
            // 
            // ListviewLibrary
            // 
            ListviewLibrary = new ListView();
            ListviewLibrary.Location = new System.Drawing.Point(3, 3);
            ListviewLibrary.Size = new System.Drawing.Size(665, 525);
            ListviewLibrary.TabIndex = 0;
            ListviewLibrary.View = View.Details;
            ListviewLibrary.SmallImageList = imageList1;
            ListviewLibrary.Columns.Add("MP3", 300, HorizontalAlignment.Center);
            ListviewLibrary.Columns.Add("Tile", 100, HorizontalAlignment.Center);
            ListviewLibrary.Columns.Add("Artsist", 100, HorizontalAlignment.Center);
            ListviewLibrary.Columns.Add("Album", 100, HorizontalAlignment.Center);
            ListviewLibrary.Columns.Add("Year", 50, HorizontalAlignment.Center);
            
            ListviewLibrary.UseCompatibleStateImageBehavior = false;
            ListviewLibrary.FullRowSelect = true;
            ListviewLibrary.MouseClick += new MouseEventHandler(ListviewLibrary_MouseClick);
            ListviewLibrary.DoubleClick += new EventHandler(ListviewLibrary_DoubleClick);

            // 
            // ImagePicture
            // 
            ImagePicture = new PictureBox();
            ImagePicture.Location = new System.Drawing.Point(671, 3);
            ImagePicture.Size = new System.Drawing.Size(180, 150);
            ImagePicture.BackgroundImageLayout = ImageLayout.Stretch;
            //
            // panel1
            // 
            panel2 = new Panel();
            panel2.Controls.Add(ImagePicture);
            panel2.Controls.Add(ListviewLibrary);
            panel2.Location = new System.Drawing.Point(226, 146);
            panel2.Size = new System.Drawing.Size(877, 528);
            panel2.TabIndex = 1119;
            this.Controls.Add(panel2);
            // 
            // Content
            // 
            Content = new TextBox();
            Content.Location = new System.Drawing.Point(226, 107);
            Content.Size = new System.Drawing.Size(115, 20);
            Content.TabIndex = 1120;
            Content.KeyPress += new KeyPressEventHandler(Content_KeyPress);
            this.Controls.Add(Content);
            // 
            // combombox1
            // 
            combombox1 = new ComboBox();
            combombox1.FormattingEnabled = true;
            combombox1.Items.AddRange(new object[] {
            "Artist",
            "Album",
            "Year",
            "Default"});
            combombox1.Location = new System.Drawing.Point(437, 106);
            combombox1.Size = new System.Drawing.Size(121, 21);
            combombox1.TabIndex = 1121;
            combombox1.SelectedIndexChanged += new EventHandler(combombox1_SelectedIndexChanged);
            this.Controls.Add(combombox1);
            //
            //searchLibrary
            //
            SearchLibrary = new MediaButton();
            this.SearchLibrary.AutoScrollMargin = new System.Drawing.Size(0, 0);
            this.SearchLibrary.AutoScrollMinSize = new System.Drawing.Size(0, 0);
            this.SearchLibrary.BorderStyle = PWR_UI.BorderStyle.Thin;
            this.SearchLibrary.DisabledForeColor = System.Drawing.Color.DarkGray;
            this.SearchLibrary.FocusedForeColor = System.Drawing.Color.WhiteSmoke;
            this.SearchLibrary.FocusOnClick = false;
            this.SearchLibrary.FocusOnHover = false;
            this.SearchLibrary.Image = global::WindowsFormsApplication9.Properties.Resources.Search_text;
            this.SearchLibrary.ImageAlign = PWR_UI.MediaButton.Alignment.MiddleCenter;
            this.SearchLibrary.ImageFocused = null;
            this.SearchLibrary.ImagePadding = new System.Windows.Forms.Padding(8);
            this.SearchLibrary.Location = new System.Drawing.Point(361, 89);
            this.SearchLibrary.ResizeOnLoad = false;
            this.SearchLibrary.Size = new System.Drawing.Size(56, 51);
            this.SearchLibrary.Style = PWR_UI.MediaButton.ButtonStyle.MenuButton;
            this.SearchLibrary.TabIndex = 1122;
            this.SearchLibrary.TextAlign = PWR_UI.MediaButton.Alignment.BottomRight;
            this.SearchLibrary.TextPadding = new System.Windows.Forms.Padding(4);
            SearchLibrary.Click += new EventHandler(SearchLibrary_Click);
            this.Controls.Add(SearchLibrary);
        }

        private void ListviewLibrary_DoubleClick(object sender, EventArgs e)
        {
            ListViewItem item = ListviewLibrary.SelectedItems[0];
            add_playlist(readwrite.read("Data.txt"), Convert.ToInt32(item.Name));
            stop1();
            play_play(item.Tag.ToString());
        }

        public void Hidelibrary(bool flagLi)
        {
            if (flagLi)
            {
                panel2.Hide();
                Content.Hide();
                combombox1.Hide();
                SearchLibrary.Hide();
                mediaButton9.Show();
            }
            else
            { }
            
        }

        public void AddItemListview(string[] arrString)
        {
            int dem = 0;
            int n = arrString.Length;
            try
            {
                for (int k = 0; k < n; k++)
                {
                    ListViewItem item = new ListViewItem();
                    string temp = arrString[k];
                    string path = temp;
                    if (temp.Contains("\r\n"))
                    {
                        path = "";
                        string delim = "\r\n";//luot bo khi no xuat hien trong chuoi temp
                        path = temp.Trim(delim.ToCharArray());
                    }

                    if (File.Exists(path))
                    {

                        ID3vDetails detail = new ID3vDetails(path);
                        item.Text = detail.Namemp3;
                        if (detail.Attach != null)
                        {
                            ListviewLibrary.SmallImageList.Images.Add(detail.Attach);
                            item.ImageIndex = ListviewLibrary.SmallImageList.Images.Count - 1;
                        }
                        else
                            item.ImageKey = "mp3";
                        item.Name = dem.ToString();
                        item.Tag = path;
                        string tile = detail.Title;
                        item.SubItems.Add(tile);
                        string artist = detail.Artist;
                        item.SubItems.Add(artist);
                        string album = detail.Album;
                        item.SubItems.Add(album);
                        string year = detail.Year;
                        item.SubItems.Add(year);
                        ListviewLibrary.Items.Add(item);
                        ++dem;
                    }
                }


            }
            catch (Exception ex)
            {
            }
            if (dem != n)
            {
                for (int k = dem + 1; k < n; k++)
                {
                    ListViewItem item = new ListViewItem();
                    string temp = arrString[k];
                    string path = temp;
                    if (temp.Contains("\r\n"))
                    {
                        path = "";
                        string delim = "\r\n";//luot bo khi no xuat hien trong chuoi temp
                        path = temp.Trim(delim.ToCharArray());
                    }

                    if (File.Exists(path))
                    {

                        ID3vDetails detail = new ID3vDetails(path);
                        item.Text = detail.Namemp3;
                        if (detail.Attach != null)
                        {
                            ListviewLibrary.SmallImageList.Images.Add(detail.Attach);
                            item.ImageIndex = ListviewLibrary.SmallImageList.Images.Count - 1;
                        }
                        else
                            item.ImageKey = "mp3";
                        item.Name = dem.ToString();
                        item.Tag = path;
                        string tile = detail.Title;
                        item.SubItems.Add(tile);
                        string artist = detail.Artist;
                        item.SubItems.Add(artist);
                        string album = detail.Album;
                        item.SubItems.Add(album);
                        string year = detail.Year;
                        item.SubItems.Add(year);
                        ListviewLibrary.Items.Add(item);
                        ++dem;
                    }
                }
            }
        }
        public void AddItemListview1(string[] arrString, string[] info)
        {
            int dem = 0;
            ListViewGroup[] listGroup = new ListViewGroup[info.Length];
            int i;
            for (i = 0; i < info.Length; i++)
            {

                if (i < info.Length - 1)
                {
                    listGroup[i] = new ListViewGroup(info[i], HorizontalAlignment.Left);
                    listGroup[i].Header = info[i].ToString();
                }
                else
                {
                    listGroup[i] = new ListViewGroup("UnKnow", HorizontalAlignment.Left);
                    listGroup[i].Header = "UnKnow";
                }
                ListviewLibrary.Groups.Add(listGroup[i]);
            }
            int n = arrString.Length;
            try
            {

                for (int k = 0; k < n; k++)
                {
                    ListViewItem item = new ListViewItem();
                    string temp = arrString[k];
                    string path = temp;
                    if (temp.Contains("\r\n"))
                    {
                        path = "";
                        string delim = "\r\n";//luot bo khi no xuat hien trong chuoi temp
                        path = temp.Trim(delim.ToCharArray());
                    }
                    if (File.Exists(path))
                    {
                        ID3vDetails detail = new ID3vDetails(path);
                        ++dem;
                        item.Text = detail.Namemp3;
                        if (detail.Attach != null)
                        {
                            ListviewLibrary.SmallImageList.Images.Add(detail.Attach);
                            item.ImageIndex = ListviewLibrary.SmallImageList.Images.Count - 1;
                        }
                        else
                            item.ImageKey = "mp3";
                        item.Tag = path;
                        string tile = detail.Title;
                        item.SubItems.Add(tile);
                        string artist = detail.Artist;
                        item.SubItems.Add(artist);
                        string album = detail.Album;
                        item.SubItems.Add(album);
                        string year = detail.Year;
                        for (i = 0; i < info.Length; i++)
                        {
                            if (artist == listGroup[i].Header)
                            {
                                item.Group = listGroup[i];
                                break;
                            }
                            else
                                item.Group = listGroup[info.Length - 1];
                        }
                        item.SubItems.Add(year);
                        ListviewLibrary.Items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            { }
            if (dem != n)
            {
                for (int k = dem + 1; k < n; k++)
                {
                    ListViewItem item = new ListViewItem();
                    string temp = arrString[k];
                    string path = temp;
                    if (temp.Contains("\r\n"))
                    {
                        path = "";
                        string delim = "\r\n";//luot bo khi no xuat hien trong chuoi temp
                        path = temp.Trim(delim.ToCharArray());
                    }
                    if (File.Exists(path))
                    {
                        ID3vDetails detail = new ID3vDetails(path);
                        ++dem;
                        item.Text = detail.Namemp3;
                        if (detail.Attach != null)
                        {
                            ListviewLibrary.SmallImageList.Images.Add(detail.Attach);
                            item.ImageIndex = ListviewLibrary.SmallImageList.Images.Count - 1;
                        }
                        else
                            item.ImageKey = "mp3";
                        item.Tag = path;
                        string tile = detail.Title;
                        item.SubItems.Add(tile);
                        string artist = detail.Artist;
                        item.SubItems.Add(artist);
                        string album = detail.Album;
                        item.SubItems.Add(album);
                        string year = detail.Year;
                        for (i = 0; i < info.Length; i++)
                        {
                            if (artist == listGroup[i].Header)
                            {
                                item.Group = listGroup[i];
                                break;
                            }
                            else
                                item.Group = listGroup[info.Length - 1];
                        }
                        item.SubItems.Add(year);
                        ListviewLibrary.Items.Add(item);
                    }
                }
            }
        }
        public void AddItemListview2(string[] arrString, string[] info)
        {
            ListViewGroup[] listGroup = new ListViewGroup[info.Length];
            int i;
            int dem = 0;
            for (i = 0; i < info.Length; i++)
            {

                if (i < info.Length - 1)
                {
                    listGroup[i] = new ListViewGroup(info[i], HorizontalAlignment.Left);
                    listGroup[i].Header = info[i].ToString();
                }
                else
                {
                    listGroup[i] = new ListViewGroup("UnKnow", HorizontalAlignment.Left);
                    listGroup[i].Header = "UnKnow";
                }
                ListviewLibrary.Groups.Add(listGroup[i]);
            }
            int n = arrString.Length;
            try
            {

                for (int k = 0; k < n; k++)
                {
                    ListViewItem item = new ListViewItem();
                    string temp = arrString[k];
                    string path = temp;
                    if (temp.Contains("\r\n"))
                    {
                        path = "";
                        string delim = "\r\n";//luot bo khi no xuat hien trong chuoi temp
                        path = temp.Trim(delim.ToCharArray());
                    }
                    if (File.Exists(path))
                    {
                        ID3vDetails detail = new ID3vDetails(path);
                        ++dem;
                        item.Text = detail.Namemp3;
                        if (detail.Attach != null)
                        {
                            ListviewLibrary.SmallImageList.Images.Add(detail.Attach);
                            item.ImageIndex = ListviewLibrary.SmallImageList.Images.Count - 1;
                        }
                        else
                            item.ImageKey = "mp3";
                        item.Tag = path;
                        string tile = detail.Title;
                        item.SubItems.Add(tile);
                        string artist = detail.Artist;
                        item.SubItems.Add(artist);
                        string album = detail.Album;
                        item.SubItems.Add(album);
                        string year = detail.Year;
                        for (i = 0; i < info.Length; i++)
                        {
                            if (album == listGroup[i].Header)
                            {
                                item.Group = listGroup[i];
                                break;
                            }
                            else
                                item.Group = listGroup[info.Length - 1];
                        }
                        item.SubItems.Add(year);
                        ListviewLibrary.Items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            { }
            if (dem != n)
            {
                for (int k = dem + 1; k < n; k++)
                {
                    ListViewItem item = new ListViewItem();
                    string temp = arrString[k];
                    string path = temp;
                    if (temp.Contains("\r\n"))
                    {
                        path = "";
                        string delim = "\r\n";//luot bo khi no xuat hien trong chuoi temp
                        path = temp.Trim(delim.ToCharArray());
                    }
                    if (File.Exists(path))
                    {
                        ID3vDetails detail = new ID3vDetails(path);
                        item.Text = detail.Namemp3;
                        if (detail.Attach != null)
                        {
                            ListviewLibrary.SmallImageList.Images.Add(detail.Attach);
                            item.ImageIndex = ListviewLibrary.SmallImageList.Images.Count - 1;
                        }
                        else
                            item.ImageKey = "mp3";
                        item.Tag = path;
                        string tile = detail.Title;
                        item.SubItems.Add(tile);
                        string artist = detail.Artist;
                        item.SubItems.Add(artist);
                        string album = detail.Album;
                        item.SubItems.Add(album);
                        string year = detail.Year;
                        for (i = 0; i < info.Length; i++)
                        {
                            if (album == listGroup[i].Header)
                            {
                                item.Group = listGroup[i];
                                break;
                            }
                            else
                                item.Group = listGroup[info.Length - 1];
                        }
                        item.SubItems.Add(year);
                        ListviewLibrary.Items.Add(item);
                    }
                }
            }
        }
        public void AddItemListview3(string[] arrString, string[] info)
        {
            ListViewGroup[] listGroup = new ListViewGroup[info.Length];
            int i;
            int dem = 0;
            for (i = 0; i < info.Length; i++)
            {

                if (i < info.Length - 1)
                {
                    listGroup[i] = new ListViewGroup(info[i], HorizontalAlignment.Left);
                    listGroup[i].Header = info[i].ToString();
                }
                else
                {
                    listGroup[i] = new ListViewGroup("UnKnow", HorizontalAlignment.Left);
                    listGroup[i].Header = "UnKnow";
                }
                ListviewLibrary.Groups.Add(listGroup[i]);
            }
            int n = arrString.Length;
            try
            {
                for (int k = 0; k < n; k++)
                {
                    ListViewItem item = new ListViewItem();
                    string temp = arrString[k];
                    string path = temp;
                    if (temp.Contains("\r\n"))
                    {
                        path = "";
                        string delim = "\r\n";//luot bo khi no xuat hien trong chuoi temp
                        path = temp.Trim(delim.ToCharArray());
                    }
                    if (File.Exists(path))
                    {
                        ID3vDetails detail = new ID3vDetails(path);
                        ++dem;
                        item.Text = detail.Namemp3;
                        if (detail.Attach != null)
                        {
                            ListviewLibrary.SmallImageList.Images.Add(detail.Attach);
                            item.ImageIndex = ListviewLibrary.SmallImageList.Images.Count - 1;
                        }
                        else
                            item.ImageKey = "mp3";
                        item.Tag = path;
                        string tile = detail.Title;
                        item.SubItems.Add(tile);
                        string artist = detail.Artist;
                        item.SubItems.Add(artist);
                        string album = detail.Album;
                        item.SubItems.Add(album);
                        string year = detail.Year;
                        for (i = 0; i < info.Length; i++)
                        {
                            if (year == listGroup[i].Header)
                            {
                                item.Group = listGroup[i];
                                break;
                            }
                            else
                                item.Group = listGroup[info.Length - 1];
                        }
                        item.SubItems.Add(year);
                        ListviewLibrary.Items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            { }
            if (dem != n)
            {
                for (int k = dem + 1; k < n; k++)
                {
                    ListViewItem item = new ListViewItem();
                    string temp = arrString[k];
                    string path = temp;
                    if (temp.Contains("\r\n"))
                    {
                        path = "";
                        string delim = "\r\n";//luot bo khi no xuat hien trong chuoi temp
                        path = temp.Trim(delim.ToCharArray());
                    }
                    if (File.Exists(path))
                    {
                        ID3vDetails detail = new ID3vDetails(path);
                        ++dem;
                        item.Text = detail.Namemp3;
                        if (detail.Attach != null)
                        {
                            ListviewLibrary.SmallImageList.Images.Add(detail.Attach);
                            item.ImageIndex = ListviewLibrary.SmallImageList.Images.Count - 1;
                        }
                        else
                            item.ImageKey = "mp3";
                        item.Tag = path;
                        string tile = detail.Title;
                        item.SubItems.Add(tile);
                        string artist = detail.Artist;
                        item.SubItems.Add(artist);
                        string album = detail.Album;
                        item.SubItems.Add(album);
                        string year = detail.Year;
                        for (i = 0; i < info.Length; i++)
                        {
                            if (year == listGroup[i].Header)
                            {
                                item.Group = listGroup[i];
                                break;
                            }
                            else
                                item.Group = listGroup[info.Length - 1];
                        }
                        item.SubItems.Add(year);
                        ListviewLibrary.Items.Add(item);
                    }
                }
            }
        }
        #endregion 

        #region even Libraty
        private void combombox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (combombox1.SelectedIndex > -1)
            {
                switch (combombox1.SelectedIndex)
                {
                    case 0:
                        {
                            string[] stringArtist = readwrite.StringGroup(2);
                            string[] arrString = readwrite.read("Data.txt");
                            ListviewLibrary.Items.Clear();
                            AddItemListview1(arrString, stringArtist);
                            break;
                        }
                    case 1:
                        {
                            string[] stringAlbum = readwrite.StringGroup(3);
                            string[] arrString = readwrite.read("Data.txt");
                            ListviewLibrary.Items.Clear();
                            AddItemListview2(arrString, stringAlbum);
                            break;
                        }
                    case 2:
                        {
                            string[] stringYear = readwrite.StringGroup(4);
                            string[] arrString = readwrite.read("Data.txt");
                            ListviewLibrary.Items.Clear();
                            AddItemListview3(arrString, stringYear);
                            break;
                        }
                    case 3:
                        {
                            ListviewLibrary.Items.Clear();
                            string[] arrString = readwrite.read("Data.txt");
                            AddItemListview(arrString);
                            break;
                        }
                }
            }
        }
        private void SearchLibrary_Click(object sender, EventArgs e)
        {
            ListviewLibrary.Items.Clear();
            ArrayList ArrList = new ArrayList();
            string[] ArrString = readwrite.read("Search.txt");
            int n = ArrString.Length;
            int i, j;
            for (i = 0; i < n; i = i + 5)
            {
                for (j = i; j <= i + 4; j++)
                {
                    if (j >= n)
                        break;
                    if (readwrite.SearchSubString(ArrString[j].ToUpper(), Content.Text.ToUpper()))
                    {
                        ArrList.Add(ArrString[i]);
                        break;
                    }
                }
            }
            int m = ArrList.Count;
            string[] ArrResult = new string[m];
            for (i = 0; i < m; i++)
            {
                ArrResult[i] = ArrList[i].ToString();
            }
            AddItemListview(ArrResult);

        }
        private void Content_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == 13)
                SearchLibrary_Click(e.KeyChar, e);
        }
        private void ListviewLibrary_DoubleClick(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                ListViewItem item = ListviewLibrary.SelectedItems[0];
                play_play(item.Tag.ToString());
            }
        }
        private void ListviewLibrary_MouseClick(object sender, MouseEventArgs e)
        {
            try
            {
                if (ListviewLibrary.Items.Count > 0)
                {
                    int i = 0;
                    ListViewItem item = ListviewLibrary.SelectedItems[0];
                    //string[] arrString = readwrite.read("Data.txt");
                    //for (i = 0; i < arrString.Length; i++)
                    //{
                    //    if (readwrite.SearchSubString(arrString[i], item.Text))
                    //        break;
                    //}
                    ID3vDetails detail = new ID3vDetails(item.Tag.ToString());
                    if (detail.Attach != null)
                        ImagePicture.BackgroundImage = detail.Attach;
                    else
                        ImagePicture.BackgroundImage = global::WindowsFormsApplication9.Properties.Resources.music;

                }
            }
            catch (Exception ex)
            { }
        }
        #endregion

        #region constructor

        private void constructor()
        {
            openFileDialog1.Title = "Chon Files nhac (*.mpg,*.avi,*.dat)";
            openFileDialog1.Filter = "Media File(*.mpg,*.dat,*.avi,*.flv,*.vob,*.wmv,*.wav,*.mp3)|*.wav;*.mp3;*.mpg;*.dat;*.avi;*.flv;*.vob;*.wmv|" + "All file(*.*)|*.*";
            openFileDialog1.Multiselect = true;

            openFileDialog2.Title = "Chon Files anh";
            openFileDialog2.Filter = "(*.bmp,*.dib,*.jpg,*.jpeg,*.jpe,*.jfif,*.gif,*.tif,*.tiff,*.png,*.icon)" +
                "|*.bmp;*.dib;*.jpg;*.jpeg;*.jpe;*.jfif;*.gif;*.tif;*.tiff;*.png;*.icon|" + "All file(*.*)|*.*";
            openFileDialog2.Multiselect = true;

            get_volume();
            vol = get_volume();
            progress_vol.Value = vol * 10;
            btn_vol.Text = vol.ToString();

            Microsoft.Win32.RegistryKey r = Microsoft.Win32.Registry.LocalMachine;
            r = r.OpenSubKey("HARDWARE\\DESCRIPTION\\System\\BIOS");
            object get = r.GetValue("SystemManufacturer");
            Product.Text = get.ToString();

            time_main.Interval = 1000;
            time_main.Tick += new EventHandler(time_main_Tick);
            time_main.Start();

            monitor_width = SystemInformation.PrimaryMonitorSize.Width;
            monitor_height = SystemInformation.PrimaryMonitorSize.Height;
            button4.Location = new Point(482, monitor_height - 26);
            
            player_hide_show(true);
            lyric_hide_show(true);
            pic_hide_show(true);

            play_list.Columns.Add("", 160);
            play_list.Columns.Add("", 60);
            play_list.View = View.Details;
        }


        private void time_main_Tick(object sender, EventArgs e)
        {
            get_clock();

            if(flag_play)
                if (posi_min == longlength)
                    next_song();
        }

        private void get_clock()
        {
            label_clock.Show();
            DateTime time;
            time = DateTime.Now;
            label_clock.Text = time.ToShortTimeString().ToString();
        }
        private void buttonX1_Click(object sender, EventArgs e)
        {
            Close();
        }
        private void buttonX2_Click(object sender, EventArgs e)
        {
            this.WindowState = System.Windows.Forms.FormWindowState.Minimized;
        }
        private void Form1_Load(object sender, EventArgs e)
        {

        }
        #endregion

        #region hide(true) show(fasle)
        private void player_hide_show(bool flag)
        {
            if (flag)
            {
                this.buttonX1.Hide();
                this.buttonX2.Hide();
                
                this.play_position.Hide();
           
        
                this.dockSite4.Hide();
                this.dockSite1.Hide();
                this.dockSite2.Hide();
                this.dockSite3.Hide();
                this.Product.Hide();
                this.progress_vol.Hide();
                this.btn_vol.Hide();
                this.mediaButton15.Hide();
                this.mediaButton16.Hide();
                this.btn_play1.Hide();
                this.mediaButton12.Hide();
                this.btn_open1.Hide();
                this.btn_stop1.Hide();
                this.mediaButton11.Hide();
                this.mediaButton10.Hide();
                this.pictureBox1.Hide();
                this.progress_play.Hide();
                this.play_list.Hide();
                this.btn_save.Hide();
                this.textBox1.Hide();
                this.button5.Hide();
                this.button6.Hide();

            }
            else
            {
                this.buttonX1.Show();
                this.buttonX2.Show();
                
                this.play_position.Show();
   
                this.dockSite4.Show();
                this.dockSite1.Show();
                this.dockSite2.Show();
                this.dockSite3.Show();
                this.Product.Show();
                this.progress_vol.Show();
                this.btn_vol.Show();
                this.mediaButton15.Show();
                this.mediaButton16.Show();
                this.btn_play1.Show();
                this.mediaButton12.Show();
                this.btn_open1.Show();
                this.btn_stop1.Show();
                this.mediaButton11.Show();
                this.mediaButton10.Show();
                this.pictureBox1.Show();
                this.play_list.Show();
                this.btn_save.Show();
                this.textBox1.Show();
                this.button5.Show();
                this.button6.Show();
            }
        }
        private void lyric_hide_show(bool flag)
        {
            if (flag)
            {
                panel1.Hide();
            }
            else
            {
                panel1.Show();
            }
        }
        private void pic_hide_show(bool flag)
        {
            if (flag)
            {
                button1.Hide();
                button2.Hide();
                button3.Hide();
                button4.Hide();
                progress_load.Hide();
                try
                {
                    foreach (PictureBox p in pic)
                        p.Hide();
                }
                catch { }
            }

            else
            {
                try
                {
                    foreach (PictureBox p in pic)
                        p.Show();
                }
                catch { }
            }
        }

        private void fullSre_hide_show()
        {
            if (!flag_full)
            {
                this.buttonX1.Hide();
                this.buttonX2.Hide();
                
                this.play_position.Hide();
 

                time_main.Enabled = false;
                this.label_clock.Hide();


                this.dockSite4.Hide();
                this.dockSite1.Hide();
                this.dockSite2.Hide();
                this.dockSite3.Hide();
                this.Product.Hide();
                this.progress_vol.Hide();
                this.btn_vol.Hide();
                this.mediaButton15.Hide();
                this.mediaButton16.Hide();
                this.mediaButton9.Hide();

                this.mediaButton13.Hide();
                this.btn_play1.Hide();
                this.mediaButton12.Hide();
                this.btn_open1.Hide();
                this.btn_stop1.Hide();
                this.mediaButton5.Hide();
                this.mediaButton11.Hide();
                this.mediaButton7.Hide();
                this.mediaButton4.Hide();
                this.mediaButton3.Hide();
                this.mediaButton2.Hide();
                this.mediaButton1.Hide();
                this.mediaButton6.Hide();
                this.mediaButton10.Hide();
                this.mediaButton8.Hide();
                this.button5.Hide();
                this.button6.Hide();
                this.btn_save.Hide();
                this.textBox1.Hide();
                progress_play.Location = new Point(0, monitor_height - 40);
                progress_play.Size = new Size(monitor_width, 15);
            }
            else
            {
                this.buttonX1.Show();
                this.buttonX2.Show();
                
                this.play_position.Show();


                time_main.Enabled = true;
                this.label_clock.Show();

                this.dockSite4.Show();
                this.dockSite1.Show();
                this.dockSite2.Show();
                this.dockSite3.Show();
                this.Product.Show();
                this.progress_vol.Show();
                this.btn_vol.Show();
                this.mediaButton15.Show();
                this.mediaButton16.Show();
                this.mediaButton9.Show();

                this.mediaButton13.Show();
                this.btn_play1.Show();
                this.mediaButton12.Show();
                this.btn_open1.Show();
                this.btn_stop1.Show();
                this.mediaButton5.Show();
                this.mediaButton11.Show();
                this.mediaButton7.Show();
                this.mediaButton4.Show();
                this.mediaButton3.Show();
                this.mediaButton2.Show();
                this.mediaButton1.Show();
                this.mediaButton6.Show();
                this.mediaButton10.Show();
                this.mediaButton8.Show();
                this.button5.Show();
                this.button6.Show();
                this.btn_save.Show();
                this.textBox1.Show();
                progress_play.Location = new Point(508, 530);
                progress_play.Size = new Size(450, 15);
            }
        }

        private void pic_full(bool flag)
        {
            if (flag)
            {
                this.buttonX1.Hide();
                this.buttonX2.Hide();
                
                this.play_position.Hide();

                time_main.Enabled = false;
                this.label_clock.Hide();


                this.dockSite4.Hide();
                this.dockSite1.Hide();
                this.dockSite2.Hide();
                this.dockSite3.Hide();
                this.Product.Hide();
                this.progress_vol.Hide();
                this.btn_vol.Hide();
                this.mediaButton15.Hide();
                this.mediaButton16.Hide();
                this.mediaButton9.Hide();

                this.mediaButton13.Hide();
                this.btn_play1.Hide();
                this.mediaButton12.Hide();
                this.btn_open1.Hide();
                this.btn_stop1.Hide();
                this.mediaButton5.Hide();
                this.mediaButton11.Hide();
                this.mediaButton7.Hide();
                this.mediaButton4.Hide();
                this.mediaButton3.Hide();
                this.mediaButton2.Hide();
                this.mediaButton1.Hide();
                this.mediaButton6.Hide();
                this.mediaButton10.Hide();
                this.mediaButton8.Hide();

                progress_play.Hide();
                pictureBox1.Hide();
            }
            else
            {
                this.buttonX1.Show();
                this.buttonX2.Show();
                
                this.play_position.Show();

                time_main.Enabled = true;
                this.label_clock.Show();

                this.dockSite4.Show();
                this.dockSite1.Show();
                this.dockSite2.Show();
                this.dockSite3.Show();
                this.Product.Show();
                this.progress_vol.Show();
                this.btn_vol.Show();
                this.mediaButton15.Show();
                this.mediaButton16.Show();
                this.mediaButton9.Show();

                this.mediaButton13.Show();
                this.btn_play1.Show();
                this.mediaButton12.Show();
                this.btn_open1.Show();
                this.btn_stop1.Show();
                this.mediaButton5.Show();
                this.mediaButton11.Show();
                this.mediaButton7.Show();
                this.mediaButton4.Show();
                this.mediaButton3.Show();
                this.mediaButton2.Show();
                this.mediaButton1.Show();
                this.mediaButton6.Show();
                this.mediaButton10.Show();
                this.mediaButton8.Show();

                progress_play.Show();
                pictureBox1.Show();
            }
        }
        #endregion
        
        #region move menu
        private void move_down(ref MediaButton[] bt, int high, int first)
        {
            int[] x = new int[bt.Length];
            for (int i = 0; i < x.Length; ++i)
                x[i] = bt[i].Location.X;

            int y2 = bt[first].Location.Y + high;

            for (int i = bt[first].Location.Y; i <= y2; i += 4)
            {
                for (int j = 0; j < x.Length; ++j)
                {
                    bt[j].Location = new Point(x[j], i + 80 * (j - first));
                }
                //Thread.Sleep(2);
            }
        }
        private void move_up(ref MediaButton[] bt, int high, int first)
        {
            int[] x = new int[bt.Length];
            for (int i = 0; i < x.Length; ++i)
                x[i] = bt[i].Location.X;

            int y2 = bt[first].Location.Y - high;
            for (int i = bt[first].Location.Y; i >= y2; i -= 4)
            {
                for (int j = 0; j < x.Length; ++j)
                {
                    bt[j].Location = new Point(x[j], i + 80 * (j - first));
                }
                //Thread.Sleep(1);
            }
        }

        

        private void mediaButton1_MouseClick(object sender, MouseEventArgs e)
        {
            button = 1;
            player_hide_show(true);
            lyric_hide_show(true);
            pic_hide_show(false);
            Hidelibrary(flaglibrary);
            flaglibrary = false;
            if (flag > 1)
            {
                MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_down(ref bt, 80 * (flag - 1), 0);
                flag = 1;

                bt = new MediaButton[] { mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_down(ref bt, 160, 0);

                this.mediaButton9.Image = global::WindowsFormsApplication9.Properties.Resources.Applications;
                this.mediaButton9.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.Applications;
                this.mediaButton13.Image = global::WindowsFormsApplication9.Properties.Resources.Pictures;
                this.mediaButton13.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.Pictures;
            }

        }

        private void mediaButton2_MouseClick(object sender, MouseEventArgs e)
        {
            button = 2;
            player_hide_show(true);
            lyric_hide_show(true);
            pic_hide_show(true);
            Hidepicture(flagpicture);
            flagpicture = false;
            if (flag > 2)
            {
                this.mediaButton9.Image = global::WindowsFormsApplication9.Properties.Resources.media;
                this.mediaButton9.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.mediaactive;
                this.mediaButton13.Image = global::WindowsFormsApplication9.Properties.Resources.search;
                this.mediaButton13.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.search;

                MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_down(ref bt, 80 * (flag - 2), 1);
                flag = 2;

                bt = new MediaButton[] { mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_down(ref bt, 160, 0);
            }
            else if (flag < 2)
            {
                this.mediaButton9.Image = global::WindowsFormsApplication9.Properties.Resources.media;
                this.mediaButton9.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.mediaactive;
                this.mediaButton13.Image = global::WindowsFormsApplication9.Properties.Resources.search;
                this.mediaButton13.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.search;

                MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_up(ref bt, 80 * (2 - flag), 2);
                flag = 2;

                bt = new MediaButton[] { mediaButton1, mediaButton2 };
                move_up(ref bt, 160, 0);
            }
        }

        private void mediaButton3_MouseClick(object sender, MouseEventArgs e)
        {
            button = 3;
            player_hide_show(false);
            lyric_hide_show(true);
            pic_hide_show(true);
            Hidelibrary(flaglibrary);
            flaglibrary = false;
            Hidepicture(flagpicture);
            flagpicture = false;
            if (flag > 3)
            {
                this.mediaButton9.Image = global::WindowsFormsApplication9.Properties.Resources.music;
                this.mediaButton9.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.musicactive;
                this.mediaButton13.Image = global::WindowsFormsApplication9.Properties.Resources.film;
                this.mediaButton13.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.filmactive;

                MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_down(ref bt, 80 * (flag - 3), 2);
                flag = 3;

                bt = new MediaButton[] { mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_down(ref bt, 160, 0);
            }
            else if (flag < 3)
            {
                this.mediaButton9.Image = global::WindowsFormsApplication9.Properties.Resources.music;
                this.mediaButton9.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.musicactive;
                this.mediaButton13.Image = global::WindowsFormsApplication9.Properties.Resources.film;
                this.mediaButton13.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.filmactive;

                MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_up(ref bt, 80 * (3 - flag), 3);
                flag = 3;

                bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3 };
                move_up(ref bt, 160, 0);
            }
        }

        private void mediaButton4_MouseClick(object sender, MouseEventArgs e)
        {
            button = 4;
            player_hide_show(true);
            lyric_hide_show(true);
            pic_hide_show(true);
            Hidelibrary(flaglibrary);
            flaglibrary = false;
            Hidepicture(flagpicture);
            flagpicture = false;
            if (flag > 4)
            {
                this.mediaButton9.Image = global::WindowsFormsApplication9.Properties.Resources.music;
                this.mediaButton9.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.musicactive;

                MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_down(ref bt, 80 * (flag - 4), 3);
                flag = 4;

                bt = new MediaButton[] { mediaButton5, mediaButton6, mediaButton7 };
                move_down(ref bt, 160, 0);
            }
            else if (flag < 4)
            {
                this.mediaButton9.Image = global::WindowsFormsApplication9.Properties.Resources.music;
                this.mediaButton9.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.musicactive;

                MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_up(ref bt, 80 * (4 - flag), 4);
                flag = 4;

                bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4 };
                move_up(ref bt, 160, 0);
            }
        }

        private void mediaButton5_MouseClick(object sender, MouseEventArgs e)
        {
            button = 5;
            player_hide_show(true);
            lyric_hide_show(false);
            pic_hide_show(true);
            Hidelibrary(flaglibrary);
            flaglibrary = false;
            Hidepicture(flagpicture);
            flagpicture = false;
            if (flag > 5)
            {
                this.mediaButton13.Image = global::WindowsFormsApplication9.Properties.Resources.save;
                this.mediaButton13.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.save;
                this.mediaButton9.Image = global::WindowsFormsApplication9.Properties.Resources.lyric;
                this.mediaButton9.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.lyric;

                MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_down(ref bt, 80 * (flag - 5), 4);
                flag = 5;

                bt = new MediaButton[] { mediaButton6, mediaButton7 };
                move_down(ref bt, 160, 0);

                //if (fileopenning)
                //{
                    //if (play_list.Items[count_play].Tag.ToString().Length > 0)
                     //   get_lyric(play_list.Items[count_play].Tag.ToString());
                   // else
                        get_lyric(str_play_path);
                //}
                

            }
            else if (flag < 5)
            {
                this.mediaButton13.Image = global::WindowsFormsApplication9.Properties.Resources.save;
                this.mediaButton13.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.save;
                this.mediaButton9.Image = global::WindowsFormsApplication9.Properties.Resources.lyric;
                this.mediaButton9.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.lyric;

                MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_up(ref bt, 80 * (5 - flag), 5);
                flag = 5;

                bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5 };
                move_up(ref bt, 160, 0);

                /*if (fileopenning)
                {
                    if (play_list.Items[count_play].Tag.ToString().Length > 0)
                        get_lyric(play_list.Items[count_play].Tag.ToString());
                    else*/
                        get_lyric(str_play_path);
                //}
            }


        }

        private void mediaButton6_MouseClick(object sender, MouseEventArgs e)
        {
            button = 6;
            player_hide_show(true);
            lyric_hide_show(true);
            pic_hide_show(true);
            Hidelibrary(flaglibrary);
            flaglibrary = false;
            Hidepicture(flagpicture);
            flagpicture = false;
            if (flag > 6)
            {
                this.mediaButton9.Image = global::WindowsFormsApplication9.Properties.Resources.favorites;
                this.mediaButton9.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.favorites;
                this.mediaButton13.Image = global::WindowsFormsApplication9.Properties.Resources.web;
                this.mediaButton13.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.web;

                MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_down(ref bt, 80 * (flag - 6), 5);
                flag = 6;

                bt = new MediaButton[] { mediaButton7 };
                move_down(ref bt, 160, 0);
            }
            else if (flag < 6)
            {
                this.mediaButton9.Image = global::WindowsFormsApplication9.Properties.Resources.favorites;
                this.mediaButton9.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.favorites;
                this.mediaButton13.Image = global::WindowsFormsApplication9.Properties.Resources.web;
                this.mediaButton13.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.web;

                MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_up(ref bt, 80 * (6 - flag), 6);
                flag = 6;

                bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6 };
                move_up(ref bt, 160, 0);
            }
        }

        private void mediaButton7_MouseClick(object sender, MouseEventArgs e)
        {
            button = 7;
            player_hide_show(true);
            lyric_hide_show(true);
            pic_hide_show(true);
            Hidelibrary(flaglibrary);
            flaglibrary = false;
            Hidepicture(flagpicture);
            flagpicture = false;
            if (flag < 7)
            {
                this.mediaButton13.Image = global::WindowsFormsApplication9.Properties.Resources.add_on;
                this.mediaButton13.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.add_on;
                this.mediaButton9.Image = global::WindowsFormsApplication9.Properties.Resources.Google;
                this.mediaButton9.ImageFocused = global::WindowsFormsApplication9.Properties.Resources.Google;

                MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_up(ref bt, 80 * (7 - flag), 6);
                flag = 7;

                bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
                move_up(ref bt, 160, 0);
            }
        }

        private void mediaButton8_MouseClick(object sender, MouseEventArgs e)
        {
            button = 8;
            player_hide_show(true);
            lyric_hide_show(true);
            pic_hide_show(true);

            MediaButton[] bt = new MediaButton[] { mediaButton1, mediaButton2, mediaButton3, mediaButton4, mediaButton5, mediaButton6, mediaButton7 };
            for (int i = 0; i < bt.Length; ++i)
                if (i < 3)
                    bt[i].Location = new Point(bt[i].Location.X, 50 + i * 80);
                else
                    bt[i].Location = new Point(bt[i].Location.X, 230 + i * 80);
            flag = 3;
        }

        private void mediaButton13_MouseClick(object sender, MouseEventArgs e)
        {
            if (button == 1 || button == 11)
            {
                flagpicture = true;
                mediaButton9.Hide();
                AddControlPicture();
            }
            else if (button == 3)
            {
                //button = 31;
                player_hide_show(false);
            }
            else if (button == 5)
            {
                SaveFileDialog s = new SaveFileDialog();
                s.Filter = "Word Documents (*.doc)|*.doc|Text (.txt)|*.txt";
                if (tbx_song.Text != "")
                    s.FileName = tbx_song.Text + "_Lyric";

                if (s.ShowDialog() == DialogResult.OK)
                    write_lyric(s.FileName);
            }
            else if (button == 6)
            {
                browser b = new browser();
                b.Show();
            }
        }

        

        private void mediaButton9_MouseClick(object sender, MouseEventArgs e)
        {
            if (button == 5)
            {
                if (!tbx_song.Enabled)
                {
                    tbx_song.Enabled = true;
                    tbx_artist.Enabled = true;
                    tbx_artist.Focus();
                    richTextBox1.Text = "";
                }
                else 
                {
                    get_lyric(tbx_song.Text, tbx_artist.Text);
                }
            }
            else if (button == 2)
            {
                flaglibrary = true;
                AddControlLibary();
                mediaButton9.Hide();
                string[] arrString = readwrite.read("Data.txt");
                //string[] arr = readwrite.StringGroup(4);
                AddItemListview(arrString);
            }
        }

        #endregion
        
        #region player

        public string Status()
        {
            mciSendString("status mediafile mode", sbf, sbf.Capacity, IntPtr.Zero);
            return sbf.ToString();
        }

        private void btn_open1_MouseClick(object sender, MouseEventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                fileopenning = true;

                add_playlist(openFileDialog1.FileNames, 0);
                btn_save.Text = "unsave list";
                stop1();
                play_play(play_list.Items[count_play].Tag.ToString());
            }
        }

        private void add_playlist(string[] address, int playing)
        {
            path = address;
            play_list.Items.Clear();
            if (address.Length < 16)
                btn_save.Size = new Size(229, 23);
            else
                btn_save.Size = new Size(211, 23);

            flag_save = false;
            

            count_list = address.Length - 1;
            count_play = playing;
            stack_play = new Stack();

            StringBuilder length = new StringBuilder(1024);
            foreach (string item in address)
            {
                ListViewItem l = new ListViewItem();
                l.Text = convert(item);
                l.Tag = item;

                try
                {
                    mciSendString("open \"" + item + "\" type mpegvideo alias mediafile", null, 0, IntPtr.Zero);

                    mciSendString("set mediafile time format milliseconds", null, 0, IntPtr.Zero);

                    mciSendString("status mediafile length", length, 1024, IntPtr.Zero);

                    l.SubItems.Add(FormatSeconds((long)Math.Floor(Convert.ToDouble(length.ToString().Trim())) / 1000));

                    mciSendString("close mediafile", null, 0, IntPtr.Zero);
                }
                catch
                {
                    l.SubItems.Add("time");
                }

                play_list.Items.Add(l);
            }
            textBox1.Text = (count_list + 1).ToString() + " item";
        }


        private string convert(string item)
        {
            FileInfo file = new FileInfo(item);
            int whereDot = file.Name.LastIndexOf('.');
            if (0 < whereDot && whereDot <= file.Name.Length - 2)
            {
                return file.Name.Substring(0, whereDot);
            }
            return "";
        }
        private void play_list_ItemActivate(object sender, System.EventArgs e)
        {
            play_list.Items[count_play].ForeColor = Color.Black;
            stack_play.Push(count_play.ToString());
            stop1();
            count_play = play_list.FocusedItem.Index;
            play_play(play_list.Items[count_play].Tag.ToString());
            
        }

        private void btn_save_Click(object sender, EventArgs e)
        {
            if (!flag_save && path.Length > 0)
            {
                foreach (string a in path)
                {
                    get_details detail = new get_details(a);
                    readwrite.write(detail.get_name());
                    readwrite.writeinfo(detail.get_name());
                    readwrite.writeinfo(detail.get_title());
                    readwrite.writeinfo(detail.get_artist());
                    readwrite.writeinfo(detail.get_album());
                    readwrite.writeinfo(detail.get_year());
                }
                flag_save = true;
                btn_save.Text = "saved";
            }
        }

        private void btn_play1_MouseClick(object sender, MouseEventArgs e)
        {
            play1();
        }

        private void play1()
        {
            if (fileopenning)
            {
                if (!flag_play)
                    play_play(play_list.Items[count_play].Tag.ToString());
                else
                    play_pause();
            }            
        }
        private bool isMusic(string filename)
        {
            if (filename.Contains(".mp3") || filename.Contains(".wav"))
                return true;
            else
                return false;
        }
        private void play_play(string name)
        {

            staying = "open \"" + name + "\" type mpegvideo alias mediafile style child parent " + pictureBox1.Handle.ToInt32();
            mciSendString(staying, null, 0, IntPtr.Zero);
            staying = "put mediafile window at 0 0 " + pictureBox1.Width + " " + pictureBox1.Height;
            mciSendString(staying, null, 0, IntPtr.Zero);

            if (isMusic(name))
                pictureBox1.Hide();
            else
                pictureBox1.Show();
            str_play_path = name;

            btn_play1.Enabled = true;
            btn_stop1.Enabled = true;

            staying = "play mediafile";
            mciSendString(staying, null, 0, IntPtr.Zero);

            staying = "set mediafile time format milliseconds";
            mciSendString(staying, null, 0, IntPtr.Zero);


            this.btn_play1.Image = global::WindowsFormsApplication9.Properties.Resources.Pause_Button2;
            flag_play = true;

            get_length();

            System.Windows.Forms.Timer timer = new System.Windows.Forms.Timer();
            timer.Interval = 100;
            timer.Start();
            timer.Tick += new EventHandler(timer_Tick);

            //if (play_list.Items.Count > 0)
                play_list.Items[count_play].ForeColor = Color.Red;
        }

        private void play_pause()
        {
            staying = "pause mediafile";
            mciSendString(staying, null, 0, IntPtr.Zero);
            this.btn_play1.Image = global::WindowsFormsApplication9.Properties.Resources.Play_button2;
            flag_play = false;
        }

        private int random(int min, int max)
        {
            Random r = new Random();
            return r.Next(min, max);
        }

        /*private void next_song()
        {
            if (count_play < count_list)
            {
                if (!play_random)
                    ++count_play;
                else
                    count_play = random(0,count_list);

                stop1();                
                play_play(play_list.Items[count_play].Tag.ToString());                

                return;
            }
            stop1();

        }*/
        private void next_song()
        {
            play_list.Items[count_play].ForeColor = Color.Black;
            stack_play.Push(count_play.ToString());
            if (!play_random)
                if (!play_repeat)
                    if (count_play < count_list)
                        ++count_play;
                    else
                    {
                        count_play = 0;
                        play_list.Items[count_play].ForeColor = Color.Red;
                        stop1();
                        return;
                    }
                else
                {
                    ++count_play;
                    if (count_play == count_list + 1)
                        count_play = 0;
                }
            else
            {
                count_play = random(0, count_list);
            }

            stop1();

            play_play(play_list.Items[count_play].Tag.ToString());
            
            return;
        }


        private void review_song()
        {
            if (stack_play.Count > 0)
            {
                play_list.Items[count_play].ForeColor = Color.Black;
                stop1();
                count_play = Convert.ToInt32(stack_play.Pop());
                play_play(play_list.Items[count_play].Tag.ToString());
            }

            return;
        }
        
        private void mediaButton16_MouseClick(object sender, MouseEventArgs e)
        {
            next_song();
        }
        private void mediaButton15_MouseClick(object sender, MouseEventArgs e)
        {
            review_song();
        }

        private void button5_Click(object sender, EventArgs e)
        {
            //ngau nhien            
            if (play_random)
                play_random = false;
            else
                play_random = true;
            
        }

        private void button6_Click(object sender, EventArgs e)
        {
            //lap lai
            if (play_repeat)
                play_repeat = false;
            else
                play_repeat = true;
           
        }

        private void get_length()
        {
            StringBuilder length = new StringBuilder(1024);
            mciSendString("status mediafile length", length, 1024, IntPtr.Zero);
            try
            {
                longlength = (long)Math.Floor(Convert.ToDouble(length.ToString().Trim()));
                str_length = FormatSeconds(longlength / 1000);
            }
            catch { }

            //play_length.Text = FormatSeconds(longlength / 1000);
        }
        private void playPauseToolStripMenuItem_Click(object sender, EventArgs e)
        {
            play1();
        }


        private void btn_stop1_MouseClick(object sender, MouseEventArgs e)
        {
            stop1();
        }

        private void stop1()
        {
            staying = "close mediafile";
            mciSendString(staying, null, 0, IntPtr.Zero);
            if (flag_play)
            {
                this.btn_play1.Image = global::WindowsFormsApplication9.Properties.Resources.Play_button2;
                flag_play = false;

                progress_play.Value = 0;
                play_position.Text = "";
                str_length = "";
            }
        }

        private void stopToolStripMenuItem_Click(object sender, EventArgs e)
        {
            stop1();
        }


        

        private void progress_play_MouseClick(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            if(!flag_full)
                progress_play.Value = (int)(e.X * 20000 / 450);
            else
                progress_play.Value = (int)(e.X * 20000 / monitor_width);

            long time = progress_play.Value * longlength / 20000;

            mciSendString("seek mediafile to " + time.ToString(), null, 0, IntPtr.Zero);
            staying = "play mediafile";
            mciSendString(staying, null, 0, IntPtr.Zero);
        }



        public string FormatSeconds(long seconds)
        {
            if (seconds < 3600)
            {
                long minus = seconds / 60;
                seconds = seconds % 60;
                return minus.ToString().PadLeft(2, '0')
                + ":" + seconds.ToString().PadLeft(2, '0');
            }
            else
            {
                long hour, minus;
                hour = seconds / 3600;
                seconds -= hour * 3600;

                minus = seconds / 60;
                seconds %= 60;
                string a = hour.ToString().PadLeft(2, '0') + ":" + minus.ToString().PadLeft(2, '0')
                + ":" + seconds.ToString().PadLeft(2, '0');
                return a;
            }
        }

        public void timer_Tick(object sender, EventArgs e)
        {
            StringBuilder fileleng = new StringBuilder(256);
            mciSendString("status mediafile position", fileleng, 256, IntPtr.Zero);
            try
            {
                //play_position.Text = fileleng.ToString();
                //hScrollBar1.Value = (int)(((long)Math.Floor(Convert.ToDouble(fileleng.ToString().Trim())) / 1000) * 100 / longlength);
                posi_min = ((long)Math.Floor(Convert.ToDouble(fileleng.ToString().Trim())));
                progress_play.Value = (int)(posi_min * 20000 / longlength);
                
                //play_position.Text = progress_play.Value.ToString();
                play_position.Text = FormatSeconds((long)Math.Floor(Convert.ToDouble(fileleng.ToString().Trim()) / 1000)) + " / " + str_length;//play_length.Text.ToString();
            }
            catch { }
        }

        public int get_volume()
        {
            uint CurrVol = 0;
            waveOutGetVolume(IntPtr.Zero, out CurrVol);
            ushort CalcVol = (ushort)(CurrVol & 0x0000ffff);

            return CalcVol / (ushort.MaxValue / 10);
        }

        public void set_volume(int value)
        {
            int NewVolume = ((ushort.MaxValue / 10) * value);
            uint NewVolumeAllChannels = (((uint)NewVolume & 0x0000ffff) | ((uint)NewVolume << 16));
            waveOutSetVolume(IntPtr.Zero, NewVolumeAllChannels);
        }

        private void mediaButton12_MouseClick(object sender, MouseEventArgs e)
        {
            vol_mute();
        }
        private void vol_mute()
        {
            if (vol > 0)
            {
                set_volume(0);
                vol *= -1;
                progress_vol.Value = 0;
                btn_vol.Text = "0";
            }
            else
            {
                vol *= -1;
                set_volume(vol);
                progress_vol.Value = vol * 10;
                btn_vol.Text = vol.ToString();
            }
        }
        private void mediaButton10_MouseClick(object sender, MouseEventArgs e)
        {
            vol_up();
        }
        private void vol_up()
        {
            if (vol < 10 && vol > -10)
            {
                vol = Math.Abs(vol) + 1;
                set_volume(vol);
                progress_vol.Value = vol * 10;
                btn_vol.Text = vol.ToString();
            }
        }
        private void mediaButton11_MouseClick(object sender, MouseEventArgs e)
        {
            vol_down();
        }
        private void vol_down()
        {
            if (vol <= 10 && vol >= -10 && vol != 0)
            {
                vol = Math.Abs(vol) - 1;
                set_volume(vol);
                progress_vol.Value = vol * 10;
                btn_vol.Text = vol.ToString();
            }
        }
        private void upToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            vol_up();
        }

        private void downToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            vol_down();
        }

        private void muteToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            vol_mute();
        }


        private void fullSreToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (!flag_full)
            {
                fullSre_hide_show();
                pictureBox1.Size = new Size(monitor_width, monitor_height);
                pictureBox1.Location = new Point(0, 0);
                staying = "put mediafile window at 0 0 " + monitor_width.ToString() + " " + monitor_height.ToString(); ;
                mciSendString(staying, null, 0, IntPtr.Zero);
                flag_full = true;
            }
            else
            {
                fullSre_hide_show();
                pictureBox1.Size = new Size(450, 250);
                pictureBox1.Location = new Point(508, 242);
                staying = "put mediafile window at 0 0 450 250";
                mciSendString(staying, null, 0, IntPtr.Zero);
                flag_full = false;
            }
        }

        #endregion

        #region event mouse move

        private void Form1_MouseMove(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            if (e.X >= label_clock.Location.X && e.Y <= label_clock.Location.Y)
            {
                buttonX1.Show();
                buttonX2.Show();
            }
            else if (e.X < label_clock.Location.X && e.Y > label_clock.Location.Y)
            {
                buttonX1.Hide();
                buttonX2.Hide();
            }

            if (button == 3)
            {

                if (e.X >= 508 && e.X <= 958 && e.Y >= 498 && e.Y <= 545)
                {
                    progress_play.Show();
                }
                else if (e.X < 508 || e.X > 958 || e.Y < 498 || e.Y > 545)
                {
                    progress_play.Hide();
                }
            }
            else if (button == 11)
            {
                if (e.X < 480 || e.X > 620)
                {
                    //label2.Text = e.X.ToString();
                    zoom_pic((int)(e.Y / 100) + p, 100);
                    //return;
                }
                else if (e.X >= 480 && e.X <= 620)
                {
                    //label1.Text = e.X.ToString();//"defaul: " + " + num: " + ((int)(e.Y / 100)).ToString();
                    zoom_pic((int)(e.Y / 100) + p, 500);
                    //return;
                }
            }
        }
        
        #endregion

        #region menu lyric

        private void get_lyric(string path)
        {
            get_details detail = new get_details(path);
            
            tbx_song.Text = detail.get_title();
            tbx_artist.Text = detail.get_artist();
            tbx_artist.Enabled = false;
            tbx_song.Enabled = false;
            get_lyric(tbx_song.Text, tbx_artist.Text);
        }

        private void get_lyric(string song, string artist)
        {
            if (song != "" || artist != "")
            {
                if (System.Net.NetworkInformation.NetworkInterface.GetIsNetworkAvailable())
                {
                    try
                    {
                        WebClient client1 = new WebClient();
                        string queryUrl = "http://www.lyricsplugin.com/plugin/?title=" + song + "&artist=" + artist;
                        string result1 = client1.DownloadString(queryUrl);


                        const string lyricsMarker = @"<div id=""lyrics"">";
                        int index = result1.IndexOf(lyricsMarker);
                        if (index >= 0)
                        {
                            result1 = result1.Substring(index + lyricsMarker.Length);
                            index = result1.IndexOf("</div>");
                        }
                        if (index > 0)
                        {
                            result1 = result1.Substring(0, index);
                            result1 = result1.Replace("<br />\n", Environment.NewLine);
                            result1 = result1.Replace("<br />", "");

                            Encoding ANSI = Encoding.GetEncoding(1252);
                            UTF8Encoding utf8 = new UTF8Encoding();

                            result1 = utf8.GetString(ANSI.GetBytes(result1));
                            str_lyric = result1;
                            richTextBox1.Text = result1.TrimEnd();
                        }
                    }
                    catch (WebException ex)
                    {
                        richTextBox1.Text = "Lyrics not found in database";
                    }
                    toolStripStatusLabel2.Text = "Done!";
                    toolStripStatusLabel2.Visible = true;
                }
                else
                {
                    richTextBox1.Text = "Lyrics not found";
                    toolStripStatusLabel2.Text = "Check your network";
                    toolStripStatusLabel2.Visible = true;
                }

            }
            else
            {
                richTextBox1.Text = "Lyrics not found";
                toolStripStatusLabel2.Text = "Done!";
                toolStripStatusLabel2.Visible = true;
            }
        }

        private void write_lyric(string tag)
        {
            FileStream fs = new FileStream(tag, FileMode.Create, FileAccess.Write);
            StreamWriter sw = new StreamWriter(fs);
            sw.WriteLine("Title: " + tbx_song.Text);
            sw.WriteLine("Artist: " + tbx_artist.Text);
            sw.WriteLine("");
            sw.Write(str_lyric);
            sw.Flush();
            fs.Flush();
            fs.Close();
        }
        #endregion

        #region menu picture
        private void open_picture()
        {            
            if (openFileDialog2.ShowDialog() == DialogResult.OK)
            {
                int num = openFileDialog2.FileNames.Length;
                System.Drawing.Image image;

                if (num > 0)
                {
                    button = 11;
                    pic_hide_show(true);

                    pic = new PictureBox[num];
                    progress_load.Maximum = num;
                    progress_load.Show();
                    pic_max = int.MaxValue;

                    for (int i = 0; i < num; )
                    {
                        image = new Bitmap(openFileDialog2.FileNames[i]);
                        pic[i] = new PictureBox();
                        pic[i].Location = new Point(500, i * 100 + 100);

                        pic[i].MouseDoubleClick += new MouseEventHandler(Form1_MouseDoubleClick);

                        pic[i].BackgroundImage = image;
                        pic[i].BackgroundImageLayout = ImageLayout.Stretch;
                        pic[i].Size = reSize(image.Size, 100);

                        this.Controls.Add(pic[i]);

                        progress_load.Value = ++i;
                    }
                    progress_load.Hide();
                }
            }
        }

        void Form1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            if (flag_full)
            {
                fullSre_hide_show();
                flag_full = false;
            }
            else
            {
                fullSre_hide_show();
                flag_full = true;
            }
            
        }

        private Size reSize(Size first, int defaul)
        {
            Size size = first;

            int max_size = Math.Max(first.Width, first.Height);
            //if (max_size < limit - 10 || max_size > limit + 10)
            {
                double t = Convert.ToDouble(max_size) / Convert.ToDouble(defaul);
                size.Height = Convert.ToInt32(size.Height/t);
                size.Width = Convert.ToInt32(size.Width / t);
            }
            return size;
        }
        
        private void zoom_pic(int num, int defaul)
        {
            --num;
            if (defaul == 500 && (num) >= 0 && (num) < pic.Length )
            {
                if (pic_max != int.MaxValue && (num) != pic_max)
                    pic[pic_max].Size = reSize(pic[num + p].Size, 100);
                pic_max = num ;

                pic[num].Size = reSize(pic[num].Size, 500);
                pic[num].Location = new Point(500, num * 100 - 150);

                pic_move_up(0, num - 1);
                pic_move_down(num + 1, pic.Length - 1);
                
            }
            else if (defaul == 100 && (num) >= 0 && (num) < pic.Length)
            {
                if(pic_max != int.MaxValue)
                    pic[pic_max].Size = reSize(pic[pic_max].Size, 100);

                for (int i = 0; i < pic.Length; ++i)
                    pic[i].Location = new Point(500, (i - p) * 100 + 100);
            }        
        }

        private static int p = 0;

        private void pic_move_up(int num1, int num2)
        {
            for (int i = num2; i >= num1; --i)
            {
                //System.Threading.Thread.Sleep(1000);
                pic[i].Location = new Point(500, i * 100 - 150);
            }
        }

        private void pic_move_down(int num1, int num2)
        {
            for (int i = num2; i >= num1; --i)
            {
                //System.Threading.Thread.Sleep(1000);
                pic[i].Location = new Point(500, (i + 1) * 100 + 150);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (p < pic.Length)
            {
                ++p;
                for (int i = 0; i < pic.Length; ++i)
                    //for(int y = pic[i].Location.Y; y >= (i - p + 1) * 100; --y)
                        pic[i].Location = new Point(500, (i - p + 1) * 100);
            }

        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (p > 0)
            {
                --p;
                for (int i = 0; i < pic.Length; ++i)
                    pic[i].Location = new Point(500, (i - p + 1) * 100);
            }
        }

        #endregion                  
    }
}
 