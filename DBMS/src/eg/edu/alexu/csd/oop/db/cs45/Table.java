package eg.edu.alexu.csd.oop.db.cs45;

import java.util.ArrayList;

public class Table {

	public Table() {}

	private ArrayList<ArrayList<Object>> table = new ArrayList<>();
	private ArrayList<String> dataTypes = new ArrayList<>();
	private String name;

	
	public Table(ArrayList<ArrayList<Object>> table) {
		this.table = table;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<ArrayList<Object>> getTable() {
		return table;
	}
	public void setDataTypes(ArrayList<String> dataTypes) {
		this.dataTypes = dataTypes;
	}
	public ArrayList<String> getDataTypes() {
		return dataTypes;
	}
	public void setTable(ArrayList<ArrayList<Object>> table) {
		this.table = table;
	}

	public Object[][] select(String[] columns, Object 
			condition, String columnToCompare, String operator) {
		if(!valid(columns))
			return null;
		Object[][] resultOfSelection = new Object[table.size() - 1][columns.length];
		int indexOfColumnToCompare = table.get(0).indexOf(columnToCompare), size = 0;
		for (int j = 1; j < table.size(); j++) {
			ArrayList<Object> raw = table.get(j);
			if ( condition == null || validateCondition(operator,
					condition, raw.get(indexOfColumnToCompare))) {
				for (int i = 0; i < columns.length; i++) {
					resultOfSelection[size][i] = (raw.get(table.get(0).indexOf(columns[i])));
				}
				size++;
			}
		}
		Object[][] resultOfSelection1 = new Object[size][columns.length];
		for (int l = 0; l < size; l++) {
			resultOfSelection1[l] = resultOfSelection[l];
		}
		return resultOfSelection1;
	}

	/*public ArrayList<Object> distinct (ArrayList<Object> culomnToBeFiltered) {
		if(!valid(culomnToBeFiltered.toArray(new String[culomnToBeFiltered.size()])))
			return null;
		ArrayList<Object> distinctColumn = new ArrayList<Object>();
		for (Object element : culomnToBeFiltered) {
			if (!distinctColumn.contains(element))
				distinctColumn.add(element);
		}
		return distinctColumn;
	}*/

	public void insert(ArrayList<Object> columns, ArrayList<Object> values) {
		if(!valid(columns.toArray(new String[columns.size()])))
			return;
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
		if(!valid(columns.toArray(new String[columns.size()])))
			return;
		int indexOfColumnToCompare = table.get(0).indexOf(columnToCompare);
		for (int j = 1; j < table.size(); j++) {
			ArrayList<Object> raw = table.get(j);
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
		for (int j = 1; j < table.size(); j++) {
			ArrayList<Object> raw = table.get(j);
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
			return ((Integer.parseInt((String)target)) < (Integer.parseInt((String)condition)));
		case ">":
			return ((Integer.parseInt((String)target)) > (Integer.parseInt((String)condition)));
		case "<=":
			return ((Integer.parseInt((String)target)) <= (Integer.parseInt((String)condition)));
		case ">=":
			return ((Integer.parseInt((String)target)) >= (Integer.parseInt((String)condition)));
		default:
			return false;
		}
	}
	public void insertColumn(String[] columns, String[] dataTypes) {
		ArrayList<Object> newCol = new ArrayList<>();
		for (int i = 0; i < columns.length; i++) {
			newCol.add(columns[i]);
			this.dataTypes.add(dataTypes[i]);
		}
		table.add(newCol);
	}

	private boolean valid(String[] cols)
	{
		ArrayList<Object> validCols = table.get(0);
		for(int i = 0; i < cols.length; i++)
		{
			if(!(validCols.contains(cols[i])))
			{
				System.out.println(cols[i] + " : is not a valid colomn.");
				return false;
			}
		}
		return true;
	}
	
}
