package com.yd.model;

import java.util.List;

public class OvsNode {
	private String switchIp;
	private String dpid;
	private List<String> neutronPortId;
	private List<String> tenantIdPortIp;
	private String ovsIp;
	private String bridgeName;
	private String ofSwitchMAC;
	private String ofSwitchdpid;
	private int vmCount;
	private int tunnelCount;

	public OvsNode(String switchIp, String dpid, List<String> neutronPortId,
			List<String> tenantIdPortIp, String ovsIp, String bridgeName,
			String ofSwitchMAC, String ofSwitchdpid, int vmCount,
			int tunnelCount) {
		this.switchIp = switchIp;
		this.dpid = dpid;
		this.neutronPortId = neutronPortId;
		this.tenantIdPortIp = tenantIdPortIp;
		this.ovsIp = ovsIp;
		this.bridgeName = bridgeName;
		this.ofSwitchMAC = ofSwitchMAC;
		this.ofSwitchdpid = ofSwitchdpid;
		this.vmCount = vmCount;
		this.tunnelCount = tunnelCount;
	}
	public String getSwitchIp() {
		return switchIp;
	}
	public void setSwitchIp(String switchIp) {
		this.switchIp = switchIp;
	}
	public String getDpid() {
		return dpid;
	}
	public void setDpid(String dpid) {
		this.dpid = dpid;
	}
	public List<String> getNeutronPortId() {
		return neutronPortId;
	}
	public void setNeutronPortId(List<String> neutronPortId) {
		this.neutronPortId = neutronPortId;
	}
	public List<String> getTenantIdPortIp() {
		return tenantIdPortIp;
	}
	public void setTenantIdPortIp(List<String> tenantIdPortIp) {
		this.tenantIdPortIp = tenantIdPortIp;
	}
	public String getOvsIp() {
		return ovsIp;
	}
	public void setOvsIp(String ovsIp) {
		this.ovsIp = ovsIp;
	}
	public String getBridgeName() {
		return bridgeName;
	}
	public void setBridgeName(String bridgeName) {
		this.bridgeName = bridgeName;
	}
	public String getOfSwitchMAC() {
		return ofSwitchMAC;
	}
	public void setOfSwitchMAC(String ofSwitchMAC) {
		this.ofSwitchMAC = ofSwitchMAC;
	}
	public String getOfSwitchdpid() {
		return ofSwitchdpid;
	}
	public void setOfSwitchdpid(String ofSwitchdpid) {
		this.ofSwitchdpid = ofSwitchdpid;
	}
	public int getVmCount() {
		return vmCount;
	}
	public void setVmCount(int vmCount) {
		this.vmCount = vmCount;
	}
	public int getTunnelCount() {
		return tunnelCount;
	}
	public void setTunnelCount(int tunnelCount) {
		this.tunnelCount = tunnelCount;
	}
	@Override
	public String toString() {
		return "OvsNode [switchIp=" + switchIp + ", dpid=" + dpid
				+ ", neutronPortId=" + neutronPortId + ", tenantIdPortIp="
				+ tenantIdPortIp + ", ovsIp=" + ovsIp + ", bridgeName="
				+ bridgeName + ", ofSwitchMAC=" + ofSwitchMAC
				+ ", ofSwitchdpid=" + ofSwitchdpid + ", vmCount=" + vmCount
				+ ", tunnelCount=" + tunnelCount + "]";
	}
}
