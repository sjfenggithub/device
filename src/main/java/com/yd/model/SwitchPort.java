package com.yd.model;

public class SwitchPort {
	String portName;
	String macAd;
	String receivePac;
	String ContractNum;
	int ofPort;
	String speed;
	String mtu;
	String UUID;
	public SwitchPort(String portName, String macAd, String receivePac,
			String contractNum, int ofPort, String speed, String mtu,
			String UUID) {
		this.portName = portName;
		this.macAd = macAd;
		this.receivePac = receivePac;
		this.ContractNum = contractNum;
		this.ofPort = ofPort;
		this.speed = speed;
		this.mtu = mtu;
		this.UUID = UUID;
	}
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	public String getMacAd() {
		return macAd;
	}
	public void setMacAd(String macAd) {
		this.macAd = macAd;
	}
	public String getReceivePac() {
		return receivePac;
	}
	public void setReceivePac(String receivePac) {
		this.receivePac = receivePac;
	}
	public String getContractNum() {
		return ContractNum;
	}
	public void setContractNum(String contractNum) {
		ContractNum = contractNum;
	}
	public int getOfPort() {
		return ofPort;
	}
	public void setOfPort(int ofPort) {
		this.ofPort = ofPort;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getMtu() {
		return mtu;
	}
	public void setMtu(String mtu) {
		this.mtu = mtu;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String toString(){
		return "port名称："+portName+"MAC地址："+macAd+"收包数："+receivePac+"发包数："+ContractNum+"ofPort号："+ofPort+"速率:"+speed+"MTU:"+mtu+"管理UUID:"+UUID;
	}
	
	
}