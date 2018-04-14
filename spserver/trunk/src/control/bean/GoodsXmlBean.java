package control.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GoodsXmlBean {

	private String m_config_dir_path;

	private String picPath;
	private File configFile;

	public long configLastModifyTime;

	private ArrayList<GoodsItem> goods_item_list = new ArrayList<GoodsItem>();

	public GoodsXmlBean(String path) {
		m_config_dir_path = path;
		picPath = m_config_dir_path + File.separator + "pic/";
		String config_path = m_config_dir_path + File.separator + "goods.xml";
		configFile = new File(config_path);
	}

	/**
	 * 返回false 是没有进行更新. 代表界面不需要更新
	 * 
	 * @return
	 */
	public boolean updateGoods() {

		if (configFile.lastModified() == configLastModifyTime) {
			return false;
		}
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(configFile);
			Element root = document.getRootElement();

			//读取 root节点下的item
			for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();
				List<Attribute> list = element.attributes();
				GoodsItem gi = new GoodsItem();

				//读取 item 的属性值
				for (Iterator<Attribute> it = list.iterator(); it.hasNext();) {
					Attribute attr = it.next();
					String name = attr.getName();

//					if (name.equals("numberId")) {
//						gi.setGid(attr.getValue());
//						gi.initPic(picPath);
//					} else if (name.equals("totalCount")) {
//						gi.setTotalCount(attr.getValue());
//					} else if (name.equals("onecCount")) {
//						gi.setOnecCount(attr.getValue());
//					} else if (name.equals("goods_name")) {
//						gi.setGoods_name(attr.getValue());
//					} else if (name.equals("second_name")) {
//						gi.setSecond_name(attr.getValue());
//					} else if (name.equals("open_time")) {
//						gi.setOpen_time(attr.getValue());
//					} else if (name.equals("jiexiao_total_count")) {
//						gi.setJiexiao_total_count(attr.getValue());
//					}
					//地区 area
				}
				goods_item_list.add(gi);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// 数据重新读取
		return true;
	}

}
