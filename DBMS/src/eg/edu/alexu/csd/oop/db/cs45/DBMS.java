package eg.edu.alexu.csd.oop.db.cs45;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.db.Command;
import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs45.commands.CreateDatabase;
import eg.edu.alexu.csd.oop.db.cs45.commands.CreateTable;
import eg.edu.alexu.csd.oop.db.cs45.commands.Delete;
import eg.edu.alexu.csd.oop.db.cs45.commands.DropDatabase;
import eg.edu.alexu.csd.oop.db.cs45.commands.DropTable;
import eg.edu.alexu.csd.oop.db.cs45.commands.Insert;
import eg.edu.alexu.csd.oop.db.cs45.commands.Select;
import eg.edu.alexu.csd.oop.db.cs45.commands.Update;
import eg.edu.alexu.csd.oop.db.cs45.commands.UpdateQuery;

public class DBMS implements Database {
	
	private static String separator =System.getProperty("file.separator");
	/**
	 * where all databases will be created.
	 */
	private static final String PATH = ".." + separator + "server";
	/**
	 * the database that we working on it.
	 */
	
	private static DataBase database;
	
	public static String getSeparator() {
		return separator;
	}

	public static String getPath() {
		return PATH;
	}

	/**
	 * all executable commands.
	 */
	private Map<String, Command> commands;

	public DBMS() {
		database = null;
		commands = new HashMap<>();
		getAvailableCommands();
	}

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		File serverDir = new File(PATH);
		if (!serverDir.exists()) {
			serverDir.mkdir();
			try {
				executeStructureQuery("CREATE DATABASE " + databaseName );
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			boolean isExist = new File(PATH+separator+databaseName).exists();
			if (isExist) {
				if (dropIfExists) {
					try {
						executeStructureQuery("DROP DATABASE " + databaseName);
						executeStructureQuery("CREATE DATABASE " + databaseName);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					executeStructureQuery("CREATE DATABASE " + databaseName);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return new File(PATH + separator + databaseName).getAbsolutePath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		String key = getCommand(query, 0).toUpperCase() + " " +getCommand(query, 1).toUpperCase() ;//assume that query is true
		Command cmd = commands.get(key);
		if(cmd != null) {
			if(cmd.execute(query)) {
				if(cmd instanceof CreateDatabase) {
					database = new DataBase(((CreateDatabase) cmd).getDbName());
				}else if(cmd instanceof DropDatabase) {
					if(database != null&&database.getName().equals(((DropDatabase) cmd).getDbName())) {
						database = null;
					}
				}
				return true;
			}else if(cmd instanceof CreateDatabase) {
				database = new DataBase(((CreateDatabase) cmd).getDbName());
			}
		}else {
			throw new SQLException();
		}
		return false;
	}

	public static DataBase getDatabase() {
		return database;
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		return null;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		String key = getCommand(query, 0).toUpperCase();
		Command cmd = commands.get(key);
		if(cmd != null) {
			if(cmd.execute(query)) {
				return ((UpdateQuery)cmd).getUpdatedRows();
			}
		}
		return 0;
	}

	/**
	 * set all commands are exist in DBMS.
	 */
	private void getAvailableCommands() {
		commands.put("CREATE DATABASE", new CreateDatabase());
		commands.put("CREATE TABLE", new CreateTable());
		commands.put("DELETE", new Delete());
		commands.put("DROP DATABASE", new DropDatabase());
		commands.put("DROP TABLE", new DropTable());
		commands.put("INSERT", new Insert());
		commands.put("SELECT", new Select());
		commands.put("UPDATE", new Update());
	}

	private String getCommand(String query, int index) {
		String temp = query.trim();
		if(!temp.isEmpty()) {
			String[] q = temp.split("\\s+");
			return q[index];
		}
		return null;
	}

}
