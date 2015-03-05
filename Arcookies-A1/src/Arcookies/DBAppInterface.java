package Arcookies;

import java.util.Hashtable;
import java.util.Iterator;

public interface DBAppInterface {

	public void init( );
	
	public void createTable(String strTableName,
	 Hashtable<String,String> htblColNameType,
	 Hashtable<String,String>htblColNameRefs,
	 String strKeyColName)
	throws DBAppException;
	
	
	public void createIndex(String strTableName,
	 String strColName)
	throws DBAppException;
	
	
	public void createMultiDimIndex(String strTableName,
	 Hashtable<String,String> htblColNames )
	throws DBAppException ;
	
	
	
	public void insertIntoTable(String strTableName,
			 Hashtable<String,String> htblColNameValue)
			throws DBAppException;
	
	
			public void deleteFromTable(String strTableName,
			Hashtable<String,String> htblColNameValue,
			 String strOperator)
			throws DBEngineException;
			
			

			public Iterator selectFromTable(String strTable,
			Hashtable<String,String> htblColNameValue,
			String strOperator)
			throws DBEngineException;
			
			
			public void saveAll( ) throws DBEngineException ;
			
			// General Notes:
			// For the parameters, the name documents what is
			// being passed – for example htblColNameType
			// is a hashtable with key as ColName and value is
			// the Type.
			//
			// strOperator can either be OR or AND (just two)
			//
			// DBEngineException is a generic exception to avoid
			// breaking the test cases when they run. You can
			// customize the Exception by passing a different message
			// upon creation.
			//
			// Iterator is java.util.Iterator It is an interface that
			// enables client code to iterate over the results row
			// by row. Do not implement the remove method.
			// // The method saveAll saves all data pages and indices to
			// disk and can be called at any time (not just before
			// the program terminates) 
}
