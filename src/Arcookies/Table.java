package Arcookies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Table {

	private String tableName;
	private ArrayList<String> pages;
	private Page latestPage;
	private int pageCount;
	private ArrayList<String> columns;

	

	
	public Table (String strTableName) throws IOException {
		 
		 ArrayList<String> pages = new ArrayList<String>();
		 
		 pageCount = 0;
		 this.tableName = strTableName;
	
		
	 }
	
	public void createNew(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName) throws IOException{
		
		 Set nameType = htblColNameType.entrySet();
		    Iterator it1 = nameType.iterator();
		 
		 while(it1.hasNext()){
			 
			 boolean flag = false;
			 boolean key = false;
			 Map.Entry entry = (Map.Entry) it1.next();
			 columns.add((String)entry.getKey());
			 
			 
			 
			 if (((String)entry.getKey())== strKeyColName){
				 key = true;
			 }
			 
			 Set nameRefs = htblColNameRefs.entrySet();
			    Iterator it2 = nameRefs.iterator();
			    
			 while(it2.hasNext()){
				 Map.Entry entry2 = (Map.Entry) it2.next();
				 
				 if((String)entry.getKey() == (String) entry2.getKey()){
					 CsvController.writeCsvFile(strTableName,(String)entry.getKey(),key,false,(String)(entry2.getValue()),(String)(entry.getValue()));
				 flag = true;
				 }
			 }
			 if(!flag){
				 CsvController.writeCsvFile(strTableName,(String)entry.getKey(),key,false,null,(String)entry.getValue());
			 }
		 }
	}

	public ArrayList<String> getPages() {
		return pages;
	}

	public void setPages(ArrayList<String> pages) {
		this.pages = pages;
	}

	public void insertIntoPage(Hashtable<String, String> htblColNameValue)
			throws IOException, ClassNotFoundException {
		ArrayList<String> tuples = new ArrayList<String>(
				htblColNameValue.values());
		if (latestPage == null) {
			try {
				latestPage = Page.loadFromDisk(tableName + "_" + pageCount);
			} catch (IOException e) {
				latestPage = createNewPage();
			}
		}
		if (latestPage.row_count >= 200) {
			latestPage = createNewPage();
			latestPage.insertTuple((Comparable[]) tuples.toArray());
		} else {
			// momken yekoon fih case msh handled
			latestPage.insertTuple((Comparable[]) tuples.toArray());
		}
		latestPage.saveToDisk();
	}

	public Page createNewPage() {
		Page page = new Page(tableName + "_" + pageCount);
		pageCount++;
		return page;
	}
	
	

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Page getLatestPage() {
		return latestPage;
	}

	public void setLatestPage(Page latestPage) {
		this.latestPage = latestPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public ArrayList<String> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<String> columns) {
		this.columns = columns;
	}

	public static void main (String [] args) {
		
	}

}
