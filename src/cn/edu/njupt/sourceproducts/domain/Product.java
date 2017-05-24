package cn.edu.njupt.sourceproducts.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 封装产品的Javabean
 * 
 * @author hhw
 */
public class Product {

	private int pid;
	private String pname;
	private double price;
	private String image;
	private String des;
	private int cid;

	/**
	 * 解析json字符串，得到产品的List集合
	 * 
	 * @param json
	 *            要解析的字符串
	 * @return 产品的List集合
	 * @throws JSONException
	 *             解析字符串时出现的异常
	 */
	public static List<Product> toProductList(String json) {
		List<Product> productList = new ArrayList<Product>();
		try {
			JSONArray array = new JSONArray(json);
			
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				
				int pid = obj.getInt("pid");
				String pname = obj.getString("pname");
				double price = obj.getDouble("price");
				String image = obj.getString("image");
				
				productList.add(new Product(pid, pname, price, image));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return productList;
	}

	public Product(int pid, String pname, double price, String image,
			String des, int cid) {
		this(pid, pname, price, image);
		this.des = des;
		this.cid = cid;
	}

	public Product(int pid, String pname, double price, String image) {
		this.pid = pid;
		this.pname = pname;
		this.price = price;
		this.image = image;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

}
