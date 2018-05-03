package cn.tlrfid.bean;

import cn.tlrfid.framework.BaseBean;

public class SafetyItemBean extends BaseBean {
	
	private int category;
	private String description;
	private int id;
	private int level;
	private String levelName;
	private String title;
	
	private boolean qualify; // 合格
	
	private boolean mistake; // 不合格
	
	public boolean isQualify() {
		return qualify;
	}
	
	public void setQualify(boolean qualify) {
		this.qualify = qualify;
	}
	
	public boolean isMistake() {
		return mistake;
	}
	
	public void setMistake(boolean mistake) {
		this.mistake = mistake;
	}
	
	public int getCategory() {
		return category;
	}
	
	public void setCategory(int category) {
		this.category = category;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getLevelName() {
		return levelName;
	}
	
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return "SafetyItemBean [category=" + category + ", description=" + description + ", id=" + id + ", level="
				+ level + ", levelName=" + levelName + ", title=" + title + ", qualify=" + qualify + ", mistake="
				+ mistake + "]";
	}
	
}
