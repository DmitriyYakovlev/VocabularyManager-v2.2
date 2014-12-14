package com.yakovlev.prod.vocabularymanager.connection;

public class InternetConst {

	public static final String utf_8 = "UTF-8";

	public static final String MSG_ASK_FOR_UPDATE = "MSG_ASK_FOR_UPDATE";
	public static final String MSG_ASK_FOR_UPDATE_ANSWER = "MSG_ASK_FOR_UPDATE_ANSWER";
	public static final String MSG_SEND_UPDATES = "MSG_SEND_UPDATES";
	public static final String MSG_UPDATE_RECEIVED = "MSG_UPDATE_RECEIVED";

	public static final String MSG_SEND_ORDERS = "MSG_SEND_ORDERS";
	public static final String MSG_SEND_TABLE_CATEGORIES = "MSG_SEND_TABLE_CATEGORIES";
	public static final String MSG_SEND_TABLES = "MSG_SEND_TABLES";
	public static final String MSG_SEND_FOOD_CATEGORIES = "MSG_SEND_FOOD_CATEGORIES";
	public static final String MSG_SEND_FOOD = "MSG_SEND_FOOD";
	public static final String MSG_READY_TO_RECEIVE = "MSG_READY_TO_RECEIVE";
	public static final String MSG_RECEIVED = "MSG_RECEIVED";
	public static final String MSG_READY_TO_SEND = "MSG_READY_TO_SEND";
	public static final String MSG_RECEIVE_ALL_UPDATES = "MSG_RECEIVE_ALL_UPDATES";
	public static final String MSG_NOT_RECEIVED = "MSG_NOT_RECEIVED";

	public static final String AUTENTIFICATION_ENABLED = "enable_login";
	public static final String PASSWORD = "password";
	public static final String DEFAULT_PRINTER_ADDRESS = "default_printer_address_and_port";
	public static final String DISH_PRINTER_ADDRESS = "dish_printer_address_and_port";
	public static final String BEVERAGES_PRINTER_ADDRESS = "beverages_printer_address_and_port";
	public static final String OTHER_PRINTER_ADDRESS = "other_printer_address_and_port";

	public static final int NETWORK_STATUS_NO_NETWORK = 0;
	public static final int SUCCESS = 1;
	public static final int ERROR = -1;

	public static final int STATUS_OK = 200;

	public static final int PAGER_MORE_OBJECTS_ON_SERVER = 10;
	public static final int PAGER_NO_OBJECTS_ON_SERVER = 11;
	public static final int PAGER_DEFAULT_PAGE_SIZE = 100;

	public static final String API_ACTION_GET_CONTACTS = "get_contacts";// Temporary

	// requests
	public static final String REQUEST_PASWORD = "password";
	public static final String REQUEST_EMAIL = "email";
	public static final String REQUEST_LOGIN = "login";
	public static final String REQUEST_DEVICES_LIST = "devices-list";
	public static final String REQUEST_DEVICE_GROUPS_LIST = "get-device-groups";
	public static final String REQUEST_DEVICE_REGISTER_DEVICE = "register-device";
	public static final String REQUEST_DEVICE_GET_CERTIFICATES = "get-certificates";
	public static final String REQUEST_DEVICE_GET_SECURITY_SETTINGS = "security-settings";
	public static final String REQUEST_UPDATE_SECURITY_SETTINGS = "update-security-settings";
	public static final String REQUEST_GET_TIME_LIMITS = "get-time-limits";
	public static final String REQUEST_SET_TIME_LIMITS = "set-time-limits";
	public static final String REQUEST_UID = "uid";
	public static final String REQUEST_DIVICE_ID = "device_id";
	public static final String TARIFFS = "tariffs";

	// JSON constants
	public static final String JSON_SUCCESS = "success";
	public static final String JSON_RESULT = "result";
	public static final String JSON_MESSAGE = "messages";
	// public static final String VPN_PACKAGE_NAME = "net.openvpn.openvpn";
	public static final String VPN_PACKAGE_NAME = "de.blinkt.openvpn";

	// shared preferences
	public static final String PREFERENCE_API_KEY = "api_key";
	public static final String PREFERENCE_DEVICE_NAME = "device_name";

	public static final String OS_TYPE = "android";


	// public static final String OPEN_VPN_MIME_TYPE = "*/*";
	public static final String OPEN_VPN_MIME_TYPE = "application/x-openvpn-profile";
	public static final String OPEN_VPN_EXTENTION = ".ovpn";
	public static final String FOLDER_FOR_DOVNLOADED_PROFILES_NAME = ".1Secuclouder";
	public static final String ZIP_EXTENTION = ".zip";

}
