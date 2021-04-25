/**
 * WorkOrdersEntity.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice;

public class WorkOrdersEntity  implements java.io.Serializable {
    private int WId;

    private int RId;

    private java.lang.String performPerson;

    private java.lang.String respondTime;

    private java.lang.String completeTime;

    private java.lang.String ribbieInfo;

    private java.lang.String memo;

    private java.lang.String status;

    private java.lang.String siteUrl;

    public WorkOrdersEntity() {
    }

    public WorkOrdersEntity(
           int WId,
           int RId,
           java.lang.String performPerson,
           java.lang.String respondTime,
           java.lang.String completeTime,
           java.lang.String ribbieInfo,
           java.lang.String memo,
           java.lang.String status,
           java.lang.String siteUrl) {
           this.WId = WId;
           this.RId = RId;
           this.performPerson = performPerson;
           this.respondTime = respondTime;
           this.completeTime = completeTime;
           this.ribbieInfo = ribbieInfo;
           this.memo = memo;
           this.status = status;
           this.siteUrl = siteUrl;
    }


    /**
     * Gets the WId value for this WorkOrdersEntity.
     * 
     * @return WId
     */
    public int getWId() {
        return WId;
    }


    /**
     * Sets the WId value for this WorkOrdersEntity.
     * 
     * @param WId
     */
    public void setWId(int WId) {
        this.WId = WId;
    }


    /**
     * Gets the RId value for this WorkOrdersEntity.
     * 
     * @return RId
     */
    public int getRId() {
        return RId;
    }


    /**
     * Sets the RId value for this WorkOrdersEntity.
     * 
     * @param RId
     */
    public void setRId(int RId) {
        this.RId = RId;
    }


    /**
     * Gets the performPerson value for this WorkOrdersEntity.
     * 
     * @return performPerson
     */
    public java.lang.String getPerformPerson() {
        return performPerson;
    }


    /**
     * Sets the performPerson value for this WorkOrdersEntity.
     * 
     * @param performPerson
     */
    public void setPerformPerson(java.lang.String performPerson) {
        this.performPerson = performPerson;
    }


    /**
     * Gets the respondTime value for this WorkOrdersEntity.
     * 
     * @return respondTime
     */
    public java.lang.String getRespondTime() {
        return respondTime;
    }


    /**
     * Sets the respondTime value for this WorkOrdersEntity.
     * 
     * @param respondTime
     */
    public void setRespondTime(java.lang.String respondTime) {
        this.respondTime = respondTime;
    }


    /**
     * Gets the completeTime value for this WorkOrdersEntity.
     * 
     * @return completeTime
     */
    public java.lang.String getCompleteTime() {
        return completeTime;
    }


    /**
     * Sets the completeTime value for this WorkOrdersEntity.
     * 
     * @param completeTime
     */
    public void setCompleteTime(java.lang.String completeTime) {
        this.completeTime = completeTime;
    }


    /**
     * Gets the ribbieInfo value for this WorkOrdersEntity.
     * 
     * @return ribbieInfo
     */
    public java.lang.String getRibbieInfo() {
        return ribbieInfo;
    }


    /**
     * Sets the ribbieInfo value for this WorkOrdersEntity.
     * 
     * @param ribbieInfo
     */
    public void setRibbieInfo(java.lang.String ribbieInfo) {
        this.ribbieInfo = ribbieInfo;
    }


    /**
     * Gets the memo value for this WorkOrdersEntity.
     * 
     * @return memo
     */
    public java.lang.String getMemo() {
        return memo;
    }


    /**
     * Sets the memo value for this WorkOrdersEntity.
     * 
     * @param memo
     */
    public void setMemo(java.lang.String memo) {
        this.memo = memo;
    }


    /**
     * Gets the status value for this WorkOrdersEntity.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this WorkOrdersEntity.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the siteUrl value for this WorkOrdersEntity.
     * 
     * @return siteUrl
     */
    public java.lang.String getSiteUrl() {
        return siteUrl;
    }


    /**
     * Sets the siteUrl value for this WorkOrdersEntity.
     * 
     * @param siteUrl
     */
    public void setSiteUrl(java.lang.String siteUrl) {
        this.siteUrl = siteUrl;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WorkOrdersEntity)) return false;
        WorkOrdersEntity other = (WorkOrdersEntity) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.WId == other.getWId() &&
            this.RId == other.getRId() &&
            ((this.performPerson==null && other.getPerformPerson()==null) || 
             (this.performPerson!=null &&
              this.performPerson.equals(other.getPerformPerson()))) &&
            ((this.respondTime==null && other.getRespondTime()==null) || 
             (this.respondTime!=null &&
              this.respondTime.equals(other.getRespondTime()))) &&
            ((this.completeTime==null && other.getCompleteTime()==null) || 
             (this.completeTime!=null &&
              this.completeTime.equals(other.getCompleteTime()))) &&
            ((this.ribbieInfo==null && other.getRibbieInfo()==null) || 
             (this.ribbieInfo!=null &&
              this.ribbieInfo.equals(other.getRibbieInfo()))) &&
            ((this.memo==null && other.getMemo()==null) || 
             (this.memo!=null &&
              this.memo.equals(other.getMemo()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.siteUrl==null && other.getSiteUrl()==null) || 
             (this.siteUrl!=null &&
              this.siteUrl.equals(other.getSiteUrl())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getWId();
        _hashCode += getRId();
        if (getPerformPerson() != null) {
            _hashCode += getPerformPerson().hashCode();
        }
        if (getRespondTime() != null) {
            _hashCode += getRespondTime().hashCode();
        }
        if (getCompleteTime() != null) {
            _hashCode += getCompleteTime().hashCode();
        }
        if (getRibbieInfo() != null) {
            _hashCode += getRibbieInfo().hashCode();
        }
        if (getMemo() != null) {
            _hashCode += getMemo().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSiteUrl() != null) {
            _hashCode += getSiteUrl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WorkOrdersEntity.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "WorkOrdersEntity"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("WId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "WId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("performPerson");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PerformPerson"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("respondTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RespondTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("completeTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CompleteTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ribbieInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RibbieInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("memo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Memo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siteUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SiteUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
