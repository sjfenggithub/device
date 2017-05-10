package com.yd.service.impl;


import java.util.ArrayList;
import java.util.List;

import com.yd.util.TheUtil;
import com.yd.model.SwitchNode;
import com.yd.service.IGetSwitchInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class GetSwitchInfoService implements IGetSwitchInfo {
	
	private static String XMLUrl = "http://192.168.224.26:8181/restconf/operational/network-topology:network-topology/topology/ovsdb:1";
	public List<SwitchNode> getSwitchBean() {
		List<SwitchNode> list = new ArrayList<SwitchNode>();
		String type=null;
		String json = TheUtil.getXMLDoc(XMLUrl);
		JSONObject jsonTopo = JSONObject.fromObject(json);
		JSONArray jsonArray =jsonTopo.getJSONArray("topology");
		String topologyId = (String)((JSONObject)jsonArray.get(0)).get("topology-id");
		//System.out.println(topologyId);
		if("ovsdb:1".equals(topologyId)){
			type="vSwitch";
		}
		JSONArray nodeArray = ((JSONObject)jsonArray.get(0)).getJSONArray("node");
		//System.out.println(nodeArray.size());
		for(int i=0;i<nodeArray.size();i++){
			JSONObject jsonObj =(JSONObject) nodeArray.get(i);//表示node节点对象
			String dbVersion = (String)jsonObj.get("ovsdb:db-version");
			//System.out.println(dbVersion);
			if(dbVersion!=null){//表示链接的节点
				String UUIDInit =(String) jsonObj.get("node-id");
				String UUIDLink = getUUID(UUIDInit);
				//System.out.println(UUIDLink);
				String name = (String)jsonObj.getJSONObject("ovsdb:connection-info").get("remote-ip");
				for(int j =0;j<nodeArray.size();j++){
					//System.out.println(nodeArray.size());
					JSONObject jsonObj2 = nodeArray.getJSONObject(j);
					String dbVersion2 = (String)jsonObj2.get("ovsdb:ovs-version");
					//System.out.println(dbVersion2);
					if(dbVersion2==null){//表示含switch信息节点
						String UUIDSwitch =getUUID((String)jsonObj2.get("node-id"));
						//System.out.println(UUIDSwitch);
						if(UUIDSwitch.equals(UUIDLink)){
							String dpidStr = (String)jsonObj2.get("ovsdb:datapath-id");
							while(dpidStr.indexOf("00:")==0){
								dpidStr=dpidStr.substring(3);	
							}
							String mac = dpidStr;
							//将16进制表示的MAC转化为十进制数字字符串
							String[] arr = mac.split(":");
							String dpid="";
							for(int k=0;k<arr.length;k++){
								
								dpid+=Integer.parseInt(arr[k], 16);
							}
							String value = "dpid:"+dpid+"<br/>MAC:"+mac+"<br/>type:"+type;
							//System.out.println(value);
							//封装对象
							int category = 2;
							SwitchNode switchNode = new SwitchNode(name,category,value);
							list.add(switchNode);
						}
					}
				}
				
				
			}
		}
		return list;
	}
	//将UUID转化为32位的字符串
	public String getUUID(String UUIDInit){
		int index = UUIDInit.indexOf("ovsdb://uuid/");
		int len1="ovsdb://uuid/".length();
		int startIndex = index+len1;
		int len ="f17edca4-fa46-490c-afaf-16c149b9dda7".length();
		int endIndex = startIndex+len;
		String nodeUUID = UUIDInit.substring(startIndex, endIndex);
		String UUID = nodeUUID.replace("-","");
		return UUID;
	}
	
}
