package com.yd.model;

public class Gateway {
	private String ip;
	private String vendor;
	private String version;
	private String href;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Gateway(String ip, String vendor, String version, String href) {
		this.ip = ip;
		this.vendor = vendor;
		this.version = version;
		this.href = href;
	}
	
}
