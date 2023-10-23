import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class fileDocGhi1 {
	
	public static void writeTofile(ThuVien thuvien,String file)
	throws Exception{
		ObjectOutputStream out = null;
		
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(thuvien);
			out.close();
	}
	public Object readFromFile(String file) throws Exception  {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		Object list = ois.readObject();
		ois.close();
		return list;
	}
}
