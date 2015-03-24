package Arcookies;

import java.util.ArrayList;

public class Table {

	private String tableName;

	public Table() {
			tableName = "";
	}

	public Table(ArrayList<Column> fields) {		
		tableName = "";
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String name) {
		tableName = name;
	}

}
