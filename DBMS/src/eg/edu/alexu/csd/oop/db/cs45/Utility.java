package eg.edu.alexu.csd.oop.db.cs45;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Command;
import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs45.commands.Update;

public class Utility {

	
	public static void main(String[] args) throws SQLException {
		Database x = new DBMS();
		/*System.out.println(x.executeStructureQuery("create database mohamed"));
		System.out.println(x.executeStructureQuery("create table ccc(mm ll, ll jj)"));
		System.out.println(x.executeStructureQuery("drop table ccc"));
		System.out.println(x.executeStructureQuery("drop database mohamed"));*/
		String s = System.lineSeparator();
		Command m = new Update();
		System.out.println(m.isValid("UPDATE Customers_5 \r\n" + 
				"SET ContactName = 'Alfred Schmidt',ContactName = 'Alfred Schmidt',City= 'Frankfurt'\r\n" + 
				"WHERE CustomerID = 2;"));
	}
	
	/*public Table getTable(String name) {
		//Table t = new Table();
		//get rows from table of xml file  into object table t 
		//return t;
	}
	*/public static String separator() {
		return System.getProperty("file.separator");
	}
}
