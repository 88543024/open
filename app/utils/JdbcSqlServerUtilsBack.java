package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcSqlServerUtilsBack {
    //sqlServer 2005 及以上
	//private static String url="jdbc:sqlserver://10.246.140.220:1433;DatabaseName=SCOMData";
	private static String url="jdbc:sqlserver://10.246.140.171:51990;DatabaseName=SCOMData";
	//private static String url="jdbc:microsoft:sqlserver://10.246.140.220:1433;DatabaseName=SCOMData";
	private static String user="dyw";
	private static String password="1qaz@WSX";
	
	private JdbcSqlServerUtilsBack(){}
	static{
		try {
		    //sqlServer 2005 及以上
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		   //sqlServer2000 
			//Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			System.out.println("sqlServer驱动加载成功！");
		} catch (ClassNotFoundException e) {
			System.out.println("sqlServer驱动加载时报错！");
			throw new ExceptionInInitializerError(e);
		}
	}
	public static Connection getConnection(String dataName) throws SQLException{
	    String myUrl="jdbc:sqlserver://10.246.140.171:51990;DatabaseName="+dataName;
		return DriverManager.getConnection(myUrl, user, password);
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
			}finally{
			    if(conn!=null)
				try{
			       conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		 }
	}
	
	public static void main(String[] args) {
		Connection con;
		try {
			con = JdbcSqlServerUtilsBack.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
