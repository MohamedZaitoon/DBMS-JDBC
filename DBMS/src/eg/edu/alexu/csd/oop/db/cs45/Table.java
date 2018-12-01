package eg.edu.alexu.csd.oop.db.cs45;

import java.util.ArrayList;

public class Table {

	public Table() {}

	private ArrayList<ArrayList<Object>> table;
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Table(ArrayList<ArrayList<Object>> table) {
		this.table = table;
	}

	public ArrayList<ArrayList<Object>> getTable() {
		return table;
	}

	public void setTable(ArrayList<ArrayList<Object>> table) {
		this.table = table;
	}

	public Table select(String[] columns, String 
			condition, String columnToCompare, String operator) {
		ArrayList<ArrayList<Object>> resultTable = new ArrayList<
				ArrayList<Object>>();
		ArrayList<Object> newRaw = new ArrayList<Object>();
		int indexOfColumnToCompare = table.get(0).indexOf(columnToCompare);
		for (ArrayList<Object> raw : table) {
			if ( condition == null || validateCondition(operator,
					condition, raw.get(indexOfColumnToCompare))) {
				for (int i = 0; i < columns.length; i++) {
					newRaw.add(raw.get(table.get(0).indexOf(columns[i])));
				}
				resultTable.add(newRaw);
			}
		}
		Table selectedTable = new Table(resultTable);
		return selectedTable;
	}

	public ArrayList<Object> distinct (ArrayList<Object> culomnToBeFiltered) {
		ArrayList<Object> distinctColumn = new ArrayList<Object>();
		for (Object element : culomnToBeFiltered) {
			if (!distinctColumn.contains(element))
				distinctColumn.add(element);
		}
		return distinctColumn;
	}

	public void insert(ArrayList<String> columns, ArrayList<Object> values) {
		ArrayList<Object> newRaw = new ArrayList<>();
		for(Object element : table.get(0)) {
			if (columns.contains(element))
				newRaw.add(values.get(columns.indexOf(element)));
			else
				newRaw.add(null);
		}
		table.add(newRaw);
	}

	public void update(ArrayList<String> columns, ArrayList<Object> values,
			Object condition, String columnToCompare, String operator) {
		int indexOfColumnToCompare = table.get(0).indexOf(columnToCompare);
		for (ArrayList<Object> raw : table) {
			if (condition == null || validateCondition(operator,
					condition, raw.get(indexOfColumnToCompare))) {
				for(Object column : table.get(0)) {
					if (columns.contains(column))
						raw.set(table.get(0).indexOf(column), values.get(
								columns.indexOf(column)));
				}
			}
		}
	}

	public void delete(Object condition, String columnToCompare, String operator) {
		int indexOfColumnToCompare = table.get(0).indexOf(columnToCompare);
		for (ArrayList<Object> raw : table) {
			if (condition == null || validateCondition(operator,
					condition, raw.get(indexOfColumnToCompare)))
				table.remove(raw);
		}
	}

	private boolean validateCondition(String operator, Object condition, Object target) {
		switch (operator) {
		case "=":
			return (target == condition);
		case "<":
			return ((int)target < (int)condition);
		case ">":
			return ((int)target > (int)condition);
		case "<=":
			return ((int)target <= (int)condition);
		case ">=":
			return ((int)target >= (int)condition);
		default:
			return false;
		}
	}
	
}
