package com.stone.util;

import com.alibaba.fastjson.JSONObject;
import com.stone.conf.constant.error.ErrorEnum;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class ReturnJSONUtils {

	/**
	 * 返回一个returnData为空对象的成功消息的json
	 */
	public static JSONObject successJson() {
		return successJson(new JSONObject());
	}

	/**
	 * 返回一个返回码为100的json
	 */
	public static JSONObject successJson(Object returnData) {
		return new JSONObject()
			.fluentPut("success", true)
			.fluentPut("returnData", returnData);
	}

	/**
	 * 返回错误信息JSON
	 */
	public static JSONObject errorJson(ErrorEnum errorEnum) {
		return new JSONObject()
			.fluentPut("success", false)
			.fluentPut("returnCode", errorEnum.getErrorCode())
			.fluentPut("msg", errorEnum.getErrorMsg())
			.fluentPut("returnData", new JSONObject());
	}

	/**
	 * 将request参数值转为json
	 */
	public static JSONObject request2Json(HttpServletRequest request) {
		JSONObject requestJson = new JSONObject();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] pv = request.getParameterValues(paramName);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < pv.length; i++) {
				if (pv[i].length() > 0) {
					if (i > 0) {
						sb.append(",");
					}
					sb.append(pv[i]);
				}
			}
			requestJson.put(paramName, sb.toString());
		}
		return requestJson;
	}

}
