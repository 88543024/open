package controllers;
//资源报表
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.TMsWorkorder;
import models.TWorkorder;
import models.report.ResourceUsedEntity;
public class ResourceReports extends CRUD{
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	 public static void resAllocationReport() {
		 String modelName = params.get("modelName");
		 List<TMsWorkorder> msorders = TMsWorkorder.findAll();
		 List<TWorkorder> orders0 = TWorkorder.findAll();
		 Map<Integer,String> map = null;
		 Map<String,String> map0 = null;
		 List<ResourceUsedEntity> entyList = new ArrayList<ResourceUsedEntity>();
		 List<ResourceUsedEntity> entyList0 = new ArrayList<ResourceUsedEntity>();
		 if(msorders!=null){
			 map = new HashMap<Integer,String>();
			 map0 = new HashMap<String,String>();
			 
			 for(int i=0;i<msorders.size();i++){
				 map.put(msorders.get(i).ELEMENT_ID, null);
			 }
			 Iterator iter = (Iterator)map.keySet().iterator();
			 while(iter.hasNext()){
				 String elementId = String.valueOf(iter.next());
				 TWorkorder order = TWorkorder.getWorkOrderByEleId(elementId);
				 Integer cpuUsed = 0;
				 Integer memUsed = 0;
				 Integer hardUsed = 0;
				 ResourceUsedEntity reUsed = new ResourceUsedEntity();
				 reUsed.setElementId(elementId);
				 reUsed.setProjectName(order.getPROJECT_NAME());
				 for(int i=0;i<msorders.size();i++){
					 if((msorders.get(i).ELEMENT_ID+"").equals(elementId)){
						 cpuUsed = Integer.valueOf(cpuUsed)+Integer.valueOf(msorders.get(i).vcpu);
						 memUsed = Integer.valueOf(memUsed)+Integer.valueOf(msorders.get(i).memory);
						 hardUsed = Integer.valueOf(hardUsed)+Integer.valueOf(msorders.get(i).disksize.split("G")[0]);
					 }
					 
				 }
				 reUsed.setCpuUsed(cpuUsed+"");
				 reUsed.setMemUsed(memUsed+"");
				 reUsed.setHardUsed(hardUsed+"");
				 entyList0.add(reUsed);
			 }
			 //----------------
			 for(int i=0;i<orders0.size();i++){
				 map0.put(orders0.get(i).PROJECT_NAME, null);
			 }
			 Iterator iter0 = (Iterator)map0.keySet().iterator();
			 while(iter0.hasNext()){
				 String projectName = String.valueOf(iter0.next());
				 Integer cpuUsed = 0;
				 Integer memUsed = 0;
				 Integer hardUsed = 0;
				 ResourceUsedEntity reUsed = new ResourceUsedEntity();
				 
				 for(int i=0;i<entyList0.size();i++){
					 if(entyList0.get(i).getProjectName().equals(projectName)){
						 reUsed.setElementId(entyList0.get(i).getElementId());
						 reUsed.setProjectName(projectName);
						 cpuUsed = cpuUsed+Integer.valueOf(entyList0.get(i).getCpuUsed());
						 memUsed = memUsed+Integer.valueOf(entyList0.get(i).getMemUsed());
						 hardUsed = hardUsed+Integer.valueOf(entyList0.get(i).getHardUsed());
					 }
					 
				 }
				 reUsed.setCpuUsed(cpuUsed+"");
				 reUsed.setMemUsed(memUsed+"G");
				 reUsed.setHardUsed(hardUsed+"G");
				 entyList.add(reUsed);
			 }
			 
		 }
		 
	     render(modelName,entyList);
	 }
	
}
