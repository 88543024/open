package models.rest.createVM.orderParam;

public class OrderParam {
	private String memory;
	private String os;
	private String dbsoft;
	private String middleware;
	private String disksize;
	private String vcpu;
	private String projectId;
	private String projectName;
	private String imageId;
	private String serverNum;
	
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getDbsoft() {
		return dbsoft;
	}
	public void setDbsoft(String dbsoft) {
		this.dbsoft = dbsoft;
	}
	public String getMiddleware() {
		return middleware;
	}
	public void setMiddleware(String middleware) {
		this.middleware = middleware;
	}
	public String getDisksize() {
		return disksize;
	}
	public void setDisksize(String disksize) {
		this.disksize = disksize;
	}
	public String getVcpu() {
		return vcpu;
	}
	public void setVcpu(String vcpu) {
		this.vcpu = vcpu;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getServerNum() {
		return serverNum;
	}
	public void setServerNum(String serverNum) {
		this.serverNum = serverNum;
	}
	
}
