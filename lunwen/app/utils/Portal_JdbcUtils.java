package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import play.Play;

public final class Portal_JdbcUtils {
	
	private Portal_JdbcUtils(){}
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	public static Connection getConnection() throws SQLException{
		//String url = "jdbc:mysql://localhost:3306/sxydcloud?user=root&password=123456";
		String url = Play.configuration.getProperty("jdbcMySql");
		return DriverManager.getConnection(url);
	}
	
	public static void free(ResultSet rs,Statement st,Connection conn){
		try{
			if(rs!=null)
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
			
				try{
			if(st!=null)
			st.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			finally{
			if(conn!=null)
				try{
			conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
				}

		
	}
}
