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
    public partial class thongtin : Office2007RibbonForm
    {
        public thongtin()
        {
            InitializeComponent();
        }

        public void set(string ten, string kieutep, string dung_luong, string vitri, string duaration)
        {
            String temp = ten;
            temp += "\n\n" + kieutep;
            temp += "\n\n" + dung_luong;
            temp += "\n\n" + vitri;
            temp += "\n\n" + duaration;
            this.label5.Text = temp;
        }

        private void buttonX1_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
