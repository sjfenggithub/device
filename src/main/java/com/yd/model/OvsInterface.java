package com.yd.model;

public class OvsInterface {
	private String switchIp;
	private String interfaceName;
	private String neutronPortId;
	private String tenantId;
	private String portIp;
	private String ovsMgtIp;
	private String interfacaMac;
	private int ofPort;
	private String rx_packets;
	private String tx_packets;
	private String speed;
	
	public OvsInterface(String switchIp, String interfaceName,
			String neutronPortId, String tenantId, String portIp,
			String ovsMgtIp, String interfacaMac, int ofPort,
			String rx_packets, String tx_packets, String speed) {
		this.switchIp = switchIp;
		this.interfaceName = interfaceName;
		this.neutronPortId = neutronPortId;
		this.tenantId = tenantId;
		this.portIp = portIp;
		this.ovsMgtIp = ovsMgtIp;
		this.interfacaMac = interfacaMac;
		this.ofPort = ofPort;
		this.rx_packets = rx_packets;
		this.tx_packets = tx_packets;
		this.speed = speed;
	}
	public String getSwitchIp() {
		return switchIp;
	}
	public void setSwitchIp(String switchIp) {
		this.switchIp = switchIp;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getNeutronPortId() {
		return neutronPortId;
	}
	public void setNeutronPortId(String neutronPortId) {
		this.neutronPortId = neutronPortId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getPortIp() {
		return portIp;
	}
	public void setPortIp(String portIp) {
		this.portIp = portIp;
	}
	public String getOvsMgtIp() {
		return ovsMgtIp;
	}
	public void setOvsMgtIp(String ovsMgtIp) {
		this.ovsMgtIp = ovsMgtIp;
	}
	public String getInterfacaMac() {
		return interfacaMac;
	}
	public void setInterfacaMac(String interfacaMac) {
		this.interfacaMac = interfacaMac;
	}
	public int getOfPort() {
		return ofPort;
	}
	public void setOfPort(int ofPort) {
		this.ofPort = ofPort;
	}
	public String getRx_packets() {
		return rx_packets;
	}
	public void setRx_packets(String rx_packets) {
		this.rx_packets = rx_packets;
	}
	public String getTx_packets() {
		return tx_packets;
	}
	public void setTx_packets(String tx_packets) {
		this.tx_packets = tx_packets;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	@Override
	public String toString() {
		return "OvsInterface [switchIp=" + switchIp + ", interfaceName="
				+ interfaceName + ", neutronPortId=" + neutronPortId
				+ ", tenantId=" + tenantId + ", portIp=" + portIp
				+ ", ovsMgtIp=" + ovsMgtIp + ", interfacaMac=" + interfacaMac
				+ ", ofPort=" + ofPort + ", rx_packets=" + rx_packets
				+ ", tx_packets=" + tx_packets + ", speed=" + speed + "]";
	}
}
