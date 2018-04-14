package control.inter;

import control.bean.Person;

public interface IOSClicentInterface {
	public DataPair<DBErrorCode,Person> queryInfo4Mail(String mailBox);
	public DBErrorCode updatePerson(String mailBox, String verificationCode);
	public DBErrorCode registIOSClient(String mailBox, String verificationCode, String password,String device);//
}
