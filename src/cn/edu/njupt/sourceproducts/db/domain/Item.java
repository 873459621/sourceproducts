package cn.edu.njupt.sourceproducts.db.domain;

/**
 * 购物车的bean对象
 * 
 * @author hhw
 */
public class Item {

	private int pid;
	private String pname;
	private double price;
	private String image;
	private String des;
	private int number;
	private double subtotal;

	public Item(int pid, String pname, double price, String image, String des,
			int number, double subtotal) {
		this.pid = pid;
		this.pname = pname;
		this.price = price;
		this.image = image;
		this.des = des;
		this.number = number;
		this.subtotal = subtotal;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

}
