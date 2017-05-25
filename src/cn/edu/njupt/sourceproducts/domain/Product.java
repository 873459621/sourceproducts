package cn.edu.njupt.sourceproducts.domain;

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
