package enumPKG;

public enum GOODS_ORDER {
	PROGRESS(0, "progress DESC"),
	NEED_COUNT(1, "needCount ASC"),
	FOLLOW(2, "followCount DESC"),
	OPEN_TIME(3, "open_time DESC"),
	TOTAL_COUNT(4, "totalCount DESC"),
	PUBLISH_TIME(5, "pub_time DESC");
	
	
	public int value;
	public String columnName;

	GOODS_ORDER(int value, String columnName) {
		this.value = value;
		this.columnName = columnName;
	}
}
