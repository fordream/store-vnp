using System;
using System.Collections.Generic;
using System.Text;
using System.Runtime.Serialization.Formatters.Binary;
using System.IO;
namespace Xepso
{
    [Serializable()]
    class saveandload
    {
        public static void save(gameshape gs, string file)
        {
            Stream f = File.Open(file,FileMode.Create);
            BinaryFormatter b = new BinaryFormatter();
            b.Serialize(f, gs);
            f.Close();

        }
        public static gameshape load(string file)
        {
            Stream f = File.Open(file, FileMode.Open);
            BinaryFormatter b = new BinaryFormatter();
            gameshape g = (gameshape)b.Deserialize(f);
            f.Close();
            return g;

        }
    }
}
