package Arcookies;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.javaml.core.kdtree.KDTree;

public class Table {

	private String tableName;
	private ArrayList<String> pages;
	private ArrayList<Page> usedPages;
	private int pageCount;
	private int maxRowsPerPage;
	private ArrayList<String> columns;
	private String strKeyColName;
	private LinearHashTable LHT;
	private ArrayList <String> multiIndex;
	private String singleIndexedCol;
	private KDTree KDT;

	
	public Table (String strTableName, int maxRowsPerPage) throws IOException {
		 
		 pageCount = 0;
		 this.tableName = strTableName;
		 this.maxRowsPerPage = maxRowsPerPage;
		 this.pages = new ArrayList<String>();
		 usedPages = new ArrayList<Page>();
		 columns = new ArrayList<String>();
		 LHT = new LinearHashTable((float) 0.75, 200);
	
	 }
	
	public void createNew(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName) throws IOException{
		
		this.strKeyColName = strKeyColName;
		
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

	public String getName() {
		return this.tableName;
	}

	public ArrayList<Page> getUsedPages() {
		return usedPages;
	}

	public void setUsedPages(ArrayList<Page> usedPages) {
		this.usedPages = usedPages;
	}

	public String getStrKeyColName() {
		return strKeyColName;
	}

	public void setStrKeyColName(String strKeyColName) {
		this.strKeyColName = strKeyColName;
	}

	public Page insertIntoPage(Hashtable<String, String> htblColNameValue)
			throws IOException, ClassNotFoundException {
		ArrayList<String> record = new ArrayList<String>();
		for(String columnHead: columns) {
			String value = htblColNameValue.get(columnHead);
			record.add(value);
		}
	
		Page lastPage = Page.loadFromDisk(tableName + "_" + pageCount);
		if(lastPage == null || lastPage.getRow_count() >= maxRowsPerPage) {
			lastPage = createNewPage();
		}
		lastPage.insertTuple(record);
		if(!usedPages.contains(lastPage)) {
			usedPages.add(lastPage);
		}
		return lastPage;
	}
	
	public String getValueFromPage(Page page, String colName, int index) {
		int colIndex = columns.indexOf(colName);
		return page.getRecord(index).get(colIndex);
	}
	
	public ArrayList<String> getRecordFromPage(Page page, String primaryValue) {
		int colIndex = columns.indexOf(strKeyColName);
		ArrayList<ArrayList<String>> pageTuples = page.getTuples();
		for(ArrayList<String> oneTuple: pageTuples) {
			if (oneTuple.get(colIndex).equals(primaryValue)){
				return oneTuple;
			}
		}
		return null;
	}

	public Page createNewPage() {
		pageCount++;

		Page page = new Page(tableName + "_" + pageCount);
		System.out.println("ABC");
		usedPages.add(page);
		pages.add(tableName +"_"+pageCount);
		return page;
	}
	
	

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	public LinearHashTable getLHT() {
		return LHT;
	}

	public void setLHT(LinearHashTable lHT) {
		LHT = lHT;
	}
	
	public void saveIndexToDisk() throws IOException {
		String filename = tableName + "_index";
		FileOutputStream fileOut =
		         new FileOutputStream(filename);
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(this);
		         out.close();
		         fileOut.close();
		         System.out.println(filename);
	}

	public static void main(String[] args) {
		Hashtable x = new Hashtable<String, String>();
		x.put("name", "String");
		x.put("id", "String");
	
		Hashtable y = new Hashtable<String, String>();
		y.put("name", "");
		y.put("id", "");
	
	}

	public ArrayList<String> getMultiIndex() {
		return multiIndex;
	}

	public void setMultiIndex(ArrayList<String> multiIndex) {
		this.multiIndex = multiIndex;
	}

	public String getSingleIndexedCol() {
		return singleIndexedCol;
	}

	public void setSingleIndexedCol(String singleIndexedCol) {
		this.singleIndexedCol = singleIndexedCol;
	}

	public KDTree getKDT() {
		return KDT;
	}

	public void setKDT(int dim) {
		KDT = new KDTree(dim);
	}
	
	public void addPages(String s){
		pages.add(s);
	}

}
