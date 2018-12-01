package eg.edu.alexu.csd.oop.db.cs45;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs45.commands.Select;

public class Utility {

	
	public static void main(String[] args) throws SQLException {
		Select select = new Select();
		System.out.println(select.execute("SELECT asda, kiro hvh FROM Customers WHERE Country = 45 " + 
				"WHERE Country= 15151"));
		Database x = new DBMS();
		System.out.println(x.executeStructureQuery("create database s"));
		System.out.println(x.executeStructureQuery("create table ccc(mm int, ll varchar)"));
		x.executeUpdateQuery("UPDATE Customers_5 \r\n" + 
				"SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'\r\n");
		
//		System.out.println(x.executeUpdateQuery("DELETE FROM Customers\r\n" + 
//							"WHERE CustomerName ='Alfreds Futterkiste'"));
		System.out.println(x.executeStructureQuery("drop table ccc"));
		System.out.println(x.executeStructureQuery("drop database s"));
	}
	
	public static String separator() {
		return System.getProperty("file.separator");
	}
}
