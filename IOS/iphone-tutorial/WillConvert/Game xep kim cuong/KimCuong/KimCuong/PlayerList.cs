using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KimCuong
{
    [Serializable()]
    class PlayerList
    {
        public Player[] list;
        //tao danh sach cac Player
        public PlayerList(Player[] list)
        {
            this.list = list;
        }
    }
}
