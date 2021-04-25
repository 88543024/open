package soap;

import java.rmi.RemoteException;

import javax.xml.rpc.JAXRPCException;

import com.ibm.tivoli.soap.proxy.TpmLiteSoapServiceProxy;

public class NimDiscovery {

	/**
	 * @Title: main
	 * @Description: TODO
	 * @param @param args
	 * @return void
	 * @throws
	 * @since CloudWei v1.0.0
	 */
	public static void nimDiscovery(String host, String user, String pwd) {
		if (host == null || host.length() == 0)
			host = "10.246.147.2:8777";
		if (user == null || user.length() == 0)
			user = "maxadmin";
		if (pwd == null || pwd.length() == 0)
			pwd = "Passw0rd";
		TpmLiteSoapServiceProxy proxy = new TpmLiteSoapServiceProxy(host, user,
				pwd);
		String workflowName = "Discovery.Discover";
		String requestParameters = "DiscoveryID=9401";
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
	
	public static void nimDiscovery() {
		String	host = "10.246.147.2:8777";	
		String	user = "maxadmin";
		String	pwd = "Passw0rd";
		TpmLiteSoapServiceProxy proxy = new TpmLiteSoapServiceProxy(host, user,
				pwd);
		String workflowName = "Discovery.Discover";
		String requestParameters = "DiscoveryID=9401";
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

}
