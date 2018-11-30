package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.Command;
import eg.edu.alexu.csd.oop.db.cs45.DBMS;
import eg.edu.alexu.csd.oop.db.cs45.DataBase;

public class DropTable implements Command {

	/**
	 * The validation pattern for the Drop Table statement.
	 */
	private String validation = "\\s*drop\\s+table\\s+([a-zA-Z_0-9]+)\\s*";


	@Override
	public boolean execute(String query) throws SQLException {
		if (isValid(query)) {
			String tableName;
			String temp = query.trim().split("\\s+")[2];
			Matcher m = Pattern.compile("([a-zA-Z_0-9]+)").matcher(temp);
			m.find();
			tableName = m.group(0);
			return getDB().dropTable(tableName);
		}
		return false;
		
	}

	@Override
	public boolean isValid(String query) throws SQLException {
		Pattern p = Pattern.compile(validation, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(query);
		if (!m.find()) {
			throw new SQLException();
		}
		return true;
	}
	private DataBase getDB() { 
		return DBMS.getDatabase();
	}

}
