package Arcookies;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;



public class Page  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//2D array of comparable
	ArrayList<Comparable [] >tuples;
	/*El page_id hayeb2a set men el table whenever a new page is create 
	it's id will be "[tablename]_[page number], example student_1 page number will be incremented every time "*/	
	String page_id;
	
	public void saveToDisk() throws IOException {
		FileOutputStream fileOut =
		         new FileOutputStream(page_id + ".class");
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(this);
		         out.close();
		         fileOut.close();
	}
	
	public Page loadFromDisk() throws ClassNotFoundException, IOException {
		FileInputStream fileIn = new FileInputStream(page_id + ".class");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Page page = (Page) in.readObject();
        in.close();
        fileIn.close();
        return page;
	}
	
	
	
	
	

}
