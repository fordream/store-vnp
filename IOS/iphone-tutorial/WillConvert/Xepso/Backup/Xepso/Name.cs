using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace Xepso
{
    public partial class Name : Form
    {
        public Name()
        {
            InitializeComponent();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (textBox1.Text == "")
            {
                MessageBox.Show(" Ban phai dien ten cua ban ", " Thong Bao ");
            }
            else
            {
                player p = new player(textBox1.Text, Form1.moves, Form1.gio, Form1.phut, Form1.giay);
                function.insert(p);
                this.Close();
                new HighScores().ShowDialog();
            }
        }
    }
}