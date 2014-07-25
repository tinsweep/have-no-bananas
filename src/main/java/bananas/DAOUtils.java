package bananas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/* Author: Bryan Thetford
 * Date: 6/25/14
 */
public class DAOUtils {
	private Connection con;
	private static final String URL = "jdbc:h2:~/bananas/ShoppingLists;MODE=MySQL";

	public void closeConn(Connection conn){
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			throw new DAOException("There was a problem closing the connection.", e);
		}
	}
	
	public void closePrepared(PreparedStatement ps){
		try {
			if(ps != null){
				ps.close();
			}
		} catch (SQLException e) {
			throw new DAOException("There was a problem closing the connection.", e);
		}
	}
	
	public void closeStatement(Statement st){
		try {
			if(st != null){
				st.close();	
			}
		} catch (SQLException e) {
			throw new DAOException("There was a problem closing the connection.", e);
		}
	}
	
	public void closeResultSet(ResultSet rs){
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) { 
			throw new DAOException("There was a problem closing the connection.", e);
		} 
	}
	
	//added throws to test SQLExceptions in DAO using a mock DAOUtil
	public Connection getConnection() throws SQLException{
		try{
			con = DriverManager.getConnection(URL);
		}catch(SQLException e){
			throw new DAOException("There was a problem connecting to the Database.", e);		
		}
		return con;
		
	}
}
