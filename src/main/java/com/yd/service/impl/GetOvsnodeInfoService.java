package com.yd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.yd.util.TheUtil;

import com.yd.model.OvsNode;
import com.yd.model.SwitchBasicInfo;
import com.yd.model.SwitchPort;
import com.yd.service.IGetOvsnode;

public class GetOvsnodeInfoService implements IGetOvsnode {
	/*
	private static String XMLUrl = "http://192.168.224.26:8181/restconf/operational/network-topology:network-topology/topology/ovsdb:1";
	private static String neutronPortUrl = "http://192.168.224.26:8181/restconf/config/neutron:neutron/ports/";
	*/
	private static String XMLUrl ="C://web_project/topologyInfo/src/node.json";
	private static String neutronPortUrl="C://web_project/topologyInfo/src/neutronPort.json";
	@Override
	public List<OvsNode> getOvsnodeBean() throws Exception {
		List<OvsNode> ovsnodeList = new ArrayList<OvsNode>();//将ovsnode对象放入一个容器中
		GetSwitchBasicInfoService gsbis = new GetSwitchBasicInfoService();
		List<SwitchBasicInfo> swtBasicInfoList = gsbis.getSwitchBasicInfoBean();
		for(SwitchBasicInfo temp:swtBasicInfoList){//SwitchBasicInfo包含了一些需要的信息，故而直接拿过来用
			List<String> neutronPortList = new ArrayList<String>();//特定交换机的端口列表
			List<String> tenantIdList = new ArrayList<String>();//端口的租户
			String switchIp = temp.getIp();//交换机ip(查询条件)
			String ovsIp = temp.getIp();//交换机ip（列表显示）
			String dpid = temp.getDpid();//交换机dpid（查询条件）
			String ofswitchdpid =temp.getDpid();//交换机dpid（列表显示）
			String ofswitchMAC = getMacFormat(Long.toHexString((Long.parseLong(dpid))),":",2);//交换机mac地址
			int vmCount = temp.getVirtualLink();//交换机虚机连接数（tap接口）
			String bridgeName=null;
			int tunnelCount=0;
			//得到各个端口对应的租户ID
			List<SwitchPort> swtPortList =temp.getPortList();
			for(SwitchPort item:temp.getPortList()){
				String macAd = item.getMacAd();//端口的mac地址
				//System.out.println(macAd);
				List<Map<String,Object>> tenant = getTenantId();
				for(Map<String,Object> tenantMap:tenant){
					String portMac = (String) tenantMap.get("portMac");
					//System.out.println(portMac);
					if(portMac.equalsIgnoreCase(macAd)){
						//System.out.println(portMac);
						tenantIdList.add((String)tenantMap.get("tenantId"));
						break;
					}
				}	
			}
		
			//根据交换机mac地址得到该交换机对应的网桥名，neutronportId以及隧道数量
			List<Map<String,Object>> neutron = getOvsInfo();
			for(Map<String,Object> neutronMap:neutron){
				if(((String)neutronMap.get("mac")).equalsIgnoreCase(ofswitchMAC)){
					bridgeName= (String)neutronMap.get("bridgename");
					tunnelCount=(int)neutronMap.get("tunnelNum");
					neutronPortList=(List<String>) neutronMap.get("ifaceId");
				}
			}
			
			OvsNode ovsnode = new OvsNode(switchIp, dpid, neutronPortList, tenantIdList, ovsIp, bridgeName, ofswitchMAC, ofswitchdpid, vmCount, tunnelCount);
			ovsnodeList.add(ovsnode);
		}
		return ovsnodeList;
	}
	/*******************************规范mac地址的形式，每隔两个数字加冒号*******************************************/
	public static String getMacFormat(String mac,String seprator,int count){
		StringBuffer ofswitchMAC = new StringBuffer(mac);
		int index=count;
		int len=mac.length();
		while(len>count&&index<len-1){
			ofswitchMAC.insert(index,seprator);
			len++;
			index+=count+1;
		}
		return ofswitchMAC.toString();
	}
	
	/*******************************获取SwitchBasicInfo中未封装的XMLUrl中的数据信息*******************************************/
	public static List<Map<String,Object>>  getOvsInfo(){//获取和交换机对应的网桥名，neutron port ID，tunnelCount
	    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String json = TheUtil.getXMLDoc(XMLUrl);
		JSONObject jsonTopo = JSONObject.fromObject(json);
		JSONArray jsonArray =jsonTopo.getJSONArray("topology");
		JSONArray nodeArray = ((JSONObject)jsonArray.get(0)).getJSONArray("node");
		for(int i=0;i<nodeArray.size();i++){
			String dbVersion=null;
			JSONObject jsonObj =(JSONObject) nodeArray.get(i);//node节点
			dbVersion = (String)jsonObj.get("ovsdb:db-version");
			if(dbVersion==null){//表示switch信息的node
				int tunnelNum=0;
				Map<String,Object> map = new HashMap<String,Object>();
				String bridgename= jsonObj.getString("ovsdb:bridge-name");//桥名
				String dpidStr = (String)jsonObj.get("ovsdb:datapath-id");
				while(dpidStr.indexOf("00:")==0){
					dpidStr=dpidStr.substring(3);	
				}
				String mac = dpidStr;//规范mac地址的形式
					//-----------------termination point主机----------------------------------
					JSONArray termiArray = jsonObj.getJSONArray("termination-point");
					List<String> ifaceIdList = new ArrayList<String>();
					for(int k=0;k<termiArray.size();k++){
						JSONObject portObject = JSONObject.fromObject(termiArray.get(k));
						String portName =(String) portObject.get("ovsdb:name");
						if(portName.contains("vxlan")){tunnelNum++;}
						if(portName.contains("tap")){
							JSONArray portMacArray=portObject.getJSONArray("ovsdb:interface-external-ids");
							for(int m=0; m<portMacArray.size();m++){
								JSONObject vmObj = JSONObject.fromObject(portMacArray.get(m));
								String ifaceKey = (String)vmObj.get("external-id-key");
								if("iface-id".equals(ifaceKey)){
									String ifaceId =(String)vmObj.get("external-id-value");
									ifaceIdList.add(ifaceId);//将终端的非vxlan接口UUID放入容器
								}
								
							}
						}
						
					}
					map.put("mac", mac);//交换机的mac地址
					map.put("tunnelNum", tunnelNum);//vxlan的个数
					map.put("bridgename", bridgename);//网桥名
					map.put("ifaceId", ifaceIdList);//该交换机所包含的neutronport的ID的集合
					list.add(map);
			}
		}
		return list;
		
	}
	
	/*******************************由mac转化为dpid*******************************************/
	public static String getDpidFromMac(String mac){
		mac = mac.replace(":", "");
		Long dpidLong = Long.parseLong(mac,16);
		String dpid = ""+dpidLong;
		return dpid;
	}
	
	/*********************从neutronUrl中获取租户ID信息，并与交换机mac地址关联************************/
	public static List<Map<String,Object>> getTenantId(){//SwitchBasicInfo的portList属性中已经含有各个port的mac地址
		List<Map<String,Object>> tenantIdList = new ArrayList<Map<String,Object>>();
		String jsonString = TheUtil.getXMLDoc(neutronPortUrl);
		JSONObject obj= JSONObject.fromObject(jsonString);
		JSONObject portsObj = obj.getJSONObject("ports");
		JSONArray portArr = portsObj.getJSONArray("port");
		for(int i=0;i<portArr.size();i++){
			Map<String,Object> map = new HashMap<String,Object>();
			JSONObject portObj = portArr.getJSONObject(i);
			String tenantId = portObj.getString("tenant-id");
			String portMac = portObj.getString("mac-address");
			map.put("tenantId", tenantId);
			map.put("portMac", portMac);
			tenantIdList.add(map);
		}
		return tenantIdList;
	}
	/*******************************测试*******************************************/
	public static void main(String[] args) throws Exception{
		
		GetOvsnodeInfoService gois = new GetOvsnodeInfoService();
		List<OvsNode> list = gois.getOvsnodeBean();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ovsnode", list);
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json);
		System.exit(0);
	}
	
}
