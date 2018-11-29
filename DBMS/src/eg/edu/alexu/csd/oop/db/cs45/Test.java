package eg.edu.alexu.csd.oop.db.cs45;

import java.util.HashMap;

public class Test {

	public static void main(String[] args) {
		
		HashMap<String, String> H = new HashMap<String, String>();
		H.put("table", "clients");
		H.put("id", "int");
		H.put("name", "String");
		H.put("Salary", "int");
		H.put("address", "String");
		new Table("dataBase\\clients.xml", H);
	}

}
