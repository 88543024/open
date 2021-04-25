package rest;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MediaType;

import models.TImage;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class GetImageContact {
	private static final String SRM_CARDCREATE_URL = "http://10.246.147.2/maxrest/rest/os/TPV02IMGLIBENTRYMSTR";

	public static List<TImage> getImage(){
		Client c = Client.create();
		c.addFilter(new HTTPBasicAuthFilter("PMRDPCAUSR", "maxadmin"));
		System.setProperty("javax.net.ssl.trustStore", "d:\\jssecacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		System.setProperty("org.jboss.security.ignoreHttpsHost", "true");
		WebResource r = c.resource(SRM_CARDCREATE_URL);
		String xmlStr = r.accept(MediaType.APPLICATION_XML).get(String.class);
		System.out.println(xmlStr);
		return getImage(xmlStr);
	}
	
	public static List<TImage> getImage(String xmlStr) {
		TImage imageModel;
		Document doc;
		List<TImage> imageList = new ArrayList<TImage>();
		try {
			doc = DocumentHelper.parseText(xmlStr);
			Element rootEle = doc.getRootElement();
			Iterator<?> iter = rootEle.elementIterator("TPV02IMGLIBENTRYMSTRSet");
			while (iter.hasNext()) {
				Element cardCreateSetEle = (Element) iter.next();
				Iterator<?> pmsccrIter = cardCreateSetEle
						.elementIterator("TPILMASTERIMAGEENTRY");
				while (pmsccrIter.hasNext()) {
					imageModel = new TImage();
					Element pmsccrEle = (Element) pmsccrIter.next();
					imageModel.imageName = pmsccrEle.elementTextTrim("NAME");
					imageModel.image_id = pmsccrEle
							.elementTextTrim("SOFTWARE_STACK_ID");
					String flag = pmsccrEle.elementTextTrim("POOL_IDENTIFIER");
					if (imageModel.image_id == null
							|| imageModel.image_id.length() <= 0
							|| flag == null || flag.length() <= 0)
						imageModel.IS_REGISTER = "0";
					else
						imageModel.IS_REGISTER = "1";
					imageModel.DB_PATCH = null;
					imageModel.OS_PATCH = null;
					imageModel.WEB_PATCH = null;
					imageModel.path = null;
					imageList.add(imageModel);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return imageList;
	}
}
