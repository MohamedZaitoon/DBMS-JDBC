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
	private String validation = "\\s*drop\\s+table\\s+(\\w+)\\s*(?!.)";


	@Override
	public boolean execute(String query) throws SQLException {
		if(getDB() == null) {
			throw new SQLException();
		}
		if (isValid(query)) {
			Pattern p = Pattern.compile(validation, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(query);
			m.find();
			String tableName = m.group(1).trim();
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
