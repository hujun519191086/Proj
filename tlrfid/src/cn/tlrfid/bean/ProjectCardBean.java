package cn.tlrfid.bean;

import cn.tlrfid.framework.BaseBean;

public class ProjectCardBean extends BaseBean {
	
	private int id;
	
	private String name;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	private String tagCode;
	
	public String getTagCode() {
		return tagCode;
	}
	
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}
	
	@Override
	public String getInTag() {
		return tagCode;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectCardBean other = (ProjectCardBean) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjectCardBean [id=" + id + ", name=" + name + ", tagCode=" + tagCode + "]";
	}
	
}
