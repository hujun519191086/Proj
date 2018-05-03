package cn.tlrfid.engine;

import java.util.ArrayList;

public interface RFIDReadEngine {
	ArrayList<String> readRFID(ArrayList<String> mEpcList);
	byte[] read6CUserData(byte[] mByte);
}
