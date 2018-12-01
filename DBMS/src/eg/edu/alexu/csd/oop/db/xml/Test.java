package eg.edu.alexu.csd.oop.db.xml;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import eg.edu.alexu.csd.oop.db.cs45.Table;

public class Test {

	public static void main(String[] args) throws ParserConfigurationException, IOException {
		ArrayList<ArrayList<Object>> a = new ArrayList<>();
		ArrayList<Object > a2 = new ArrayList<>();
		ArrayList<Object > a3 = new ArrayList<>();
		a2.add("id");
		a2.add("client");
		a2.add("country");
		a.add(a2);
		a3.add("54");
		a3.add("Mostafa");
		a3.add("Egypt");
		a.add(a3);
		Table t = new Table();
		t.setName("again");
		String[] types = {"int", "string", "string"};
		t.insertColumn(a2.toArray(new String[a2.size()]), types);
		t.insert(a2, a3);
		SaveAsXml save = new SaveAsXml("dataBase\\", t);
		save.createXML();
		XMLParser xp = new XMLParser("dataBase\\again.xml");
		
		if (xp.domValidationWithDtd()) {
			Table table = xp.getTable();
			for (ArrayList<Object> A : table.getTable()) {
				for (Object element : A) {
					System.out.println(element);
				}
			}
		}
	}

}
