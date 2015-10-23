using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace Xepso
{
    public partial class HighScores : Form
    {
        public HighScores()
        {
            InitializeComponent();
            playerlist pl = function.readhigh();
            ListViewItem[] listv = new ListViewItem[10];
            for( int i = 0;i<10;i++)
            {
               
                listv[i] = new ListViewItem(pl.list[i].convert(i+1),-1);
            }
            this.listView1.Items.AddRange(listv);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}