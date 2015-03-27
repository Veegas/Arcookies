package Arcookies;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import net.sf.javaml.core.kdtree.KDTree;
import exceptions.DBAppException;
import exceptions.DBEngineException;

public class DBApp implements DBAppInterface {

	ArrayList<Table> tables = new ArrayList<Table>();
	int KDTreeN;
	int MaximumRowsCountinPage;

	public static void main(String[] args) {

		byte[] encoded = "hashbarownies".getBytes(StandardCharsets.UTF_8);
		System.out.println(encoded[0]);

		/*
		 * try { Hashtable<String, String> namesTypes = new Hashtable<String,
		 * String>();
		 * 
		 * Hashtable<String, String> namesRefs = new Hashtable<String,
		 * String>();
		 * 
		 * Hashtable<String, String> namesValues = new Hashtable<String,
		 * String>(); namesTypes.put("name", "String");
		 * namesTypes.put("tutorial", "String"); namesTypes.put("id", "String");
		 * namesTypes.put("lol", "String");
		 * 
		 * namesRefs.put("tutorial", "class"); namesRefs.put("name", "student");
		 * 
		 * namesValues.put("name", "testname"); namesValues.put("tutorial",
		 * "testtutorial"); namesValues.put("id", "testid");
		 * namesValues.put("lol", "testlol");
		 * 
		 * DBApp app = new DBApp(); app.init();
		 * 
		 * app.createTable("TrialTable", namesTypes, namesRefs, "id");
		 * System.out.print("done"); app.insertIntoTable("TrialTable",
		 * namesValues); System.out.print("inserted");
		 * 
		 * } catch (DBAppException | IOException e) {
		 * System.out.println("error hena"); e.printStackTrace(); }
		 */
		DBApp app = new DBApp();
		app.init();

		System.out.println(app.tables);

	}

	public ArrayList<Table> getTables() {
		return tables;
	}

	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}

	@Override
	public void init() {

		try {
			Properties prop = new Properties();
			String configFileName = "./config/DBApp.properties";
			InputStream input = new FileInputStream(configFileName);
			prop.load(input);
			KDTreeN = Integer.parseInt(prop.getProperty("KDTreeN"));
			MaximumRowsCountinPage = Integer.parseInt(prop
					.getProperty("MaximumRowsCountinPage"));

			tables = new ArrayList<Table>();
			tables = CsvController.readCsvFile(MaximumRowsCountinPage);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void createTable(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws DBAppException, IOException {

		boolean alreadyExists = false;
		String name = "";
		for (Table table : tables) {
			if (table.getName().equalsIgnoreCase(strTableName)) {
				alreadyExists = true;
				name = table.getName();
				break;
			}
		}
		if (!alreadyExists) {
			Table newTable = new Table(strTableName, MaximumRowsCountinPage);
			newTable.createNew(strTableName, htblColNameType, htblColNameRefs,
					strKeyColName);
			tables.add(newTable);
		} else {
			throw new DBAppException("The table " + name + " already exists!");
		}

	}

	public void createIndex(String strTableName, String strColName)
			throws DBAppException {
		// TODO Auto-generated method stub
		for (Table table : tables) {
			if (table.getName().equalsIgnoreCase(strTableName)) {
				table.setSingleIndexedCol(strColName);
				for (String p : table.getPages()) {
					try {
						Page temporaryPage = Page.loadFromDisk(p);
						for (int i = 0; i <= temporaryPage.getRow_count(); i++) {
							String record = table.getValueFromPage(
									temporaryPage, strColName, i);
							if (!(record == null)) {
								table.getLHT().put(record, p);
							} else {
								break;
							}

						}
					} catch (ClassNotFoundException e) {

						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void createMultiDimIndex(String strTableName,
			Hashtable<String, String> htblColNames) throws DBAppException {
		// TODO Auto-generated method stub
		for (Table table : tables) {
			if (table.getName().equals(strTableName)) {
					table.setKDT(htblColNames.size());// 2 hardcoded as hashtable has 2
									// strings(columns) only
					KDTree tree = table.getKDT();
					double [] keys = new double[htblColNames.size()];
					for (String p : table.getPages()) {
						Page current;
						try {

							current = Page.loadFromDisk(p);
							for (int i = 0; i < current.getRow_count(); i++) {
							Enumeration<String> colNames = htblColNames.keys();
							String currentColName = colNames.nextElement();
							for (int k = 0; k < htblColNames.size(); k++) {
								String record = table.getValueFromPage(current
										,colNames.nextElement(), i);
								keys[k] = Double.parseDouble(record);
								tree.insert(keys, p);	
						}
					}
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
				}
			}

			
		}
	}

	@Override
	public void insertIntoTable(String strTableName,
			Hashtable<String, String> htblColNameValue) throws DBAppException {
		// TODO Auto-generated method stub
		for (Table table : tables) {
			if (table.getName().equalsIgnoreCase(strTableName)) {
				try {
					Page tempPage = table.insertIntoPage(htblColNameValue);
					tempPage.saveToDisk();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void deleteFromTable(String strTableName,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException {
		for (Table table : tables) {
			if (table.getName().equalsIgnoreCase(strTableName)) {
				
			}
		}
	}

	@Override
	public Iterator selectFromTable(String strTable,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException, ClassNotFoundException {
	
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		Iterator result;
		String linearHashPage = null;
		
		Table table=null;
		
		for (Table currenttable: tables){
			if(currenttable.getName().equalsIgnoreCase(strTable)){
				table = currenttable;
				break;
			}	
		}
		
		Set nameValue = htblColNameValue.entrySet();
	    Iterator it1 = nameValue.iterator();
	 
	    while(it1.hasNext()){
	    	Map.Entry entry = (Map.Entry) it1.next();
	    	
	     if (((String)entry.getKey()).equalsIgnoreCase(table.getSingleIndexedCol())){
	    	 linearHashPage = table.getLHT().get((String)entry.getValue());
	    	 break;
	     }
	    }
	    nameValue = htblColNameValue.entrySet();
	    it1 = nameValue.iterator();
	    ArrayList<String> temp = new ArrayList<String>();
	    boolean flag = true;
	    while(it1.hasNext()){
	    	Map.Entry entry = (Map.Entry) it1.next();
	        temp.add((String)entry.getKey());
	    }
	    for (String multKey : table.getMultiIndex()){
	    	if(!temp.contains(multKey)){
	    		flag = false;
	    	}
	    }
	    
	    ArrayList<Double> indexedColVals = new ArrayList<Double>();
	    String multiKdPage = null;
		if(flag){
			for(String ind: table.getMultiIndex()){
				indexedColVals.add(Double.parseDouble(htblColNameValue.get(ind)));
				
			}
			double [] doublecol = new double[indexedColVals.size()];
			for (int i = 0; i <indexedColVals.size(); i++) {
				 doublecol[i]=indexedColVals.get(i);
				
			}
			multiKdPage = (String) table.getKDT().search(doublecol);
		}
		
		
		
		if(multiKdPage.equals(linearHashPage) && strOperator.equalsIgnoreCase("AND")){
			Page page = Page.loadFromDisk(multiKdPage);
			for(int i = 0 ; i< page.getRow_count(); i++)
			{
			ArrayList<String> temp1 = page.getRecord(i);
			if(temp1.contains(table.getSingleIndexedCol())){
			res.add(temp1);	
			}
			}
		}
		
		else if(!multiKdPage.equals(linearHashPage) && strOperator.equalsIgnoreCase("OR")){
			Page page = Page.loadFromDisk(multiKdPage);
			Page page1 = Page.loadFromDisk(linearHashPage);
			for(int i = 0 ; i< page.getRow_count(); i++)
			{
			ArrayList<String> temp1 = page.getRecord(i);
			boolean f= true;
			for(String s: table.getMultiIndex()){
			if(!temp1.contains(s)){
			f= false;	
			}
			}
			if(f){
				res.add(temp1);
			}
			}
			for(int i = 0 ; i< page1.getRow_count(); i++)
			{
			ArrayList<String> temp1 = page1.getRecord(i);
			boolean f= true;
			for(String s: table.getMultiIndex()){
			if(!temp1.contains(s)){
			f= false;	
			}
			}
			if(f){
				res.add(temp1);
			}
			}
		}
		else if(multiKdPage.equals(linearHashPage) && strOperator.equalsIgnoreCase("OR")){
			Page page = Page.loadFromDisk(multiKdPage);
			for(int i = 0 ; i< page.getRow_count(); i++)
			{
			ArrayList<String> temp1 = page.getRecord(i);
			boolean f= true;
			for(String s: table.getMultiIndex()){
			if(!temp1.contains(s)){
			f= false;	
			}
			}
			if(f){
				res.add(temp1);
			}
			}
		} 
		return result = res.iterator();
	
	}

	@Override
	public void saveAll() throws DBEngineException {
		// TODO Auto-generated method stub
		for (Table table : tables) {
			for (Page page : table.getUsedPages()) {
				try {
					page.saveToDisk();
					table.saveIndexToDisk();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

/*	public static double AlphabetsToFloat(String s) {
		byte[] encoded = s.getBytes(StandardCharsets.UTF_8);
		for (byte c : encoded) {}
		 * s=s.toLowerCase(); StringBuilder sb = new StringBuilder(); for (char
		 * c : s.toCharArray()) { sb.append((char) (c - 'a' + 1)); } String b =
		 * ""; for (int i = 0; i < sb.length(); i++) { b = b +
		 * (sb.codePointAt(i)); }
		 
	return Float.parseFloat(c);
*/
}
