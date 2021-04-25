package controllers;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.TDataDic;
import models.TImage;
import models.TImageLog;

import org.apache.commons.io.FileUtils;

import rest.GetImageContact;
import rest.RegisterImageContact;
import rest.UnregisterImageContact;
import soap.NimDiscovery;
import utils.SFTPUtil;

import com.jcraft.jsch.ChannelSftp;

@CRUD.For(TImage.class)
public class Images extends CRUD {

	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 镜像上传
	 * @param imag
	 */
	public static void upload(File imag) {
		String imageName = params.get("imageName");
		String osPatch = params.get("osPatch");
		String dbPatch = params.get("dbPatch");
		String webPatch = params.get("webPatch");
		String remark = params.get("remark");
		String flag = "0";
		try {
			SFTPUtil sftpUtil = new SFTPUtil();
			ChannelSftp sftp = sftpUtil.connect(SFTPUtil.host, SFTPUtil.port, SFTPUtil.username, SFTPUtil.password);
			sftpUtil.upload(SFTPUtil.directory, imag.getPath(), sftp);//上传文件
			sftpUtil.disconnect();
			TImageLog imLog = new TImageLog();
			imLog.imageName = imageName;
			imLog.path = SFTPUtil.directory+File.separator+imag.getName();
			imLog.OS_PATCH = osPatch;
			imLog.DB_PATCH = dbPatch;
			imLog.WEB_PATCH = webPatch;
			imLog.IMPORT_TIME = sdf1.format(new Date());
			imLog.IMPORT_MAN = session.get("username");
			imLog.remark = null2Str(remark).trim();
			imLog.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		try {
//			InputStream is = new FileInputStream(imag);
////			String ctxPath = Play.ctxPath;
//			File applicationFile = Play.applicationPath;
//			String applicationPath = applicationFile.getPath();
////			System.out.println("ctxPath:"+ctxPath);
////			System.out.println("applicationPath:"+applicationPath);
//			String newPath = applicationPath + File.separator +"public"+ File.separator +"userfiles"+ File.separator+  System.currentTimeMillis() +  ".zip";
//			File file = new File(newPath);
//			TImage image = new TImage();
//			image.imageName = imageName;
//			image.OS_PATCH = osPatch;
//			image.DB_PATCH = dbPatch;
//			image.WEB_PATCH = webPatch;
//			image.path = newPath;
//			image.remark = null2Str(remark).trim();
//			image.save();
//			FileUtils.copyInputStreamToFile(is, file);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		renderJSON(flag);
	}
	
	/**
	 * 跳转到镜像修改页面
	 * @param id
	 */
	public static void view(Long id){
		TImage object = TImage.findById(id);
		//数据字典
		List<TDataDic> dicList = TDataDic.findAll();
		List<TDataDic> osPatchList = new ArrayList();
		List<TDataDic> dbPatchList = new ArrayList();
		List<TDataDic> webPatchList = new ArrayList();
		for (TDataDic dic : dicList) {
			if (dic.DIC_TYPE != null && "OSPATCH".equals(dic.DIC_TYPE)) {
				osPatchList.add(dic);
			} else if (dic.DIC_TYPE != null && "DBPATCH".equals(dic.DIC_TYPE)) {
				dbPatchList.add(dic);
			} else if (dic.DIC_TYPE != null && "WEBPATCH".equals(dic.DIC_TYPE)) {
				webPatchList.add(dic);
			}
		}
		render(object,osPatchList, dbPatchList,webPatchList);
	}
	
	/**
	 * 镜像修改：只修改备注信息
	 * @param id
	 */
	public static void editImageInfo(Long id){
		TImage object = TImage.findById(id);
		String os = params.get("os");//操作系统版本
		String middleware = params.get("middleware");//中间件版本
		String dbsoft = params.get("dbsoft");//数据库版本
		String remark = params.get("remark");
		if(os!=null&&!"".equals(os.trim())&&!"no".equals(os.trim())){
			object.OS_PATCH = os;
		}
		if(middleware!=null&&!"".equals(middleware.trim())&&!"no".equals(middleware.trim())){
			object.WEB_PATCH = middleware;
		}
		if(dbsoft!=null&&!"".equals(dbsoft.trim())&&!"no".equals(dbsoft.trim())){
			object.DB_PATCH = dbsoft;
		}
		object.remark = null2Str(remark).trim();
		object.save();
		redirect(request.controller + ".list");
	}
	
	/**
	 * 镜像文件删除，DB记录删除
	 * @param id
	 */
	public static void deleteImage(Long id){
		TImage object = TImage.findById(id);
		String path = object.path;
		File file = null;
		String flag = "";
		try {
			file = new File(path);
			boolean isDelete = FileUtils.deleteQuietly(file);
			if(isDelete){
				object.delete();
				flag = "1";//删除成功
			}else{
				object.delete();
				flag = "2";//信息删除成功，文件删除失败：镜像文件不存在或已移除
			}
		} catch (Exception e) {
			object.delete();
			flag = "0";//信息删除成功，文件删除失败：文件存储路径有问题
		}
		renderJSON(flag);
//		redirect(request.controller + ".list",flag);
//		render("/Images/list.html",flag);
	}
	
	public static void blank(){
		List<TDataDic> dicList = TDataDic.findAll();
		List<TDataDic> osPatchList = new ArrayList();
		List<TDataDic> dbPatchList = new ArrayList();
		List<TDataDic> webPatchList = new ArrayList();
		for(TDataDic dic:dicList){
			if(dic.DIC_TYPE!=null&&"OSPATCH".equals(dic.DIC_TYPE)){
				osPatchList.add(dic);
			}else if(dic.DIC_TYPE!=null&&"DBPATCH".equals(dic.DIC_TYPE)){
				dbPatchList.add(dic);
			}else if(dic.DIC_TYPE!=null&&"WEBPATCH".equals(dic.DIC_TYPE)){
				webPatchList.add(dic);
			}
			
		}
		render(osPatchList,dbPatchList,webPatchList);
	}
	
	//镜像取消
	public static void cancel() {
		String flag = "0";//提交失败
		String id = params.get("id");//主键
		TImage image = TImage.findById(Long.parseLong(id));
		image.IS_REGISTER = "0";
		image.save();
		System.out.println(image.image_id);
		try {
			UnregisterImageContact.modifyServer(image.image_id);
			flag = "1";//提交成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJSON(flag);
	}
	
	//镜像注册
	public static void registration() {
		String flag = "0";//提交失败
		String id = params.get("id");//主键
		TImage image = TImage.findById(Long.parseLong(id));
		image.IS_REGISTER = "1";
		image.save();
		System.out.println(image.image_id);
		try {
			RegisterImageContact.modifyServer(image.image_id, image.imageName);
			flag = "1";//提交成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJSON(flag);
	}
	
	//获取查找新的镜像
	public static void getFind(){
		String flag = "0";//获取失败
		try{
		   //调用方法获取新的镜像
		   NimDiscovery.nimDiscovery();
		   //调用方法查询新的镜像
		   List<TImage> Images = GetImageContact.getImage();
		   //清空镜像表
		   if(Images!=null && Images.size()>0 ){
		       TImage.deleteAll();
		       for(int i=0;i<Images.size();i++){
			       TImage tImag = Images.get(i);
				   /*String query = "";
				   if(tImag != null){
					   String image_id = tImag.image_id;
					   String imageName = tImag.imageName;
					   if(tImag.image_id == null && imageName!=null){
				           query = "select t from TImage t where t.image_id is null or t.imageName = '"+imageName+"'";
					   }else if(image_id != null && imageName == null){
						   query = "select t from TImage t where t.image_id ='"+image_id+"'or t.imageName is null";
					   }else if(image_id == null && imageName == null){
						   query = "select t from TImage t where t.image_id is null or t.imageName is null"; 
					   }else{
						   query = "select t from TImage t where t.image_id = '"+image_id+"' or t.imageName = '"+imageName+"'";
					   }
					}*/
					  // List<TImage> Objes = TImage.find(query).fetch();
				      /*if(Objes!=null && Objes.size()>0){
						   System.out.println("已保存");
					   }else{
						   tImag.save();
						   System.out.println("保存");
					   }*/
				   tImag.save();
		   	  }
		   }
		  flag = "1";
		}catch(Exception e){
			e.printStackTrace();
		}
		renderJSON(flag);
	}
	
	   /**
     * NULL转""
     * @param str
     * @return str
     */
    public static String null2Str(String str){
    	if(str==null){
    		str = "";
    	}
    	return str;
    }
    
    
}
