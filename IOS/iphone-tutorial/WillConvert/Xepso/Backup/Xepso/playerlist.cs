using System;
using System.Collections.Generic;
using System.Text;
using System.Collections;

namespace Xepso
{
    [Serializable()]
    class playerlist
    {
        public player[] list;
        public playerlist(player[] list1)
        {
            this.list = list1;
        }
    }
}
