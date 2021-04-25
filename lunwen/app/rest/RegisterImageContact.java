package rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MediaType;

import models.rest.createVM.orderParam.OrderParam;
import models.rest.createVM.step2.req.PmrdpVsrParm;
import models.rest.createVM.step2.req.StepTwoReqBody;
import models.rest.createVM.step2.res.Sr;
import models.rest.createVM.step2.res.SrmSrCreateResponseBody;
import models.rest.createVM.step2.res.StepTwoResBody;
import models.rest.createVM.step2.res.TicketSpec;
import models.rest.createVM.step3.req.StepThreeReqBody;
import models.rest.createVM.step3.req.TicAttr;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import play.Logger;
import utils.JsonUtil;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.representation.Form;

public class RegisterImageContact {
	private static final String SRM_CARDCREATE_URL = "http://10.246.147.2/maxrest/rest/os/SRM_CARDCREATE";// 步骤一url
	private static final String SRM_SRCREATE_URL = "http://10.246.147.2/maxrest/rest/os/SRM_SRCREATE";// 步骤二url
	private static final String SRM_SRCREATE363_URL = "http://10.246.147.2/maxrest/rest/os/SRM_SRCREATE";// 步骤三url

	private static DateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'+08:00'");// 2014-10-27T07:11:50-04:30
	private static DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void modifyServer(String id,String name){
		Client c = Client.create();
		c.addFilter(new HTTPBasicAuthFilter("PMRDPCAUSR", "maxadmin"));
		System.setProperty("javax.net.ssl.trustStore", "d:\\jssecacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		System.setProperty("org.jboss.security.ignoreHttpsHost", "true");
		WebResource r = null;

		r = c.resource(SRM_CARDCREATE_URL);
		Logger.info("------------------- 步骤一  -------------------");
		String xmlStr = postFormStep1(r);
		StepTwoReqBody Step2req = jxStepOneGetStepTwoParam(xmlStr);// 解析第一步返回的xml

		r = c.resource(SRM_SRCREATE_URL);
		Logger.info("------------------- 步骤二  -------------------");
		String jsonContent = postFormStep2(r, Step2req);
	
		
		StepThreeReqBody Step3req = jxStepTwoGetStepThreeParam(jsonContent,id,name);

		if (Step3req.getTicketUid() != null) {
			r = c.resource(SRM_SRCREATE363_URL + "/" + Step3req.getTicketUid());
			Logger.info("------------------- 步骤三  -------------------");
			postFormStep3(r, Step3req);
		}
//		MailUtil.getMail(workOrderId,projectName,id);
	}
	
	// 步骤一：创建目录请求
	public static String postFormStep1(WebResource r) {
		Form form = new Form();
		ClientResponse response = r.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		String reStr = response.getEntity(String.class);
		Logger.info("步骤一返回:" + reStr);
		return reStr;
	}

	// 步骤二：创建服务请求
	public static String postFormStep2(WebResource r, StepTwoReqBody step2Req) {
		String reStr = null;
		Form form = new Form();
		if (step2Req != null) {
			form.add("CREATEBY", step2Req.getCREATEBY());
			form.add("REPORTEDBY", step2Req.getREPORTEDBY());
			form.add("CHANGEBY", step2Req.getCHANGEBY());
			form.add("PMSCCRID", step2Req.getPMSCCRID());
			form.add("DESCRIPTION", step2Req.getDESCRIPTION());
			form.add("PMSCITEMNUM", step2Req.getPMSCITEMNUM());
			form.add("CLASSSTRUCTUREID", step2Req.getCLASSSTRUCTUREID());
			form.add("COMMODITYGROUP", step2Req.getCOMMODITYGROUP());
			form.add("COMMODITY", step2Req.getCOMMODITY());
			form.add("TARGETSTART", step2Req.getTARGETSTART());
//			form.add("TARGETFINISH", step2Req.getTARGETFINISH());
			form.add("ITEMNUM", step2Req.getITEMNUM());
			form.add("ITEMSETID", step2Req.getITEMNUM());
			form.add("ORDERUNIT", step2Req.getORDERUNIT());
			form.add("QTY", step2Req.getQTY());
			form.add("PLUSPCUSTOMER", step2Req.getPLUSPCUSTOMER());

			form.add("PMRDPVSRPARM.0.SRCATTRNAME","PMRDP.Net.1.HostNameResolve");
			form.add("PMRDPVSRPARM.0.SRCATTRVAL", "true");
			form.add("PMRDPVSRPARM.0.SRCTOKEN","1" );
			form.add("PMRDPVSRPARM.0.SRCTYPE", "NETWORK");
			
			form.add("PMRDPVSRPARM.1.SRCATTRNAME","PMRDP.Net.1.NetworkType");
			form.add("PMRDPVSRPARM.1.SRCATTRVAL", "Management");
			form.add("PMRDPVSRPARM.1.SRCTOKEN","1" );
			form.add("PMRDPVSRPARM.1.SRCTYPE", "NETWORK");
			
			form.add("PMRDPVSRPARM.2.SRCATTRNAME","PMRDP.Net.1.NetworkUsages");
			form.add("PMRDPVSRPARM.2.SRCATTRVAL", "");
			form.add("PMRDPVSRPARM.2.SRCTOKEN","1" );
			form.add("PMRDPVSRPARM.2.SRCTYPE", "NETWORK");
			
			form.add("_compact", "1");
			form.add("_format", "json");
		}
		ClientResponse response = r.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		reStr = response.getEntity(String.class);
		Logger.info("步骤二返回:" + reStr);
		return reStr;
	}

	// 步骤三：更新凭单
	public static String postFormStep3(WebResource r, StepThreeReqBody reqbody) {
		String reStr = null;
		if (reqbody != null) {
			Form form = new Form();
			form.add("PMSCCRID", reqbody.getPMSCCRID());
			form.add("SOURCE", "TSAMWEBUI");// 此属性这是为TSAMWEBUI将开始处理该请求
			form.add("TICKETID", reqbody.getTICKETID());
			// form.add("CHANGEBY", "MAXADMIN");
			form.add("_format", "json");
			form.add("_compact", "1");
			List<TicAttr> attrList = reqbody.getAttrList();
			if (attrList != null) {
				for (int i = 0; i < attrList.size(); i++) {
					int j = i + 2;
					TicAttr attr = attrList.get(i);
					form.add("TICKETSPEC." + j + ".TICKETSPECID",
							attr.getTICKETSPECID());
					form.add("TICKETSPEC." + j + ".ASSETATTRID",
							attr.getASSETATTRID());
					form.add("TICKETSPEC." + j + ".SECTION", attr.getSECTION());
					form.add("TICKETSPEC." + j + ".REFOBJECTID",
							attr.getREFOBJECTID());

					if (attr.getValType() != null
							&& "ALN".equals(attr.getValType())) {
						form.add("TICKETSPEC." + j + ".ALNVALUE", attr.getVAL());
					} else if (attr.getValType() != null
							&& "TABLE".equals(attr.getValType())) {
						form.add("TICKETSPEC." + j + ".TABLEVALUE",
								attr.getVAL());
					} else if (attr.getValType() != null
							&& "NUMERIC".equals(attr.getValType())) {
						form.add("TICKETSPEC." + j + ".NUMVALUE", attr.getVAL());
					}

				}
			}
			ClientResponse response = r.type(
					MediaType.APPLICATION_FORM_URLENCODED).post(
					ClientResponse.class, form);
			reStr = response.getEntity(String.class);
			Logger.info("步骤三返回:" + reStr);
		}
		return reStr;
	}

	// -------------------------------------------------------------------------------

	// 解析第一步xml数据，得到第二步请求的model
	public static StepTwoReqBody jxStepOneGetStepTwoParam(String xmlStr) {
		StepTwoReqBody reqbody = new StepTwoReqBody();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr);
			Element rootEle = doc.getRootElement();
			System.out.println("根节点：" + rootEle.getName());
			Iterator iter = rootEle.elementIterator("SRM_CARDCREATESet");
			while (iter.hasNext()) {
				Element cardCreateSetEle = (Element) iter.next();
				Iterator pmsccrIter = cardCreateSetEle
						.elementIterator("PMSCCR");
				while (pmsccrIter.hasNext()) {
					Element pmsccrEle = (Element) pmsccrIter.next();

//					String PMSCCRID = pmsccrEle.elementTextTrim("PMSCCRID");// 第二步需要
					String PMSCCRNUM = pmsccrEle.elementTextTrim("PMSCCRNUM");// 第二步需要
					String REQUESTEDBY = pmsccrEle
							.elementTextTrim("REQUESTEDBY");// 第二步需要
					String CHANGEBY = pmsccrEle.elementTextTrim("CHANGEBY");
//					String ORGID = pmsccrEle.elementTextTrim("ORGID");
//					String REQUESTEDFOR = pmsccrEle
//							.elementTextTrim("REQUESTEDFOR");
//					String SITEID = pmsccrEle.elementTextTrim("SITEID");
//					String STATUS = pmsccrEle.elementTextTrim("STATUS");
					//
					reqbody.setCREATEBY(REQUESTEDBY);
					// reqbody.setREPORTEDBY("PMRDPCAUSR");
					reqbody.setREPORTEDBY(REQUESTEDBY);
					reqbody.setCHANGEBY(CHANGEBY);
					reqbody.setPMSCCRID(PMSCCRNUM);// 第一步请求返回内容
													// PMSCCR对象的PMSCCRNUM 属性值
					// reqbody.setDESCRIPTION("Add VMware Servers "+df1.format(new
					// Date()));
					reqbody.setDESCRIPTION("Register POWER LPAR Image");
					reqbody.setPMSCITEMNUM("PMRDP_0223A_72");// PMRDP_0211A_72P/E2E_ADDSERVER
					reqbody.setCLASSSTRUCTUREID("8100244");//8100211
					reqbody.setCOMMODITYGROUP("SRVAUTOM");// 标识提供服务的组
					reqbody.setCOMMODITY("VSERVER");// 提供或购买的服务
					reqbody.setTARGETSTART(df.format(new Date()));
					reqbody.setTARGETFINISH(df.format(new Date()));
					reqbody.setITEMNUM("PMRDP_0223A_72");// PMRDP_0211A_72
					reqbody.setITEMSETID("PMSCS1");//
					reqbody.setORDERUNIT("EACH");//
					reqbody.setQTY("1");//
					reqbody.setPLUSPCUSTOMER("PMRDPCUST");
				}
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return reqbody;
	}

	// 解析第二步json数据，得到第三步请求的model
	public static StepThreeReqBody jxStepTwoGetStepThreeParam(String jsonContent,String id,String name) {
		StepThreeReqBody reqbody = null;
		if (jsonContent != null) {
			StepTwoResBody obj = (StepTwoResBody) JsonUtil.json2obj(
					jsonContent, StepTwoResBody.class);
			if (obj != null) {
				SrmSrCreateResponseBody body = obj
						.getCreateSrmSrCreateResponse();
				Sr sr = body.getSrmSet().getSr();
				String pmsccrid = sr.getPMSCCRID();// 第三步1个参数值
				String ticketUid = sr.getTICKETUID() + "";// 第三步url需要用到
				String ticketId = sr.getTICKETID();// 第三步url需要用到
				reqbody = new StepThreeReqBody();// 第三步请求的model
				reqbody.setTicketUid(ticketUid);
				reqbody.setPMSCCRID(pmsccrid);
				reqbody.setTICKETID(ticketId);

				List<TicAttr> ticList = reqbody.getAttrList();

				List<TicketSpec> ticketSpecList = sr.getTicSpecList();
				TicAttr attr = new TicAttr();

				for (int i = 0; i < ticketSpecList.size(); i++) {
					TicketSpec spec = ticketSpecList.get(i);
					String ticketSpecId = spec.getTICKETSPECID() + "";
					String refObjId = spec.getREFOBJECTID() + "";
					String assetAttrId = spec.getASSETATTRID();
					attr = new TicAttr();
					attr.setTICKETSPECID(ticketSpecId);
					attr.setSECTION("");
					attr.setREFOBJECTID(refObjId);

					if (assetAttrId != null
							&& "PMRDPCLCVSIMG_NAME".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVSIMG_NAME");
						attr.setValType("ALN");
						attr.setVAL(name);
						ticList.add(attr);				
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_RESGRPNUM".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_RESGRPNUM");
						attr.setValType("TABLE");
						attr.setVAL("/cloud/rest/pools/0/");
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVSIMG_IMG_TPMDCMID".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVSIMG_IMG_TPMDCMID");
						attr.setValType("TABLE");
						attr.setVAL(id);
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_NUMBER_CPUS".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_NUMBER_CPUS");
						attr.setValType("NUMERIC");
						attr.setVAL("8");// 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_NUMBER_PCPUS".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_NUMBER_PCPUS");
						attr.setValType("NUMERIC");
						attr.setVAL("80.0"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_MEMORY".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_MEMORY");
						attr.setValType("NUMERIC");
						attr.setVAL("131072"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_STORAGE_SIZE".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_STORAGE_SIZE");
						attr.setValType("NUMERIC");
						attr.setVAL("20"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVSIMG_NUMBER_CPUS".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVSIMG_NUMBER_CPUS");
						attr.setValType("NUMERIC");
						attr.setVAL("1"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVSIMG_NUMBER_PCPUS".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVSIMG_NUMBER_PCPUS");
						attr.setValType("NUMERIC");
						attr.setVAL("10"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVSIMG_MEMORY".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVSIMG_MEMORY");
						attr.setValType("NUMERIC");
						attr.setVAL("256"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVSIMG_STORAGE_SIZE".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVSIMG_STORAGE_SIZE");
						attr.setValType("NUMERIC");
						attr.setVAL("1"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPREGIMG_ROOTPWD".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPREGIMG_ROOTPWD");
						attr.setValType("ALN");
						attr.setVAL("Sinopec8"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPREGIMG_SW_RESOURCE_TYPE".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPREGIMG_SW_RESOURCE_TYPE");
						attr.setValType("ALN");
						attr.setVAL("Installation"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPREGIMG_SW_MODULE_DEV_MOD".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPREGIMG_SW_MODULE_DEV_MOD");
						attr.setValType("ALN");
						attr.setVAL("Cloud AIX Operating System"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPREGIMG_SW_INSTALL_DEV_MOD".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPREGIMG_SW_INSTALL_DEV_MOD");
						attr.setValType("ALN");
						attr.setVAL("Cloud PPC AIX Installation"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPREGIMG_SEARCHKEY".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPREGIMG_SEARCHKEY");
						attr.setValType("ALN");
						attr.setVAL("default"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPREGIMG_USERNAME".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPREGIMG_USERNAME");
						attr.setValType("ALN");
						attr.setVAL("root"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPREGIMG_HV".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPREGIMG_HV");
						attr.setValType("ALN");
						attr.setVAL("LPAR"); 
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDP_SR_WF".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDP_SR_WF");
						attr.setValType("TABLE");
						attr.setVAL("PMRDPIMREG"); 
						ticList.add(attr);
					}
				}

			}
		}
		return reqbody;
	}
	
	public static void main(String args[])
	{
		modifyServer("18346","AIX7102-OSA-OR11204-141031");
	}
}
