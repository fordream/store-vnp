using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace KimCuong
{
    public partial class FormMain
    {
        
        private int Hint()
        {
            cout = 0;
            foreach (KimCuong kc in mokimcuong.kimcuong)
            {
                kc.TrangThai = false;
            }
            XuatHinh();
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 7; j++)
                {
                    
                        #region tim theo hang ngang

                        if (mokimcuong.kimcuong[i, j].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                        {
                            if (i == 0)
                            {
                                if (j == 0)
                                {
                                    if (mokimcuong.kimcuong[i + 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i + 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j + 3].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i, j + 3].Image = bitmap[mokimcuong.kimcuong[i, j + 3].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 1)
                                {
                                    if (mokimcuong.kimcuong[i + 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j -1].Image = bitmap[mokimcuong.kimcuong[i + 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i + 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i + 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j + 3].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j + 3].Image = bitmap[mokimcuong.kimcuong[i , j + 3].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j >= 2 && j < 5)
                                {
                                    if (mokimcuong.kimcuong[i + 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j -1].Image = bitmap[mokimcuong.kimcuong[i + 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j - 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j - 2].Image = bitmap[mokimcuong.kimcuong[i , j - 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i + 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i + 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j + 3].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i, j +3].Image = bitmap[mokimcuong.kimcuong[i , j + 3].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 5)
                                {
                                    if (mokimcuong.kimcuong[i + 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j -1].Image = bitmap[mokimcuong.kimcuong[i + 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j - 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j - 2].Image = bitmap[mokimcuong.kimcuong[i + 1, j - 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i + 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i + 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 6)
                                {
                                    if (mokimcuong.kimcuong[i + 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j -1].Image = bitmap[mokimcuong.kimcuong[i + 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j - 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j - 2].Image = bitmap[mokimcuong.kimcuong[i , j - 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                            }
                            if (i > 0 && i < 7)
                            {
                                if (j == 0)
                                {
                                    if (mokimcuong.kimcuong[i - 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i - 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i + 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i + 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j + 3].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j + 3].Image = bitmap[mokimcuong.kimcuong[i , j + 3].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 1)
                                {
                                    if (mokimcuong.kimcuong[i - 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i - 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i + 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i + 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j + 3].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j + 3].Image = bitmap[mokimcuong.kimcuong[i , j + 3].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i - 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j -1].Image = bitmap[mokimcuong.kimcuong[i -1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i + 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j -1].Image = bitmap[mokimcuong.kimcuong[i + 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j >= 2 && j < 5)
                                {
                                    if (mokimcuong.kimcuong[i - 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j -1].Image = bitmap[mokimcuong.kimcuong[i - 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i + 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j -1].Image = bitmap[mokimcuong.kimcuong[i + 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j - 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j - 2].Image = bitmap[mokimcuong.kimcuong[i , j - 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i - 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i - 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i + 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i + 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j + 3].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j + 3].Image = bitmap[mokimcuong.kimcuong[i, j + 3].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 5)
                                {
                                    if (mokimcuong.kimcuong[i - 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j -1].Image = bitmap[mokimcuong.kimcuong[i - 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i + 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j -1].Image = bitmap[mokimcuong.kimcuong[i + 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j - 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j - 2].Image = bitmap[mokimcuong.kimcuong[i , j - 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i - 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i - 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i + 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i + 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 6)
                                {
                                    if (mokimcuong.kimcuong[i - 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j -1].Image = bitmap[mokimcuong.kimcuong[i - 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i + 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i + 1, j -1].Image = bitmap[mokimcuong.kimcuong[i + 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j - 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j - 2].Image = bitmap[mokimcuong.kimcuong[i , j - 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                            }
                            if (i == 7)
                            {
                                if (j == 0)
                                {
                                    if (mokimcuong.kimcuong[i - 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i - 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j + 3].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j + 3].Image = bitmap[mokimcuong.kimcuong[i , j + 3].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 1)
                                {

                                    if (mokimcuong.kimcuong[i - 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j -1].Image = bitmap[mokimcuong.kimcuong[i -1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i - 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i - 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j + 3].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j + 3].Image = bitmap[mokimcuong.kimcuong[i , j + 3].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j >= 2 && j < 5)
                                {
                                    if (mokimcuong.kimcuong[i - 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j -1].Image = bitmap[mokimcuong.kimcuong[i - 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j - 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j - 2].Image = bitmap[mokimcuong.kimcuong[i , j - 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i - 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i - 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j + 3].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j + 3].Image = bitmap[mokimcuong.kimcuong[i , j + 3].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 5)
                                {
                                    if (mokimcuong.kimcuong[i - 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i -1, j -1].Image = bitmap[mokimcuong.kimcuong[i -1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j - 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j - 2].Image = bitmap[mokimcuong.kimcuong[i , j - 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i - 1, j + 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j + 2].Image = bitmap[mokimcuong.kimcuong[i - 1, j + 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 6)
                                {
                                    if (mokimcuong.kimcuong[i - 1, j - 1].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i - 1, j -1].Image = bitmap[mokimcuong.kimcuong[i - 1, j -1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[i, j - 2].SoMau == mokimcuong.kimcuong[i, j + 1].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[i , j - 2].Image = bitmap[mokimcuong.kimcuong[i , j - 2].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                            }
                        }
                        #endregion
                        #region tim theo cot doc
                        if (mokimcuong.kimcuong[j, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                        {
                            if (i == 0)
                            {
                                if (j == 0)
                                {
                                    if (mokimcuong.kimcuong[j + 2, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i + 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 3, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 3, i].Image = bitmap[mokimcuong.kimcuong[j + 3, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 1)
                                {
                                    if (mokimcuong.kimcuong[j - 1, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i + 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 2, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i + 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 3, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 3, i].Image = bitmap[mokimcuong.kimcuong[j + 3, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j >= 2 && j < 5)
                                {
                                    if (mokimcuong.kimcuong[j - 1, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i + 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 2, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i + 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 2, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 2, i].Image = bitmap[mokimcuong.kimcuong[j - 2, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 3, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 3, i].Image = bitmap[mokimcuong.kimcuong[j + 3, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 5)
                                {
                                    if (mokimcuong.kimcuong[j - 1, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i + 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 2, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i + 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 2, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 2, i].Image = bitmap[mokimcuong.kimcuong[j - 2, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 6)
                                {
                                    if (mokimcuong.kimcuong[j - 1, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i + 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 2, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 2, i].Image = bitmap[mokimcuong.kimcuong[j - 2, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                            }
                            if (i > 0 && i < 7)
                            {
                                if (j == 0)
                                {
                                    if (mokimcuong.kimcuong[j + 2, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i - 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 2, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i + 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 3, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 3, i].Image = bitmap[mokimcuong.kimcuong[j + 3, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 1)
                                {
                                    if (mokimcuong.kimcuong[j + 2, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i - 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 1, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i - 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 1, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i + 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 2, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i + 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 3, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 3, i].Image = bitmap[mokimcuong.kimcuong[j + 3, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j >= 2 && j < 5)
                                {
                                    if (mokimcuong.kimcuong[j + 2, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i - 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 1, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i - 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 1, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i + 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 2, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i + 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 2, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 2, i].Image = bitmap[mokimcuong.kimcuong[j - 2, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 3, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 3, i].Image = bitmap[mokimcuong.kimcuong[j + 3, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 5)
                                {
                                    if (mokimcuong.kimcuong[j + 2, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i - 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 1, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i - 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 1, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i + 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 2, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i + 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 2, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 2, i].Image = bitmap[mokimcuong.kimcuong[j - 2, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 6)
                                {
                                    if (mokimcuong.kimcuong[j - 1, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i - 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 1, i + 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i + 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i + 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 2, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 2, i].Image = bitmap[mokimcuong.kimcuong[j - 2, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                            }
                            if (i == 7)
                            {
                                if (j == 0)
                                {
                                    if (mokimcuong.kimcuong[j + 2, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i - 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 3, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 3, i].Image = bitmap[mokimcuong.kimcuong[j + 3, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 1)
                                {
                                    if (mokimcuong.kimcuong[j + 2, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i - 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 1, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i - 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 3, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 3, i].Image = bitmap[mokimcuong.kimcuong[j + 3, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j >= 2 && j < 5)
                                {
                                    if (mokimcuong.kimcuong[j + 2, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i - 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 1, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i - 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 2, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 2, i].Image = bitmap[mokimcuong.kimcuong[j - 2, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j + 3, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 3, i].Image = bitmap[mokimcuong.kimcuong[j + 3, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 5)
                                {
                                    if (mokimcuong.kimcuong[j + 2, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j + 2, i - 1].Image = bitmap[mokimcuong.kimcuong[j + 2, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 1, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i - 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 2, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 2, i].Image = bitmap[mokimcuong.kimcuong[j - 2, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                                if (j == 6)
                                {
                                    if (mokimcuong.kimcuong[j - 1, i - 1].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 1, i - 1].Image = bitmap[mokimcuong.kimcuong[j - 1, i - 1].SoMau - 1, 0];
                                        return 1;
                                    }
                                    if (mokimcuong.kimcuong[j - 2, i].SoMau == mokimcuong.kimcuong[j + 1, i].SoMau)
                                    {
                                        bitmap = clip(hinh_hint, 7, 1);
                                        mokimcuong.kimcuong[j - 2, i].Image = bitmap[mokimcuong.kimcuong[j - 2, i].SoMau - 1, 0];
                                        return 1;
                                    }
                                }
                            }
                        }
                        #endregion
                   
                }
            }
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 6; j++)
                {
                    #region tim theo hang ngang
                    if (mokimcuong.kimcuong[i, j].SoMau == mokimcuong.kimcuong[i, j + 2].SoMau)
                    {
                        if (i == 0)
                        {
                            if (mokimcuong.kimcuong[i + 1, j + 1].SoMau == mokimcuong.kimcuong[i, j].SoMau)
                            {
                                bitmap = clip(hinh_hint, 7, 1);
                                mokimcuong.kimcuong[i + 1, j + 1].Image = bitmap[mokimcuong.kimcuong[i + 1, j + 1].SoMau - 1, 0];
                                return 1;
                            }
                        }
                        if (i > 0 && i < 7)
                        {
                            if (mokimcuong.kimcuong[i + 1, j + 1].SoMau == mokimcuong.kimcuong[i, j].SoMau)
                            {
                                bitmap = clip(hinh_hint, 7, 1);
                                mokimcuong.kimcuong[i + 1, j + 1].Image = bitmap[mokimcuong.kimcuong[i + 1, j + 1].SoMau - 1, 0];
                                return 1;
                            }
                            if (mokimcuong.kimcuong[i - 1, j + 1].SoMau == mokimcuong.kimcuong[i, j].SoMau)
                            {
                                bitmap = clip(hinh_hint, 7, 1);
                                mokimcuong.kimcuong[i - 1, j + 1].Image = bitmap[mokimcuong.kimcuong[i - 1, j + 1].SoMau - 1, 0];
                                return 1;
                            }
                        }
                        if (i == 7)
                        {
                            if (mokimcuong.kimcuong[i - 1, j + 1].SoMau == mokimcuong.kimcuong[i, j].SoMau)
                            {
                                bitmap = clip(hinh_hint, 7, 1);
                                mokimcuong.kimcuong[i - 1, j + 1].Image = bitmap[mokimcuong.kimcuong[i - 1, j + 1].SoMau - 1, 0];
                                return 1;
                            }
                        }
                    }
                    #endregion
                    #region tim theo cot doc
                    if (mokimcuong.kimcuong[j, i].SoMau == mokimcuong.kimcuong[j + 2, i].SoMau)
                    {
                        if (i == 0)
                        {
                            if (mokimcuong.kimcuong[j + 1, i + 1].SoMau == mokimcuong.kimcuong[j, i].SoMau)
                            {
                                bitmap = clip(hinh_hint, 7, 1);
                                mokimcuong.kimcuong[j + 1, i + 1].Image = bitmap[mokimcuong.kimcuong[j + 1, i + 1].SoMau - 1, 0];
                                return 1;
                            }
                        }
                        if (i > 0 && i < 7)
                        {
                            if (mokimcuong.kimcuong[j + 1, i + 1].SoMau == mokimcuong.kimcuong[j, i].SoMau)
                            {
                                bitmap = clip(hinh_hint, 7, 1);
                                mokimcuong.kimcuong[j + 1, i + 1].Image = bitmap[mokimcuong.kimcuong[j + 1, i + 1].SoMau - 1, 0];
                                return 1;
                            }
                            if (mokimcuong.kimcuong[j + 1, i - 1].SoMau == mokimcuong.kimcuong[j, i].SoMau)
                            {
                                bitmap = clip(hinh_hint, 7, 1);
                                mokimcuong.kimcuong[j + 1, i - 1].Image = bitmap[mokimcuong.kimcuong[j + 1, i - 1].SoMau - 1, 0];
                                return 1;
                            }
                        }
                        if (i == 7)
                        {
                            if (mokimcuong.kimcuong[j + 1, i - 1].SoMau == mokimcuong.kimcuong[j, i].SoMau)
                            {
                                bitmap = clip(hinh_hint, 7, 1);
                                mokimcuong.kimcuong[j + 1, i - 1].Image = bitmap[mokimcuong.kimcuong[j + 1, i - 1].SoMau - 1, 0];
                                return 1;
                            }
                        }
                    }
                    #endregion
                }
            }
            return 0;
        }
    }
}
