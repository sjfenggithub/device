package com.yd.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yd.service.ITopoService;

@Controller
@RequestMapping("/topoController")
public class TopoController extends BaseController {

	@Autowired
	private ITopoService topoService;
	
	@RequestMapping("/selectToptList")
	@ResponseBody
	public void selectToptList(HttpServletResponse response){
		System.out.println("拓扑数据信息");
		String json = null;
		try {
			json = topoService.selectTopoList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.outString(json, response);
	}
	
}

	