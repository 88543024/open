package models;

import java.util.Arrays;
import java.util.List;

import models.deadbolt.Role;
import models.deadbolt.RoleHolder;

public class MyRoleHolder implements RoleHolder{
	
	 public List<? extends Role> getRoles(){
		 return Arrays.asList(new MyRole("supermanager"),
                 new MyRole("manager"),
                 new MyRole("operator"));
	 }

}
