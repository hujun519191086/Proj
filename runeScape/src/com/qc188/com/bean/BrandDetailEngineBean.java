package com.qc188.com.bean;

import java.util.List;
import java.util.Map;

import com.qc188.com.framwork.BaseBean;

/**
 * 车系首页，每个条目信息
 * 
 * @author mryang
 * 
 */
public class BrandDetailEngineBean extends BaseBean {

	/**
	 * "engine": [ { "title": "1.8升，涡轮增压160马力", "list": [ { "introduce":
	 * "2015款30TFIS搜懂舒适型", "sale": "27.28万", "detail": "前置前驱6挡手动", "brandId":
	 * 1236123 }, { "introduce": "2015款30TFIS搜懂舒适型", "sale": "27.28万", "detail":
	 * "前置前驱6挡手动", "brandId": 1236123 } ] }, { "title": "2升，涡轮增压22马力", "list": [
	 * { "introduce": "2015款30TFIS搜懂舒适型", "sale": "27.28万", "detail":
	 * "前置前驱6挡手动", "brandId": 1236123 }, { "introduce": "2015款30TFIS搜懂舒适型",
	 * "sale": "27.28万", "detail": "前置前驱6挡手动", "brandId": 1236123 } ] }
	 */

	private String title;

	private List<CopyOfbrandDetailEngineItem> list;

	public void checkCompairList(Map<String, ?> maps) {
		if (list == null || list.size() < 1) {
			return;
		}

		// for (int i = 0; i < list.size(); i++)
		// {
		// CopyOfbrandDetailEngineItem cdei = list.get(i);

		// cdei.setInCompair((maps == null) || !(maps.get(cdei.getBrandId()
		// + "") == null));
		// }
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<CopyOfbrandDetailEngineItem> getList() {
		return list;
	}

	public void setList(List<CopyOfbrandDetailEngineItem> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "brandDetailEngineBean [title=" + title + ", list=" + list + "]";
	}

}
