using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace KimCuong
{
    public partial class FormHighScore : Form
    {
        public FormHighScore()
        {
            InitializeComponent();
            PlayerList pl = HighScoreFunctions.readHighScores();
            ListViewItem[] listViewItems = new ListViewItem[10];
            for (int i = 0; i < 10; i++)
            {
                listViewItems[i] = new ListViewItem(pl.list[i].convert(i + 1), -1);
            }
            this.listView1.Items.AddRange(listViewItems);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
