package models.base;

import java.lang.reflect.Field;

import javax.persistence.MappedSuperclass;

import play.db.jpa.Model;

@MappedSuperclass
public class MapModel extends Model {
	
	public Object get(String fieldName){
		try {
			Field field = getClass().getField(fieldName);
			return field.get(this);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void set(String fieldName,String value){
		try {
			Field field = getClass().getField(fieldName);
			field.set(this, value);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}
