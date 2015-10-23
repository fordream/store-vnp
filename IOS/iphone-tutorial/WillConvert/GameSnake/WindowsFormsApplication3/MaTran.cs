using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;

namespace GamesSnake
{
    public class MaTran
    {
        /// <summary>
        /// giá trị 0: không có vật cảng nào
        /// giá trị 1: vật cản động vào thì chết :D
        /// giá trị 2: thức ăn, ăn vào tăng nút lên +1
        /// </summary>
        int[,] mt = new int[50, 50];//độ rộng tối đa 60*60
        public MaTran()
        {
 
        }
        /// <summary>
        /// dua gia tri tu mang vi tri diem vao trong matran
        /// </summary>
        /// <param name="a">mang cac vi tri can gan</param>
        /// <param name="loai">gia tri gan</param>
       public void Change( Point[] a,int loai)
       {
           //cai này chỉ dùng cho con rắn nên bỏ qua đầu rắn a[1]
           for (int i = 1; i < a.Length; i++)
           {
               mt[a[i].X, a[i].Y] = loai;
           }
       }
        /// <summary>
        /// tạo lại khoảng trống cho matran
        /// </summary>
        /// <param name="a"></param>
        public void Change(Point[]a)
        {
            
            for (int i = 0; i < a.Length; i++)
            {
                mt[a[i].X, a[i].Y] = 0;
            }
        }
        /// <summary>
        /// dùng để thay đổi giá trị trong matran
        /// </summary>
        /// <param name="a">tọa độ trong ma trận cần thiết lập vị trí</param>
        /// <param name="loai">loại 0,1 ,2</param>
        public void Change(Point a, int loai)
        {
            mt[a.X, a.Y] = loai;
        }
        public bool avail(Point a)
        {
            return mt[a.X, a.Y] == 0;
        }
        /// <summary>
        /// kiểm tra trạng thái chuyển động hợp lệ của rắn.
        /// p là đầu của con rắn.
        /// return giá trị tại ô đầu đang ở.
        /// với 0:trạng thái hợp lệ.
        /// 1:di chuyển ko họp lệ -> chết.
        /// 2:thức ăn ->tăng nút.
        /// </summary>
        /// <param name="p">đầu con rắn đang ở vị trí p này :D</param>
        public int Check(Point p)
        {
            try
            {
                return mt[p.X, p.Y];
            }
            catch
            {
                //vược ra ngoài phạm vi
                return 1;
            }           
        }
    }
}
