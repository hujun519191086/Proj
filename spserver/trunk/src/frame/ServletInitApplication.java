package frame;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletInitApplication extends HttpServlet implements
		ServletContextListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 199554L;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// 初始化目录
		Global_value.REAL_PATH = arg0.getServletContext().getRealPath("/");
		Global_value.CLASS_PATH = ServletInitApplication.class.getResource("/")
				.getPath();
		// 初始化配置目录
		Global_value.CONFIG_FILE_NAME = Global_value.REAL_PATH + "/config";
		Global_value.CONTEXT_PATH = arg0.getServletContext().getContextPath();

		// = LoggerFactory.getLogger(AJob.class);
		String logPath = (getClass().getResource("/").getPath()
				.replace("/build/classes", "").replace("%20", " ")
				.replace("/classes", "") + Global_value.LOG4J_PROPERTIES_PATH)
				.replaceFirst("/", "");
		PropertyConfigurator.configure(logPath);// 初始化日志

		Logger logger = LoggerFactory.getLogger(getClass());
		logger.debug("load logger success!");

		// 创建单例,并且初始化配置读取
		SingleFactory.ins().getGoodsConfigReader().init(arg0);
		// 初始化购买历史数据库，创建定时事件
		SingleFactory.ins().getBuyHistoryIns().init();
	}
}
