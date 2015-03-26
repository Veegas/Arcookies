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
	private ArrayList<Page> usedPages;
	private int pageCount;
	private int maxRowsPerPage;
	private ArrayList<String> columns;
	private String strKeyColName;

	public Table(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws IOException {

		ArrayList<String> pages = new ArrayList<String>();

		pageCount = 0;
		this.tableName = strTableName;
		this.strKeyColName = strKeyColName;

		Set nameType = htblColNameType.entrySet();
		Iterator it1 = nameType.iterator();

		while (it1.hasNext()) {

			boolean flag = false;
			boolean key = false;
			Map.Entry entry = (Map.Entry) it1.next();

			if (((String) entry.getKey()) == strKeyColName) {
				key = true;
			}

			Set nameRefs = htblColNameRefs.entrySet();
			Iterator it2 = nameRefs.iterator();

			while (it2.hasNext()) {
				Map.Entry entry2 = (Map.Entry) it2.next();

				if ((String) entry.getKey() == (String) entry2.getKey()) {
					CsvController.writeCsvFile(strTableName,
							(String) entry.getKey(), key, false,
							(String) (entry2.getValue()),
							(String) (entry.getValue()));
					flag = true;
				}
			}
			if (!flag) {
				CsvController.writeCsvFile(strTableName,
						(String) entry.getKey(), key, false, null,
						(String) entry.getValue());
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

	public void insertIntoPage(Hashtable<String, String> htblColNameValue)
			throws IOException, ClassNotFoundException {
		ArrayList<String> record = new ArrayList<String>();
		for(String columnHead: columns) {
			String value = htblColNameValue.get(columnHead);
			record.add(value);
		}
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
		Page page = new Page(tableName + "_" + pageCount);
		pageCount++;
		usedPages.add(page);
		return page;
	}

	public static void main(String[] args) {
		Hashtable x = new Hashtable<String, String>();
		x.put("name", "String");
		x.put("id", "String");

		Hashtable y = new Hashtable<String, String>();
		y.put("name", "");
		y.put("id", "");

		try {
			Table table = new Table("ExampleTable", x, y, "id");
			System.out.println(table);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
