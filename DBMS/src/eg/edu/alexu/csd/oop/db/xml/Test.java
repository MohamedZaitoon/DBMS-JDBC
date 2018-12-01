package eg.edu.alexu.csd.oop.db.xml;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import eg.edu.alexu.csd.oop.db.cs45.Table;

public class Test {

	public static void main(String[] args) throws ParserConfigurationException, IOException {
		Table table = new Table();
		String[] columns = new String[]{"id", "client","country"};
		String[] dataTypes = new String[]{"int", "String","String"};
		table.insertColumn(columns, dataTypes);
		ArrayList<Object> values = new ArrayList<Object>();
		values.add("54");
		values.add("Mostafa");
		values.add("Egypt");
		ArrayList<Object > columns1 = new ArrayList<>();
		columns1.add("id");
		columns1.add("client");
		columns1.add("country");
		table.insert(columns1, values);
		values = new ArrayList<Object>();
		values.add("55");
		values.add("Jordan");
		columns1 = new ArrayList<>();
		columns1.add("id");
		columns1.add("country");
		table.insert(columns1, values);
		Object[][] selctionTest1 = table.select(columns, null, null, null);
		columns = new String[] {"client", "country"};
		String columnToCompare = "id", operator = ">=";
		Object condition = "55";
		Object[][] selectionTest2 = table.select(columns, condition, columnToCompare, operator);
		ArrayList<String> columns2 = new ArrayList<String>();
		columns2.add("country");
		values = new ArrayList<Object>();
		values.add("UK");
		table.update(columns2, values, condition, columnToCompare, operator);
		table.update(columns2, values, null, null, null);
		table.delete(condition, columnToCompare, operator);
		table.delete(null, null, null);
//		/*ArrayList<ArrayList<Object>> a = new ArrayList<>();
//		ArrayList<Object > a2 = new ArrayList<>();
//		ArrayList<Object > a3 = new ArrayList<>();
//		a2.add("id");
//		a2.add("client");
//		a2.add("country");
//		a.add(a2);
//		a3.add("54");
//		a3.add("Mostafa");
//		a3.add("Egypt");
//		a.add(a3);
//		Table t = new Table();
//		t.setName("again");
//		String[] types = {"int", "string", "string"};
//		t.insertColumn(a2.toArray(new String[a2.size()]), types);
//		t.insert(a2, a3);
//		SaveAsXml save = new SaveAsXml("dataBase\\", t);
//		save.createXML();
//		//XMLParser xp = new XMLParser("dataBase\\");
//		
//		 * if (xp.domValidationWithDtd()) {
//		XMLParser xp = new XMLParser("dataBase\\again.xml");
//		
//		if (xp.domValidationWithDtd()) {
//			Table table = xp.getTable();
//			for (ArrayList<Object> A : table.getTable()) {
//				for (Object element : A) {
//					System.out.println(element);
//				}
//			}
//		}
	}

}
