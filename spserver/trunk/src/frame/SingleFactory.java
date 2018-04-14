package frame;

import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import util.SpeedLimitUtil;

import com.mchange.v2.c3p0.DataSources;

import control.bean.AD;
import control.bean.Catelog;
import control.db.BuyHistoryDB;
import control.db.GoodsDB;
import control.db.NumberDB;
import control.db.ShoppingCartDB;
import control.db.TradeNoDB;
import control.db.personDB.IOSDB;
import control.db.personDB.WebDB;

public class SingleFactory {

	private static SingleFactory dbFactory = new SingleFactory();
	private IOSDB iosdb = new IOSDB();
	private WebDB webdb = new WebDB();
	private GoodsDB goodsdb = new GoodsDB();
	private NumberDB numberdb = new NumberDB();
	private BuyHistoryDB buyHistorydb = new BuyHistoryDB();
	private ShoppingCartDB shoppingCartdb = new ShoppingCartDB();
	private TradeNoDB tradeNodb = new TradeNoDB();
	
	private static DataSource userDB;
	private static DataSource numberDB;
	private static DataSource goodsDB;
	private static DataSource buyHistoryDB;
	private static DataSource shoppingCartDB;
	private static DataSource tradeNoDB;
	private static String webinfo_URL;
	private static ArrayList<Catelog> catelogList;
	
	private static ArrayList<AD> adList;

	private GoodsConfigReader goodsConfig = new GoodsConfigReader();

	private SingleFactory() {
	}

	public static SingleFactory ins() {

		return dbFactory;
	}

	// ---------------------------------数据库操作类
	public IOSDB getIOSDBIns() {
		return iosdb;
	}

	public WebDB getWebDBIns() {
		return webdb;
	}

	/**
	 * 获取物品数据库
	 * 
	 * @return
	 */
	public GoodsDB getGoodsIns() {
		return goodsdb;
	}
	//获取购买历史数据库
	public BuyHistoryDB getBuyHistoryIns(){
		return buyHistorydb;
	}
	public NumberDB getNumberIns() {
		return numberdb;
	}

	//获取购物车数据库信息
	public ShoppingCartDB getShoppingCart(){
		return shoppingCartdb;
	}
	//获取订单数据库信息
	public  TradeNoDB getTradeNodb(){
		return  tradeNodb;
	}
	// --------------------------------------------------------

	// 数据源获取----------------------------------- 获取用户的db
	public DataSource getUserDataSource() {
		return userDB;
	}

	// 获取中奖号码的db
	public DataSource getNumberDataSource() {
		return numberDB;
	}

	// 获取物品的db
	public DataSource getGoodsDataSource() {
		return goodsDB;
	}

	// 购买历史
	public static DataSource getBuyHistoryDataSource() {
		return buyHistoryDB;
	}

	// -------------------------------------------------

	public GoodsConfigReader getGoodsConfigReader() {
		return goodsConfig;
	}
	public static ArrayList<Catelog> getCatelogs(){
		return catelogList;
	}
	public static ArrayList<AD> getADs(){
		return adList;
	}

	/**
	 * 初始化c3p0连接池，初始化目录信息
	 */
	static {
		init();
		initDBSource();
		initCatelog();
		initAD();
	}
	private static final String JDBC_DRIVER = "driverClass";
	public static final String USER_URL = "spservice";
	public static final String NUMBER_URL = "number";
	public static final String GOODS_URL = "goods";
	public static final String BUY_HISTORY_URL = "buy_history";
	public static final String SHOPPING_CART_URL = "shopping_cart";
	public static final String TRADENO_URL = "tradeno";
	

	private static final void initDBSource() {
		Properties c3p0Pro = new Properties();
		try {
			// 加载配置文件
			String websiteURL = webinfo_URL + "c3p0.properties";
//			String path = SingleFactory.class.getResource("/").getPath();
//			String websiteURL = (path.replace("/build/classes", "")
//					.replace("%20", " ").replace("/classes", "") + "c3p0.properties")
//					.replaceFirst("/", "");
			FileInputStream in = new FileInputStream(websiteURL);
			c3p0Pro.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String drverClass = c3p0Pro.getProperty(JDBC_DRIVER);
		if (drverClass != null) {
			try {
				// 加载驱动类
				Class.forName(drverClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}

		Properties jdbcpropes = new Properties();
		Properties c3propes = new Properties();
		for (Object key : c3p0Pro.keySet()) {
			String skey = (String) key;
			if (skey.startsWith("c3p0.")) {
				c3propes.put(skey, c3p0Pro.getProperty(skey));
			} else {
				jdbcpropes.put(skey, c3p0Pro.getProperty(skey));
			}
		}

		try {
			// 建立连接池
			DataSource unPooled = DataSources.unpooledDataSource(
					c3p0Pro.getProperty(USER_URL), jdbcpropes);
			userDB = DataSources.pooledDataSource(unPooled, c3propes);

			SingleFactory.ins().getIOSDBIns().setDataSource(userDB);
			SingleFactory.ins().getWebDBIns().setDataSource(userDB);

			// 建立幸运号码连接池
			unPooled = DataSources.unpooledDataSource(
					c3p0Pro.getProperty(NUMBER_URL), jdbcpropes);
			numberDB = DataSources.pooledDataSource(unPooled, c3propes);
			SingleFactory.ins().getNumberIns().setDataSource(numberDB);

			// 建立物品连接池
			unPooled = DataSources.unpooledDataSource(
					c3p0Pro.getProperty(GOODS_URL), jdbcpropes);
			goodsDB = DataSources.pooledDataSource(unPooled, c3propes);
			SingleFactory.ins().getGoodsIns().setDataSource(goodsDB);

			// 建立用户的购买记录连接池
			unPooled = DataSources.unpooledDataSource(
					c3p0Pro.getProperty(BUY_HISTORY_URL), jdbcpropes);
			buyHistoryDB = DataSources.pooledDataSource(unPooled, c3propes);
			SingleFactory.ins().getBuyHistoryIns().setDataSource(buyHistoryDB);
			
			
//			
			// SingleFactory.ins().get().setDataSource(userDB);
			// 建立用户g购物车连接池
			unPooled = DataSources.unpooledDataSource(
					c3p0Pro.getProperty(SHOPPING_CART_URL), jdbcpropes);
			shoppingCartDB = DataSources.pooledDataSource(unPooled, c3propes);
			SingleFactory.ins().getShoppingCart().setDataSource(shoppingCartDB);
			
			//创建用户的订单连接池
			unPooled = DataSources.unpooledDataSource(
					c3p0Pro.getProperty(TRADENO_URL), jdbcpropes);
			tradeNoDB = DataSources.pooledDataSource(unPooled, c3propes);
			SingleFactory.ins().getTradeNodb().setDataSource(tradeNoDB);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private SpeedLimitUtil slu = new SpeedLimitUtil();

	public SpeedLimitUtil getSpeedLimitUtil() {

		return slu;
	}
	private static void init(){
		//获取WEB-INF路径
		String path = SingleFactory.class.getResource("/").getPath();
		webinfo_URL = path.replace("/build/classes", "").replace("%20", " ").replace("/classes", "").replaceFirst("/", "");
	}
	private static void initCatelog(){
		//从xml文件读取分类信息
		File catelog_xml = new File(webinfo_URL+"catelog.xml");
		SAXReader reader = new SAXReader();
		try{
			catelogList = new ArrayList<Catelog>();
			Document document = reader.read(catelog_xml);
			Element root = document.getRootElement();
			List<Element> childElements = root.elements();
			for(Element child:childElements){
				Catelog catelog = new Catelog();
				catelog.setCatelogId(Integer.valueOf(child.attributeValue("catelogId")));
				catelog.setCatelogName(child.attributeValue("catelogName"));
				catelog.setImg(child.attributeValue("img"));
				
				catelogList.add(catelog);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private static void initAD(){
		//广告
		File ad_xml = new File(webinfo_URL+"ad.xml");
		SAXReader reader = new SAXReader();
		try{
			adList = new ArrayList<AD>();
			Document document = reader.read(ad_xml);
			Element root = document.getRootElement();
			List<Element> childElements = root.elements();
			for(Element child:childElements){
				AD ad = new AD();
				ad.setADId(Integer.valueOf(child.attributeValue("ADId")));
				ad.setADName(child.attributeValue("ADName"));
				ad.setImg(child.attributeValue("img"));
				ad.setLink(child.attributeValue("link"));
				
				adList.add(ad);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
