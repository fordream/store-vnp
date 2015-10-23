using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace dean
{
    public class GeneralValidation
    {
        //public static bool IsEmail(string Email)
        //{
        //    string strRegex = @"^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}" +
        //      @"\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\" +
        //      @".)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$";
        //    Regex re = new Regex(strRegex);
        //    if (re.IsMatch(Email))
        //        return (true);
        //    else
        //        return (false);
        //}

        public static bool IsUrl(string Url)
        {
            string strRegex = "^(https?://)"
            + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //user@
            + @"(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP- 199.194.52.184
            + "|" // allows either IP or domain
            + @"([0-9a-z_!~*'()-]+\.)*" // tertiary domain(s)- www.
            + @"([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // second level domain
            + "[a-z]{2,6})" // first level domain- .com or .museum
            + "(:[0-9]{1,4})?" // port number- :80
            + "((/?)|" // a slash isn't required if there is no file name
            + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
            Regex re = new Regex(strRegex);

            if (re.IsMatch(Url))
                return (true);
            else
                return (false);
        }
    }
}

