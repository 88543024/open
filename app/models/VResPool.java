package models;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import utils.Portal_JdbcOracleUtilsBACK;

public class VResPool implements Serializable {
	
	public String TOTAL_CPU;//资源池总CPU数
	
	public String ALLOC_CPU;//已分配CPU数
	
	public String TOTAL_MEMORY;//资源池总内存数（G）
	
	public String ALLOC_MEMORY;//已分配内存数（G）
	
	public String TOTAL_STORAGE;//资源池总存储容量（G）
	
	public String ALLOC_STORAGE;//已分配存储容量（G)
	
	public Integer Remain_Cpu; //剩余Cpu数
	
	public Integer Remain_Memory; //剩余内存数
	
	public Integer Remain_Storage; //剩余存储
	
	//返回资源池统计情况
	public static List<VResPool> getVResPool(){
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
				 pool.Remain_Cpu = Integer.valueOf(pool.TOTAL_CPU) - Integer.valueOf(pool.ALLOC_CPU);
				 pool.Remain_Memory = Integer.valueOf(pool.TOTAL_MEMORY) - Integer.valueOf(pool.ALLOC_MEMORY);
				 pool.Remain_Storage = Integer.valueOf(pool.TOTAL_STORAGE) - Integer.valueOf(pool.ALLOC_STORAGE);
				 entyList.add(pool);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entyList;
	}
}
