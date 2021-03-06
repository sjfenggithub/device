package com.yd.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;




import com.yd.model.SwitchBasicInfo;
import com.yd.model.SwitchPort;
import com.yd.service.IGetSwitchBasicInfo;
import com.yd.util.TheUtil;

public class GetSwitchBasicInfoService implements IGetSwitchBasicInfo {
	private static String XMLUrl = "http://192.168.224.26:8181/restconf/operational/network-topology:network-topology/topology/ovsdb:1";
	@Override
	public List<SwitchBasicInfo> getSwitchBasicInfoBean() {
		List<SwitchBasicInfo> switchList = new ArrayList<SwitchBasicInfo>();
		String json =TheUtil.getXMLDoc(XMLUrl);//������Ŀ����Ҫ�Ķ�TheUtil.getXMLDoc()
		//System.out.println(json);
		JSONObject jsonTopo = JSONObject.fromObject(json);
		JSONArray jsonArray =jsonTopo.getJSONArray("topology");
		//String topologyId = (String)((JSONObject)jsonArray.get(0)).get("topology-id");
		//System.out.println(topologyId);
		JSONArray nodeArray = ((JSONObject)jsonArray.get(0)).getJSONArray("node");
		//System.out.println(nodeArray.size());
		for(int i=0;i<nodeArray.size();i++){
			JSONObject jsonObj =(JSONObject) nodeArray.get(i);//��ʾnode�ڵ����
			String dbVersion = (String)jsonObj.get("ovsdb:db-version");
			//System.out.println(dbVersion);
			if(dbVersion!=null){//��ʾ���ӵĽڵ�
				String UUIDInit =(String) jsonObj.get("node-id");
				String UUIDLink = getUUID(UUIDInit);
				//System.out.println(UUIDLink);
				String name = (String)jsonObj.getJSONObject("ovsdb:connection-info").get("remote-ip");
				for(int j =0;j<nodeArray.size();j++){
					//System.out.println(nodeArray.size());
					JSONObject jsonObj2 = nodeArray.getJSONObject(j);
					String dbVersion2 = (String)jsonObj2.get("ovsdb:ovs-version");
					//System.out.println(dbVersion2);
					if(dbVersion2==null){//��ʾ��switch��Ϣ�ڵ�
						String UUIDSwitch =getUUID((String)jsonObj2.get("node-id"));
						//System.out.println(UUIDSwitch);
						if(UUIDSwitch.equals(UUIDLink)){//��ʾ�����ڵ��໥����
							int virtualLinkNum =0;
							String openflow = getOpenflow();
							String tunnelPort = getTunnelPort();
							String dpidStr = (String)jsonObj2.get("ovsdb:datapath-id");
							while(dpidStr.indexOf("00:")==0){
								dpidStr=dpidStr.substring(3);	
							}
							String mac = dpidStr;//�õ�mac��ַ
							//��16���Ʊ�ʾ��MACת��Ϊʮ���������ַ�,�õ�dpid
							String dpid = getDpidFromMac(mac);
							
							
							
							//-----------------ÿ��������ĵĶ˿ڶ�������----------------------------------
							JSONArray termiArray = jsonObj2.getJSONArray("termination-point");
							List<SwitchPort> portList = new ArrayList<SwitchPort>();
							for(int k=0;k<termiArray.size();k++){
								String portName =null;
								String macAd = null;
								String receivePac = getReceivePac();
								String contractNum = getContractNum();
								String speed = getSpeed();
								String mtu = getMTU();
								String UUID = null;
								JSONObject portObject = JSONObject.fromObject(termiArray.get(k));
								portName =(String) portObject.get("ovsdb:name");
								//System.out.println(portName);
								
								if(!portName.contains("br-int")){
									if(portName.contains("tap")){//tap�˿�
										virtualLinkNum++;
										//System.out.println(virtualLinkNum);
										//--------tap�˿ڵ�mac��ַ��ֻ��tap�˿��У�--------------
										JSONArray portMacArray=portObject.getJSONArray("ovsdb:interface-external-ids");
										for(int m=0; m<portMacArray.size();m++){
											JSONObject vmObj = JSONObject.fromObject(portMacArray.get(m));
											String macKey = (String)vmObj.get("external-id-key");
											if("attached-mac".equals(macKey)){
												macAd =(String)vmObj.get("external-id-value");
											}
										}	
									}
									String ofPortString = portObject.get("ovsdb:ofport").toString();
									//System.out.println(ofPortString);
									Integer ofPort = Integer.parseInt(ofPortString);
									UUID = ((String)portObject.get("ovsdb:port-uuid")).replace("-","");
									SwitchPort switchPort = new SwitchPort(portName,macAd,receivePac,contractNum,ofPort,speed,mtu,UUID);
									portList.add(switchPort);
								}	
							}
							
							
							SwitchBasicInfo switchBasicInfo = new SwitchBasicInfo(name,dpid,openflow,tunnelPort,virtualLinkNum,portList);
							switchList.add(switchBasicInfo);
						}
					}
				}
				
				
			}
		}
		System.out.println(switchList);
		return switchList;
	}
	public String getOpenflow(){
		String openflow ="4788";
		return openflow;
	}
	public String getTunnelPort(){
		String tunnelPort ="5637";
		return tunnelPort;
	}
	//��UUIDת��Ϊ32λ���ַ�
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
	//ͨ��mac��ַ���dpid
	public String getDpidFromMac(String mac){
		mac = mac.replace(":", "");
		Long dpidLong = Long.parseLong(mac,16);
		String dpid = ""+dpidLong;
		return dpid;
	}
	//�����հ���
	public String getReceivePac(){
		String receivePac = null;
		return receivePac;
	}
	//���ط�����
	public String getContractNum(){
		String contractNum = null;
		return contractNum;
	}
	public String getSpeed(){
		String speed ="1000M";
		return speed;
	}
	public String getMTU(){
		String mtu ="10000";
		return mtu;
	}
}
