package com.yd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yd.model.Vtn;
import com.yd.service.IGetVtnInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yd.util.TheUtil;
@Service("vtnService")
public class GetVtnInfoService implements IGetVtnInfo{
	//private String neutronUrl ="http://192.168.224.26:8181/restconf/config/neutron:neutron/";
	private static String neutronUrl = "C://web_project/topologyInfo/src/neutron.json";
	@Override
	public Vtn getVtnBean() throws Exception {
		List<Map<String,Object>> neutronPort=getNeutronPort();
		List<Map<String,Object>> neutronSubnet=getNeutronSubnet();
		List<Map<String,Object>> neutronNetwork=getNeutronNetwork();
		List<Map<String,Object>> neutronRouter=getNeutronRouter();
		List<Map<String,Object>> neutronFloatingIp=getNeutronFloatingIp();
		List<Map<String,Object>> neutronSecurityGroup=getNeutronSecurityGroup();
		List<Map<String,Object>> neutronSecurityGroupRule=getNeutronSecurityGroupRule();
		Vtn vtn = new Vtn(neutronPort,neutronSubnet,neutronNetwork,neutronRouter,neutronFloatingIp,
				neutronSecurityGroup,neutronSecurityGroupRule);
		return vtn;
	}
	public static JSONObject getNeutronObj(){
	String neutronString = TheUtil.getXMLDoc(neutronUrl);
	JSONObject jsonObj = JSONObject.fromObject(neutronString);
    JSONObject neutronObj = jsonObj.getJSONObject("neutron");
    return neutronObj;
	}
	public static List<Map<String,Object>> getNeutronPort(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		JSONObject ports = getNeutronObj().getJSONObject("ports");
		JSONArray portArr = ports.getJSONArray("port");
		 for(int i=0;i<portArr.size();i++){
			 Map<String,Object> map= new HashMap<String,Object>();
			 JSONObject obj = portArr.getJSONObject(i);
			 String ID = obj.getString("uuid");
			 String portId = ID;
			 String tenantId = (String) obj.get("tenant-id");
			 String networkId = (String) obj.get("network-id");
			 String macAddress = (String) obj.get("mac-address");
			 String portMac = macAddress;
			 String name = (String) obj.get("name");
			 String fixedIps =obj.getJSONArray("fixed-ips").getJSONObject(0).toString();
			 String ip =(String) obj.getJSONArray("fixed-ips").getJSONObject(0).get("ip-address");
			 map.put("ID", ID);
			 map.put("portId", portId);
			 map.put("tenantId",tenantId);
			 map.put("networkId", networkId);
			 map.put("macAddress",macAddress);
			 map.put("name",name);
			 map.put("fixedIps",fixedIps);
			 map.put("ip",ip);
			 map.put("portMac", portMac);
			 list.add(map); 
		 }
		 
		return list;
	}
	public static List<Map<String,Object>> getNeutronSubnet(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		JSONObject subnets = getNeutronObj().getJSONObject("subnets");
		JSONArray subnetArr = subnets.getJSONArray("subnet");
		for(int i=0;i<subnetArr.size();i++){
			Map<String,Object> map= new HashMap<String,Object>();
			JSONObject obj = subnetArr.getJSONObject(i);
			String subnetId = (String)obj.get("uuid");
			String networkIp = (String)obj.get("gateway-ip");
			String tenantId = (String)obj.get("tenant-id");
			String id = subnetId;
			String name= (String)obj.get("name");
			String cidr =(String)obj.get("cidr");
			String allocationPools = obj.getJSONArray("allocation-pools").getJSONObject(0).toString();
			map.put("subnetId", subnetId);
			map.put("networkIp", networkIp);
			map.put("tenantId", tenantId);
			map.put("id", id);
			map.put("name", name);
			map.put("cidr", cidr);
			map.put("allocationPools",allocationPools);
			list.add(map);
			
		}
		return list;
		
	}
	public static List<Map<String,Object>> getNeutronNetwork(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> subnetList = new ArrayList<Map<String,Object>>();
		JSONObject subnetsObj = getNeutronObj().getJSONObject("subnets");
		JSONArray subnetArr = subnetsObj.getJSONArray("subnet");
		for(int i=0;i<subnetArr.size();i++){
			Map<String,Object> subnetmap= new HashMap<String,Object>();
			JSONObject subnetobj = subnetArr.getJSONObject(i);
			String subnetId = (String)subnetobj.get("uuid");
			String networkId =(String)subnetobj.get("network-id");
			subnetmap.put("subnetId", subnetId);
			subnetmap.put("networkId", networkId);
			subnetList.add(subnetmap);
		}
		JSONObject networks = getNeutronObj().getJSONObject("networks");
		JSONArray networkArr = networks.getJSONArray("network");
		for(int i=0;i<networkArr.size();i++){
			Map<String,Object> map= new HashMap<String,Object>();
			JSONObject obj = networkArr.getJSONObject(i);
			String networkId =(String)obj.get("uuid");
			String id = networkId;
			String tenantId = (String)obj.get("tenant-id");
			String name =(String)obj.get("name");
			String subnets = null;
			for(Map<String,Object> subnetMap:subnetList){
				if(networkId.equals((String)subnetMap.get("networkId"))){
					subnets=(String) subnetMap.get("subnetId");
				}
			}
			map.put("networkId", networkId);
			map.put("tenantId", tenantId);
			map.put("id", id);
			map.put("name", name);
			map.put("subnets", subnets);
			list.add(map);
		}
		return list;
	}
	public static List<Map<String,Object>> getNeutronRouter(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		JSONObject routers = getNeutronObj().getJSONObject("routers");
		JSONArray routerArr = routers.getJSONArray("router");
		for(int i=0;i<routerArr.size();i++){
			Map<String,Object> map= new HashMap<String,Object>();
			JSONObject obj = routerArr.getJSONObject(i);
			String routerId =(String)obj.get("uuid");
			String tenantId =(String)obj.get("tenant-id");
			String name =(String)obj.get("name");
			String externalGatewayInfo = null;
			String externalNetworkId = (String)obj.getJSONObject("external_gateway_info").get("external-network-id");
			boolean enableSnat =(boolean)obj.getJSONObject("external_gateway_info").get("enable-snat");
			externalGatewayInfo="{\"networkid\":"+externalNetworkId+","+"\"enable-snat\":"+enableSnat+"}";
			map.put("routerId", routerId);
			map.put("tenantId", tenantId);
			map.put("name", name);
			map.put("externalGatewayInfo", externalGatewayInfo);
			list.add(map);
		}
		return list;
	}
	public static List<Map<String,Object>> getNeutronFloatingIp(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		JSONObject floatingIps = getNeutronObj().getJSONObject("floatingips");
		JSONArray floatingIp =floatingIps.getJSONArray("floatingip");
		for(int i=0;i<floatingIp.size();i++){
			Map<String,Object> map= new HashMap<String,Object>();
			JSONObject obj = floatingIp.getJSONObject(i);
			String floatingIpId = (String)obj.get("uuid");
			String id = floatingIpId;
			String tenantId = (String) obj.get("tenant-id");
			String networkId =(String)obj.get("network-id");
			String fixedIpAddress = (String)obj.get("fixed-ip-address");
			String floatingIpAddress =(String)obj.get("floating-ip-address");
			String portId =(String)obj.get("port-id");
			map.put("floatingIpId", floatingIpId);
			map.put("id", id);
			map.put("networkId", networkId);
			map.put("fixedIpAddress", fixedIpAddress);
			map.put("floatingIpAddress", floatingIpAddress);
			map.put("tenantId", tenantId);
			map.put("portId", portId);
			list.add(map);
		}
		return list;
	}
	public static List<Map<String,Object>> getNeutronSecurityGroup(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		JSONObject securityGroups = getNeutronObj().getJSONObject("security-groups");
		JSONArray securityGroup =securityGroups.getJSONArray("security-group");
		for(int i=0;i<securityGroup.size();i++){
			Map<String,Object> map= new HashMap<String,Object>();
			JSONObject obj = securityGroup.getJSONObject(i);
			String securityGroupId = (String)obj.get("uuid");
			String id = securityGroupId;
			String tenantId = (String)obj.get("tenant-id");
			String name = (String)obj.get("name");
			map.put("securityGroupId",securityGroupId );
			map.put("id", id);
			map.put("tenantId",tenantId );
			map.put("name", name);
			list.add(map);
		}
		return list;
		
	}
	public static List<Map<String,Object>> getNeutronSecurityGroupRule(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		JSONObject securityRules = getNeutronObj().getJSONObject("security-rules");
		JSONArray securityRule =securityRules.getJSONArray("security-rule");
		for(int i=0;i<securityRule.size();i++){
			Map<String,Object> map= new HashMap<String,Object>();
			JSONObject obj = securityRule.getJSONObject(i);
			String securityRuleId = (String)obj.get("uuid");
			String id=securityRuleId;
			String securityGroupId = (String)obj.get("security-group-id");
			String remoteGroupId = (String)obj.get("remote-group-id");
			String direction = (String)obj.get("direction");
			String tenantId = (String)obj.get("tenant-id");
			String protocol = (String)obj.get("protocol");
			String remoteIpPrefix = (String)obj.get("remote_ip_prefix");
			String remoteGroup = (String)obj.get("remote_group");
			map.put("securityRuleId",securityRuleId );
			map.put("id", id);
			map.put("securityGroupId",securityGroupId );
			map.put("remoteGroupId",remoteGroupId );
			map.put("direction",direction );
			map.put("tenantId", tenantId);
			map.put("protocol",protocol );
			map.put("remoteIpPrefix",remoteIpPrefix );
			map.put("remoteGroup",remoteGroup );
			list.add(map);
		}
		return list;
	}
	public void main(String args) throws Exception{
		GetVtnInfoService obj = new GetVtnInfoService();
		Vtn vtn =obj.getVtnBean();
		System.out.println(JSONObject.fromObject(vtn).toString());
		System.exit(0);
	}
	

}
