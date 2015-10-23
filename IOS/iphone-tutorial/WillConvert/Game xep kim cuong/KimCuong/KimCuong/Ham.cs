using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Runtime.InteropServices;

namespace KimCuong
{
    public partial class FormMain
    {
        public void XuatHinh()
        {
            foreach (KimCuong kc in mokimcuong.kimcuong)
            {
                kc.Image = hinh[kc.SoMau];
            }
        }
        private void swap(ref KimCuong a, ref KimCuong b)
        {
            int somau1, doc1, ngang1, sott1;
            int somau2, doc2, ngang2, sott2;
            somau1 = a.SoMau; somau2 = b.SoMau;
            doc1 = a.Doc; doc2 = b.Doc;
            ngang1 = a.Ngang; ngang2 = b.Ngang;
            sott1 = a.SoTT; sott2 = b.SoTT;
            KimCuong temp = new KimCuong();
            temp = a;
            a = b;
            b = temp;
            a.SoMau = somau2;
            a.Doc = doc2;
            a.Ngang = ngang2;
            a.SoTT = sott2;
            b.SoMau = somau1;
            b.SoTT = sott1;
            b.Doc = doc1;
            b.Ngang = ngang1;
        }
    }

}