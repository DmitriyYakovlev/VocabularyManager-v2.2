package com.yakovlev.prod.vocabularymanager.connection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import android.util.Log;

/**
 * 
 * @author codescriptor
 * 
 */

public class RouteBuilder {

	private static final String DIVIDER = "=";
	public static final String API_VERSION_1 = "v.1/";
	public static final String API_VERSION_1_1 = "v.1.1/";
	public static final String API_VERSION_2 = "v.2/";

	public static String buildRoute(String apiVersion, String baseRoute, Map<String, String> parameters) {
		if (baseRoute == null)
			return "";
		StringBuilder route = new StringBuilder();
		route.append(apiVersion);
		route.append(baseRoute);
		if (parameters == null || parameters.isEmpty())
			return route.toString();
		route.append("?");
		String firstParameter = parameters.keySet().iterator().next();
		String encodedKey = encode(firstParameter);
		route.append(encodedKey);
		route.append(DIVIDER);
		String firstValue = encode(parameters.get(firstParameter));
		route.append(firstValue);
		parameters.remove(firstParameter);

		Set<String> keySet = parameters.keySet();
		String encodedParameter = "";
		for (String key : keySet) {
			String parameter = parameters.get(key);
			encodedKey = encode(key);
			if (parameter != null && parameter.length() > 0) {
				encodedParameter = encode(parameter);
				route.append("&");
				route.append(encodedKey);
				route.append(DIVIDER);
				route.append(encodedParameter);
			}
		}
		return route.toString();
	}
	public static String buildRouteWithoutParameters(String apiVersion, String baseRoute) {
		if (baseRoute == null)
			return "";
		StringBuilder route = new StringBuilder();
		route.append(apiVersion);
		route.append(baseRoute);
		return route.toString();
	}

	public static String buildRouteWithoutParametersWithId(String apiVersion, String baseRoute, String devId) {
		if (baseRoute == null)
			return "";
		StringBuilder route = new StringBuilder();
		route.append(apiVersion);
		route.append(baseRoute);
		route.append("/");
		route.append(devId);
		return route.toString();
	}
	
	public static String encode(String value) {
		try {
			return URLEncoder.encode(value, InternetConst.utf_8);
		} catch (UnsupportedEncodingException ex) {
			Log.e(RouteBuilder.class.getSimpleName(), "Wrong encoding", ex);
		}
		return "";
	}

}
