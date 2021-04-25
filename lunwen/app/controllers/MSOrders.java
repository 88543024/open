package controllers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import play.db.helper.JdbcHelper;
import models.TMsWorkorder;
import models.TResource;

@CRUD.For(TResource.class)
public class MSOrders  extends CRUD{
	
	public static void view(Long id){
		TResource object = TResource.findById(id);
	    render(object);
	}
	
    
}
