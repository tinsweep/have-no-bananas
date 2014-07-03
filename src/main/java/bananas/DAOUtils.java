package main.java.bananas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/* Author: Bryan Thetford
 * Date: 6/25/14
 */
public class DAOUtils {
	private static Logger logger = Logger.getLogger("Have No Bananas Log");
	private static final String URL = "jdbc:h2:~/bananas/ShoppingLists;MODE=MySQL";
	
	public DAOUtils(){
		
	}
	public static void closeConn(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.log(Level.WARNING, "There was a problem closing the connection.");
			throw new DAOException("There was a problem closing the connection.", e);
		}
	}
	
	public static void closePrepared(PreparedStatement ps){
		try {
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "There was a problem closing the Prepared Statement." + e.getMessage());
			throw new DAOException("There was a problem closing the connection.", e);
		}
	}
	
	public static void closeStatement(Statement st){
		try {
			st.close();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "There was a problem closing the Statement.");
			throw new DAOException("There was a problem closing the connection.", e);
		}
	}
	
	public static void closeResultSet(ResultSet rs){
		try {
			rs.close();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "There was a problem closing the Result Set.");
			throw new DAOException("There was a problem closing the connection.", e);
		}
	}
	public static Connection getConnection(Connection con){
		try{
			con = DriverManager.getConnection(URL);
		}catch(SQLException e){
			logger.log(Level.WARNING, "There was a problem connecting to the Database.");
			throw new DAOException("There was a problem connecting to the Database.", e);		}
		return con;
		
	}
	

}
