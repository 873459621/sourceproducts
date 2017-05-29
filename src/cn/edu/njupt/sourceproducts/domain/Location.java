package cn.edu.njupt.sourceproducts.domain;

/**
 * 封装产品溯源信息的Javabean
 * 
 * @author hhw
 */
public class Location {

	private String ltime;
	private String location;

	public Location(String ltime, String location) {
		this.ltime = ltime;
		this.location = location;
	}

	public String getLtime() {
		return ltime;
	}

	public void setLtime(String ltime) {
		this.ltime = ltime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
