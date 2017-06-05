package cn.edu.njupt.sourceproducts.dao;

import cn.edu.njupt.sourceproducts.engine.ConstantValue;
import cn.edu.njupt.sourceproducts.utils.HttpUtils;

/**
 * 订单表的dao，单例设计模式
 * 
 * @author hhw
 */
public class OrderDao {

	private static OrderDao mInstance;

	private OrderDao() {
	}

	/**
	 * 获取OrderDao的实例
	 * 
	 * @return OrderDao的实例
	 */
	public static OrderDao getInstance() {
		if (mInstance == null) {
			mInstance = new OrderDao();
		}
		return mInstance;
	}

	/**
	 * 提交订单到服务端
	 * 
	 * @param data
	 *            封装订单信息的字符串
	 * @return 服务端返回的信息
	 */
	public String summitOrder(String data) {
		String path = ConstantValue.IP_ADDRESS + "/OrderServlet";
		return HttpUtils.getStringByPost(path, data);
	}

}
