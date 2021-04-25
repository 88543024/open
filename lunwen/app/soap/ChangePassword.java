package soap;

import java.rmi.RemoteException;

import javax.xml.rpc.JAXRPCException;

import com.ibm.tivoli.soap.proxy.TpmLiteSoapServiceProxy;

public class ChangePassword {


	/**
	 * @Title: changePassword
	 * @Description: 更改密码
	 * @param @param host - tsam ip地址
	 * @param @param user - tsam user 
	 * @param @param pwd  - tsam pwd
	 * @param @param id   - TResVM.DEVICE_ID
	 * @return void
	 * @throws
	 * @since CloudWei v1.0.0
	*/
	public static void changePassword(String host, String user, String pwd, String id) {
		if (host == null || host.length() == 0)
			host = "10.246.147.2:8777";
		if (user == null || user.length() == 0)
			user = "maxadmin";
		if (pwd == null || pwd.length() == 0)
			pwd = "Passw0rd";
		TpmLiteSoapServiceProxy proxy = new TpmLiteSoapServiceProxy(host, user,
				pwd);
		String workflowName = "Cloud_Server_ResetAdminPassword";
		String requestParameters = "NewPassword=Passw0rd,DeviceID="+id;
		try {
			proxy.executeDeploymentRequest(workflowName, requestParameters);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXRPCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public static void main(String args[])
//	{
//		String	host = "10.246.147.2:8777";	
//		String	user = "maxadmin";
//		String	pwd = "Passw0rd";
//		String id = "21094";
//		changePassword(host, user, pwd,id);
//	}
	
	/**
	 * @Title: changePassword
	 * @Description: 更改密码
	 * @param @param id  - TResVM.DEVICE_ID
	 * @return void
	 * @throws
	 * @since CloudWei v1.0.0
	*/
	public static void changePassword(String id) {
		String	host = "10.246.147.2:8777";	
		String	user = "maxadmin";
		String	pwd = "Passw0rd";
		changePassword(host, user, pwd,id);
	}

}
