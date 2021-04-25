package webservice;

public class IBMCloudServiceSoapProxy implements webservice.IBMCloudServiceSoap {
  private String _endpoint = null;
  private webservice.IBMCloudServiceSoap iBMCloudServiceSoap = null;
  
  public IBMCloudServiceSoapProxy() {
    _initIBMCloudServiceSoapProxy();
  }
  
  public IBMCloudServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initIBMCloudServiceSoapProxy();
  }
  
  private void _initIBMCloudServiceSoapProxy() {
    try {
      iBMCloudServiceSoap = (new webservice.IBMCloudServiceLocator()).getIBMCloudServiceSoap();
      if (iBMCloudServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iBMCloudServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iBMCloudServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iBMCloudServiceSoap != null)
      ((javax.xml.rpc.Stub)iBMCloudServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public webservice.IBMCloudServiceSoap getIBMCloudServiceSoap() {
    if (iBMCloudServiceSoap == null)
      _initIBMCloudServiceSoapProxy();
    return iBMCloudServiceSoap;
  }
  
  public webservice.T_Workorder getWorkOrders() throws java.rmi.RemoteException{
    if (iBMCloudServiceSoap == null)
      _initIBMCloudServiceSoapProxy();
    return iBMCloudServiceSoap.getWorkOrders();
  }
  
  public boolean updateWorkOrders(webservice.WorkOrdersEntity workOrdersEntity) throws java.rmi.RemoteException{
    if (iBMCloudServiceSoap == null)
      _initIBMCloudServiceSoapProxy();
    return iBMCloudServiceSoap.updateWorkOrders(workOrdersEntity);
  }
  
  
}