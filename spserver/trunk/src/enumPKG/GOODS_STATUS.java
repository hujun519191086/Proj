package enumPKG;

public enum GOODS_STATUS {
	
	NOT_OPEN(0, "未开放"),
	NOT_PUBLISH(1, "正在进行"),
	SOON_PUBLISH(2, "即将揭晓");
	
	public int value;
	public String columnName;

	GOODS_STATUS(int value, String columnName) {
		this.value = value;
		this.columnName = columnName;
	}
}
