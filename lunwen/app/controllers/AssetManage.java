package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.view.ComboBox;
import models.view.TUsedBusiness;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.db.helper.JdbcHelper;
import play.mvc.Controller;

public class AssetManage extends Controller {

    public static void list() {
        String ipAddress = params.get("ipAddress");
        String modelName = params.get("modelName");
        render(ipAddress, modelName);
    }

    public static void getModelList(String modelName) {
        List<ComboBox> result = new ArrayList<ComboBox>();
        if (StringUtils.isNotBlank(modelName)) {
            ComboBox comBox = new ComboBox();
            comBox.setId(modelName);
            comBox.setText(modelName);
            result.add(comBox);
        } else {
            List<String> list = TUsedBusiness.getModelList();
            for (String t : list) {
                ComboBox comBox = new ComboBox();
                comBox.setId(t);
                comBox.setText(t);
                result.add(comBox);
            }
        }
        renderJSON(result);
    }

    public static void getBusinessList(String modelName) {
        List<ComboBox> result = new ArrayList<ComboBox>();
        List<String> list = TUsedBusiness.getRealBusinessList(modelName);
        for (String t : list) {
            ComboBox comBox = new ComboBox();
            comBox.setId(t);
            comBox.setText(t);
            result.add(comBox);
        }
        renderJSON(result);
    }
    
    public static void getIpAddressList(String modelName,String businessSys) {
    	List<ComboBox> result = new ArrayList<ComboBox>();
    	StringBuilder where = new StringBuilder();
        where.append(" 1 = 1 ");
        if (StringUtils.isNotBlank(params.get("modelName"))) {
            String[] strs = StringUtils.split(params.get("modelName"), ",");
            where.append(" and modelName in ");
            StringBuffer tmp = new StringBuffer("( ");
            for (int i = 0; i < strs.length; i++) {
                if (i == strs.length - 1) {
                    tmp.append("'").append(strs[i]).append("'");
                } else {
                    tmp.append("'").append(strs[i]).append("', ");
                }
            }
            tmp.append(")");
            where.append(tmp);
        }
        if (StringUtils.isNotBlank(params.get("businessSys"))) {
            String[] strs = StringUtils.split(params.get("businessSys"), ",");
            StringBuffer sb = new StringBuffer("( ");
            for (int i = 0; i < strs.length; i++) {
                if (i == strs.length - 1) {
                    sb.append("'").append(strs[i]).append("'");
                } else {
                    sb.append("'").append(strs[i]).append("', ");
                }
            }
            sb.append(")");
            where.append(" and businessSys in ").append(sb.toString());
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" select  distinct ipAddress from T_ASSET  where" ).append(where.toString());
        Logger.info("getIpAddressList = "+sql);
        
		try {
			ResultSet rs = JdbcHelper.execute(sql.toString());
			while(rs.next()){
	        	ComboBox comBox = new ComboBox();
	        	comBox.setId(rs.getString(1));
	        	comBox.setText(rs.getString(1));
	        	result.add(comBox);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        renderJSON(result);
    }
    
    public static void getIpAddressViewList() {
    	List<ComboBox> result = new ArrayList<ComboBox>();
    	StringBuffer sql = new StringBuffer();
        sql.append(" select  distinct ipAddress from vw_port_details" );
        Logger.info("getIpAddressViewList = "+sql);
        
		try {
			ResultSet rs = JdbcHelper.execute(sql.toString());
			while(rs.next()){
	        	ComboBox comBox = new ComboBox();
	        	comBox.setId(rs.getString(1));
	        	comBox.setText(rs.getString(1));
	        	result.add(comBox);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        renderJSON(result);
    }
    
    public static void getPortNameList() {
    	List<ComboBox> result = new ArrayList<ComboBox>();
    	StringBuilder where = new StringBuilder();
        where.append(" 1 = 1 ");
        if (StringUtils.isNotBlank(params.get("ipAddress"))) {
            String[] strs = StringUtils.split(params.get("ipAddress"), ",");
            where.append(" and ipAddress in ");
            StringBuffer tmp = new StringBuffer("( ");
            for (int i = 0; i < strs.length; i++) {
                if (i == strs.length - 1) {
                    tmp.append("'").append(strs[i]).append("'");
                } else {
                    tmp.append("'").append(strs[i]).append("', ");
                }
            }
            tmp.append(")");
            where.append(tmp);
        }
    	StringBuffer sql = new StringBuffer();
        sql.append(" select  distinct portname from vw_port_details where" ).append(where.toString());
        Logger.info("getIpAddressViewList = "+sql);
        
		try {
			ResultSet rs = JdbcHelper.execute(sql.toString());
			while(rs.next()){
	        	ComboBox comBox = new ComboBox();
	        	comBox.setId(rs.getString(1));
	        	comBox.setText(rs.getString(1));
	        	result.add(comBox);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        renderJSON(result);
    }
}
