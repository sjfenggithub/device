package com.yd.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class BaseController {

	//(打印字符串)
	public void writeJson(Object object, HttpServletResponse response){
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//(打印字符串)
	protected void outString(String json, HttpServletResponse response) {
		PrintWriter writer = null;
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		try {
			writer = response.getWriter();
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				writer.close();
				writer = null;
			}
		}
	}
}