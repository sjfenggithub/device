package com.yd.controller;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yd.service.IContService;

@Controller
@RequestMapping("/contController")
public class ContController extends BaseController {

	@Autowired
	private IContService contService;
	
	@RequestMapping("/selectContList")
	@ResponseBody
	public void selectContList(HttpServletResponse response){
		System.out.println("控制器信息");
		String json = null;
		try {
			json = contService.selectContList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.outString(json, response);
	}
}

	