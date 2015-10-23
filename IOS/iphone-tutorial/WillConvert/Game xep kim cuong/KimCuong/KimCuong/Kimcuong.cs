using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace KimCuong
{
    class KimCuong:Button
    {
        public int SoTT { get; set; }
        public bool TrangThai{get;set;}
        public int SoMau { get; set; }
        public int Doc { get; set; }
        public int Ngang { get; set; }
        public int XuLy { get; set; }
        public KimCuong()
        {
            this.Size = new System.Drawing.Size(85,85);
            SoTT = 0;
            TrangThai = false;
            SoMau = 0;
            Doc = 0;
            Ngang = 0;
            XuLy = 0;
        }

        public KimCuong(bool trangthai,int sott,int somau,int doc,int ngang,int xuly)
        {
            this.SoTT = sott;
            this.TrangThai = trangthai;
            this.SoMau = somau;
            this.Doc = doc;
            this.Ngang = ngang;
            this.XuLy = xuly;
        }

    }//KimCuong
}
