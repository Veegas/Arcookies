package Arcookies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Table {

	private String tableName;
	private ArrayList<String> pages;
	private Page latestPage;
	private int pageCount; 
	
	
	
 public Table (String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName) throws IOException {
	 
	 ArrayList<String> pages = new ArrayList<String>();
	 pageCount = 0;
	 this.tableName = strTableName;
	 
	 Set nameType = htblColNameType.entrySet();
	    Iterator it1 = nameType.iterator();
	 
	 while(it1.hasNext()){
		 
		 boolean flag = false;
		 boolean key = false;
		 Map.Entry entry = (Map.Entry) it1.next();
		 
		 if (entry.getKey()== strKeyColName){
			 key = true;
		 }
		 
		 Set nameRefs = htblColNameRefs.entrySet();
		    Iterator it2 = nameRefs.iterator();
		    
		 while(it2.hasNext()){
			 Map.Entry entry2 = (Map.Entry) it2.next();
			 if(entry.getKey() == entry2.getKey()){
				 CsvController.writeCsvFile(strTableName,(String)entry.getKey(),key,false,(String)(entry2.getValue()),(String)(entry.getValue()));
			 flag = true;
			 }
		 }
		 if(!flag){
			 CsvController.writeCsvFile(strTableName,(String)entry.getKey(),key,false,null,(String)entry.getValue());
		 }
	 }
	 
 }
	
	
}
