package com.yakovlev.prod.vocabularymanager.connection;

import java.util.HashMap;
import java.util.Map;

public class RequestParametrsSeter {

	public static Map<String, String> getParametersMapWithApiKey(String api_key) {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(InternetConst.PREFERENCE_API_KEY, api_key);
		return parameters;
	}

	public static Map<String, String> getParametersMap(String api_key, String constant) {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(constant, api_key);
		return parameters;
	}

	public static Map<String, String> getParametersMapForLogin(String userName, String password) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(InternetConst.REQUEST_PASWORD, password);
		parameters.put(InternetConst.REQUEST_EMAIL, userName);
		return parameters;
	}

	public static Map<String, String> getParametersMapForGetTimeLimits(String devId) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(InternetConst.REQUEST_DIVICE_ID, devId);
		return parameters;
	}

	public static Map<String, String> getParametersMapForGetDeviceGROUPSForCurentUser(String api_key) {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(InternetConst.PREFERENCE_API_KEY, api_key);
		return parameters;
	}

}
