package control.inter;

public enum PersonStatus {
	NOT_USER(4), // 不是用户
	NOT_EMAIL_VERIFICATION(5), // 没有邮箱验证的用户
	NOT_PHONENUM_VERIFICATION(6),//没有手机验证的用户
	ALL_CLEAR(7), // 正常用户
	BE_LOCK(8);// 被锁住
	public int value;

	PersonStatus(int value) {
		this.value = value;
	}
}
