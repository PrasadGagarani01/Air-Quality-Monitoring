package connection;

import java.sql.Connection;

import java.sql.DriverManager;

public class DBconnect {
	
	static Connection con = null;
	public static Connection getConnect() {
		
		if(con==null) {
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/airqualitymonitor","root","");
				return con;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return con;
		
	}

}