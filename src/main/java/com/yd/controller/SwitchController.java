package com.yd.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yd.service.ISwitchService;
import com.yd.util.TheUtil;
@Controller
@RequestMapping("/switchController")
public class SwitchController extends BaseController {

	@Autowired
	private ISwitchService switchService;
	private String ips=null;
	@RequestMapping(value="/selectSwitchFromIp", method=RequestMethod.POST)
	@ResponseBody
	public void SelectSwitchFromIp(@RequestParam String ip){
		ips=ip;
	}
	@RequestMapping("/selectSwitch")
	@ResponseBody
	public void selectSwitch(HttpServletResponse response){
		System.out.println("Switch信息");
		String json = null;
		try {
			if(ips==null){
			json =switchService.selectSwitch();
			}else{
			json=TheUtil.getSwtFromIp(ips);
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		super.outString(json, response);
	}
	
}

	