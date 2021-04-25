/**
 * IBMCloudServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice;

public interface IBMCloudServiceSoap extends java.rmi.Remote {
    public webservice.T_Workorder getWorkOrders() throws java.rmi.RemoteException;
    public boolean updateWorkOrders(webservice.WorkOrdersEntity workOrdersEntity) throws java.rmi.RemoteException;
}
