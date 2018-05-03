package cn.tlrfid.bean;

/**
 * 施工人员
 * 
 * @author sk
 * 
 */
public class ConstructionPeson {
	
	private String name; // 姓名
	
	private String style; // 工种
	
	public ConstructionPeson(String name, String style) {
		super();
		this.name = name;
		this.style = style;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStyle() {
		return style;
	}
	
	public void setStyle(String style) {
		this.style = style;
	}
	
}
