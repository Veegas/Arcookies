package Arcookies;

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
	int MaximumRowsCountinPage;
	int KDTreeN;

	public static void main(String[] args) {
		DBApp app = new DBApp();
		app.init();
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
				htblColNameRefs, strKeyColName);
		tables.add(newTable);

	}

	@Override
	public void createIndex(String strTableName, String strColName)
			throws DBAppException {
		// TODO Auto-generated method stub

	}

	@Override
	public void createMultiDimIndex(String strTableName,
			Hashtable<String, String> htblColNames) throws DBAppException {

	}

	@Override
	public void insertIntoTable(String strTableName,
			Hashtable<String, String> htblColNameValue) throws DBAppException {
		// TODO Auto-generated method stub

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
			ArrayList<String> pages = table.getPages();
			for (String page : pages) {

			}
		}
	}
}
