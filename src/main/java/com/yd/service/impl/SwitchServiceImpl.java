package com.yd.service.impl;

import org.springframework.stereotype.Service;

import com.yd.service.ISwitchService;
import com.yd.util.TheUtil;

@Service("switchService")
public class SwitchServiceImpl implements ISwitchService {

	TheUtil theUtil = new TheUtil();

	@Override
	public String selectSwitch() throws Exception {
		String switchBasicInfoString = theUtil.getSwitchBasicInfoString();//theUtil.getSwtFromIp("192.168.224.19"); //
		return switchBasicInfoString;
	}
	
}

	