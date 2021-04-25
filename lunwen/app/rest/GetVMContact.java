package rest;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MediaType;

import models.TResVM;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class GetVMContact {
	private static final String SRM_CARDCREATE_URL = "http://10.246.147.2/maxrest/rest/os/PMZHBR1_PMRDPSRVVIEW";

	public static List<TResVM> getVM() {
		Client c = Client.create();
		c.addFilter(new HTTPBasicAuthFilter("PMRDPCAUSR", "maxadmin"));
		System.setProperty("javax.net.ssl.trustStore", "d:\\jssecacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		System.setProperty("org.jboss.security.ignoreHttpsHost", "true");
		WebResource r = c.resource(SRM_CARDCREATE_URL);
		String xmlStr = r.accept(MediaType.APPLICATION_XML).get(String.class);
		return getVM(xmlStr);
	}

	public static List<TResVM> getVM(String xmlStr) {
		TResVM imageModel;
		Document doc;
		List<TResVM> imageList = new ArrayList<TResVM>();
		try {
			doc = DocumentHelper.parseText(xmlStr);
			Element rootEle = doc.getRootElement();
			Iterator<?> iter = rootEle
					.elementIterator("PMZHBR1_PMRDPSRVVIEWSet");
			while (iter.hasNext()) {
				Element cardCreateSetEle = (Element) iter.next();
				Iterator<?> pmsccrIter = cardCreateSetEle
						.elementIterator("PMRDPSRVVIEW");
				while (pmsccrIter.hasNext()) {
					imageModel = new TResVM();
					Element pmsccrEle = (Element) pmsccrIter.next();
					imageModel.CPU_NUMBER = Float.valueOf(
							pmsccrEle.elementTextTrim("VSCPU")).intValue();
					imageModel.CREATE_TIME = pmsccrEle
							.elementTextTrim("PROJSTARTTIME");
					imageModel.DATA_VG_SIZE = 0;
					imageModel.NAME = pmsccrEle.elementTextTrim("PARTNAME");
					imageModel.IP = formatIp(imageModel.NAME);
					imageModel.MEMORY_SIZE = (int) (Float.valueOf(pmsccrEle
							.elementTextTrim("VSMEMORY")) / 1024);
					imageModel.PENDING_TIME = pmsccrEle
							.elementTextTrim("PROJENDTIME");
					imageModel.CREATE_TIME = pmsccrEle
							.elementTextTrim("PROJSTARTTIME");
					imageModel.SYS_INFO = pmsccrEle.elementTextTrim("NAME");
					imageModel.DMZ = pmsccrEle.elementTextTrim("VSRESGRPNUM");
					imageModel.VS_ID = pmsccrEle.elementTextTrim("OBJID");
					imageModel.DEVICE_ID = pmsccrEle.elementTextTrim("VSERVERDCMID");
					imageModel.ROOT_VG_SIZE = 100;
					imageModel.WORK_ORDER_ID = 0;
					imageModel.UPDATE_TIME = "2014-12-25T01:17:10+08:00";
					imageList.add(imageModel);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return imageList;
	}

	public static String formatIp(String name) {
		String ip = name.substring(name.length() - 3, name.length());
		if (ip.startsWith("0"))
			ip = ip.substring(1);
		ip = "10.246.149." + ip;
		return ip;
	}
}
