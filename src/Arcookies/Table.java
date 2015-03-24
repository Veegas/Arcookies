package Arcookies;

import java.util.ArrayList;

public class Table {

	private ArrayList<Column> fields;
	private String tableName;

	public Table() {
		fields = new ArrayList<Column>();
		tableName = "";
	}

	public Table(ArrayList<Column> fields) {
		this.fields = fields;
		tableName = "";
	}

	public ArrayList<Column> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Column> array) {
		fields = array;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String name) {
		tableName = name;
	}

}
