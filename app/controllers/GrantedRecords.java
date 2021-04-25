package controllers;


import models.record.TGrantedLog;

@CRUD.For(TGrantedLog.class)
public class GrantedRecords extends CRUD {
	
	public static void view(Long id){
		TGrantedLog object = TGrantedLog.findById(id);
		render(object);
	}
	
}
