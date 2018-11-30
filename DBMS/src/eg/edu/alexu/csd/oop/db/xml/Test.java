package eg.edu.alexu.csd.oop.db.xml;

import java.util.ArrayList;
import eg.edu.alexu.csd.oop.db.cs45.Table;

public class Test {

	public static void main(String[] args) {
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
		Table t = new Table(a);
		t.setName("example");
		SaveAsXml save = new SaveAsXml("dataBase\\example2.xml", t);
	}

}
