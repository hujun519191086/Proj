package cn.tlrfid.bean.warehouse;

public class AssetsCategoryBean {
	private String name;
	private String value;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "AssetsCategoryBean [name=" + name + ", value=" + value + "]";
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
