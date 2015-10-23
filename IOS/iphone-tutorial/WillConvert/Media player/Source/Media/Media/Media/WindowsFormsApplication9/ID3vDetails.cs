using System;
using System.Collections.Generic;
//using System.Linq;
using System.Text;
using System.IO;
using System.Data;
using System.Drawing;
using Tags.ID3;
using System.Collections;
using Tags.ID3.ID3v2Frames.BinaryFrames;

namespace WindowsFormsApplication9
{
    class ID3vDetails
    {
        ID3v1 MP3;
        ID3Info ImageMP3;
        string namemp3;
        string title;
        string artist;
        string album;
        string year;
        //Image attach = new Bitmap(64, 64);
        Image attach = null;
        public ID3vDetails(string filename)
        {
            ImageMP3 = new ID3Info(filename, true);
            foreach (AttachedPictureFrame frame in ImageMP3.ID3v2Info.AttachedPictureFrames)
            {
                Image temp = frame.Picture;
                attach = temp;
            }
            MP3 = new ID3v1(filename, true);
            namemp3 = MP3.FileName;
            title = MP3.Title;
            artist = MP3.Artist;
            album = MP3.Album;
            year = MP3.Year;
        }
        public string Namemp3
        {
            get
            {
                return namemp3;
            }
        }
        public string Title
        {
            get
            {
                return title;
            }
        }
        public string Artist
        {
            get
            {
                return artist;
            }
        }
        public string Album
        {
            get
            {
                return album;
            }
        }
        public string Year
        {
            get
            {
                return year;
            }
        }
        public Image Attach
        {
            get
            {
                return attach;
            }
        }
    }
}
