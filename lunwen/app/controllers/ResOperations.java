package controllers;


import models.record.TResOperationLog;

@CRUD.For(TResOperationLog.class)
public class ResOperations extends CRUD {
	
	public static void view(Long id){
		TResOperationLog object = TResOperationLog.findById(id);
		render(object);
	}
	
}
