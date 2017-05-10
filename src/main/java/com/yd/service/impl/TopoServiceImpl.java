package com.yd.service.impl;

import org.springframework.stereotype.Service;


import com.yd.service.ITopoService;
import com.yd.util.TheUtil;

@Service("topoService")
public class TopoServiceImpl implements ITopoService {

	
	@Override
	public String selectTopoList() throws Exception {
		String jsonobject = TheUtil.getUpdateString();
		return jsonobject;
	}

	
}

	