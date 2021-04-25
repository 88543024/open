//package controllers;
//
//import java.util.List;
//
//import models.TCfgDepartment;
//import utils.TreeUtil;
//
//
//@CRUD.For(TCfgDepartment.class)
//public class Computes extends CRUD{
//	
//   public static void buildTree() {
//        List<TCfgDepartment> list = TCfgDepartment.findAll();
//        TreeUtil<TCfgDepartment> tool = new TreeUtil<TCfgDepartment>();
//        List<TCfgDepartment> result = tool.buildTree(list, -1L);
//        renderJSON(result);
//    }
//	
//	/**
//	 * 计算资源展示
//	 */
//    public static void list() {
//        render();
//    }
//	
//
//    
//}
