package main.java.bananas;

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
	private static final String URL = "jdbc:h2:~/bananas/ShoppingLists;MODE=MySQL";

	public static void closeConn(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DAOException("There was a problem closing the connection.", e);
		}
	}
	
	public static void closePrepared(PreparedStatement ps){
		try {
			ps.close();
		} catch (SQLException e) {
			throw new DAOException("There was a problem closing the connection.", e);
		}
	}
	
	public static void closeStatement(Statement st){
		try {
			st.close();
		} catch (SQLException e) {
			throw new DAOException("There was a problem closing the connection.", e);
		}
	}
	
	public static void closeResultSet(ResultSet rs){
		try {
			rs.close();
		} catch (SQLException e) {
			throw new DAOException("There was a problem closing the connection.", e);
		}
	}
	public static Connection getConnection(Connection con){
		try{
			con = DriverManager.getConnection(URL);
		}catch(SQLException e){
			throw new DAOException("There was a problem connecting to the Database.", e);		
		}
		return con;
		
	}
}
