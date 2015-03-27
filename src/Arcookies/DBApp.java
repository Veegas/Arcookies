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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import exceptions.DBAppException;
import exceptions.DBEngineException;

public class DBApp implements DBAppInterface {

	ArrayList<Table> tables = new ArrayList<Table>();
	int KDTreeN;
	int MaximumRowsCountinPage;

	public static void main(String[] args) {

		try {
			Hashtable<String, String> namesTypes = new Hashtable<String, String>();

			Hashtable<String, String> namesRefs = new Hashtable<String, String>();

			Hashtable<String, String> namesValues = new Hashtable<String, String>();
			namesTypes.put("name", "String");
			namesTypes.put("tutorial", "String");
			namesTypes.put("id", "String");
			namesTypes.put("lol", "String");

			namesRefs.put("tutorial", "class");
			namesRefs.put("name", "student");

			namesValues.put("name", "test");
			namesValues.put("tutorial", "test");
			namesValues.put("id", "test");
			namesValues.put("lol", "test");

			DBApp app = new DBApp();
			app.init();

			app.createTable("TrialTable", namesTypes, namesRefs, "id");
			System.out.print("done");
			app.insertIntoTable("TrialTable", namesValues);
			System.out.print("inserted");

		} catch (DBAppException | IOException e) {
			System.out.println("error hena");
			e.printStackTrace();
		}
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
						for (int i = 0; i > 0; i++) {
							String record = table.getValueFromPage(
									Page.loadFromDisk(p), strColName, i);
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
				if (table.getSingleIndexedCol().equals(null)) {

					Enumeration<String> colNames = htblColNames.keys();
					String col0 = colNames.nextElement();
					String col1 = htblColNames.get(col0);
					for (String p : table.getPages()) {
						try {
							for (int i = 0; i > 0; i++) {
								String recordCol0 = table.getValueFromPage(
										Page.loadFromDisk(p), col0, i);
								String recordCol1 = table.getValueFromPage(
										Page.loadFromDisk(p), col1, i);
								if (!(recordCol0 == null || recordCol1 == null)) {

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
	}

	@Override
	public void insertIntoTable(String strTableName,
			Hashtable<String, String> htblColNameValue) throws DBAppException {
		// TODO Auto-generated method stub
		for (Table table : tables) {
			if (table.getName().equalsIgnoreCase(strTableName)) {
				try {
					Page tempPage = table.insertIntoPage(htblColNameValue);
					table.getLHT().put(table.getSingleIndexedCol(),
							tempPage.getPage_id());

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
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator selectFromTable(String strTable,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException, ClassNotFoundException {
	
		
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
		
		if(multiKdPage.equals(linearHashPage)){
			Page page = Page.loadFromDisk(multiKdPage);
			
		}
	
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
}
