package frame;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TreeMap;

import javax.servlet.ServletContextEvent;

import control.bean.GoodsXmlBean;

public class GoodsConfigReader {

	private TreeMap<String, GoodsXmlBean> goods_list = new TreeMap<String, GoodsXmlBean>();
	private String contextPath;

	public void init(ServletContextEvent context) {

		// 初始化物品目录
		Global_value.GOODS_FILE_NAME = Global_value.CONFIG_FILE_NAME
				+ "/goods_config/";
		// new Thread(new AutoCheckFileTime(goods_list)).start();
		
		
		//new AutoCheckFileTime(goods_list, Global_value.GOODS_FILE_NAME).run();
	}

	private class AutoCheckFileTime implements Runnable {

		TreeMap<String, GoodsXmlBean> m_area_goods_list;
		ArrayList<GoodsXmlBean> m_all_goods;
		private String m_path;
		File goods_dir;

		// 如果配置正在读取. 就不会重复读取
		private boolean inFix = false;

		public AutoCheckFileTime(TreeMap<String, GoodsXmlBean> goods_list,
				String path) {
			m_area_goods_list = goods_list;
			this.m_path = path;
			goods_dir = new File(m_path);
		}

		@Override
		public void run() {
			while (true) {

				System.out.println("ajhsadfjkhasdkfhsakjdhfkjasdhkf");
				if (inFix) {

				} else {
					fixXML();
				}

				try {
					Thread.sleep(Global_value.CHECK_GOODS_FILE_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void fixXML() {
			inFix = true;
			String[] goods_config_list = goods_dir.list();
			if (goods_config_list == null || goods_config_list.length <= 0) {// 不解析解析物品配置
				m_area_goods_list.clear();
				return;
			}

			for (int i = 0; i < goods_config_list.length; i++) {

				String itemFileName = goods_config_list[i];
				GoodsXmlBean gb = m_area_goods_list.get(itemFileName);

				if (gb == null) {
					gb = new GoodsXmlBean(m_path+itemFileName);//某一个地区
					m_area_goods_list.put(itemFileName, gb);//只放入地区物品
					m_all_goods.add(gb);//将所有物品放入
				}

				// 检查更新
				boolean hasUpdate = gb.updateGoods();
				System.out.println("hasUpdate:"+hasUpdate);
				
			}

			inFix = false;
		}
	}
}
