package context;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
	
	public DBContext () {}
	
	public Connection getConnection2() throws Exception {
		String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + "\\" + instance +";databaseName=" + dbName;
		if(instance == null || instance.trim().isEmpty()) {
			url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;
		}
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection(url, userID, password);
	}
	
	private final String serverName = "DESKTOP-OBKN98T";
	private final String dbName = "ShoppingDB";
	private final String portNumber = "1433";
	private final String instance = "";
	private final String userID = "sa";
	private final String password = "123456";
	
	
	public Connection getConnection() throws Exception {
		
		try {
			String url = "jdbc:sqlserver://DESKTOP-OBKN98T:1433;databaseName=ShoppingDB;encrypt=false;";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection(url, "sa", "123456");
			
		} catch (Exception e) {
			System.out.println("khong ket noi duoc CSDL");
		}
		
		return null;
	}
}
