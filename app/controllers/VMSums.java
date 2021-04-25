package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.VProjectVMSum;
import play.Logger;
import utils.Portal_JdbcOracleUtilsBACK;

//按项目统计
public class VMSums extends CRUD{

	public static void sumListByProject() {
		List<VProjectVMSum> entyList = new ArrayList<VProjectVMSum>();
		PreparedStatement st = null;
    	ResultSet rs = null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from \"v_sinopec_project_vm_sum\" where " ).append("1=1");
        Logger.info("sql = "+sql);
        try {
			conn = Portal_JdbcOracleUtilsBACK.getConnection();
			 st = conn.prepareStatement(sql.toString());
			 rs = st.executeQuery();
			 while(rs.next()){
				 VProjectVMSum sum = new VProjectVMSum();
				 sum.PROJECT_NAME = rs.getString(1);
				 sum.VM_NUMBER = rs.getString(2);
				 sum.VCPU = rs.getString(3);
				 sum.MEMORY_SIZE = rs.getString(4);
				 sum.STORAGE_SIZE = rs.getString(5);
				 entyList.add(sum);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		render(entyList);
	}
	
}
