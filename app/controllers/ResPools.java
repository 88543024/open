package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.VResPool;
import play.Logger;
import utils.Portal_JdbcOracleUtilsBACK;
//资源池情况
public class ResPools extends CRUD{

	public static void poolList() {
		List<VResPool> entyList = new ArrayList<VResPool>();
		PreparedStatement st = null;
    	ResultSet rs = null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from \"v_sinopec_resource_pool\" where " ).append("1=1");
        Logger.info("sql = "+sql);
        try {
			conn = Portal_JdbcOracleUtilsBACK.getConnection();
			 st = conn.prepareStatement(sql.toString());
			 rs = st.executeQuery();
			 while(rs.next()){
				 VResPool pool = new VResPool();
				 pool.TOTAL_CPU = rs.getString(1);
				 pool.ALLOC_CPU = rs.getString(2);
				 pool.TOTAL_MEMORY = rs.getString(3);
				 pool.ALLOC_MEMORY = rs.getString(4);
				 pool.TOTAL_STORAGE = rs.getString(5);
				 pool.ALLOC_STORAGE = rs.getString(6);
				 entyList.add(pool);
			 }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
		render(entyList);
	}
	
}
