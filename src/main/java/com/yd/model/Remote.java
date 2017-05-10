package com.yd.model;

public class Remote{
	private Object ip;
	private Object port;
	public Object getIp() {
		return ip;
	}
	public void setIp(Object ip) {
		this.ip = ip;
	}
	public Object getPort() {
		return port;
	}
	public void setPort(Object port) {
		this.port = port;
	}
	public Remote(Object ip, Object port) {
		
		this.ip = ip;
		this.port = port;
	}
	
}
