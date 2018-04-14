package enumPKG;

public enum PUBLISH_STATUS {
	WAITING_PUBLISH(0, "等待揭晓"),
	PUBLISHED(1, "已揭晓");
	
	
	
	public int value;
	public String columnName;

	PUBLISH_STATUS(int value, String columnName) {
		this.value = value;
		this.columnName = columnName;
	}
}
