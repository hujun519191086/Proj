package com.qc188.com.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;

//异步加载图片栈类  
public class AsyncImageTask {
	private static final String TAG = "AsyncImageTask";
	// 缓存图片
	public LruCache<String, Drawable> imageMap;

	public AsyncImageTask() {
		this.imageMap = new LruCache<String, Drawable>((int) Runtime
				.getRuntime().maxMemory() / 8);
	}

	public Drawable loadImage(final Object id, final String imageUrl,
			final ImageCallback callback) {
		// 查看缓存内是否已经加载过此图片
		final String tempUrl = MD5Utils.md5(imageUrl) + "_fix";
		Drawable d = LoadFromNow(tempUrl);
		if (d != null) {
			return d;
		}

		if (ConstantValues.NO_PIC) {
			return null;
		}
		// 更新图片UI的消息队列
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				callback.imageLoaded((Drawable) message.obj, id);
			}
		};
		// 加载图片的线程
		new Thread() {
			@Override
			public void run() {
				// 加载图片
				Drawable drawable = null;
				try {
					drawable = BitmapUtils.getDrawable(imageUrl);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (drawable != null) {
					BitmapUtils.saveFile(drawable, tempUrl);
					// 加入缓存集合中
					imageMap.put(tempUrl, drawable);
					// 通知消息队列更新UI
					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				}
			}
		}.start();
		return null;
	}

	public Drawable LoadFromNow(String tempUrl) {
		Drawable drawable = loadImageFromCache(tempUrl, imageMap);
		if (drawable == null) {
			drawable = loadImageFromFile(tempUrl);
			if (drawable != null) {
				LogUtil.d(TAG, "put:" + tempUrl + " drawable:" + drawable);
				imageMap.put(tempUrl, drawable);
			}
		}
		return drawable;
	}

	// ID为记录标识,标识是那条记录iamge
	public Drawable loadImage(final Object objectId, final String imageUrl,
			final int width, final int height, final ImageCallback callback) {
		// 查看缓存内是否已经加载过此图片

		final String tempUrl = MD5Utils.md5(imageUrl);

		Drawable d = loadImageFromCache(tempUrl, imageMap);
		if (d != null) {
			return d;
		}
		Bitmap bt = BitmapUtils.getBitmap(tempUrl);

		LogUtil.d(TAG, bt + "");
		if (bt != null) {
			return new BitmapDrawable(bt);
		}
		if (ConstantValues.NO_PIC) {
			return null;
		}
		// 更新图片UI的消息队列
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				callback.imageLoaded((Drawable) message.obj, objectId);
			}
		};
		// 加载图片的线程
		new Thread() {
			@Override
			public void run() {
				// 加载图片
				Drawable drawable = loadImageByUrl(imageUrl, width, height);
				// drawable = new BitmapDrawable(
				// BitmapUtils.compressImage(BitmapUtils
				// .convertDrawable2BitmapByCanvas(drawable)));
				if (drawable != null) {
					BitmapUtils.saveFile(drawable, tempUrl);
					// 加入缓存集合中
					imageMap.put(tempUrl, drawable);
					// 通知消息队列更新UI
					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				}
			}
		}.start();
		return null;
	}

	public static Drawable loadImageFromCache(String tempUrl,
			LruCache<String, Drawable> imageMap) {
		Drawable drawable = null;
		if (imageMap != null && imageMap.get(tempUrl) != null) {
			drawable = imageMap.get(tempUrl);
		}
		return drawable;
	}

	public static Drawable loadImageFromFile(String tempUrl) {
		Bitmap bt = BitmapUtils.getBitmap(tempUrl);
		LogUtil.d(TAG, bt + "");
		if (bt == null) {
			return null;
		}
		Drawable drawable = new BitmapDrawable(bt);
		return drawable;
	}

	// 根据图片地址加载图片，并保存为Drawable
	public static Drawable loadImageByUrl(String imageUrl, int width, int height) {

		URL url = null;
		InputStream inputStream = null;
		try {
			url = new URL(imageUrl);
			inputStream = (InputStream) url.getContent();
			Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;

			Bitmap bt = BitmapFactory.decodeStream(inputStream, null, opts);

			// 然后使用方法decodeByteArray（）方法解析编码，生成Bitmap对象。

			 inputStream.close();
			 inputStream = (InputStream) url.getContent();
			if (width < 0) {

			} else {
				int xScale = opts.outWidth / width;
				int yScale = opts.outHeight / height;
				opts.inSampleSize = xScale > yScale ? xScale : yScale;
			}
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			opts.inJustDecodeBounds = false;
			opts.outWidth = width;
			opts.outHeight = height;
			bt = BitmapFactory.decodeStream(inputStream, null, opts);
			Drawable dr = new BitmapDrawable(bt);
			return dr;
		} catch (Exception e) {
			LogUtil.d(TAG, "connection fale");
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024]; // 用数据装
		int len = -1;
		while ((len = is.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		// 关闭流一定要记得。
		return outstream.toByteArray();
	}



	// 利用借口回调，更新图片UI
	public interface ImageCallback {
		public void imageLoaded(Drawable image, Object id);
	}
}