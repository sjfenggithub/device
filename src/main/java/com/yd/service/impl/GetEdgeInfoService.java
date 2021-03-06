package com.yd.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yd.util.TheUtil;
import com.yd.service.IGetEdgeInfo;
import com.yd.model.EdgeLink;

public class GetEdgeInfoService implements IGetEdgeInfo {

	private static String XMLUrl = "http://192.168.224.26:8181/restconf/operational/network-topology:network-topology/topology/ovsdb:1";
	public List<EdgeLink> fromXML() {//�˴���ʾ���ǿ������ͽ�����֮������ӣ��������������ļ��ж�ȡ
		List<EdgeLink> list = new ArrayList<EdgeLink>();
		String json = TheUtil.getXMLDoc(XMLUrl);
		JSONObject jsonTopo = JSONObject.fromObject(json);
		JSONArray jsonArray =jsonTopo.getJSONArray("topology");
		JSONArray nodeArray = ((JSONObject)jsonArray.get(0)).getJSONArray("node");
		for(int i=0;i<nodeArray.size();i++){
			JSONObject jsonObj =(JSONObject) nodeArray.get(i);//��ʾnode�ڵ����
			String dbVersion = (String)jsonObj.get("ovsdb:db-version");
			//System.out.println(dbVersion);
			if(dbVersion!=null){//��ʾ���ӵĽڵ�
				String target = (String)jsonObj.getJSONObject("ovsdb:connection-info").get("remote-ip");
				String source = (String)jsonObj.getJSONObject("ovsdb:connection-info").get("local-ip");
				int value = 50;
				EdgeLink edgeLink = new EdgeLink(source, target,value);
				list.add(edgeLink);
			}
		}
		return list;
	}
	//�������ļ������ǿ�����-���������Ӷ�ȡ��������֮ƴ��
	@Override
	public List<EdgeLink> getEdgeBean(){
		GetEdgeInfoService geis = new GetEdgeInfoService();
		List<EdgeLink> list = geis.fromXML();
		JSONObject jsonObject = JSONObject.fromObject(TheUtil.getFileString());
		JSONArray edgeArray = jsonObject.getJSONArray("edge");
		System.out.println(edgeArray.size());
		for(int i=0;i<edgeArray.size();i++){
			JSONObject jsonObj =(JSONObject)edgeArray.get(i);
			String source = jsonObj.getString("source");
			String target = jsonObj.getString("target");
			int value = Integer.parseInt(jsonObj.getString("value"));
			if(!(source.equals("controller")&&target.contains("switch"))){
				EdgeLink edgeLink = new EdgeLink(source,target,value);
				list.add(edgeLink);
			}
		}
		return list;
	}
}
