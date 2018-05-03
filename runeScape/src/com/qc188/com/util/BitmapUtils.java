package com.qc188.com.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class BitmapUtils {
	private static final String TAG = "BitmapUtils";

	public static Bitmap getImage(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("GET");
		InputStream inStream = conn.getInputStream();
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			Bitmap temp = BitmapFactory.decodeStream(inStream);
			saveFile(temp, path);
			return temp;
		}
		return null;
	}

	public static void LogMemoryDrawable(Drawable drawable) {
		Bitmap bitmap = convertDrawable2BitmapByCanvas(drawable);
		int size = bitmap.getRowBytes() * bitmap.getHeight();
		LogUtil.d("sale_memory:", size / 1024 + "kb");
	}

	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 80;
		while (baos.toByteArray().length / 1024 > 80) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		image.recycle();
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	public static Drawable getDrawable(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("GET");
		Options opts = new BitmapFactory.Options();
		InputStream inStream = conn.getInputStream();
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			Bitmap temp = BitmapFactory.decodeStream(inStream, null, opts);
			saveFile(temp, path);
			return new BitmapDrawable(temp);
		}
		return null;
	}

	public static void recycleView(View view) {
		Drawable drawable = view.getBackground();
		if (drawable != null && drawable instanceof BitmapDrawable) {
			((BitmapDrawable) drawable).getBitmap().recycle();
		}
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}

	public static void saveFile(Drawable drawable, String fileName) {
		Bitmap bt = convertDrawable2BitmapByCanvas(drawable);
		try {
			saveFile(bt, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveFile(Bitmap bm, String fileName) throws IOException {
		File dirFile = new File(ConstantValues.FILE_PATH);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(ConstantValues.FILE_PATH + "/" + fileName);
		// LogUtils.d(TAG, "myCaptureFile:"+myCaptureFile);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		if (bm != null && bos != null) {
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		}
		bos.flush();
		bos.close();
	}

	public static Bitmap getBitmap(String path) {
		File dirFile = new File(ConstantValues.FILE_PATH + "/" + path);
		LogUtil.d(TAG, "getFile:" + dirFile + "dirfile:" + dirFile.exists()
				+ "  文件存在吗:" + dirFile.exists());
		if (dirFile.exists()) {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;

			opt.inPurgeable = true;

			opt.inInputShareable = true;
			// opt.inSampleSize = 2;

			Bitmap bmp = BitmapFactory.decodeFile(dirFile.toString(), opt);
			// if (bmp != null) {
			// bmp = compressImage(bmp);
			// }
			return bmp;
		}
		return null;
	}

	public Bitmap readBitMap(Context context, int resId) {

		BitmapFactory.Options opt = new BitmapFactory.Options();

		opt.inPreferredConfig = Bitmap.Config.RGB_565;

		opt.inPurgeable = true;

		opt.inInputShareable = true;

		// 获取资料
		InputStream is = context.getResources().openRawResource(resId);

		return BitmapFactory.decodeStream(is, null, opt);

	}

	public static Bitmap convertDrawable2BitmapByCanvas(Drawable drawable) {

		BitmapDrawable bd = (BitmapDrawable) drawable;

		Bitmap bm = bd.getBitmap();

		return bm;
	}
}
