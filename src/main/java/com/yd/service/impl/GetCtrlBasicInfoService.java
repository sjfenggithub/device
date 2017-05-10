package com.yd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yd.util.TheUtil;
import com.yd.model.CtrlBasicInfo;
import com.yd.model.FW;
import com.yd.model.Gateway;
import com.yd.model.Local;
import com.yd.model.Remote;
import com.yd.model.PhysicalSwt;
import com.yd.model.SwitchNode;
import com.yd.model.VirtualSwt;
import com.yd.service.IGetCtrlBasicInfo;

public class GetCtrlBasicInfoService implements IGetCtrlBasicInfo {
	TheUtil theUtil = new TheUtil();
	private static String XMLUrl = "http://192.168.224.26:8181/restconf/operational/network-topology:network-topology/topology/ovsdb:1";
	@Override//
	public CtrlBasicInfo getCtrlBasicInfoBean() {
		VirtualSwt  virtualSwt= getVitualSwtBean();
		PhysicalSwt physicalSwt = getPhysicalSwtBean();
		List<Gateway> gatewayList= getGateway();
		List<FW> fwList = getFW();
		CtrlBasicInfo ctrl = new CtrlBasicInfo(virtualSwt,physicalSwt,gatewayList,fwList);
		return ctrl;
	}
	//从静态文件读取网关信息
	public List<Gateway> getGateway(){
		List<Gateway> list = new ArrayList<Gateway>();
//		String s_xmlpath="com/yd/json/apkinfo.json";
//		String fileStr = theUtil.ReadFile(s_xmlpath);
		String fileStr = theUtil.getFileString();//如果不行，换上面两行
		JSONObject jsonObj = JSONObject.fromObject(fileStr);
		//System.out.println(jsonObj.toString());
		JSONArray arr = jsonObj.getJSONArray("NFV");
		for(int i=0;i<arr.size();i++){
			JSONObject NFVObj = JSONObject.fromObject(arr.get(i));
			String type = (String)NFVObj.get("type");
			if(type.equals("Gateway")){
				String[] value = ((String)NFVObj.get("value")).split("<br/>");
				String ip=value[0].substring(value[0].indexOf(":")+1);
				String vendor=value[1].substring(value[1].indexOf(":")+1);
				String version=value[2].substring(value[2].indexOf(":")+1);
				String href = (String)NFVObj.get("url");
				Gateway gateway = new Gateway(ip,vendor,version,href);
				list.add(gateway);
			}
		}
		return list;
	}
	//从静态文件读取防火墙信息
	public List<FW> getFW(){
		List<FW> list = new ArrayList<FW>();
//		String s_xmlpath="com/yd/json/apkinfo.json";
//		String fileStr = theUtil.ReadFile(s_xmlpath);
		String fileStr = theUtil.getFileString();//如果不行，换上面两行
		JSONObject jsonObj = JSONObject.fromObject(fileStr);
		//System.out.println(jsonObj.toString());
		JSONArray arr = jsonObj.getJSONArray("NFV");
		for(int i=0;i<arr.size();i++){
			JSONObject NFVObj = JSONObject.fromObject(arr.get(i));
			String type = (String)NFVObj.get("type");
			if(type.equals("FW")){
				String[] value = ((String)NFVObj.get("value")).split("<br/>");
				String ip=value[0].substring(value[0].indexOf(":")+1);
				String vendor=value[1].substring(value[1].indexOf(":")+1);
				String version=value[2].substring(value[2].indexOf(":")+1);
				String href = (String)NFVObj.get("url");
				FW fw = new FW(ip,vendor,version,href);
				list.add(fw);
			}
		}
		return list;
	}
	//从node节点中读取虚拟交换机信息װ
	public VirtualSwt getVitualSwtBean(){
		List<VirtualSwt> list = new ArrayList<VirtualSwt>();
		List<Local> localList = new ArrayList<Local>();
		List<Remote> remoteList = new ArrayList<Remote>();
		String type=null;
		String json = TheUtil.getXMLDoc(XMLUrl);
		//System.out.println(json);
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
				JSONObject LRObj = jsonObj.getJSONObject("ovsdb:connection-info");
				Object localIp =LRObj.get("local-ip");
				Object localPort= LRObj.get("local-port");
				//System.out.println(localPort);
				Object remoteIp=LRObj.get("remote-ip"); 
				//System.out.println(remoteIp);
				Object remotePort=LRObj.get("remote-port");
				//System.out.println(remotePort);
				Local local = new Local(localIp,localPort);
				Remote remote = new Remote(remoteIp,remotePort);
				localList.add(local);
				remoteList.add(remote);
			}
		}
		VirtualSwt vswt = new VirtualSwt(localList,remoteList);
		return vswt;
	}
	//从node节点中读取物理交换机信息װ
	public PhysicalSwt getPhysicalSwtBean(){
		return null;
	}
	//得到json格式的控制器的基本信息
		public String getJsonString(){
			CtrlBasicInfo ctrl= getCtrlBasicInfoBean();
			String fileStr = TheUtil.getFileString();
			JSONObject jsonObj = JSONObject.fromObject(fileStr);
			Map<Object,Object> map = new HashMap<Object,Object>();
			Object number = jsonObj.get("number");
			Object ips = jsonObj.get("ips");
			Object Theserver = jsonObj.getJSONArray("Theserver");
			map.put("number", number);
			map.put("ips", ips);
			map.put("Theserver", Theserver);
			map.put("listData",ctrl);
			return JSONObject.fromObject(map).toString();
		}
	
}
 