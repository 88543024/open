package controllers;

import java.text.SimpleDateFormat;

import models.TResComputer;

@CRUD.For(TResComputer.class)
public class ResComputers extends CRUD{
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//详情页面
	public static void view(Long id){
		TResComputer object = TResComputer.findById(id);
	    render(object);
	}
	
}
