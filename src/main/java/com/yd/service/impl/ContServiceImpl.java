package com.yd.service.impl;

import org.springframework.stereotype.Service;

import com.yd.service.IContService;
import com.yd.util.TheUtil;

@Service("contService")
public class ContServiceImpl implements IContService {
	
	TheUtil theUtil = new TheUtil();
	
	@Override
	public String selectContList() throws Exception {
		GetCtrlBasicInfoService gcbis = new GetCtrlBasicInfoService();
		String str = gcbis.getJsonString();
		return str;
	}
}

	