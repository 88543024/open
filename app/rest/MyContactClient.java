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
import utils.MailUtil;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.representation.Form;

public class MyContactClient {
	private static final String SRM_CARDCREATE_URL = "http://10.246.147.2/maxrest/rest/os/SRM_CARDCREATE";// 步骤一url
	private static final String SRM_SRCREATE_URL = "http://10.246.147.2/maxrest/rest/os/SRM_SRCREATE";// 步骤二url
	private static final String SRM_SRCREATE363_URL = "http://10.246.147.2/maxrest/rest/os/SRM_SRCREATE";// 步骤三url
	private static String SEGMENTVAL;
	private static DateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'+08:00'");// 2014-10-27T07:11:50-04:30
	private static DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void createProject(String memory,String os,String dbsoft,String middleware,String disksize,String vcpu,Integer workOrderId,String projectName,String imageId,String serverNum,Long id,String segment){
		Client c = Client.create();
		c.addFilter(new HTTPBasicAuthFilter("PMRDPCAUSR", "maxadmin"));
//		c.addFilter(new HTTPBasicAuthFilter("maxadmin", "Passw0rd"));
		System.setProperty("javax.net.ssl.trustStore", "d:\\jssecacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		System.setProperty("org.jboss.security.ignoreHttpsHost", "true");
		WebResource r = null;
		SEGMENTVAL = segment;
		r = c.resource(SRM_CARDCREATE_URL);
		Logger.info("------------------- 步骤一  -------------------");
		String xmlStr = postFormStep1(r);
		StepTwoReqBody Step2req = jxStepOneGetStepTwoParam(xmlStr);// 解析第一步返回的xml

		r = c.resource(SRM_SRCREATE_URL);
		Logger.info("------------------- 步骤二  -------------------");
		String jsonContent = postFormStep2(r, Step2req);
		
		OrderParam op = new OrderParam();
		op.setMemory(1024*(Integer.valueOf(memory))+"");
		op.setOs(os);
		op.setDbsoft(dbsoft);
		op.setMiddleware(middleware);
		op.setVcpu(vcpu);
//		op.setProjectId(projectId);
		op.setProjectName(projectName);
		op.setServerNum(serverNum);
		op.setDisksize(disksize.split("G")[0]);
		op.setImageId(imageId);
		StepThreeReqBody Step3req = jxStepTwoGetStepThreeParam(jsonContent,op);

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

			form.add("PMRDPVSRPARM.0.SRCATTRNAME",
					step2Req.getParamList().get(0).getSRCATTRNAME());
			form.add("PMRDPVSRPARM.0.SRCATTRVAL", step2Req.getParamList()
					.get(0).getSRCATTRVAL());
			form.add("PMRDPVSRPARM.0.SRCTOKEN", step2Req.getParamList().get(0)
					.getSRCTOKEN());
			form.add("PMRDPVSRPARM.0.SRCTYPE", step2Req.getParamList().get(0)
					.getSRCTYPE());
			
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
					
					reqbody.setCREATEBY(REQUESTEDBY);
					reqbody.setREPORTEDBY(REQUESTEDBY);
					reqbody.setCHANGEBY(CHANGEBY);
					reqbody.setPMSCCRID(PMSCCRNUM);// 第一步请求返回内容
													// PMSCCR对象的PMSCCRNUM 属性值
					reqbody.setDESCRIPTION("Create project with POWER LPAR Servers ");
					reqbody.setPMSCITEMNUM("PMRDP_0202A_72");// PMRDP_0211A_72P/E2E_ADDSERVER
					reqbody.setCLASSSTRUCTUREID("8100211");
					reqbody.setCOMMODITYGROUP("SRVAUTOM");// 标识提供服务的组
					reqbody.setCOMMODITY("VSERVER");// 提供或购买的服务
					reqbody.setTARGETSTART(df.format(new Date()));
					reqbody.setTARGETFINISH(df.format(new Date()));
					reqbody.setITEMNUM("PMRDP_0202A_72");// PMRDP_0211A_72
					reqbody.setITEMSETID("PMSCS2");//
					reqbody.setORDERUNIT("EACH");//
					reqbody.setQTY("1");//
					reqbody.setPLUSPCUSTOMER("PMRDPCUST");//

					List<PmrdpVsrParm> paramList = new ArrayList<PmrdpVsrParm>();
					PmrdpVsrParm param = new PmrdpVsrParm();
					param.setSRCATTRNAME("PMRDP.Net.Segment_1");
//					param.setSRCATTRVAL("Management Segment");
					if(SEGMENTVAL!=null && SEGMENTVAL != ""){
						 param.setSRCATTRVAL(SEGMENTVAL);
					 }
					// param.setSRCNAME("Jdk_Install_Linux");
					param.setSRCTOKEN("1");
					param.setSRCTYPE("NETWORK");
					paramList.add(param);
					reqbody.setParamList(paramList);
				}
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return reqbody;
	}

	// 解析第二步json数据，得到第三步请求的model
	public static StepThreeReqBody jxStepTwoGetStepThreeParam(String jsonContent,OrderParam op) {
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
							&& "PMRDPCLCPR_PROJECTNAME".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCPR_PROJECTNAME");
						attr.setValType("TABLE");
//						attr.setVAL("Ragnarok_Loki");// 项目名称
						attr.setVAL(op.getProjectName());
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCPR_SERVERQTY".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCPR_SERVERQTY");
						attr.setValType("NUMERIC");
//						attr.setVAL("1");//供应的服务器数量  TODO
						attr.setVAL(op.getServerNum());
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCPR_SERVICEDEFINITIONNUM"
									.equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCPR_SERVICEDEFINITIONNUM");
						attr.setValType("TABLE");
						attr.setVAL("RDPVS");// RDPVS
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCPR_SERVICEDEFINITIONREVISION"
									.equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCPR_SERVICEDEFINITIONREVISION");
						attr.setValType("TABLE");
						attr.setVAL("8");
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_MEMORY".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_MEMORY");
						attr.setValType("NUMERIC");
//						attr.setVAL("4096");// 内存容量,单位MB  TODO
						attr.setVAL(op.getMemory());
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_NUMBER_CPUS".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_NUMBER_CPUS");
						attr.setValType("NUMERIC");
//						attr.setVAL("1");// 虚拟CPU TODO
						attr.setVAL(op.getVcpu());
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_NUMBER_PCPUS".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_NUMBER_PCPUS");
						attr.setValType("NUMERIC");
						attr.setVAL("10");// 物理CPU
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_RESGRPNUM".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_RESGRPNUM");
						attr.setValType("TABLE");
						attr.setVAL("/cloud/rest/pools/0/");// 用于预留资源的资源组
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_STORAGE_SIZE".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_STORAGE_SIZE");
						attr.setValType("NUMERIC");
//						attr.setVAL("100");// 硬盘容量  TODO
						attr.setVAL(op.getDisksize());
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_SWAP_SIZE".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_SWAP_SIZE");
						attr.setValType("NUMERIC");
						attr.setVAL("0");
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCPR_STORAGEQTY".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCPR_STORAGEQTY");
						attr.setValType("NUMERIC");
						attr.setVAL("0");
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_SWINST".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_SWINST");
						attr.setValType("ALN");
						attr.setVAL("0");//
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCVS_TYPE".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCVS_TYPE");
						attr.setValType("ALN");
						attr.setVAL("LPAR");// VMware
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLSWS_IMAGEID".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLSWS_IMAGEID");
						attr.setValType("TABLE");
						attr.setVAL(op.getImageId());// 镜像11839/12638/12898
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLSWS_MONITORING".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLSWS_MONITORING");
						attr.setValType("ALN");
						attr.setVAL("off");// 是否开启监控代理程序
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLVSRV_DELETE_MPNUM".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLVSRV_DELETE_MPNUM");
						attr.setValType("TABLE");
						attr.setVAL("CANPROJECT");// DELSERVER
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLVSRV_MPNUM".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLVSRV_MPNUM");
						attr.setValType("TABLE");
						attr.setVAL("NEWPROJECT");// ADDSERVER/Create Project
													// with POWER
						// LPAR Servers
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLVSRV_NOTIFY_MPNUM".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLVSRV_NOTIFY_MPNUM");
						attr.setValType("TABLE");
						attr.setVAL("NOTIFYPENDINGDELETEPR");// NITIFYPENDINGDELETE/NOTIFYPENDINGDELETEPR
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCPR_PROJECTID".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCPR_PROJECTID");
						attr.setValType("ALN");
//						attr.setVAL("/cloud/rest/projects/1103/");// 项目ID/cloud/rest/projects/1071/
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCPR_APPLID".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCPR_APPLID");
						attr.setValType("ALN");
						attr.setVAL("12738");
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCPR_PROJECTACCOUNT".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCPR_PROJECTACCOUNT");
						attr.setValType("ALN");
						attr.setVAL("PMRDPCUST                       TEAM33");
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCPR_SAVE_IMAGES_ON_EXIT"
									.equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCPR_SAVE_IMAGES_ON_EXIT");
						attr.setValType("ALN");
						attr.setVAL("false");
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCPR_KEEP_EXISTING_IMAGES_ON_EXIT"
									.equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCPR_KEEP_EXISTING_IMAGES_ON_EXIT");
						attr.setValType("ALN");
						attr.setVAL("false");
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCPR_SERVICEINSTANCEID"
									.equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCPR_SERVICEINSTANCEID");
						attr.setValType("TABLE");
						attr.setVAL("118");
						ticList.add(attr);
					}else if (assetAttrId != null
							&& "PMRDPCLCPR_PERSONGROUP".equals(assetAttrId)) {
						attr.setASSETATTRID("PMRDPCLCPR_PERSONGROUP");
						attr.setValType("TABLE");
						attr.setVAL("TEAM33");
						ticList.add(attr);
					}

				}

			}
		}
		return reqbody;
	}

}
