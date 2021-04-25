package controllers;

import models.Alarm;
//告警报表
@CRUD.For(Alarm.class)
public class AlarmReports extends CRUD{

	public static void view(Long id){
		Alarm object = Alarm.findById(id);
	    render(object);
	}
	
	
}
