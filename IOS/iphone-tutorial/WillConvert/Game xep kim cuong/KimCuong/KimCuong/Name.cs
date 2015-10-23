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
    public partial class Name : Form
    {
        int diem;
        public Name(int diem)
        {
            InitializeComponent();
            this.diem = diem;
        }
        private void button1_Click(object sender, EventArgs e)
        {
            if (this.textBox1.Text == "")
                MessageBox.Show(this, "Your name must not be blank!", "Error", MessageBoxButtons.OK,
                    MessageBoxIcon.Error);
            else
            {
                Player p = new Player(this.textBox1.Text,diem);
                HighScoreFunctions.insert(p);
                this.Close();
                new FormHighScore().ShowDialog();
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
