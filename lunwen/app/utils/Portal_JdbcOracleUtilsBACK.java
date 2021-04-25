package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class Portal_JdbcOracleUtilsBACK {

	private static String url="jdbc:oracle:thin:@10.246.147.3:1522:portal";
	private static String user="portal";
	private static String password="Passw0rd";
	
	private Portal_JdbcOracleUtilsBACK(){}
	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("oracle驱动加载成功！");
		} catch (ClassNotFoundException e) {
			System.out.println("oracle驱动加载时报错！");
			throw new ExceptionInInitializerError(e);
		}
	}
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, user, password);
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
