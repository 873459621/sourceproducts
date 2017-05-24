package cn.edu.njupt.sourceproducts.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences工具类
 * 
 * @author hhw
 */
public class SPUtils {

	private static SharedPreferences sp;

	/**
	 * 初始化sp对象
	 * 
	 * @param context
	 *            上下文
	 */
	private static void initSP(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences("settings.config",
					Context.MODE_PRIVATE);
		}
	}

	/**
	 * 存入一个Boolean类型的值到sp配置文件中
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            存入值的键名
	 * @param value
	 *            存入的值
	 */
	public static void putBoolean(Context context, String key, boolean value) {
		initSP(context);
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * 从sp配置文件中取出一个Boolean类型的值
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            取出值的键名
	 * @param defValue
	 *            取出值不存在时返回的默认值
	 * @return 取出的值
	 */
	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		initSP(context);
		return sp.getBoolean(key, defValue);
	}

	/**
	 * 存入一个String类型的值到sp配置文件中
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            存入值的键名
	 * @param value
	 *            存入的值
	 */
	public static void putString(Context context, String key, String value) {
		initSP(context);
		sp.edit().putString(key, value).commit();
	}

	/**
	 * 从sp配置文件中取出一个String类型的值
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            取出值的键名
	 * @param defValue
	 *            取出值不存在时返回的默认值
	 * @return 取出的值
	 */
	public static String getString(Context context, String key, String defValue) {
		initSP(context);
		return sp.getString(key, defValue);
	}

	/**
	 * 从sp配置文件中删除一个节点
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            要移除节点的键名
	 */
	public static void remove(Context context, String key) {
		initSP(context);
		sp.edit().remove(key).commit();
	}

	/**
	 * 存入一个int类型的值到sp配置文件中
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            存入值的键名
	 * @param value
	 *            存入的值
	 */
	public static void putInt(Context context, String key, int value) {
		initSP(context);
		sp.edit().putInt(key, value).commit();
	}

	/**
	 * 从sp配置文件中取出一个int类型的值
	 * 
	 * @param context
	 *            上下文环境
	 * @param key
	 *            取出值的键名
	 * @param defValue
	 *            取出值不存在时返回的默认值
	 * @return 取出的值
	 */
	public static int getInt(Context context, String key, int defValue) {
		initSP(context);
		return sp.getInt(key, defValue);
	}

}
