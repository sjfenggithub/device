package com.yd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.yd.util.TheUtil;

import com.yd.model.OvsInterface;
import com.yd.service.IGetOvsInterface;

public class GetOvsInterfaceInfoService implements IGetOvsInterface {
	/*
	private static String XMLUrl = "http://192.168.224.26:8181/restconf/operational/network-topology:network-topology/topology/ovsdb:1";
	private static String neutronPortUrl = "http://192.168.224.26:8181/restconf/config/neutron:neutron/ports/";
	*/
	private static String XMLUrl ="C://web_project/topologyInfo/src/node.json";
	private static String neutronPortUrl="C://web_project/topologyInfo/src/neutronPort.json";

	@Override
	public  List<OvsInterface> getOvsInterfaceBean() throws Exception {
		List<OvsInterface> list = new ArrayList<OvsInterface>();
		String json =TheUtil.getXMLDoc(XMLUrl);
		JSONObject jsonTopo = JSONObject.fromObject(json);
		JSONArray jsonArray =jsonTopo.getJSONArray("topology");
		JSONArray nodeArray = ((JSONObject)jsonArray.get(0)).getJSONArray("node");
		for(int i=0;i<nodeArray.size();i++){
			JSONObject jsonObj =(JSONObject) nodeArray.get(i);//node节点
			String dbVersion = (String)jsonObj.get("ovsdb:db-version");
			if(dbVersion!=null){//表链接的node
				String UUIDInit =(String) jsonObj.get("node-id");
				String UUIDLink = getUUID(UUIDInit);
				String switchIp = (String)jsonObj.getJSONObject("ovsdb:connection-info").get("remote-ip");
				String ovsMgtIp=switchIp;
				for(int j =0;j<nodeArray.size();j++){
					JSONObject jsonObj2 = nodeArray.getJSONObject(j);
					String dbVersion2 = (String)jsonObj2.get("ovsdb:ovs-version");
					if(dbVersion2==null){//表示switch信息的node
						String UUIDSwitch =getUUID((String)jsonObj2.get("node-id"));
						if(UUIDSwitch.equals(UUIDLink)){
							JSONArray termiArray = jsonObj2.getJSONArray("termination-point");
							for(int k=0;k<termiArray.size();k++){//对接口进行遍历
								JSONObject portObject = JSONObject.fromObject(termiArray.get(k));
								String interfaceName = portObject.getString("ovsdb:name");//接口名
								/*********将每个tap封装成一个对象******************/
								if(interfaceName.contains("tap")){
									String interfacaMac=null;
									String portId=null;
									String portIp=null;
									String tenantId=null;
									String rx_packets=null;
									String tx_packets=null;
									String speed=null;
									int ofPort = Integer.parseInt(portObject.getString("ovsdb:ofport"));
									JSONArray portMacArray=portObject.getJSONArray("ovsdb:interface-external-ids");
									for(int m=0; m<portMacArray.size();m++){
										JSONObject vmObj = JSONObject.fromObject(portMacArray.get(m));
										String macKey = (String)vmObj.get("external-id-key");
										if("attached-mac".equals(macKey)){
											interfacaMac =(String)vmObj.get("external-id-value");
										}
										if("iface-id".equals(macKey)){
											portId=(String)vmObj.get("external-id-value");
										}
									}
									List<Map<String,Object>> portInfoList = getPortInfo();
									for(Map<String,Object> map:portInfoList){
										if(((String)map.get("portId")).equals(portId)){
											tenantId=(String)map.get("tenantId");
											portIp=(String)map.get("portIp");
										}
									}
									OvsInterface ovsInterface = new OvsInterface(switchIp, interfaceName,
											portId, tenantId, portIp,ovsMgtIp, interfacaMac, ofPort,
											rx_packets, tx_packets, speed);
									list.add(ovsInterface);
								}
							}
						}
					}
				}
			}
		}
		return list;
	}
	/*从neutronPort里面得到和交换机端口相关的接口UUID，portIP，tenantId的信息**************/
	public static List<Map<String,Object>> getPortInfo(){
		List<Map<String,Object>> portInfoList = new ArrayList<Map<String,Object>>();
		String jsonString = TheUtil.getXMLDoc(neutronPortUrl);
		JSONObject obj= JSONObject.fromObject(jsonString);
		JSONObject portsObj = obj.getJSONObject("ports");
		JSONArray portArr = portsObj.getJSONArray("port");
		for(int i=0;i<portArr.size();i++){
			Map<String,Object> map=new HashMap<String,Object>();
			JSONObject portObj = portArr.getJSONObject(i);
			String tenantId = portObj.getString("tenant-id");
			String portId =portObj.getString("uuid");
			JSONArray ipArr = portObj.getJSONArray("fixed-ips");
			String portIp = JSONObject.fromObject(ipArr.get(0)).getString("ip-address");
			map.put("tenantId", tenantId);
			map.put("portId", portId);
			map.put("portIp", portIp);
			portInfoList.add(map);
		}
		return portInfoList;
	} 
	public static String getUUID(String UUIDInit){
		int index = UUIDInit.indexOf("ovsdb://uuid/");
		int len1="ovsdb://uuid/".length();
		int startIndex = index+len1;
		int len ="f17edca4-fa46-490c-afaf-16c149b9dda7".length();
		int endIndex = startIndex+len;
		String nodeUUID = UUIDInit.substring(startIndex, endIndex);
		String UUID = nodeUUID.replace("-","");
		return UUID;
	}
	public static void main(String[] args) throws Exception{
		GetOvsInterfaceInfoService obj = new GetOvsInterfaceInfoService();
		List<OvsInterface> list = obj.getOvsInterfaceBean();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ovsinterface", list);
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json.toString());
		System.exit(0);
	}
}
