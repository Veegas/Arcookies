package Arcookies;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;



public class Page  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//2D array of comparable
	ArrayList<ArrayList<String>> tuples;
	/*El page_id hayeb2a set men el table whenever a new page is create 
	it's id will be "[tablename]_[page number], example student_1 page number will be incremented every time "*/	
	String page_id;
	int row_count;
	
	public Page(String page_id) {
		super();
		this.page_id = page_id;
		row_count = 0;
		tuples = new ArrayList(new ArrayList<String>()); 
	}

	public void saveToDisk() throws IOException {
		String filename = page_id + ".class";
		FileOutputStream fileOut =
		         new FileOutputStream(filename);
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(this);
		         out.close();
		         fileOut.close();
		         System.out.println(filename);
	}
	
	public static Page loadFromDisk(String page_id) throws ClassNotFoundException{
		
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream(page_id + ".class");
			ObjectInputStream in = new ObjectInputStream(fileIn);
	        Page page = (Page) in.readObject();
	        in.close();
	        fileIn.close();
	        return page;
		} catch (IOException e) {
			return null;
		}
        
	}
	
	public void insertTuple(ArrayList<String> tuple) {
		System.out.println("THIS IS THE TUPLE" + tuple);
		tuples.add(tuple);
	}
	
	public ArrayList<ArrayList<String>> getTuples() {
		return tuples;
	}

	public void setTuples(ArrayList<ArrayList<String>> tuples) {
		this.tuples = tuples;
	}

	public String getPage_id() {
		return page_id;
	}

	public void setPage_id(String page_id) {
		this.page_id = page_id;
	}

	public int getRow_count() {
		return row_count;
	}

	public void setRow_count(int row_count) {
		this.row_count = row_count;
	}
	
	public ArrayList<String> getRecord(int index) {
		return tuples.get(index);
	}
	
	public static void main (String [] args) throws IOException, ClassNotFoundException {
			Page page = loadFromDisk("page_1");
			System.out.println(page.tuples);
			
			System.out.println("This is page id loaded " + page.page_id);
		
	}
	
}
