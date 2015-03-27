package Arcookies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import net.sf.javaml.core.kdtree.*;
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

		Table newTable = new Table(strTableName, htblColNameType,
				htblColNameRefs, strKeyColName, MaximumRowsCountinPage);

		if (!tables.contains(newTable)) {
			tables.add(newTable);
			newTable.writeMetaData(strTableName, htblColNameType,
					htblColNameRefs, strKeyColName);
		}

	}

	@Override
	public void createIndex(String strTableName, String strColName)
			throws DBAppException {
		// TODO Auto-generated method stub
		for (Table table : tables) {
			if (table.getName().equals(strTableName)) {
				table.setSingleIndex(strColName);
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

	}

	@Override
	public void insertIntoTable(String strTableName,
			Hashtable<String, String> htblColNameValue) throws DBAppException {
		// TODO Auto-generated method stub
		for (Table table : tables) {
			if (table.getName() == strTableName) {
				try {
					Page tempPage = table.insertIntoPage(htblColNameValue);
//					table.getLHT().put(table.getSingleIndex(),
//							tempPage.getPage_id());

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
			throws DBEngineException {
		// TODO Auto-generated method stub
		return null;
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
