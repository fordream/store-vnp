using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.IO;
using System.Xml;
using System.Net;
using System.Diagnostics;
using System.Globalization;

namespace WindowsFormsApplication3
{
    public partial class browser : Form
    {        
        string adress, name;

        public static String favXml = "favorits.xml", linksXml = "links.xml";
        String settingsXml = "settings.xml", historyXml = "history.xml";
        List<String> urls = new List<String>();
        XmlDocument settings = new XmlDocument();
        String homePage;
        CultureInfo currentCulture;

        /*private void setVisibility()
        {
            if (!File.Exists(settingsXml))
            {
                XmlElement r = settings.CreateElement("settings");
                settings.AppendChild(r);
                XmlElement el;

                el = settings.CreateElement("menuBar");
                el.SetAttribute("visible", "True");
                r.AppendChild(el);

                el = settings.CreateElement("adrBar");
                el.SetAttribute("visible", "True");
                r.AppendChild(el);

                el = settings.CreateElement("linkBar");
                el.SetAttribute("visible", "True");
                r.AppendChild(el);
                                
                el = settings.CreateElement("SplashScreen");
                el.SetAttribute("checked", "True");
                r.AppendChild(el);

                el = settings.CreateElement("homepage");
                el.InnerText = "about:blank";
                r.AppendChild(el);

                el = settings.CreateElement("dropdown");
                el.InnerText = "15";
                r.AppendChild(el);
            }
            else
            {
                settings.Load(settingsXml);
                XmlElement r = settings.DocumentElement;
                menuBar.Visible = (r.ChildNodes[0].Attributes[0].Value.Equals("True"));
                adrBar.Visible = (r.ChildNodes[1].Attributes[0].Value.Equals("True"));
                //linkBar.Visible = (r.ChildNodes[2].Attributes[0].Value.Equals("True"));                
                //splashScreenToolStripMenuItem.Checked = (r.ChildNodes[4].Attributes[0].Value.Equals("True"));
                //homePage = r.ChildNodes[5].InnerText;
            }

            this.linksBarToolStripMenuItem.Checked = linkBar.Visible;
            this.menuBarToolStripMenuItem.Checked = menuBar.Visible;
            this.commandBarToolStripMenuItem.Checked = adrBar.Visible;
            //splashScreenToolStripMenuItem.Checked = (settings.DocumentElement.ChildNodes[4].Attributes[0].Value.Equals("True"));
            //homePage = settings.DocumentElement.ChildNodes[5].InnerText;
        }*/

        public browser()
        {
            InitializeComponent();
            currentCulture = CultureInfo.CurrentCulture;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            this.toolStripStatusLabel1.Text = "Done";
            comboBox1.SelectedItem = comboBox1.Items[0];
            //setVisibility();
            addNewTab();
            //if (splashScreenToolStripMenuItem.Checked == true)
            //    (new About(true)).Show()
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (browserTabControl.TabCount != 2)
            {
                DialogResult dl_re = (new close()).ShowDialog();
                if (dl_re == DialogResult.No)
                {
                    e.Cancel = true; 
                    closeTab();
                }
                else if (dl_re == DialogResult.Cancel) e.Cancel = true;
                else Application.ExitThread();
            }
        }

        private void browser_FormClosed(object sender, FormClosedEventArgs e)
        {
            //settings.Save(settingsXml);
            File.Delete("source.txt");
        }

        private void addNewTab()
        {
            TabPage tp = new TabPage();
            tp.BorderStyle = BorderStyle.Fixed3D;
            browserTabControl.TabPages.Insert(browserTabControl.TabCount - 1, tp);
            WebBrowser browser = new WebBrowser();
            browser.Navigate(homePage);
            tp.Controls.Add(browser);
            browser.Dock = DockStyle.Fill;
            browserTabControl.SelectTab(tp);
            browser.ProgressChanged += new WebBrowserProgressChangedEventHandler(Form1_ProgressChanged);
            browser.DocumentCompleted += new WebBrowserDocumentCompletedEventHandler(Form1_DocumentComplete);
            browser.Navigating += new WebBrowserNavigatingEventHandler(Form1_Navigating);
            browser.CanGoBackChanged += new EventHandler(browser_CanGoBackChanged);
            browser.CanGoForwardChanged += new EventHandler(browser_CanGoForwardChanged);
        }
            
        private void closeTab()
        {
            if (browserTabControl.TabCount != 2)
            {
                browserTabControl.TabPages.RemoveAt(browserTabControl.SelectedIndex);
            }
        }

        private void Form1_DocumentComplete(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            WebBrowser currentBrowser = getCurrentBrowser();
            this.toolStripStatusLabel1.Text = "Done";
            String text = "Blank Page";
            
            if (!currentBrowser.Url.ToString().Equals("about:blank"))
            {
                text = currentBrowser.Url.Host.ToString();
            }
            this.addressBarTextBox.Text = currentBrowser.Url.ToString();
            browserTabControl.SelectedTab.Text = text;
            //img.Image = favicon(currentBrowser.Url.ToString(), "net.png");

            if (!urls.Contains(currentBrowser.Url.Host.ToString()))
                urls.Add(currentBrowser.Url.Host.ToString());

            if (!currentBrowser.Url.ToString().Equals("about:blank") && currentBrowser.StatusText.Equals("Done"))
                addHistory(currentBrowser.Url, DateTime.Now.ToString(currentCulture));
        }

        private void Form1_ProgressChanged(object sender, WebBrowserProgressChangedEventArgs e)
        {
            //Debug.WriteLine("webBrowser1_ProgressChanged: " + e.CurrentProgress + ", " + e.MaximumProgress);
            this.toolStripProgressBar1.Visible = (!(e.CurrentProgress <= 0 || e.CurrentProgress == e.MaximumProgress));
            if (this.toolStripProgressBar1.Visible) 
            {
                this.toolStripProgressBar1.Value = (int)(((float)e.CurrentProgress / (float)e.MaximumProgress) * 100);
            } 
            else 
            {
                this.toolStripProgressBar1.Value = 0;
            }
            
            //if (e.CurrentProgress < e.MaximumProgress)
            //    toolStripProgressBar1.Value = (int)e.CurrentProgress;
            //else toolStripProgressBar1.Value = toolStripProgressBar1.Minimum;
        }

        private void Form1_Navigating(object sender, WebBrowserNavigatingEventArgs e)
        {
            this.toolStripStatusLabel1.Text = getCurrentBrowser().StatusText;
        }

        private void browserTabControl_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (browserTabControl.SelectedIndex == browserTabControl.TabPages.Count - 1)
                addNewTab();
            else
            {
                if (getCurrentBrowser().Url != null)
                    addressBarTextBox.Text = getCurrentBrowser().Url.ToString();
                else addressBarTextBox.Text = "about:blank";

                if (getCurrentBrowser().CanGoBack)
                    toolStripButton1.Enabled = true;
                else toolStripButton1.Enabled = false;

                if (getCurrentBrowser().CanGoForward)
                    toolStripButton2.Enabled = true;
                else toolStripButton2.Enabled = false;
            }
        }
        
                        
        #region Address bar

        private WebBrowser getCurrentBrowser()
        {
            return (WebBrowser)browserTabControl.SelectedTab.Controls[0];
        }
                
        private void addressBarTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                getCurrentBrowser().Navigate(addressBarTextBox.Text);
            }
        }

        private void addressBarTextBox_Click(object sender, EventArgs e)
        {
            addressBarTextBox.SelectAll();
        }

        private void showUrl()
        {
            if (File.Exists(historyXml))
            {
                XmlDocument myxml = new XmlDocument();
                myxml.Load(historyXml);
                int i = 0;
                int num = int.Parse(settings.DocumentElement.ChildNodes[6].InnerText.ToString());
                foreach (XmlElement el in myxml.DocumentElement.ChildNodes)
                {
                    if (num <= i++) break;
                    else addressBarTextBox.Items.Add(el.GetAttribute("url").ToString());
                }
            }
        }

        private void addressBarTextBox_DropDown(object sender, EventArgs e)
        {
            addressBarTextBox.Items.Clear();
            showUrl();
        }

        private void 
            addressBarTextBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            getCurrentBrowser().Navigate(addressBarTextBox.SelectedItem.ToString());
        }

        void browser_CanGoForwardChanged(object sender, EventArgs e)
        {
            toolStripButton2.Enabled = !toolStripButton2.Enabled;
        }

        void browser_CanGoBackChanged(object sender, EventArgs e)
        {
            toolStripButton1.Enabled = !toolStripButton1.Enabled;
        }

        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().GoBack();
        }

        private void toolStripButton2_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().GoForward();
        }

        private void toolStripButton3_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Navigate(addressBarTextBox.Text);
        }

        private void toolStripButton4_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Refresh();
        }

        private void toolStripButton5_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Stop();
        }

        private void searchTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
                if (googleSearch.Checked == true)
                    getCurrentBrowser().Navigate("http://www.google.com.vn/search?hl=vi&q=" + searchTextBox.Text);
                else
                    getCurrentBrowser().Navigate("http://vn.search.yahoo.com/search;_ylt=A0oGk3PrHgtM33sAtAGBdHRG;_ylc=X1MDMjE0MjQ3ODk0OARfcgMyBGZyA3NmcARuX2dwcwMwBG9yaWdpbgNzeWMEcXVlcnkDY3V0BHNhbwMw?p=" + searchTextBox.Text);
        }

        private void googleSearch_Click(object sender, EventArgs e)
        {
            yahooSearch.Checked = !googleSearch.Checked; 
        }

        private void yahooSearch_Click(object sender, EventArgs e)
        {
            googleSearch.Checked = !yahooSearch.Checked;
        }
                
        private void openInNewTabToolStripMenuItem_Click(object sender, EventArgs e)
        {
            addNewTab();
            getCurrentBrowser().Navigate(adress);
        }

        private void openInNewWindowToolStripMenuItem_Click(object sender, EventArgs e)
        {
            browser new_form = new browser();
            new_form.Show();
            new_form.getCurrentBrowser().Navigate(adress);
        }

        #endregion

                       
        #region Menu File

        private void newTabToolStripMenuItem_Click(object sender, EventArgs e)
        {
            addNewTab();
        }

        private void duplicateTabToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (getCurrentBrowser().Url != null)
            {
                Uri dup_url = getCurrentBrowser().Url;
                addNewTab();
                getCurrentBrowser().Url = dup_url;
            }
            else addNewTab();
        }

        private void newWindowToolStripMenuItem_Click(object sender, EventArgs e)
        {
            (new browser()).Show();
        }

        private void closeTabToolStripMenuItem_Click(object sender, EventArgs e)
        {
            closeTab();
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            (new open(getCurrentBrowser())).Show();
        }

        private void saveAsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().ShowSaveAsDialog();
        }

        private void pageSetupToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().ShowPageSetupDialog();
        }

        private void printToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().ShowPrintDialog();
        }

        private void printPreviewToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().ShowPrintPreviewDialog();
        }

        private void propertiesToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().ShowPropertiesDialog();
        }

        #endregion
                       
 
        #region Menu Edit
        
        private void cutToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Document.ExecCommand("Cut", false, null);
        }

        private void copyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Document.ExecCommand("Copy", false, null);
        }

        private void pasteToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Document.ExecCommand("Paste", false, null);
        }

        private void selectAllToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Document.ExecCommand("SeclectAll", true, null);
        }
        #endregion
                    
    
        #region Menu View

        private void favoritsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            favoritesPanel.Visible = true;
            favoritesTabControl.SelectedTab = favTabPage;
        }

        private void historyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            favoritesPanel.Visible = true;
            favoritesTabControl.SelectedTab = historyTabPage;
        }

        private void explorerBarsToolStripMenuItem_DropDownOpening(object sender, EventArgs e)
        {
            favoritesViewMenuItem.Checked = (favoritesPanel.Visible == true && favoritesTabControl.SelectedTab == favTabPage);
            historyViewMenuItem.Checked = (favoritesPanel.Visible == true && favoritesTabControl.SelectedTab == historyTabPage);
        }
        
        private void goTo_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Navigate(sender.ToString());
        }

        private void goToToolStripMenuItem_DropDown(object sender, EventArgs e)
        {
            backToolStripMenuItem.Enabled = getCurrentBrowser().CanGoBack;
            forwardToolStripMenuItem.Enabled = getCurrentBrowser().CanGoForward;
            while (goToMenuItem.DropDownItems.Count > 5)
                goToMenuItem.DropDownItems.RemoveAt(goToMenuItem.DropDownItems.Count - 1);
            foreach (string a in urls)
            {
                ToolStripMenuItem item = new ToolStripMenuItem(a, null, goTo_Click);
                item.Checked = (getCurrentBrowser().Url.Host.ToString().Equals(a));
                goToMenuItem.DropDownItems.Add(item);
            }
        }            

        private void backToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().GoBack();
        }

        private void forwardToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().GoForward();
        }

        private void homePageToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Navigate(homePage);
        }

        private void stopToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Stop();
        }

        private void refreshToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Refresh();
        }

        private void sourceToolStripMenuItem_Click(object sender, EventArgs e)
        {
            String source = ("source.txt");
            StreamWriter writer = File.CreateText(source);
            writer.Write(getCurrentBrowser().DocumentText);
            writer.Close();
            Process.Start("notepad.exe", source);
        }

        private void fullScreenToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (!((this.FormBorderStyle == FormBorderStyle.None) && (this.WindowState == FormWindowState.Maximized)))
            {
                this.FormBorderStyle = FormBorderStyle.None;
                this.WindowState = FormWindowState.Maximized;
                this.TopMost = true;
                menuBar.Visible = false;
                adrBar.Visible = false;
                favoritesPanel.Visible = false;
            }
            else
            {
                this.WindowState = FormWindowState.Normal;
                this.FormBorderStyle = FormBorderStyle.Sizable;
                this.TopMost = false;
                menuBar.Visible = true;
                adrBar.Visible = true;
                favoritesPanel.Visible = true;
                //menuBar.Visible = (settings.DocumentElement.ChildNodes[0].Attributes[0].Value.Equals("True"));
                //adrBar.Visible = (settings.DocumentElement.ChildNodes[1].Attributes[0].Value.Equals("True"));
                //favoritesPanel.Visible = (settings.DocumentElement.ChildNodes[2].Attributes[0].Value.Equals("True"));
            }
        }

        private void textSizeToolStripMenuItem_DropDownItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {
            string level = e.ClickedItem.ToString();
            smallerToolStripMenuItem.Checked = false;
            smallestToolStripMenuItem.Checked = false;
            mediumToolStripMenuItem.Checked = false;
            largerToolStripMenuItem.Checked = false;
            largestToolStripMenuItem.Checked = false;
            switch (level)
            {
                case "Smallest": getCurrentBrowser().Document.ExecCommand("FontSize", true, "0");
                    smallestToolStripMenuItem.Checked = true;
                    break;
                case "Smaller": getCurrentBrowser().Document.ExecCommand("FontSize", true, "1");
                    smallerToolStripMenuItem.Checked = true;
                    break;
                case "Medium": getCurrentBrowser().Document.ExecCommand("FontSize", true, "2");
                    mediumToolStripMenuItem.Checked = true;
                    break;
                case "Larger": getCurrentBrowser().Document.ExecCommand("FontSize", true, "3");
                    largerToolStripMenuItem.Checked = true;
                    break;
                case "Largest": getCurrentBrowser().Document.ExecCommand("FontSize", true, "4");
                    largestToolStripMenuItem.Checked = true;
                    break;
            }
        }
        /*
        private void commandBarToolStripMenuItem_Click(object sender, EventArgs e)
        {
            adrBar.Visible = !adrBar.Visible;
            this.commandBarToolStripMenuItem.Checked = adrBar.Visible;
            settings.DocumentElement.ChildNodes[1].Attributes[0].Value = adrBar.Visible.ToString();
        }*/
        #endregion
                   
     
        #region Menu Tools

        private void deleteBrowserHistoryToolStripMenuItem_Click(object sender, EventArgs e)
        {
            DeleteBrowsingHistory b = new DeleteBrowsingHistory();
            if (b.ShowDialog() == DialogResult.OK)
            {
                if (b.History.Checked == true)
                {
                    File.Delete(historyXml);
                    historyTreeView.Nodes.Clear();
                }
                if (b.TempFiles.Checked == true)
                {
                    urls.Clear();
                    while (imgList.Images.Count > 4)
                        imgList.Images.RemoveAt(imgList.Images.Count - 1);
                    File.Delete("source.txt");                    
                }
            }
        }

        private void yahooMailToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Navigate("https://login.yahoo.com/config/login_verify2?&.src=ym");
        }

        private void gmailToolStripMenuItem_Click(object sender, EventArgs e)
        {
            getCurrentBrowser().Navigate("https://www.google.com/accounts/ServiceLogin?service=mail&passive=true&rm=false&continue=http%3A%2F%2Fmail.google.com%2Fmail%2F%3Fui%3Dhtml%26zy%3Dl&bsv=1eic6yu9oa4y3&scc=1&ltmpl=default&ltmplcache=2");
        }
        #endregion

                        
        #region Favicon
        public static Image favicon(String u, string file)
        {
            Uri url = new Uri(u);
            String iconurl = "http://" + url.Host + "/favicon.ico";

            WebRequest request = WebRequest.Create(iconurl);
            try
            {
                WebResponse response = request.GetResponse();

                Stream s = response.GetResponseStream();
                return Image.FromStream(s);
            }
            catch (Exception ex)
            {
                return Image.FromFile(file);
            }
        }
        
        private int faviconIndex(string url)
        {
            Uri key = new Uri(url);
            if (!imgList.Images.ContainsKey(key.Host.ToString()))
                imgList.Images.Add(key.Host.ToString(), favicon(url, "link.png"));
            return imgList.Images.IndexOfKey(key.Host.ToString());
        }

        private Image getFavicon(string key)
        {
            Uri url = new Uri(key);
            if (!imgList.Images.ContainsKey(url.Host.ToString()))
                imgList.Images.Add(url.Host.ToString(), favicon(key, "link.png"));
            return imgList.Images[url.Host.ToString()];
        }

        #endregion        


        /*
        private void items_Click(object sender, EventArgs e)
        {
            ToolBarButton b = (ToolBarButton)sender;
            getCurrentBrowser().Navigate(b.ToolTipText);
        }
        
        private void b_MouseUp (object sender, MouseEventArgs e)
        {
            ToolBarButton b = (ToolBarButton)sender;
            adress = b.ToolTipText;
            name = b.Text;
            if (e.Button == MouseButtons.Right)
                linkContextMenu.Show(MousePosition);
        }

        private void showLink()
        {
            if (File.Exists(linksXml))
            {
                XmlDocument myXml = new XmlDocument();
                myXml.Load(linksXml);
                XmlElement root = myXml.DocumentElement;
                foreach (XmlElement el in root.ChildNodes)
                {
                    ToolStripButton b = new ToolStripButton(el.InnerText, getFavicon(el.GetAttribute("url")), items_Click, el.GetAttribute("url"));
                    b.ToolTipText = el.GetAttribute("url");
                    b.MouseUp += new MouseEventHandler(b_MouseUp);
                    linkBar.Items.Add(b);
                }
            }
        }*/


        #region Fovorites

        private void addFavorit(String url, string name)
        {
            XmlDocument myxml = new XmlDocument();
            XmlElement el = myxml.CreateElement("favorit");
            el.SetAttribute("url", url);
            el.InnerText = name;
            if (!File.Exists(favXml))
            {
                XmlElement root = myxml.CreateElement("favorites");
                myxml.AppendChild(root);
                root.AppendChild(el);
            }
            else
            {
                myxml.Load(favXml);
                myxml.DocumentElement.AppendChild(el);
            }

            if (favoritesPanel.Visible == true)
            {
                TreeNode node = new TreeNode(el.InnerText, faviconIndex(el.GetAttribute("url")), faviconIndex(el.GetAttribute("url")));
                node.ToolTipText = el.GetAttribute("url");
                node.Name = el.GetAttribute("url");
                node.ContextMenuStrip = favContextMenu;
                favTreeView.Nodes.Add(node);
            }
            myxml.Save(favXml);
        }

        private void addLink(String url, string name)
        {
            XmlDocument myxml = new XmlDocument();
            XmlElement el = myxml.CreateElement("link");
            el.SetAttribute("url", url);
            el.InnerText = name;
            if (!File.Exists(linksXml))
            {
                XmlElement root = myxml.CreateElement("links");
                myxml.AppendChild(root);
                root.AppendChild(el);
            }
            else
            {
                myxml.Load(linksXml);
                myxml.DocumentElement.AppendChild(el);
            }
            if (favoritesPanel.Visible == true)
            {
                TreeNode node = new TreeNode(el.InnerText, faviconIndex(el.GetAttribute("url")), faviconIndex(el.GetAttribute("url")));
                node.ToolTipText = el.GetAttribute("url");
                node.Name = el.GetAttribute("url");
                node.ContextMenuStrip = linkContextMenu;
                favTreeView.Nodes[0].Nodes.Add(node);
            }
            myxml.Save(linksXml);
        }

        private void deleteLink()
        {
            if (favoritesPanel.Visible == true)
                favTreeView.Nodes[0].Nodes[adress].Remove();
            XmlDocument myxml = new XmlDocument();
            myxml.Load(linksXml);
            XmlElement root = myxml.DocumentElement;
            foreach (XmlElement x in root.ChildNodes)
            {
                if (x.GetAttribute("url").Equals(adress))
                {
                    root.RemoveChild(x);
                    break;
                }
            }
            myxml.Save(linksXml);
        }

        private void deleteFavorit()
        {
            favTreeView.SelectedNode.Remove();
            XmlDocument myxml = new XmlDocument();
            myxml.Load(favXml);
            XmlElement root = myxml.DocumentElement;
            foreach (XmlElement x in root.ChildNodes)
            {
                if (x.GetAttribute("url").Equals(adress))
                {
                    root.RemoveChild(x);
                    break;
                }
            }
            myxml.Save(favXml);
        }

        private void toolStripButton7_Click(object sender, EventArgs e)
        {
            favoritesPanel.Visible = !favoritesPanel.Visible;
            settings.DocumentElement.ChildNodes[3].Attributes[0].Value = favoritesPanel.Visible.ToString();
        }

        private void toolStripButton8_Click(object sender, EventArgs e)
        {
            if (getCurrentBrowser().Url != null)
            {
                AddFavorites dl = new AddFavorites(getCurrentBrowser().Url.ToString());
                DialogResult res = dl.ShowDialog();
                if (res == DialogResult.OK)
                {
                    if (dl.favFile == "Favorites")
                        addFavorit(getCurrentBrowser().Url.ToString(), dl.favName);
                    else addLink(getCurrentBrowser().Url.ToString(), dl.favName);
                }
                dl.Close();
            }
        }

        private void showFavorites()
        {
            XmlDocument myxml = new XmlDocument();
            TreeNode link = new TreeNode("Links", 0, 0);
            link.NodeFont = new Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Underline, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            favTreeView.Nodes.Add(link);
            if (File.Exists(favXml))
            {
                myxml.Load(favXml);
                foreach (XmlElement el in myxml.DocumentElement.ChildNodes)
                {
                    TreeNode node = new TreeNode(el.InnerText, faviconIndex(el.GetAttribute("url")), faviconIndex(el.GetAttribute("url")));
                    node.ToolTipText = el.GetAttribute("url");
                    node.Name = el.GetAttribute("url");
                    node.ContextMenuStrip = favContextMenu;
                    favTreeView.Nodes.Add(node);
                }
            }
            if (File.Exists(linksXml))
            {
                myxml.Load(linksXml);
                foreach (XmlElement el in myxml.DocumentElement.ChildNodes)
                {
                    TreeNode node = new TreeNode(el.InnerText, faviconIndex(el.GetAttribute("url")), faviconIndex(el.GetAttribute("url")));
                    node.ToolTipText = el.GetAttribute("url");
                    node.Name = el.GetAttribute("url");
                    node.ContextMenuStrip = linkContextMenu;
                    favTreeView.Nodes[0].Nodes.Add(node);
                }
            }
        }

        void treeView1_NodeMouseClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                favTreeView.SelectedNode = e.Node;
                adress = e.Node.ToolTipText;
                name = e.Node.Text;
            }
            else
                if (e.Node != favTreeView.Nodes[0])
                    getCurrentBrowser().Navigate(e.Node.ToolTipText);
        }

        //Menu Favorites
        private void addToFavoritsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (getCurrentBrowser().Url != null)
            {
                AddFavorites dl = new AddFavorites(getCurrentBrowser().Url.ToString());
                DialogResult res = dl.ShowDialog();
                if (res == DialogResult.OK)
                {
                    if (dl.favFile == "Favorites")
                        addFavorit(getCurrentBrowser().Url.ToString(), dl.favName);
                    else addLink(getCurrentBrowser().Url.ToString(), dl.favName);
                }
                dl.Close();
            }
        }

        private void addToFavoritsBarToolStripMenuItem_Click(object sender, EventArgs e)
        {
            addLink(getCurrentBrowser().Url.ToString(), getCurrentBrowser().Url.ToString());
        }

        private void fav_Click(object sender, EventArgs e)
        {
            ToolStripMenuItem m = (ToolStripMenuItem)sender;
            getCurrentBrowser().Navigate(m.ToolTipText);
        }

        private void favoritesToolStripMenuItem_DropDownOpening(object sender, EventArgs e)
        {
            XmlDocument myxml = new XmlDocument();
            myxml.Load(favXml);
            for (int i = favoritesToolStripMenuItem.DropDownItems.Count - 1; i > 5; i--)
            {
                favoritesToolStripMenuItem.DropDownItems.RemoveAt(i);
            }
            foreach (XmlElement el in myxml.DocumentElement.ChildNodes)
            {
                ToolStripMenuItem item = new ToolStripMenuItem(el.InnerText, getFavicon(el.GetAttribute("url")), fav_Click);
                item.ToolTipText = el.GetAttribute("url");
                favoritesToolStripMenuItem.DropDownItems.Add(item);
            }
        }

        private void linksMenuItem_DropDownOpening(object sender, EventArgs e)
        {
            XmlDocument myxml = new XmlDocument();
            myxml.Load(linksXml);
            linksMenuItem.DropDownItems.Clear();
            foreach (XmlElement el in myxml.DocumentElement.ChildNodes)
            {
                ToolStripMenuItem item = new ToolStripMenuItem(el.InnerText, getFavicon(el.GetAttribute("url")), fav_Click);
                item.ToolTipText = el.GetAttribute("url");
                linksMenuItem.DropDownItems.Add(item);
            }
        }


        #endregion

                                
        #region History
        private void addHistory(Uri url, string data)
        {
            XmlDocument myxml = new XmlDocument();
            int i = 1;
            XmlElement el = myxml.CreateElement("item");
            el.SetAttribute("url", url.ToString());
            el.SetAttribute("lastVisited", data);

            if (!File.Exists(historyXml))
            {
                XmlElement root = myxml.CreateElement("history");
                myxml.AppendChild(root);
                el.SetAttribute("times", "1");
                root.AppendChild(el);
            }
            else
            {
                myxml.Load(historyXml);
                foreach (XmlElement x in myxml.DocumentElement.ChildNodes)
                {
                    if (x.GetAttribute("url").Equals(url.ToString()))
                    {
                        i = int.Parse(x.GetAttribute("times")) + 1;
                        myxml.DocumentElement.RemoveChild(x);
                        break;
                    }
                }
                el.SetAttribute("times", i.ToString());
                myxml.DocumentElement.InsertBefore(el, myxml.DocumentElement.FirstChild);

                if (favoritesPanel.Visible == true)
                {
                    if (comboBox1.Text.Equals("Ordered Visited Today"))
                    {
                        if (!historyTreeView.Nodes.ContainsKey(url.ToString()))
                        {
                            TreeNode node = new TreeNode(url.ToString(), 3, 3);
                            node.ToolTipText = url.ToString() + "\nLast Visted:" + data + "\nTimes visted:" + i.ToString();
                            node.Name = url.ToString();
                            node.ContextMenuStrip = histContextMenu;
                            historyTreeView.Nodes.Insert(0, node);
                        }
                        else
                            historyTreeView.Nodes[url.ToString()].ToolTipText = url.ToString() + "\nLast Visted:" + data + "\nTimes visted:" + i.ToString();
                    }

                    if (comboBox1.Text.Equals("View By Site"))
                    {
                        if (!historyTreeView.Nodes.ContainsKey(url.Host.ToString()))
                        {
                            historyTreeView.Nodes.Add(url.Host.ToString(), url.Host.ToString(), 0, 0);
                            TreeNode node = new TreeNode(url.ToString(), 3, 3);
                            node.ToolTipText = url.ToString() + "\nLast Visted:" + data + "\nTimes visted:" + i.ToString();
                            node.Name = url.ToString();
                            node.ContextMenuStrip = histContextMenu;
                            historyTreeView.Nodes[url.Host.ToString()].Nodes.Add(node);
                        }
                        else
                            if (!historyTreeView.Nodes[url.ToString()].Nodes.ContainsKey(url.Host.ToString()))
                            {
                                TreeNode node = new TreeNode(url.ToString(), 3, 3);
                                node.ToolTipText = url.ToString() + "\nLast Visted:" + data + "\nTimes visted:" + i.ToString();
                                node.Name = url.ToString();
                                node.ContextMenuStrip = histContextMenu;
                                historyTreeView.Nodes[url.Host.ToString()].Nodes.Add(node);
                            }
                            else
                                historyTreeView.Nodes[url.Host.ToString()].Nodes[url.ToString()].ToolTipText = url.ToString() + "\nLast Visted:" + data + "\nTimes visted:" + i.ToString();
                    }

                    if (comboBox1.Text.Equals("View By Date"))
                    {
                        if (historyTreeView.Nodes[4].Nodes.ContainsKey(url.ToString()))
                            historyTreeView.Nodes[url.ToString()].ToolTipText = url.ToString() + "\nLast Visted:" + data + "\nTimes visted:" + i.ToString();
                        else
                        {
                            TreeNode node = new TreeNode(url.ToString(), 3, 3);
                            node.ToolTipText = url.ToString() + "\nLast Visted:" + data + "\nTimes visted:" + i.ToString();
                            node.Name = url.ToString();
                            node.ContextMenuStrip = histContextMenu;
                            historyTreeView.Nodes[4].Nodes.Add(node);
                        }
                    }
                }
            }
            myxml.Save(historyXml);
        }

        private void deleteHistory()
        {
            XmlDocument myxml = new XmlDocument();
            myxml.Load(historyXml);
            XmlElement root = myxml.DocumentElement;
            foreach (XmlElement x in root.ChildNodes)
            {
                if (x.GetAttribute("url").Equals(adress))
                {
                    root.RemoveChild(x);
                    break;
                }
            }
            historyTreeView.SelectedNode.Remove();
            myxml.Save(historyXml);
        }

        private void showHistory()
        {
            historyTreeView.Nodes.Clear();
            XmlDocument myxml = new XmlDocument();
            if (File.Exists(historyXml))
            {
                myxml.Load(historyXml);
                DateTime now = DateTime.Now;
                if (comboBox1.Text.Equals("Ordered Visited Today"))
                {
                    historyTreeView.ShowRootLines = false;
                    foreach (XmlElement el in myxml.DocumentElement.ChildNodes)
                    {
                        DateTime d = DateTime.Parse(el.GetAttribute("lastVisited"), currentCulture);
                        if (!(d.Date == now.Date))
                            return;
                        TreeNode node = new TreeNode(el.GetAttribute("url"), 3, 3);
                        node.ToolTipText =el.GetAttribute("url") + "\nLast Visted:" + el.GetAttribute("lastVisited") + "\nTimes visted:" + el.GetAttribute("times");
                        node.Name = el.GetAttribute("url");
                        node.ContextMenuStrip = histContextMenu;
                        historyTreeView.Nodes.Add(node);
                    }
                }

                if (comboBox1.Text.Equals("View By Site"))
                {
                    historyTreeView.ShowRootLines = true;
                    foreach (XmlElement el in myxml.DocumentElement.ChildNodes)
                    {
                        Uri site = new Uri(el.GetAttribute("url"));
                        if (!historyTreeView.Nodes.ContainsKey(site.Host.ToString()))
                            historyTreeView.Nodes.Add(site.Host.ToString(), site.Host.ToString(), 0, 0);
                            TreeNode node = new TreeNode(el.GetAttribute("url"), 3, 3);
                            node.ToolTipText = el.GetAttribute("url") + "\nLast Visted:" + el.GetAttribute("lastVisited") + "\nTimes visted:" + el.GetAttribute("times");
                            node.Name = el.GetAttribute("url");
                            node.ContextMenuStrip = histContextMenu;
                            historyTreeView.Nodes[site.Host.ToString()].Nodes.Add(node);                           
                    }
                }

                if (comboBox1.Text.Equals("View By Date"))
                {
                    historyTreeView.ShowRootLines = true;
                    historyTreeView.Nodes.Add("2 weeks ago","2 weeks ago",2,2);
                    historyTreeView.Nodes.Add("Last week","Last week",2,2);
                    historyTreeView.Nodes.Add("This week","This week",2,2);
                    historyTreeView.Nodes.Add("Yesterday","Yesterday",2,2);
                    historyTreeView.Nodes.Add("Today","Today",2,2);
                    foreach (XmlElement el in myxml.DocumentElement.ChildNodes)
                    {
                        DateTime d = DateTime.Parse(el.GetAttribute("lastVisited"), currentCulture);
                        TreeNode node = new TreeNode(el.GetAttribute("url"), 3, 3);
                        node.ToolTipText = el.GetAttribute("url") + "\nLast Visted:" + el.GetAttribute("lastVisited") + "\nTimes visted:" + el.GetAttribute("times");
                        node.Name = el.GetAttribute("url");
                        node.ContextMenuStrip = histContextMenu;
                        
                        if (d.Date == now.Date)
                            historyTreeView.Nodes[4].Nodes.Add(node);
                        else
                            if (d.AddDays(1).ToShortDateString().Equals(now.ToShortDateString()))
                                historyTreeView.Nodes[3].Nodes.Add(node);
                            else
                                if (d.AddDays(7)>now)
                                    historyTreeView.Nodes[2].Nodes.Add(node);
                                else
                                    if (d.AddDays(14)>now)
                                        historyTreeView.Nodes[1].Nodes.Add(node);
                                    else
                                        if (d.AddDays(21)>now)
                                        historyTreeView.Nodes[0].Nodes.Add(node);
                                        else
                                            if (d.AddDays(22)>now)
                                                myxml.DocumentElement.RemoveChild(el);
                    }
                }
            }
        }

        private void historyTreeView_NodeMouseClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                historyTreeView.SelectedNode = e.Node;
                adress = e.Node.Text;
            }
            else
                if (comboBox1.Text.Equals("Ordered Visited Today"))
                {
                    if (!historyTreeView.Nodes.Contains(e.Node))
                        getCurrentBrowser().Navigate(e.Node.Text);
                }
                else
                    getCurrentBrowser().Navigate(e.Node.Text);
        }

        private void favoritesPanel_VisibleChanged(object sender, EventArgs e)
        {
            if (favoritesPanel.Visible == true)
            {
                showFavorites();
                showHistory();
            }
            else
            {
                favTreeView.Nodes.Clear();
                historyTreeView.Nodes.Clear();
            }
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            showHistory();
        }

        #endregion
        
    }
}
