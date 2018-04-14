package control.inter;

public enum DBErrorCode {

	SUCCESS(0), // 操作成功
	LOGIN_ERROR_PASS_ERROR(1), // 密码错误
	LOGIN_ERROR_NO_USER(2), // 没有这个用户
	REGIST_FAILT(3), // 注册失败
	PASSWORD_ERROR(6), // 密码错误的用户

	BE_REGISTED(9), // 已经被注册过了
	MAIL_VERIFICATION_INSERT_FAILT(10), // 邮箱验证码插入失败

	GOODS_INSERT_FAILT_CANNOT_CHANGE(11), // 物品插入失败,已经是不能改变的物品了
	GOODS_INSERT_FAILT_NOT_YOURS(12), // 物品插入失败,该物品不是你的
	
	NUMBER_CREATE_ERROR_NUMBER_IN_USE(13), // 物品的幸运号码已经被创建了.所以没办法再创建
	
	LOAD_GOODS_FAILT_NO_GOODS(14),//加载物品失败,没有物品
	
	BUY_HISTORY_INSERT_FAILT(15),//购买记录插入失败
	GOODS_UPDATE_FAILT(16),//物品数据更新失败
	
	GOODS_UPDATE_FAILT_OUT_OF_RANGER(17),//更新物品失败，数据超出范围
	LOAD_GOODS_FAILT_UNKNOW_STATUS(18),//未知的物品状态
	
	PUB_HISTORY_INSERT_FAILT(19),//往期历史插入失败
	LOAD_PUBLISH_FAILT_NO_GOODS(20),//读取往期历史失败
	   
	RECENT_PUBLISH_INSERT_FAILT(21),//最新发布表插入数据失败
	
	PUBLISH_PROCEDURE_CREATE_FAILT(22),//初始化创建商品揭晓的过程函数失败
	
	UPDATE_RECENT_PUBLISH_EVENT_FAILT(23),//创建数据库定时更新最新揭晓数据表事件失败
	
	CODE_ORDER_UPDATE_FAILT(24),//更新物品夺宝号码列表失败
	
	LOAD_SHOPPING_CART_FAILT(25),//读取购物车信息失败
	
	INSERT_SHOOPING_CART_FAILT(26),//添加购物车失败
	
	DELETE_SHOOPING_CART_FAILT(27),//删除购物车信息失败
	
	LOAD_GOODS_BUY_HISTORY_FAILT(28),//读取物品参与记录失败
	LOAD_PERSON_BUY_HISTORY_FAILT(29),//读取用户参与记录失败
	
	LOAD_USER_INFO_FAILT(30),//读取用户个人信息失败
	
	UPDATE_USER_INFO_FAILT(31),//更新用户数据失败
	
	PHONENUM_VERIFICATION_INSERT_FAILT(32), // 手机验证码插入失败
	
	COMM_FAILT(999);// 通用失败

	private int value;

	DBErrorCode(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	private Object tag;
	public Object getTag()
	{
		return tag;
	}
	public void setTag(Object obj) {
		tag = obj;
	}

}
