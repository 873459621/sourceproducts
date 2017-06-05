package cn.edu.njupt.sourceproducts.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.njupt.sourceproducts.domain.Location;
import cn.edu.njupt.sourceproducts.engine.ConstantValue;
import cn.edu.njupt.sourceproducts.utils.HttpUtils;

/**
 * 产品溯源信息表的dao，单例设计模式
 * 
 * @author hhw
 */
public class LocationDao {

	private static LocationDao mInstance;

	private LocationDao() {
	}

	/**
	 * 获取LocationDao的实例
	 * 
	 * @return LocationDao的实例
	 */
	public static LocationDao getInstance() {
		if (mInstance == null) {
			mInstance = new LocationDao();
		}
		return mInstance;
	}

	/**
	 * 根据产品ID，获取产品溯源信息的List集合
	 * 
	 * @param pid
	 *            产品ID
	 * @return 产品溯源信息的List集合
	 */
	public List<Location> getLocationList(int pid) {
		String path = ConstantValue.IP_ADDRESS + "/LocationServlet?pid=" + pid;

		return toLocationList(HttpUtils.getStringByGet(path));
	}

	/**
	 * 解析json字符串，得到产品溯源信息的List集合
	 * 
	 * @param json
	 *            要解析的字符串
	 * @return 产品溯源信息的List集合
	 * @throws JSONException
	 */
	private List<Location> toLocationList(String json) {
		List<Location> locationList = new ArrayList<Location>();

		try {
			JSONObject obj;
			JSONArray array = new JSONArray(json);

			for (int i = 0; i < array.length(); i++) {
				obj = array.getJSONObject(i);

				String ltime = obj.getString("ltime");
				String location = obj.getString("location");

				locationList.add(new Location(ltime, location));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return locationList;
	}

}
