package com.yakovlev.prod.vocabularymanager.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class HttpHelper {

	private static final String NETWORK_ERROR = "A network error occured";
	private static final String NETWORK_ERROR_CAN_NOT_SIGN_UP = "Can not sign up";
	private static final String NETWORK_ERROR_NO_STATUS = "Network error occured. No status.";
	private static final String NETWORK_ERROR_NO_CONTENT = "No content from http request";
	private static final String NETWORK_ERROR_NO_CONTENT_IN_RESPONSE = "No content in response";
	private static final String NETWORK_ERROR_INPUT_STREAM_NOT_CLOSE = "Can not close input stream";
	private static final String NETWORK_ERROR_CAN_NOT_CONSUME_RESPONSE = "Can not consume the response";

	private static final int BUFFER_SIZE = 64;

	public static String SERVER_SCHEMA = "http://";
	public static String SERVER_ADDRESS = "192.168.0.105";
	private static int SERVER_BACKEND_PORT = 8080;
	public static String SERVER_BACKEND_URL = SERVER_SCHEMA + SERVER_ADDRESS;
	private static String SERVER_BACKEND_CONTROLLER = "/diplom/AndroidClientServlet/";
	private static AuthScope AUTHSCOPE = new AuthScope(SERVER_ADDRESS, SERVER_BACKEND_PORT, AuthScope.ANY_REALM,
			AuthScope.ANY_SCHEME);

	private MyHttpClient client;


	public HttpHelper(String login, String password, Context context) {
		client = new MyHttpClient(context);
		client.getCredentialsProvider().setCredentials(AUTHSCOPE, new UsernamePasswordCredentials(login, password));
	}


	public HttpHelper(Context context) {
		String login = "";
		String password = "";
		client = new MyHttpClient(context);
		client.getCredentialsProvider().setCredentials(AUTHSCOPE, new UsernamePasswordCredentials(login, password));
	}

	public NetworkResponse doGetRequest(String route) throws NetworkException {
		String str = buildUrl(route);
		HttpGet httpGet = new HttpGet(buildUrl(route));
		try {
			return doHttpRequest(httpGet);
		} catch (AuthenticationException ex) {
			throw new NetworkException(NETWORK_ERROR_CAN_NOT_SIGN_UP);
		}
	}

	public NetworkResponse doGetRequestWithHeaders(String route, String apiKey) throws NetworkException {
		// String str = buildUrl(route);
		HttpGet httpGet = new HttpGet(buildUrl(route));
		httpGet.setHeader("Content-Type", "application/json");
		httpGet.setHeader("api_key", apiKey);
		httpGet.setHeader("Accept", "application/json");
		try {
			return doHttpRequest(httpGet);
		} catch (AuthenticationException ex) {
			throw new NetworkException(NETWORK_ERROR_CAN_NOT_SIGN_UP);
		}
	}


	public NetworkResponse doPost(String route, List<NameValuePair> nameValuePairs) throws NetworkException {
		HttpPost httpPost = new HttpPost(buildUrl(route));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			return doHttpRequest(httpPost);
		} catch (AuthenticationException e) {
			throw new NetworkException(NETWORK_ERROR_CAN_NOT_SIGN_UP);
		} catch (UnsupportedEncodingException e) {
			throw new NetworkException(NETWORK_ERROR_CAN_NOT_SIGN_UP);
		}
	}

	public NetworkResponse doPost(String route, String jsonStr, Header[] headers) throws NetworkException {
		// String str = buildUrl(route);

		HttpPost httpPost = new HttpPost(buildUrl(route));
		try {
			httpPost.setEntity(setStringEntityByJson(jsonStr));
			httpPost.setHeaders(headers);
			NetworkResponse netR = doHttpRequest(httpPost);
			return netR;
		} catch (AuthenticationException e) {
			throw new NetworkException(NETWORK_ERROR_CAN_NOT_SIGN_UP);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public StringEntity setStringEntityByJson(String json) throws UnsupportedEncodingException {
		StringEntity entity = new StringEntity(json, HTTP.UTF_8);
		entity.setContentType("application/json");
		return entity;
	}

	public static List<NameValuePair> setNameValuePairsByJson(String jsone) {
		NameValuePair namVal = new BasicNameValuePair("json", jsone);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(namVal);
		return nameValuePairs;
	}

	private NetworkResponse doHttpRequest(HttpUriRequest request) throws NetworkException, AuthenticationException {

		HttpResponse response = executeHttpRequest(request);
		checkStatus(response);

		String result = handleResponse(response.getEntity());

		Header[] headers = response.getAllHeaders();
		NetworkResponse networkResponse = new NetworkResponse(result, headers, response.getStatusLine().getStatusCode());

		return networkResponse;
	}

	private void checkStatus(HttpResponse response) throws NetworkException, AuthenticationException {
		StatusLine status = response.getStatusLine();
		if (status == null) {
			throw new NetworkException(NETWORK_ERROR_NO_STATUS);
		}

		int statusCode = status.getStatusCode();

		if (statusCode != InternetConst.STATUS_OK) {
			Log.e(HttpHelper.class.toString(), String.valueOf(statusCode) + " : " + status.getReasonPhrase());
			throw new NetworkException(String.valueOf(statusCode) + " : " + status.getReasonPhrase());
		} else {
			Log.i(HttpHelper.class.toString(), String.valueOf(statusCode) + " : " + status.getReasonPhrase());
		}
	}

	private String handleResponse(HttpEntity entity) throws NetworkException {
		if (entity == null) {
			throw new NetworkException(NETWORK_ERROR_NO_CONTENT);
		}

		String result = getResponseString(entity);
		consumeEntity(entity);
		return result;
	}

	private String getResponseString(HttpEntity entity) throws NetworkException {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStream = entity.getContent();
			inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader, BUFFER_SIZE);

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IllegalStateException ex) {
			throw new NetworkException(NETWORK_ERROR_NO_CONTENT_IN_RESPONSE);
		} catch (IOException ex) {
			throw new NetworkException(NETWORK_ERROR_INPUT_STREAM_NOT_CLOSE);
		} finally {
			if (inputStream != null)
				StreamUtils.closeStream(inputStream);
			if (inputStreamReader != null)
				StreamUtils.closeStream(inputStreamReader);
		}
		return sb.toString();
	}

	private void consumeEntity(HttpEntity entity) throws NetworkException {
		try {
			entity.consumeContent();
		} catch (IOException ex) {
			throw new NetworkException(NETWORK_ERROR_CAN_NOT_CONSUME_RESPONSE);
		}
	}

	public static String buildUrl(String routeString) {
		String route = routeString;
		if (route.startsWith("/")) {
			route = route.substring(1);
		}
		String routStr = SERVER_BACKEND_URL + ":" + SERVER_BACKEND_PORT + SERVER_BACKEND_CONTROLLER + route;

		return routStr;
	}

	private HttpResponse executeHttpRequest(HttpUriRequest request) throws NetworkException {
		try {
			return this.client.execute(request);
		} catch (ClientProtocolException ex) {
			throw new NetworkException(NETWORK_ERROR, ex);
		} catch (IOException ex) {
			NetworkException nex = new NetworkException(InternetConst.NETWORK_STATUS_NO_NETWORK, ex.getLocalizedMessage());
			throw new NetworkException(NETWORK_ERROR, nex);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NetworkException(NETWORK_ERROR, ex);
		}
	}

}
