package cn.edu.njupt.sourceproducts.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.njupt.sourceproducts.domain.Product;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;
import cn.edu.njupt.sourceproducts.utils.HttpUtils;

/**
 * 产品信息表的dao，单例设计模式
 * 
 * @author hhw
 */
public class ProductDao {

	private static ProductDao mInstance;

	private ProductDao() {
	}

	/**
	 * 获取ProductDao的实例
	 * 
	 * @return ProductDao的实例
	 */
	public static ProductDao getInstance() {
		if (mInstance == null) {
			mInstance = new ProductDao();
		}
		return mInstance;
	}

	/**
	 * 解析json字符串，得到产品的List集合
	 * 
	 * @param json
	 *            要解析的字符串
	 * @return 产品的List集合
	 * @throws JSONException
	 *             解析字符串时出现的异常
	 */
	private static List<Product> toProductList(String json) {
		List<Product> productList = new ArrayList<Product>();

		try {
			JSONObject obj;
			JSONArray array = new JSONArray(json);

			for (int i = 0; i < array.length(); i++) {
				obj = array.getJSONObject(i);

				int pid = obj.getInt("pid");
				String pname = obj.getString("pname");
				double price = obj.getDouble("price");
				String image = obj.getString("image");
				String des = obj.getString("des");
				int cid = obj.getInt("cid");

				productList
						.add(new Product(pid, pname, price, image, des, cid));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return productList;
	}

	/**
	 * 根据指定位置索引值获取20个产品的数据
	 * 
	 * @param index
	 *            位置索引值
	 * @return 20个产品的List集合
	 */
	public List<Product> getProductList(int index) {
		String path = ConstantValue.IP_ADDRESS
				+ "/ProductServlet?method=productList&index=" + index;

		return toProductList(HttpUtils.getStringByGet(path));
	}

	/**
	 * 根据指定位置索引值和产品分类ID，获取20个产品的数据
	 * 
	 * @param index
	 *            位置索引值
	 * @param cid
	 *            产品分类ID
	 * @return 20个产品的List集合
	 */
	public List<Product> getProductList(int index, int cid) {
		String path = ConstantValue.IP_ADDRESS
				+ "/ProductServlet?method=productList&index=" + index + "&cid="
				+ cid;

		return toProductList(HttpUtils.getStringByGet(path));
	}

	/**
	 * 获取产品的总个数
	 * 
	 * @return 产品的总个数
	 */
	public int getTotal() {
		String path = ConstantValue.IP_ADDRESS + "/ProductServlet?method=total";

		return Integer.parseInt(HttpUtils.getStringByGet(path));
	}

	/**
	 * 根据产品分类ID，获取分类产品的总个数
	 * 
	 * @param cid
	 *            产品分类ID
	 * @return 分类产品的总个数
	 */
	public int getTotal(int cid) {
		String path = ConstantValue.IP_ADDRESS
				+ "/ProductServlet?method=total&cid=" + cid;

		return Integer.parseInt(HttpUtils.getStringByGet(path));
	}

}
