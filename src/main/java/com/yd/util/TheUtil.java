package com.yd.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yd.model.SwitchBasicInfo;
import com.yd.model.CtrlNode;
import com.yd.model.EdgeLink;
import com.yd.model.SwitchNode;
import com.yd.service.impl.GetCtrlInfoService;
import com.yd.service.impl.GetEdgeInfoService;
import com.yd.service.impl.GetSwitchBasicInfoService;
import com.yd.service.impl.GetSwitchInfoService;
import com.yd.service.impl.ContServiceImpl;
public class TheUtil {
	static StringBuilder temp =new StringBuilder();
	static final String kuser = "admin"; // 用户名
	static final String kpass = "admin"; // 密码
	
	static class MyAuthenticator extends Authenticator {
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			PasswordAuthentication pa = new PasswordAuthentication(kuser,kpass.toCharArray());
			return pa;
		}
	}
	
	public static String getURL(String vmIp, String port){//URL的拼接
		StringBuilder sb = new StringBuilder();
		String url= sb.append("http://").append(vmIp).append(port).toString();
		return url;
	}
	public static String getXMLDoc(String XMLUrl){//通过XML文件网页抓取网页信息返回json格式字符串，即所有的节点信息
		
		try {//局域网访问外网的话，需要设置代理
//			System.getProperties().setProperty("http.proxyHost", "proxy.cmcc");            
//			System.getProperties().setProperty("http.proxyPort", "8080");
			Authenticator.setDefault(new MyAuthenticator());
			URL url = new URL(XMLUrl);
			HttpURLConnection connection =(HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/yang.data+json");
			InputStream input = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
			String line=null;
			while((line=br.readLine())!=null){
				temp.append(line+"\n");
			}
			br.close();
			
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp.toString();
	}
	
	public static String getFileString(){//读取特定配置文件的方法
		String fileString = "";
		String line = null;
		BufferedReader br=null;
		String path = "C://web_project/device/src/main/java/com/yd/json/apkinfo.json";// /home/catty/apache-tomcat-7.0.61/webapps/device/WEB-INF/classes/json/apkinfo.json";//锟斤拷要锟侥讹拷锟侥地凤拷
		File file = new File(path);
		try {
			InputStream in = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			while((line=br.readLine())!=null){
				fileString+=line;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return fileString;
	}
	//topo页面node节点信息与静态配置文件信息整合之后的包含所有对象的json格式的字符串
	public  static String getUpdateString(){
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject jsonObject = JSONObject.fromObject(TheUtil.getFileString());
		//JSONArray ctrlArray = jsonObject.getJSONArray("Controller");
		JSONArray NFVArray = jsonObject.getJSONArray("NFV");
		JSONArray dotArray = jsonObject.getJSONArray("dot");
		GetSwitchInfoService gsis = new GetSwitchInfoService();
		List<SwitchNode> switchList = gsis.getSwitchBean();
		GetCtrlInfoService gcis = new GetCtrlInfoService();
		List<CtrlNode> ctrlList = gcis.getCtrlBean();
		GetEdgeInfoService geis = new GetEdgeInfoService();
		List<EdgeLink> edgeList = geis.getEdgeBean();
		map.put("Controller", ctrlList);
		map.put("Switch",switchList);
		map.put("NFV", NFVArray);
		map.put("dot", dotArray);
		map.put("edge", edgeList);
		String jsonStr = JSONObject.fromObject(map).toString();
		System.out.println(jsonStr);
		return jsonStr;
	}
	//读取文件工具类
	public static String ReadFile(String Path){
		BufferedReader reader = null;
		String laststr = "";
		try{
//			ClassLoader classLoader = ContServiceImpl.class.getClassLoader();
//			InputStream in = classLoader.getResourceAsStream(Path);
//			InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while((tempString = reader.readLine()) != null){
				laststr += tempString;
			}
			reader.close();
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				if(reader != null){
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		return laststr;
	}
	//得到switch基本统计信息的json格式的字符串
	public static String getSwitchBasicInfoString(){
		GetSwitchBasicInfoService gsbis = new GetSwitchBasicInfoService();
		List<SwitchBasicInfo> list = gsbis.getSwitchBasicInfoBean();
		String str = JSONArray.fromObject(list).toString();
//		String switchBasicInfoString = str.substring(1,str.length()-1);
		return str;	
	}
	public static String getSwtFromIp(String ip){
		List<SwitchBasicInfo> list = new ArrayList<SwitchBasicInfo>();
		GetSwitchBasicInfoService gsbis = new GetSwitchBasicInfoService();
		List<SwitchBasicInfo> list1 = gsbis.getSwitchBasicInfoBean();
		for(SwitchBasicInfo item:list1){
			String swtIp = item.getIp();
			if(ip.equals(swtIp)){
				list.add(item);
			}
		}
		String swtStr = JSONArray.fromObject(list).toString();
		return swtStr;
	}
}
