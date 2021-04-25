package utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import models.TConfigUser;
import play.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class AdUtil {
	public static void main(String[] args) {
		String user = "SINOPEC\\YT-Cloud-Test";
		String pwd = "YT-Cloud-Test";
		String[] ips = { "10.246.144.11", "10.246.144.12", "10.246.144.13" };
		for (String ip : ips) {
			if (adCheck(ip, user, pwd)) {
				System.out.println(GetADInfo(ip));
				break;
			}
		}

	}

	/* 用户名格式为domain\\user或者user@domain.com */
	public static boolean adCheck(String host, String user, String password) {
		String port = "389"; // 端口
		String url = new String("ldap://" + host + ":" + port);
		Hashtable<String, String> env = new Hashtable<String, String>();
		DirContext ctx;
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, user);
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, url);
		try {
			ctx = new InitialDirContext(env);
			ctx.close();
			// System.out.println("验证成功！");
			return true;
		} catch (NamingException err) {
			err.printStackTrace();
			// System.out.println("验证失败！");
			return false;
		}

	}

	public static JsonArray GetADInfo(String host) {
		JsonArray array = new JsonArray();

		// String host = "10.246.144.11"; // AD服务器

		String port = "389"; // 端口

		String url = new String("ldap://" + host + ":" + port);

		Hashtable HashEnv = new Hashtable();

		// String adminName ="CN=oyxiaoyuanxy,CN=Users,DC=Hebmc,DC=com";//AD的用户名

		String adminName = "SINOPEC\\YT-Cloud-Test"; // 注意用户名的写法：domain\User

		String adminPassword = "YT-Cloud-Test"; // 密码

		HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别

		HashEnv.put(Context.SECURITY_PRINCIPAL, adminName); // AD User

		HashEnv.put(Context.SECURITY_CREDENTIALS, adminPassword); // AD Password

		HashEnv.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类

		HashEnv.put(Context.PROVIDER_URL, url);

		try {
			Gson gson = new Gson();

			LdapContext ctx = new InitialLdapContext(HashEnv, null);

			String searchBase = "OU=运维支持事业部,OU=SHYK,OU=组织机构,DC=sinopec,DC=ad";

			String searchFilter = "objectClass=User";

			SearchControls searchCtls = new SearchControls(); // Create the

			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Specify

			NamingEnumeration answer = ctx.search(searchBase, searchFilter,
					searchCtls);// Search for objects using the filter

			TConfigUser jsonModel;
			while (answer.hasMoreElements()) {// 遍历结果集
				Object obj = answer.nextElement();
				if (obj instanceof SearchResult) {
					SearchResult si = (SearchResult) obj;
					Attributes attrs = si.getAttributes();
					if (attrs != null) {
						jsonModel = new TConfigUser();
						for (NamingEnumeration ae = attrs.getAll(); ae
								.hasMoreElements();) {// 获得该节点的所有属性
							Attribute attr = (Attribute) ae.next();// 下一属性
							String attrId = attr.getID();
							if (attrId.trim().equals("cn")) {
								Object o = attr.getAll().nextElement();// 下一属性值
								jsonModel.USER_NAME = o.toString();
							} else if (attrId.trim().equals("displayName")) {
								Object o = attr.getAll().nextElement();// 下一属性值
								jsonModel.DISPLAY_NAME = o.toString();
							} else if (attrId.trim().equals("department")) {
								Object o = attr.getAll().nextElement();// 下一属性值
								jsonModel.DEPARTMENT = o.toString();
							} else if (attrId.trim().equals("mail")) {
								Object o = attr.getAll().nextElement();// 下一属性值
								jsonModel.EMAIL = o.toString();
							}				
							// else if (attrId.contains("cn")){
							// Object o = vals.nextElement();// 下一属性值
							// jsonModel.TELPHONE = (String)o;
							// }
						}
						array.add(gson.toJsonTree(jsonModel));
					}
				}
			}// while

			ctx.close();
			

		} catch (NamingException e) {

			e.printStackTrace();

			System.err.println("Throw Exception : " + e);

		}
		return array;
	}
	
	
	/**
	 * wzj 2014-11-14 15:58
	 * @param host
	 * @return
	 */
	public static List<TConfigUser> GetADInfoList(String host) {
		List<TConfigUser> userList = new ArrayList<TConfigUser>();

		String port = "389"; // 端口
		String url = new String("ldap://" + host + ":" + port);
		Hashtable HashEnv = new Hashtable();

		String adminName = "SINOPEC\\YT-Cloud-Test"; // 注意用户名的写法：domain\User
		String adminPassword = "YT-Cloud-Test"; // 密码

		HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别
		HashEnv.put(Context.SECURITY_PRINCIPAL, adminName); // AD User
		HashEnv.put(Context.SECURITY_CREDENTIALS, adminPassword); // AD Password
		HashEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
		HashEnv.put(Context.PROVIDER_URL, url);
		try {
			LdapContext ctx = new InitialLdapContext(HashEnv, null);
			String searchBase = "OU=运维支持事业部,OU=SHYK,OU=组织机构,DC=sinopec,DC=ad";
			String searchFilter = "objectClass=User";
			SearchControls searchCtls = new SearchControls(); // Create the
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Specify
			NamingEnumeration answer = ctx.search(searchBase, searchFilter,searchCtls);// Search for objects using the filter

			TConfigUser jsonModel;
			while (answer.hasMoreElements()) {// 遍历结果集
				Object obj = answer.nextElement();
				if (obj instanceof SearchResult) {
					SearchResult si = (SearchResult) obj;
					Attributes attrs = si.getAttributes();
					if (attrs != null) {
						jsonModel = new TConfigUser();
						for (NamingEnumeration ae = attrs.getAll(); ae
								.hasMoreElements();) {// 获得该节点的所有属性
							Attribute attr = (Attribute) ae.next();// 下一属性
							String attrId = attr.getID();
							if (attrId.trim().equals("cn")) {
								Object o = attr.getAll().nextElement();// 下一属性值
								jsonModel.USER_NAME = o.toString();
							} else if (attrId.trim().equals("displayName")) {
								Object o = attr.getAll().nextElement();// 下一属性值
								jsonModel.DISPLAY_NAME = o.toString();
							} else if (attrId.trim().equals("department")) {
								Object o = attr.getAll().nextElement();// 下一属性值
								jsonModel.DEPARTMENT = o.toString();
							} else if (attrId.trim().equals("mail")) {
								Object o = attr.getAll().nextElement();// 下一属性值
								jsonModel.EMAIL = o.toString();
							}				
							// else if (attrId.contains("cn")){
							// Object o = vals.nextElement();// 下一属性值
							// jsonModel.TELPHONE = (String)o;
							// }
						}
						userList.add(jsonModel);
					}
				}
			}
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
			Logger.error("Throw Exception : " + e);
		}
		return userList;
	}
	
}
