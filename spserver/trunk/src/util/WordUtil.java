package util;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

//涉及到单词的工具
public class WordUtil {
	// <a name="sendMailClick"
	// href="<%=basePath%>/servlet/LoginInfoServlet">发送邮件</a>
	private static Random randomNumber  = new Random();
	private static final String[] ABCDEFG = { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z" };
	private static final String[] NUMBER = { "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "0" };

	public static String getEmailVerificationCode() {

		return getCode(4, 6);
	}

	private static String getCode(int minSize, int maxSize) {

		
//		int codeSize = minSize
//				+ (int) (Math.random() * ((maxSize - minSize) + 1));
//
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < codeSize; i++) {
//			boolean isNumber = ((int) (Math.random() * 100)) % 2 == 0;
//
//			if (isNumber) {
//				sb.append(NUMBER[(int) (Math.random() * 10)]);
//			} else {
//				sb.append(ABCDEFG[(int) (Math.random() * 26)]);
//			}
//		}
//
//		return sb.toString();
		return String.valueOf(randomNumber.nextInt(1000000));
	}

	private static IdWorker idWorker = new IdWorker(9, 5);

	public static boolean isEmpty(String text) {
		if (text == null || text == "") {
			return true;
		}
		return false;
	}

	public static long getId() {
		return idWorker.nextId();
	}
}
