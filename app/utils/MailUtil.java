package utils;

import java.rmi.RemoteException;
//import java.sql.Timestamp;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Date;
import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.FutureTask;

import javax.xml.rpc.ServiceException;


import models.TMsWorkorder;
//import models.TResVM;
import models.TWorkorder;
import webservice.IBMCloudServiceLocator;
import webservice.IBMCloudServiceSoap;
import webservice.WorkOrdersEntity;
import play.Logger;
import webservice.Ms_Model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
//import com.moyosoft.exchange.Exchange;
//import com.moyosoft.exchange.ExchangeServiceException;
//import com.moyosoft.exchange.folder.ExchangeFolder;
//import com.moyosoft.exchange.item.ExchangeItem;
//import com.moyosoft.exchange.item.ExchangeItemField;
//import com.moyosoft.exchange.item.ItemsCollection;
//import com.moyosoft.exchange.mail.ExchangeMail;
//import com.moyosoft.exchange.search.If;
//import com.moyosoft.exchange.search.Restriction;

//public class MailUtil implements Callable<Boolean> {
public class MailUtil{

//	public String getHost() {
//		return host;
//	}
//
//	public void setHost(String host) {
//		this.host = host;
//	}
//
//	public String getUser() {
//		return user;
//	}
//
//	public void setUser(String user) {
//		this.user = user;
//	}
//
//	public String getPwd() {
//		return pwd;
//	}
//
//	public void setPwd(String pwd) {
//		this.pwd = pwd;
//	}
//
//	public List<TMsWorkorder> getMsList() {
//		return msList;
//	}
//
//	public void setMsList(List<TMsWorkorder> msList) {
//		this.msList = msList;
//	}
//
//	public List<TResVM> getVmList() {
//		return vmList;
//	}
//
//	public void setVmList(List<TResVM> vmList) {
//		this.vmList = vmList;
//	}
//
//	public Integer getElementId() {
//		return elementId;
//	}
//
//	public void setElementId(Integer elementId) {
//		this.elementId = elementId;
//	}
//
//	public String getProjectName() {
//		return projectName;
//	}
//
//	public void setProjectName(String projectName) {
//		this.projectName = projectName;
//	}
//
//	public Integer getSleepMinute() {
//		return sleepMinute;
//	}
//
//	public void setSleepMinute(Integer sleepMinute) {
//		this.sleepMinute = sleepMinute;
//	}
//
//	public MailUtil() {
//	}
//
//	public MailUtil(String host, String user, String pwd,
//			List<TMsWorkorder> msList, List<TResVM> vmList,
//			Integer elementId, String projectName, Integer sleepMinute) {
//		this.host = host;
//		this.user = user;
//		this.pwd = pwd;
//		this.msList = msList;
//		this.vmList = vmList;
//		this.elementId = elementId;
//		this.projectName = projectName;
//		this.sleepMinute = sleepMinute;
//	}
//
//	private String host;
//	private String user;
//	private String pwd;
//	private List<TMsWorkorder> msList;
//	private List<TResVM> vmList;
//	private Integer elementId;
//	private String projectName;
//	private Integer sleepMinute;
//
//	@Override
//	public Boolean call() throws Exception {
//		// TODO Auto-generated method stub
//		System.out.println("getmail2222");
//		Thread.sleep(sleepMinute * 60 * 1000);
//		System.out.println(new Timestamp(Calendar.getInstance()
//				.getTimeInMillis()));
//		return false;
////		return getMail(host, user, pwd, msList, vmList, elementId, projectName);
//	}

//	public boolean getMail(String host, String user, String pwd,
//			List<TMsWorkorder> msList, List<TResVM> vmList,
//			Integer elementId, String projectName) {
//		Exchange exchange;
//		try {
//			exchange = new Exchange(host, user, pwd, true);
//			ExchangeFolder inbox = exchange.getInboxFolder();
//			Restriction newRestriction = If.Message.IsNotRead;
//			Restriction bodyRestriction = If.Message.Body.contains(projectName);
//			Restriction restriction = If.Message.Subject
//					.contains("启动新项目的请求已处理").or().contains("添加新服务器的请求已处理")
//					.and(newRestriction).and(bodyRestriction);
//			ItemsCollection items = inbox.getItems().restrict(restriction)
//					.sortBy(ExchangeItemField.item_DateTimeReceived);
//			System.out.println("Messages found: " + items.getCount());
//			if (items.getCount() == 0)
//				return false;
//			String[] text;
//			String temp;
//			TMsWorkorder msModel;
//			TResVM vmModel;
//			Gson gson = new Gson();
//			for (ExchangeItem item : items) {
//				ExchangeMail mail = (ExchangeMail) item;
//				text = mail.getBodyAsText().split("\\r\\n");
//				for (int i = 0; i < text.length; i++) {
//					temp = text[i].trim();
//					// System.out.println(text[i].trim());
//					if (temp.startsWith("服务器的主机名")) {
//						msModel = new TMsWorkorder();
//						vmModel = new TResVM();
//						vmModel.NAME = msModel.hostname = temp.split("：")[1]
//								.trim();
//						while (!temp.startsWith("CPU")) {
//							i++;
//							temp = text[i].trim();
//						}
//						msModel.vcpu = temp.split("：")[1].trim();
//						vmModel.CPU_NUMBER = Integer.valueOf(msModel.vcpu);
//						while (!temp.startsWith("内存量")) {
//							i++;
//							temp = text[i].trim();
//						}
//						msModel.memory = temp.split("：")[1].trim().substring(0,
//								1);
//						vmModel.MEMORY_SIZE = Integer.valueOf(msModel.memory);
//						// while(!temp.startsWith("磁盘空间"))
//						// {
//						// i++;
//						// temp = text[i].trim();
//						// }
//						// msModel.disksize = temp.split("：")[1].replace("GB",
//						// "").trim();
//						// vmModel.DATA_VG_SIZE =
//						// Integer.valueOf(msModel.disksize);
//						while (!temp.startsWith("管理员密码")) {
//							i++;
//							temp = text[i].trim();
//						}
//						msModel.password = temp.split("：")[1].trim();
//						msModel.username = "root";
//						vmModel.UPDATE_TIME = vmModel.CREATE_TIME = 
//								Calendar.getInstance().getTime().toString();
//						vmModel.PENDING_TIME = Calendar.getInstance().getTime().toString();
//						vmModel.OS = "AIX";
//						msModel.ip = vmModel.IP = formatIp(msModel.hostname);
//						msModel.ELEMENT_ID = elementId;
//						vmModel.WORK_ORDER_ID = elementId;
//						vmModel.SYS_INFO = projectName;
//						msList.add(msModel);
//						vmList.add(vmModel);
//						System.out.println(gson.toJsonTree(msModel));
//						System.out.println(gson.toJsonTree(vmModel));
//					}
//					mail.setIsRead(true);
//					mail.save();
//				}
//			}
//			if (msList.size() > 0)
//				return true;
//		} catch (ExchangeServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//
//	}

//	public String formatIp(String name) {
//		String ip = name.substring(name.length()-3, name.length());	
//	    if (ip.startsWith("0"))
//				ip = ip.substring(1);
//		ip = "10.246.149."+ip;
//		return ip;
//	}

//	public static void getMail(Integer workOrderId, String projectName, Long id) {
//		List<TMsWorkorder> msList = new ArrayList<TMsWorkorder>();
//		List<TResVM> vmList = new ArrayList<TResVM>();
//		// 两个List是重要返回
//
////		int sleepMinute = 30;
//		int sleepMinute = 1;
//		MailUtil call = new MailUtil("10.5.30.30", "cloud2011", "5996@c@6746",
//				msList, vmList, workOrderId, projectName, sleepMinute);// 内网用
//		// MailUtil call = new MailUtil("mail.sinopec.com", "cloud2011",
//		// "5996@c@6746", msList, vmList, workOrderId, projectName,
//		// sleepMinute);
//		FutureTask<Boolean> futureTask;
//		Thread th;
//		boolean flag = false;
//		try {
//			while (!flag) {
//				futureTask = new FutureTask<Boolean>(call);
//				th = new Thread(futureTask);
//				th.start();
//				flag = futureTask.get();
//			}
////			callMsWS(msList, id);
//			if(id!=null){
//				callMsWS(msList, id);
//			}
//			//插入TResVM表
//			java.text.Format sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			for(int i=0;i<vmList.size();i++){
//				TResVM comp = vmList.get(i);
//				TResVM vmNew = new TResVM();
//				vmNew.NAME = comp.NAME;
//				vmNew.SYS_INFO = comp.SYS_INFO;
//				vmNew.OS = comp.OS;
//				vmNew.IP = comp.IP;
//				vmNew.SECURITY_LEVEL = comp.SECURITY_LEVEL;
//				vmNew.SERVER_ROLE = comp.SERVER_ROLE;
//				vmNew.CPU_NUMBER = comp.CPU_NUMBER;
//				vmNew.MEMORY_SIZE = comp.MEMORY_SIZE;
//				
//				vmNew.DMZ = comp.DMZ;
//				vmNew.CLUSTER_NAME = comp.CLUSTER_NAME;
//				vmNew.CLUSTER_IP = comp.CLUSTER_IP;
//				vmNew.PARENT_HOST = comp.PARENT_HOST;
//				vmNew.ROOT_VG_SIZE = comp.ROOT_VG_SIZE;
//				vmNew.PENDING_TIME = comp.PENDING_TIME;
//				
//				vmNew.DATA_VG_SIZE = comp.DATA_VG_SIZE;
//				vmNew.STATUS = comp.STATUS;
//				vmNew.UPDATE_TIME = comp.UPDATE_TIME;
//				vmNew.WORK_ORDER_ID = comp.WORK_ORDER_ID;
//				vmNew.CREATE_TIME = sdf1.format(new Date());
//				vmNew.save();
//			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	/**
	 * 调用微软接口
	 * 
	 * @param tmsOrder
	 * @return
	 */
	public static boolean callMsWS(List<TMsWorkorder> tmsList, Long id) {
		boolean bool = false;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		IBMCloudServiceLocator service = new IBMCloudServiceLocator();
		WorkOrdersEntity workOrdersEntity = new WorkOrdersEntity();
		Calendar c = Calendar.getInstance();
		JsonArray array = new JsonArray();
		Gson gson = new Gson();
		Ms_Model msModel;
		TWorkorder object = TWorkorder.findById(id);
		for (TMsWorkorder tmsOrder : tmsList) {
			msModel = new Ms_Model();
			msModel.vcpu = tmsOrder.vcpu;
			msModel.memory = tmsOrder.memory;
			msModel.os = tmsOrder.os;
			msModel.dbsoft = tmsOrder.dbsoft;
			msModel.middleware = tmsOrder.middleware;
			msModel.disksize = tmsOrder.disksize;
			msModel.hostname = tmsOrder.hostname;
			msModel.ip = tmsOrder.ip;
			msModel.user = tmsOrder.username;
			msModel.password = tmsOrder.password;
			msModel.siteUrl = object.SiteUrl;
			array.add(gson.toJsonTree(msModel));
		}
		
		workOrdersEntity.setRId(object.ELEMENT_ID);
		workOrdersEntity.setWId(object.WORKORDER_ID);
		workOrdersEntity.setRibbieInfo(array.toString());
		workOrdersEntity.setMemo("complete");
		workOrdersEntity.setPerformPerson(object.OPERATOR);
		workOrdersEntity.setCompleteTime(sdf1.format(c.getTime()));
		workOrdersEntity.setRespondTime(sdf1.format(c.getTime()));
		workOrdersEntity.setSiteUrl(object.SiteUrl);
		System.out.println(gson.toJsonTree(workOrdersEntity));
		try {
			IBMCloudServiceSoap test = service.getIBMCloudServiceSoap();
			bool = test.updateWorkOrders(workOrdersEntity);
			Logger.info("Completed to update msWorkOrders ");
		} catch (ServiceException e) {
			bool = false;
			e.printStackTrace();
		} catch (RemoteException e) {
			bool = false;
			e.printStackTrace();
		}
		return bool;
	}
}
