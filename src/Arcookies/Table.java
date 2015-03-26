package Arcookies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Table {

	private String tableName;
	private ArrayList<String> pages;
	private Page latestPage;
	private int pageCount;
	private Hashtable<String, String> htblColNameType;
	private Hashtable<String, String> htblColNameRefs;
	
	public Table(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws IOException {

		ArrayList<String> pages = new ArrayList<String>();
		pageCount = 0;
		this.tableName = strTableName;
		this.htblColNameRefs = htblColNameRefs;
		this.htblColNameType = htblColNameType;
		
		
		ArrayList<String> types = new ArrayList<String>(
				htblColNameType.values());
		Set<String> colNameType = htblColNameType.keySet();

		ArrayList<String> refs = new ArrayList<String>(htblColNameRefs.values());
		Set<String> colNameRefs = htblColNameType.keySet();

		String[] nameRefs = (String[]) colNameRefs.toArray();
		String[] nameTypes = (String[]) colNameType.toArray();
		for (int i = 0; i < nameTypes.length && i < types.size(); i++) {

			for (int j = 0; j < nameRefs.length && j < refs.size(); i++) {
				if (nameTypes[i] == nameRefs[j]) {
					boolean key = false;
					if (nameTypes[i] == strKeyColName) {
						key = true;
					}
					CsvController.writeCsvFile(strTableName, nameTypes[i], key,
							false, refs.get(j), types.get(i));
				}
			}
		}

	}

	public ArrayList<String> getPages() {
		return pages;
	}

	public void setPages(ArrayList<String> pages) {
		this.pages = pages;
	}

}