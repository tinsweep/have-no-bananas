package bananas;
/**
 * Created by Bryan on 6/18/2014.
 **/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnector {
	private static final String URL = "jdbc:h2:~/bananas/ShoppingLists";
	private Connection conn;
	public DBConnector() throws SQLException{
		try{
			
			conn = DriverManager.getConnection(URL);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return conn;
		
	}
	

}
