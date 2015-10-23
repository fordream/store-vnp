using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;
using System.Timers;

namespace GamesSnake
{
    public class snake:IDisposable
    {
        #region thành phần dữ liệu
        /// <summary>
        /// mảng chứa vị trí các nút(thân) của con rắn.
        /// với phần tử vitri[1] là đầu rắn
        /// phần tử cuối là đuôi
        /// </summary>
        Point[] viTri = new Point[120];
        /// <summary>
        /// do dai cua con ran mat dinh bang 3
        /// </summary>
        int doDai;
        /// <summary>
        /// màu sắc đầu con rắn
        /// </summary>
        Brush dauRan;
        /// <summary>
        /// màu sắc thân con rắn
        /// </summary>
        Brush thanRan;
        /// <summary>
        /// màu sắc đường viền ngoài con rắn
        /// </summary>
        Pen vienNgoai;
        /// <summary>
        /// hướng di chuyển của rắn, trái|phải|trên|dưới
        /// </summary>
        public  DiChuyen diChuyen { get; set; }
        /// <summary>
        /// tốc độ di chuyển
        /// </summary>
        private  Timer tocDo;
        /// <summary>
        /// nơi cần vẽ lên|nơi con rắn ở :D
        /// </summary>
        Graphics g;
        /// <summary>
        /// cái này làm sự kiện thì tốt hơn nhưng chưa tới nên làm ủy quyền=))
        /// mục đích để thêm vào mảng quản lí các con rắn nằm ở lớp ngoài
        /// dùng để ăn mồi, đụng vật cản ...
        /// </summary>
        /// <param name="vitri">dau va duoi hien tai cua con ran</param>
        /// <param name="tailLast">duoi cua con ran can xoa</param>
        public delegate void thaydoi(Point []vitri,Point tailLast);
      //  private thaydoi GoiSuLi;
        public event thaydoi GoiSuLi;
        #endregion
       
        /// <summary>
        /// khởi tạo các giá trị
        /// </summary>
        /// <param name="gr">vùng dùng để vẽ con ran len</param>
        /// <param name="vTri">tọa độ ban đầu cần vẽ(cái đầu của nó :D)</param>
        /// <param name="dr">màu sắc cái đầu rắn</param>
        /// <param name="th">màu sắc thân rắn</param>
        /// <param name="vn">màu sắc đường viền con rắn :D</param>
        public snake(Graphics gr,Point vTri,Brush dr,Brush th,Pen vn)
        {
            g = gr;
            doDai=4;
            dauRan =dr;
            thanRan = th;
            vienNgoai = vn;
            for (int i = doDai; i >0; i--)
                viTri[i] = new Point((vTri.X-i), vTri.Y);
            diChuyen = DiChuyen.phai;
            tocDo = new Timer(200);
            tocDo.Enabled = false;
            tocDo.Elapsed += new ElapsedEventHandler(tocDo_Elapsed);
        }
        #region các phương thức 
        private  void  tocDo_Elapsed(object sender, ElapsedEventArgs e)
        {
            move();
        }
        public void ve()
        {
            for(int i=2;i<=doDai;i++)
                //ve phan than con ran
                render.Draw(g,new Point(viTri[i].X*10,viTri[i].Y*10),vienNgoai,thanRan);
            //ve dau ran
            render.Draw(g, new Point(viTri[1].X * 10, viTri[1].Y*10), vienNgoai, dauRan);
        }
        public void TocDo(int td)
        {
            if (td >= 30 && td <= 300)
                tocDo.Interval = td;
        }
        public void TangTocDo(int td)
        {
            if (td >= 30 && td <= 300)
                tocDo.Interval -= td;
        }
        public void Go(bool b)
        {
            tocDo.Enabled = b;
        }
        private void move()
        {
            Point tailLast = viTri[doDai]; //new Point(viTri[doDai].X * 10, viTri[doDai].Y * 10);
            //xoa duoi
            render.Draw(g, new Point(viTri[doDai].X * 10, viTri[doDai].Y * 10), Pens.White, Brushes.White);
            //ve tu duoi ve toi dau
            for (int i = doDai; i > 1; i--)
            {
                viTri[i] = viTri[i - 1];
                render.Draw(g, new Point(viTri[i].X * 10, viTri[i].Y * 10), vienNgoai, thanRan);
            }
            //ve dau ran
            if (diChuyen == DiChuyen.trai)
                viTri[1].X -= 1;
            else if (diChuyen == DiChuyen.phai)
                viTri[1].X += 1;
            if (diChuyen == DiChuyen.len)
                viTri[1].Y -= 1;
            else if (diChuyen == DiChuyen.xuong)
                viTri[1].Y += 1;
            render.Draw(g, new Point(viTri[1].X * 10, viTri[1].Y * 10), vienNgoai, dauRan);
            //gọi ủy quyền
            if (GoiSuLi != null)
            {
                //vi mang vi tri bat dau tu 1 nen ta chuyen lai bat dau tu ko
                Point[] vtr = new Point[doDai];
                for (int i = 1; i <= doDai; i++)
                    vtr[i - 1] = viTri[i];
                GoiSuLi(vtr,tailLast);
            }
        }
        public void add()
        {
            doDai += 1;
            viTri[doDai] = new Point(viTri[doDai - 1].X, viTri[doDai - 1].Y);
        }
        #endregion

        #region IDisposable Members

        public void Dispose()
        {
            for (int i = 2; i <= doDai; i++)
                render.Draw(g, new Point(viTri[i].X * 10, viTri[i].Y * 10), Pens.White,Brushes.White);
            render.Draw(g, new Point(viTri[1].X * 10, viTri[1].Y * 10), Pens.White, Brushes.White);
            tocDo.Enabled = false;
            tocDo.Dispose();
        }

        #endregion
    }
}
