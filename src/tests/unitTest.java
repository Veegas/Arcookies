package tests;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.junit.Test;
import org.junit.Assert;

import exceptions.DBAppException;
import Arcookies.DBApp;
import Arcookies.Page;
import Arcookies.Table;

public class unitTest {

	@Test(timeout = 1000)
	public void CreateTable() throws DBAppException, IOException {
		Hashtable<String, String> namesTypes = new Hashtable<String, String>();

		Hashtable<String, String> namesRefs = new Hashtable<String, String>();


		namesTypes.put("name", "String");
		namesTypes.put("tutorial", "String");
		namesTypes.put("id", "String");
		namesTypes.put("lol", "String");

		namesRefs.put("tutorial", "class");
		namesRefs.put("name", "student");

		DBApp app = new DBApp();
		app.init();
		app.createTable("TrialTable", namesTypes, namesRefs, "id");
		
		Assert.assertEquals(1, app.getTables().size());
	}
	
	@Test(timeout = 1000)
	public void testReadTableFromCSV() throws DBAppException, IOException {

		DBApp app = new DBApp();
		app.init();
		
		Assert.assertEquals(1, app.getTables().size());
		
/*
 * 		System.out.print("done");
		app.insertIntoTable("TrialTable", namesValues);
		System.out.print("inserted");
*/
	}
	
	@Test(timeout = 1000)
	public void testInsertData() throws DBAppException, ClassNotFoundException, IOException {
		DBApp app = new DBApp();
		app.init();
		
		Hashtable<String, String> namesValues = new Hashtable<String, String>();
		namesValues.put("name", "testname");
		namesValues.put("tutorial", "testtutorial");
		namesValues.put("id", "testid");
		namesValues.put("lol", "testlol");
		
		ArrayList<String> array = new ArrayList<String>(namesValues.values());
		
		Page page = app.getTables().get(0).insertIntoPage(namesValues);
				
		System.out.println("PAGE ROW COUNT" + page.getRecord(0));
		System.out.println(app.getTables().get(0).getColumns());
		Assert.assertEquals(4, app.getTables().get(0).getColumns().size());
		Assert.assertEquals(array, page.getRecord(0));
		
		
	}
	@Test(timeout = 1000)
	public void testIndexData() throws DBAppException {
		
		DBApp app = new DBApp();
		app.init();
		
		Hashtable<String, String> namesValues = new Hashtable<String, String>();
		namesValues.put("name", "testname");
		namesValues.put("tutorial", "testtutorial");
		namesValues.put("id", "testid");
		namesValues.put("lol", "testlol");
		
		ArrayList<String> array = new ArrayList<String>(namesValues.values());
		app.insertIntoTable("TrialTable", namesValues);
		
		app.createIndex("TrialTable", "id");
		
//		System.out.println(app.getTables().get(0).getLHT().size());
		Assert.assertEquals(1, app.getTables().get(0).getLHT().size());
		
	}
}
