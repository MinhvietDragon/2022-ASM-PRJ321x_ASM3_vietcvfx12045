package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {

	public static void main(String[] args) throws Exception {
		getConnection();

	}
	
	public static Connection getConnection() throws Exception {
		String url = "jdbc:sqlserver://DESKTOP-OBKN98T:1433;databaseName=ShoppingDB;encrypt=false;";
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		System.out.println("OK test");
		return DriverManager.getConnection(url, "sa", "123456");
	}

}
