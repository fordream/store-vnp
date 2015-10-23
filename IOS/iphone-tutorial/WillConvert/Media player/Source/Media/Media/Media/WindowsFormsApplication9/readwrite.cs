using System;
using System.Collections.Generic;
//using System.Linq;
using System.Text;
using System.IO;
using System.Data;
using System.Drawing;
using System.Collections;
using System.Windows.Forms;
using Tags.ID3;
using System.Collections;
using Tags.ID3.ID3v2Frames.BinaryFrames;

namespace WindowsFormsApplication9
{
    class readwrite
    {
        public static bool write(string fileName)
        {
            try
            {
                FileStream file = File.Open("Data.txt", FileMode.Append);
                StreamWriter sw = new StreamWriter(file);
                sw.WriteLine(fileName);
                sw.Close();
                file.Close();
                return true;
            }
            catch (Exception ex)
            {
                return false;
            }
        }
        public static bool writeinfo(string fileName)
        {
            try
            {
                FileStream file = File.Open("Search.txt", FileMode.Append);
                StreamWriter sw = new StreamWriter(file);
                sw.WriteLine(fileName);
                sw.Close();
                file.Close();
                return true;
            }
            catch (Exception ex)
            {
                return false;
            }
        }
        public static bool SearchSubString(string Parent, string sub)
        {
            try
            {
                char c = sub.ToString()[0];
                int n = Parent.Length;
                int m = sub.Length;
                int i;
                for (i = 0; i < n; i++)
                {
                    if (Parent.ToString()[i] == c)
                    {
                        int k = 0;
                        int j = i;
                        while (j <= i + m - 1)
                        {
                            if (Parent.ToString()[j] == sub.ToString()[k])
                            {
                                k++;
                                j++;
                            }
                            else
                                break;
                        }
                        if (k == m)
                            return true;
                    }
                }
            }
            catch (Exception ex)
            { }
            return false;
        }
        public static string[] StringGroup(int count)
        {
            string[] arrString = readwrite.read("Search.txt");
            ArrayList arrList = new ArrayList();
            string temp = "";
            int i;
            for (i = count; i < arrString.Length; i=i+5)
            {
                if (i == count)
                    arrList.Add(arrString[count]);
                if(!stringResult(arrList,arrString[i]))
                    arrList.Add(arrString[i]);
            }
            arrList.Add(temp);
            string[] kq = new string[arrList.Count];
            for (i = 0; i < arrList.Count; i++)
            {
                kq[i] = arrList[i].ToString();
            }
            return kq; 
        }
        public static bool stringResult(ArrayList arrList,string file)
        {
            string[] kq = new string[arrList.Count];
            string result="";
            int i;
            for (i = 0; i < arrList.Count; i++)
            {
                kq[i] = arrList[i].ToString();
            }
            for (i = 0; i < kq.Length; i++)
            {
                result += kq[i];
            }
            int n = result.IndexOf(file);
            if (n == -1)
                return false;
            else
                return true;
        }
        public static string[] read(string fileOpen)
        {
            string kq = "";
            ArrayList ArrList = new ArrayList();
            try
            {
                FileStream file = File.Open(fileOpen, FileMode.Open);
                StreamReader sr = new StreamReader(file);
                while (!sr.EndOfStream)
                {
                    kq=sr.ReadLine();
                    ArrList.Add(kq);
                }
                sr.Close();
                file.Close();
                string[] ArrString = new string[ArrList.Count];
                for (int i = 0; i < ArrList.Count; i++)
                {
                    ArrString[i] = ArrList[i].ToString();
                }
                return ArrString;
                
            }
            catch (Exception ex)
            {
                return null;  
            }
        }
        public static string LongString()
        {
            string kq = "";
            try
            {
                FileStream file = File.Open("Data.txt", FileMode.Open);
                StreamReader sr = new StreamReader(file);
                kq = sr.ReadToEnd();
                sr.Close();
                file.Close();
                return kq;

            }
            catch (Exception ex)
            {
                return null;
            }
        }
        public static bool TestTring(string fileName)
        {
            string file=LongString();
            int n = file.IndexOf(fileName);
            if (n == -1)
                return false;
            else
                return true;
        }
    }
}
