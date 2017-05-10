package com.yd.model;

import java.util.List;
import java.util.Map;

public class Vtn {
	private List<Map<String,Object>> neutronPort;
	private List<Map<String,Object>> neutronSubnet;
	private List<Map<String,Object>> neutronNetwork;
	private List<Map<String,Object>> neutronRouter;
	private List<Map<String,Object>> neutronFloatingIp;
	private List<Map<String,Object>> neutronSecurityGroup;
	private List<Map<String,Object>> neutronSecurityGroupRule;
	public Vtn(List<Map<String, Object>> neutronPort,
			List<Map<String, Object>> neutronSubnet,
			List<Map<String, Object>> neutronNetwork,
			List<Map<String, Object>> neutronRouter,
			List<Map<String, Object>> neutronFloatingIp,
			List<Map<String, Object>> neutronSecurityGroup,
			List<Map<String, Object>> neutronSecurityGroupRule) {
		this.neutronPort = neutronPort;
		this.neutronSubnet = neutronSubnet;
		this.neutronNetwork = neutronNetwork;
		this.neutronRouter = neutronRouter;
		this.neutronFloatingIp = neutronFloatingIp;
		this.neutronSecurityGroup = neutronSecurityGroup;
		this.neutronSecurityGroupRule = neutronSecurityGroupRule;
	}
	public List<Map<String, Object>> getNeutronPort() {
		return neutronPort;
	}
	public void setNeutronPort(List<Map<String, Object>> neutronPort) {
		this.neutronPort = neutronPort;
	}
	public List<Map<String, Object>> getNeutronSubnet() {
		return neutronSubnet;
	}
	public void setNeutronSubnet(List<Map<String, Object>> neutronSubnet) {
		this.neutronSubnet = neutronSubnet;
	}
	public List<Map<String, Object>> getNeutronNetwork() {
		return neutronNetwork;
	}
	public void setNeutronNetwork(List<Map<String, Object>> neutronNetwork) {
		this.neutronNetwork = neutronNetwork;
	}
	public List<Map<String, Object>> getNeutronRouter() {
		return neutronRouter;
	}
	public void setNeutronRouter(List<Map<String, Object>> neutronRouter) {
		this.neutronRouter = neutronRouter;
	}
	public List<Map<String, Object>> getNeutronFloatingIp() {
		return neutronFloatingIp;
	}
	public void setNeutronFloatingIp(List<Map<String, Object>> neutronFloatingIp) {
		this.neutronFloatingIp = neutronFloatingIp;
	}
	public List<Map<String, Object>> getNeutronSecurityGroup() {
		return neutronSecurityGroup;
	}
	public void setNeutronSecurityGroup(
			List<Map<String, Object>> neutronSecurityGroup) {
		this.neutronSecurityGroup = neutronSecurityGroup;
	}
	public List<Map<String, Object>> getNeutronSecurityGroupRule() {
		return neutronSecurityGroupRule;
	}
	public void setNeutronSecurityGroupRule(
			List<Map<String, Object>> neutronSecurityGroupRule) {
		this.neutronSecurityGroupRule = neutronSecurityGroupRule;
	}
	@Override
	public String toString() {
		return "Vtn [neutronPort=" + neutronPort + ", neutronSubnet="
				+ neutronSubnet + ", neutronNetwork=" + neutronNetwork
				+ ", neutronRouter=" + neutronRouter + ", neutronFloatingIp="
				+ neutronFloatingIp + ", neutronSecurityGroup="
				+ neutronSecurityGroup + ", neutronSecurityGroupRule="
				+ neutronSecurityGroupRule + "]";
	}
	
}
