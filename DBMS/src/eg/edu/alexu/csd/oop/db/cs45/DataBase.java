package eg.edu.alexu.csd.oop.db.cs45;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.db.xml.SaveAsXml;

public class DataBase {
	/**
	 * name Of database
	 */
	private String name;
	/**
	 * just do some operations between table and file XML.
	 */
	private String path;

	public Map<String, Table> tables;

	public DataBase(String dbName) {
		this.name = dbName;
		this.path = DBMS.getPath() + DBMS.getSeparator() + dbName;
		tables = new HashMap<>();
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param tableName
	 */
	public void setelectTable(String tableName) {
	}

	public boolean createTable(String tableName, String[] columns, String[] dataTypes) {
		File fxml = new File(path + DBMS.getSeparator() + tableName + ".xml");
		File fdtd = new File(path + DBMS.getSeparator() + tableName + ".dtd");
		if (fxml.exists() && fdtd.exists()) {
			return false;
		}
		Table table = new Table();
		table.setName(tableName);
		table.insertColumn(columns, dataTypes);

		SaveAsXml save = new SaveAsXml(path + DBMS.getSeparator(), table);
		save.save();
		tables.put(tableName.toLowerCase(), table);
		return true;
	}

	public boolean dropTable(String tableName) {
		File fxml = new File(path + DBMS.getSeparator() + tableName + ".xml");
		File fdtd = new File(path + DBMS.getSeparator() + tableName + ".dtd");
		if (fxml.exists() && fdtd.exists()) {
			fxml.delete();
			fdtd.delete();
			return true;
		}
		return false;
	}

	public int update(String tableName,ArrayList<Object> updatedColumns, ArrayList<Object> updatedValues, String value, String operand,String operator )throws SQLException {
		Table table = tables.get(tableName.toLowerCase());
		if(table == null) {
			throw new SQLException("Table is not exist");
		}
		int n = table.update(updatedColumns, updatedValues, (Object)value, operand, operator);
		return n;
	}

	public Object[][] selectRow(String tableName, String[] splitColoumns, String operand, String operator, String value)
			throws SQLException {
		Table table = tables.get(tableName.toLowerCase());;
		if (table == null) {
			throw new SQLException("Table is not exist");
		}
		if (splitColoumns[0].equals("*")) {
			splitColoumns = table.getTable().get(0).toArray(new String[table.getTable().get(0).size()]);
		}
		Object[][] selectedRows = table.select(splitColoumns, value, operand, operator);
		return selectedRows;
	}

	public int insert(String tableName, String[] splitColumns, String[] splitValues) throws SQLException {
		Table table = tables.get(tableName.toLowerCase());;
		int before = table.getTable().size();
		if (table == null) {
			throw new SQLException("Table is not exist");
		}

		if (splitColumns == null) {
			table.insert(table.getTable().get(0), getArrayList(splitValues));
		} else {
			table.insert(getArrayList(splitColumns), getArrayList(splitValues));
		}

		int n = table.getTable().size() - before ;
		return n;
	}

	public int deleteRow(String tableName, String operand, String operator, String value) throws SQLException {
		Table table =tables.get(tableName.toLowerCase());;
		int before = table.getTable().size();
		if (table == null) {
			throw new SQLException("Table is not exist");
		}
		table.delete(value, operand, operator);
		int n = before - table.getTable().size();
		return n;
	}

	public boolean tableIsExist(String tableName) {
		File fxml = new File(path+DBMS.getSeparator()+tableName+".xml");
		return fxml.exists();
	}
	
	private ArrayList<Object> getArrayList(String[] a) {
		ArrayList<Object> x = new ArrayList<>();
		for (String s : a) {
			x.add(s);
		}
		return x;
	}

}