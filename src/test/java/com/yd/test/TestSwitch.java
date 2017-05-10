package com.yd.test;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yd.model.Vtn;
import com.yd.service.IGetVtnInfo;
import com.yd.service.ISwitchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestSwitch {

	@Autowired
	private ISwitchService switchService;
	@Autowired
	private IGetVtnInfo vtnService;
	
	@Test
	public void select(){
		try {
			String selectSwitch = switchService.selectSwitch();
			System.out.println(selectSwitch);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void vtntest(){
		Vtn vtn=null;
		try {
			vtn = vtnService.getVtnBean();
			System.out.println(JSONObject.fromObject(vtn).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

	