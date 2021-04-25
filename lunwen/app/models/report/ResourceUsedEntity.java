package models.report;

import java.io.Serializable;

public class ResourceUsedEntity implements Serializable {
	
	/** 已分配cpu */
	private String cpuUsed;
	/** 已分配内存 */
	private String memUsed;
	/** 已使用存储空间 */
	private String hardUsed;
	/** 项目名称 */
	private String projectName;
	
	private String elementId;
	
	public String getCpuUsed() {
		return cpuUsed;
	}
	public void setCpuUsed(String cpuUsed) {
		this.cpuUsed = cpuUsed;
	}
	public String getMemUsed() {
		return memUsed;
	}
	public void setMemUsed(String memUsed) {
		this.memUsed = memUsed;
	}
	public String getHardUsed() {
		return hardUsed;
	}
	public void setHardUsed(String hardUsed) {
		this.hardUsed = hardUsed;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	
}
