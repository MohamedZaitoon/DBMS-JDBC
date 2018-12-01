package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.io.File;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.Command;
import eg.edu.alexu.csd.oop.db.cs45.DBMS;
import eg.edu.alexu.csd.oop.db.cs45.Utility;

public class CreateDatabase implements Command {
	/**
	 * The validation pattern for the Create statement.
	 */
	private String validation = "\\s*create\\s+database\\s+(\\w+)\\s*(?!.)";
	/**
	 * Name of database that has been created.
	 */
	private String dbName;
	/**
	 * getter method for database's name that has been created.
	 * 
	 * @return name of database
	 */
	public String getDbName() {
		return dbName;
	}

	@Override
	public boolean execute(String query) throws SQLException {
		if(isValid(query)) {
			Pattern p = Pattern.compile(validation,Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(query);
			m.find();
			dbName = m.group(1);
			File db = new File(DBMS.getPath()+Utility.separator()+dbName);
			return db.mkdirs();
		}
		return false;
		
	}

	@Override
	public boolean isValid(String query) throws SQLException {
		Pattern p = Pattern.compile(validation,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(query);
		if(!m.find()) {
			throw new SQLException();
		}
		return true;
	}
}
