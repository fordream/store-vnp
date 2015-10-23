using System;
using System.Collections.Generic;
//using System.Linq;
using System.Text;
using TagLib;
using System.Windows.Forms;

namespace WindowsFormsApplication9
{
    class get_details
    {
        TagLib.File file;

        public get_details(string path)
        {
            try
            {
                if (path != "")
                    file = TagLib.File.Create(path);
            }
            catch { }
        }

        public string get_title()
        {
            try
            {
                return file.Tag.Title;
            }
            catch { }

            return string.Empty;
        }
        public string get_name()
        {
            try
            {
                return file.Name;
            }
            catch { }

            return string.Empty;
        }
        public string get_artist()
        {
            try
            {
                return file.Tag.Artists[0];
            }
            catch { }

            return string.Empty;
        }
        public string get_album()
        {
            try
            {
                return file.Tag.Album;
            }
            catch { }
            return string.Empty;
        }
        public string get_year()
        {
            try
            {
                return file.Tag.Year.ToString();
            }
            catch { }
            return string.Empty;
        }
    }
}
