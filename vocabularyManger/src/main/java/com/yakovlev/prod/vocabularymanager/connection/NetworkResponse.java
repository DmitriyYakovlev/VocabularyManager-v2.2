package com.yakovlev.prod.vocabularymanager.connection;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class NetworkResponse {

	private static final String DATE_HEADER_NAME = "Date";
	private String response;
	private String date;
	private int status;
	private int applicationStatusCode;
	private String applicationMessage;

	public NetworkResponse(String response) throws NetworkException {
		this.response = response;
		parseApplicationStatusCode();
	}

	public NetworkResponse(String response, Header[] headers, int statusCode) throws NetworkException {
		this.response = response;
		this.date = getDateHeader(headers);
		this.status = statusCode;
		parseApplicationStatusCode();
	}

	private String getDateHeader(Header[] headers) {
		for (int i = 0; i < headers.length; i++) {
			if (headers[i].getName().equalsIgnoreCase(DATE_HEADER_NAME)) {
				return headers[i].getValue();
			}
		}
		return "";
	}

	public String getResponse() {
		return response;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public int getApplicationStatusCode() {
		return applicationStatusCode;
	}

	public String getApplicationMessage() {
		return applicationMessage;
	}

	private void parseApplicationStatusCode() throws NetworkException {
		try {
			if (status == 200) {
				JSONObject basic = new JSONObject(response);
				String success = basic.getString("success");
				if (success.equals("false")) {
					JSONArray mesage = basic.getJSONArray("messages");
					if (mesage.get(0).equals("Unauthorized")) {
						throw new NetworkException(
								"Another device is logged in under current login or conection with server was lost. Try to login again.");
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			applicationStatusCode = 500;
		}
	}

	public boolean getSuccessFromResponse() throws JSONException {
		JSONObject basic = new JSONObject(response);
		Boolean successValue = basic.getBoolean(InternetConst.JSON_SUCCESS);

		return successValue;
	}

	public String getMessageResponse() throws JSONException {
		JSONObject basic = new JSONObject(response);
		String successValue = basic.getString(InternetConst.JSON_MESSAGE);

		return successValue;
	}

	public String getResultFromResponse() throws JSONException {
		JSONObject basic = new JSONObject(response);
		return basic.getString(InternetConst.JSON_RESULT);
	}

	private void parseApplicationMessage() {
		try {
			JSONObject basic = new JSONObject(response);
			applicationMessage = basic.getString("status");
		} catch (JSONException e) {
			e.printStackTrace();
			applicationMessage = "";
		}
	}

}
