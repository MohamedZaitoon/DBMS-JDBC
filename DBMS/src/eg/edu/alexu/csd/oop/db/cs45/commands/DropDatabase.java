package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.io.File;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.Command;
import eg.edu.alexu.csd.oop.db.cs45.DBMS;
import eg.edu.alexu.csd.oop.db.cs45.Utility;

public class DropDatabase implements Command {
	/**
	 * The validation pattern for the Drop statement.
	 */
	private String validation = "\\s*drop\\s+database\\s+(\\w+)\\s*(?!.)";
	/**
	 * Name of database that has been dropped.
	 */
	private String dbName;

	/**
	 * getter method for database's name that has been dropped.
	 * 
	 * @return name of database
	 */
	public String getDbName() {
		return dbName;
	}

	@Override
	public boolean execute(String query) throws SQLException {
		if (isValid(query)) {
			Pattern p = Pattern.compile(validation,Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(query);
			m.find();
			dbName = m.group(1);
			File db = new File(DBMS.getPath() + DBMS.getSeparator() + dbName);
			if (!db.exists())
				return false;
				
			return drop(db);
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
	public boolean drop(File file) {
		delete(file.listFiles());
		return file.delete();
	}
	private void delete(File[] files) {
		for(File f : files) {
			if(f.isDirectory()) {
				delete(f.listFiles());
			}else if(f.isFile()) {
				f.delete();
			}
		}
	}
}
