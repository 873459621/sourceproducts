package cn.edu.njupt.sourceproducts.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 提供http连接的工具类
 * 
 * @author hhw
 */
public class HttpUtils {

	/**
	 * 开启一个POST连接
	 * 
	 * @param path
	 *            要获取连接的地址
	 * @param data
	 *            要传输的数据
	 * @return 连接的实例
	 * @throws Exception
	 *             获取连接时出现的异常
	 */
	public static HttpURLConnection openPostConnection(String path, String data)
			throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();

		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5000);
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", data.length() + "");
		conn.setDoOutput(true);

		conn.getOutputStream().write(data.getBytes());

		return conn;
	}

	/**
	 * 开启一个GET连接
	 * 
	 * @param path
	 *            要获取连接的地址
	 * @return 连接的实例
	 * @throws Exception
	 *             获取连接时出现的异常
	 */
	public static HttpURLConnection openGetConnection(String path)
			throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();

		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);

		return conn;
	}

	/**
	 * 通过POST方法，从网络路径地址中读取字符串
	 * 
	 * @param path
	 *            网络路径地址
	 * @param data
	 *            POST请求发送的数据
	 * @return 字符串
	 */
	public static String getStringByPost(String path, String data) {
		try {
			return getString(openPostConnection(path, data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 通过GET方法，从网络路径地址中读取字符串
	 * 
	 * @param path
	 *            网络路径地址
	 * @return 字符串
	 */
	public static String getStringByGet(String path) {
		try {
			return getString(openGetConnection(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 从网络连接中读取字符串
	 * 
	 * @param conn
	 *            网络连接对象
	 * @return 字符串
	 */
	private static String getString(HttpURLConnection conn) {
		try {
			if (conn.getResponseCode() == 200) {
				InputStream in = conn.getInputStream();
				return StreamUtils.streamToString(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
		return null;
	}

}
