using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace KimCuong
{
    public partial class FormMain : Form
    {
        public static Bitmap[,] clip(Bitmap bigImage, int cols, int rows)
        {
            Bitmap[,] images = new Bitmap[cols, rows];

            int pieceWidth = bigImage.Width / cols;
            int pieceHeight = bigImage.Height / rows;

            for (int i = 0; i < cols; i++)
                for (int j = 0; j < rows; j++)
                {
                    images[i, j] = bigImage.Clone(
                        new Rectangle(i * pieceWidth, j * pieceHeight, pieceWidth, pieceHeight),
                        System.Drawing.Imaging.PixelFormat.DontCare);
                }
            return images;
        }
    }
}
