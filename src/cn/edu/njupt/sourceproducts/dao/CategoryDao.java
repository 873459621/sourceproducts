package cn.edu.njupt.sourceproducts.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.njupt.sourceproducts.domain.Category;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;
import cn.edu.njupt.sourceproducts.utils.HttpUtils;

/**
 * 产品分类表的dao，单例设计模式
 * 
 * @author hhw
 */
public class CategoryDao {

	private static CategoryDao mInstance;

	private CategoryDao() {
	}

	/**
	 * 获取CategoryDao的实例
	 * 
	 * @return CategoryDao的实例
	 */
	public static CategoryDao getInstance() {
		if (mInstance == null) {
			mInstance = new CategoryDao();
		}
		return mInstance;
	}

	/**
	 * 获取产品分类的数据
	 * 
	 * @return 产品分类的List集合
	 */
	public List<Category> getCategoryList() {
		String path = ConstantValue.IP_ADDRESS + "/CategoryServlet";
		return toCategoryList(HttpUtils.getStringByGet(path));
	}

	/**
	 * 解析json字符串，得到产品分类的List集合
	 * 
	 * @param json
	 *            要解析的字符串
	 * @return 产品分类的List集合
	 * @throws JSONException
	 */
	private List<Category> toCategoryList(String json) {
		List<Category> categoryList = new ArrayList<Category>();

		try {
			JSONObject obj;
			JSONArray array = new JSONArray(json);

			for (int i = 0; i < array.length(); i++) {
				obj = array.getJSONObject(i);

				int cid = obj.getInt("cid");
				String cname = obj.getString("cname");

				categoryList.add(new Category(cid, cname));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return categoryList;
	}

}
