package eg.edu.alexu.csd.oop.db.cs45;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class Utility {

	
	public static void main(String[] args) throws SQLException {
		Database x = new DBMS();
		System.out.println(x.executeStructureQuery("create database ss"));
		System.out.println(x.executeStructureQuery("create table ccc(mm ll, ll jj)"));
		x.executeUpdateQuery("UPDATE Customers_5 \r\n" + 
				"SET ContactName = 'Alfred Schmidt',  CctName = 'jkgjc',City= 'Frankfurt'\r\n");
		
		System.out.println(x.executeUpdateQuery("DELETE FROM Customers\r\n" + 
							"WHERE CustomerName ='Alfreds Futterkiste'"));
		System.out.println(x.executeStructureQuery("drop table ccc"));
		System.out.println(x.executeStructureQuery("drop database mohamed"));
	}
	
	public static String separator() {
		return System.getProperty("file.separator");
	}
}
