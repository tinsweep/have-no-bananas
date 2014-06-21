package bananas;
/**
 * Created by Bryan on 6/18/2014.
 **/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnector {
	//creates database named ShoppingLists in folder bananas within the users home directory
	private static final String URL = "jdbc:h2:~/bananas/ShoppingLists";
	private DBConnector(){
		//to prevent instantiation of class
	}
	public static Connection getConnection(Connection conn){
		try{
			conn = DriverManager.getConnection(URL);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
		
	}
}
