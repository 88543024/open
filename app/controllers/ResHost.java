package controllers;

import java.util.List;

import play.Logger;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import controllers.CRUD.ObjectType;

import models.TResHost;
import models.TResource;
import models.view.TResPv;

@CRUD.For(TResHost.class)
public class ResHost extends CRUD{
	
}
