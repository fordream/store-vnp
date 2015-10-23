using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using DevComponents.DotNetBar;
using AxWMPLib;
using WMPLib;
using System.IO;
using System.Net;

namespace dean
{
    class tkonline
    {
        private ListView listViewEx1;
        private int so;
        string text;
        private AxWindowsMediaPlayer axWindowsMediaPlayer1;
        private SaveFileDialog down;

        public SaveFileDialog dl
        {
            set { down = value; }
        }

        public string settext
        {
            set { text = value; }
        }
        public AxWindowsMediaPlayer ax
        {
            set { axWindowsMediaPlayer1 = value; }
        }

        public int s
        {
            set { so = value; }
        }

        public ListView dstk
        {
            set { listViewEx1 = value; }
        }

        //
        public void Searchnhac()
        {
            try
            {
                string path = "http://mp3.zing.vn/mp3/search/do.1.html?t=" + so.ToString() + "&q=" + text + "&row=MjM0ODU=";
                this.FindFromUrl(path);
            }
            catch (WebException exception)
            {
                MessageBox.Show("Loi search " + exception.Message);
            }
        }

        //tim kiem yeu cau tren mp3.zing.vn
        public void FindFromUrl(string path)
        {
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(path);
            request.Referer = "http://mp3.zing.vn";
            request.UserAgent = "1234556";
            Stream responseStream = ((HttpWebResponse)request.GetResponse()).GetResponseStream();
            StreamReader reader = new StreamReader(responseStream);
            WMPLib.IWMPPlaylist pl1;
            WMPLib.IWMPMedia m11;
            pl1 = axWindowsMediaPlayer1.playlistCollection.newPlaylist("SearchZing");
            string str = string.Empty;
            //bool flag = false;
            string str2 = string.Empty;
            string str3 = string.Empty;
            string str4 = string.Empty;
            string str5 = string.Empty;
            string str6 = string.Empty;
            string str7 = string.Empty;
            string str8 = string.Empty;
            string str9 = string.Empty;
            string str10 = "";
            string str11 = "";
            try
            {
                try
                {
                    do
                    {
                        str10 = reader.ReadLine().Trim().ToString();
                        if (!(!str10.StartsWith("<h1>Tìm được")))
                        {
                            string[] separator = new string[] { "<h1>Tìm được ", " bài" };
                            str = str10.Split(separator, StringSplitOptions.RemoveEmptyEntries)[0];
                            //flag = true;
                        }
                        if (str10.StartsWith("<p>Thể loại: <span class=\"typeMusic\">"))
                        {
                            string[] strArray3 = new string[] { "\">", "</a>", "Thời gian:", "|", "Lượt nghe: ", "</p>" };
                            string[] strArray4 = str10.Split(strArray3, StringSplitOptions.RemoveEmptyEntries);
                            str7 = strArray4[2];
                            str4 = strArray4[5];
                            str8 = strArray4[6];
                            str9 = strArray4[8];
                        }
                        if (str10.StartsWith("<div class=\"leftInfo\">"))
                        {
                            str10 = reader.ReadLine().Trim().ToString();
                            string[] strArray5 = new string[] { ">", "</a>", "<a href=\"", "\" title" };
                            string[] strArray6 = str10.Split(strArray5, StringSplitOptions.RemoveEmptyEntries);
                            str2 = strArray6[3];
                            str5 = "http://mp3.zing.vn" + strArray6[1];
                        }
                        if (str10.StartsWith("<div class=\"iconadd\">"))
                        {
                            str10 = reader.ReadLine().Trim().ToString();
                            string[] strArray7 = new string[] { "<a href=\"", "\" title" };
                            str6 = str10.Split(strArray7, StringSplitOptions.RemoveEmptyEntries)[0];
                            str11 = InitPlayUrl(str5);
                            m11 = axWindowsMediaPlayer1.newMedia(str11);
                            pl1.appendItem(m11);
                            string[] items = new string[] { (listViewEx1.Items.Count + 1).ToString(), str2, str3, str9, str7, str11 };
                            ListViewItem item = new ListViewItem(items);
                            listViewEx1.Items.Add(item);
                            axWindowsMediaPlayer1.currentPlaylist = pl1;
                            axWindowsMediaPlayer1.playlistCollection.remove(pl1);
                        }
                        if (str10.StartsWith("<p>Trình bày:"))
                        {
                            string[] strArray9 = new string[] { ">", "</a>" };
                            str3 = str10.Split(strArray9, StringSplitOptions.RemoveEmptyEntries)[2];
                        }
                    }
                    while ((str10 != null) && !reader.EndOfStream);
                }
                catch (WebException exception)
                {
                    MessageBox.Show("Loi FindFromUrl" + exception.Message);
                }
            }
            finally
            {
                reader.Close();
                responseStream.Close();
            }
            //if (flag)
            //{
            //    return Convert.ToInt32(str);
            //}
            reader.Close();
            responseStream.Close();
            //return -1;
        }

        //lay du lieu tu web
        public string InitPlayUrl(string s)
        {
            try
            {
                string[] strArray;
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(s);
                request.Referer = "http://mp3.zing.vn";
                request.UserAgent = "1234556";
                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                StreamReader reader = new StreamReader(response.GetResponseStream());
                string str = null;
                do
                {
                    str = reader.ReadLine().Trim();
                    if (str.StartsWith("<div style=\"display:none\" id=\"divChiaSeForum\">"))
                    {
                        strArray = new string[] { "xmlURL=", "&autoplay" };
                        s = str.Split(strArray, StringSplitOptions.RemoveEmptyEntries)[1];
                    }
                }
                while ((str != null) && !reader.EndOfStream);

                HttpWebRequest request2 = (HttpWebRequest)WebRequest.Create(s);
                request2.Referer = "http://mp3.zing.vn";
                request2.UserAgent = "1234556";
                HttpWebResponse response2 = (HttpWebResponse)request2.GetResponse();
                StreamReader reader2 = new StreamReader(response2.GetResponseStream());
                string str2 = null;
                do
                {
                    str2 = reader2.ReadLine().Trim();
                    if (str2.StartsWith("<source>"))
                    {
                        strArray = new string[] { "<source>", "</source>" };
                        s = str2.Split(strArray, StringSplitOptions.RemoveEmptyEntries)[0];
                    }
                }
                while ((str2 != null) && !reader2.EndOfStream);
                s = s + "?q=5e980a20727bb3a9263eb4d921e3a534&t=60273720";
                return s;
            }
            catch
            {
                return "";
            }
        }
        //

        //tai bai hat ve may
        public void download()
        {
            try
            {
                down.Title = "Download File";
                down.InitialDirectory = "C:";
                down.Filter = "(*.mp3)|*.mp3";
                down.FileName = listViewEx1.Items[listViewEx1.SelectedIndices[0]].SubItems[1].Text;
                if (down.ShowDialog() == DialogResult.OK)
                {
                    string filename = down.FileName;
                    if (filename != "")
                    {
                        new WebClient().DownloadFile(listViewEx1.Items[listViewEx1.SelectedIndices[0]].SubItems[5].Text, filename);
                    }
                }
            }
            catch
            {
                if (listViewEx1.Items.Count == 0)
                {
                    MessageBox.Show("Chưa Có Bài Hát");
                }
                else
                {
                    MessageBox.Show("Kiểm Tra Lại Kết Nối");
                }
            }
        }
    }
}
