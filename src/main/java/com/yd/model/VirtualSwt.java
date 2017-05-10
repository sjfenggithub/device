package com.yd.model;

import java.util.List;

public class VirtualSwt {
	List<Local> local;
	List<Remote> remote;
	public List<Local> getLocal() {
		return local;
	}
	public void setLocal(List<Local> local) {
		this.local = local;
	}
	public List<Remote> getRemote() {
		return remote;
	}
	public void setRemote(List<Remote> remote) {
		this.remote = remote;
	}
	public VirtualSwt(List<Local> local, List<Remote> remote) {
		
		this.local = local;
		this.remote = remote;
	}
	
}
