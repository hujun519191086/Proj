package com.qc188.com.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 图片下载器
 * 
 * @author mryang
 * 
 */
public class ImageLoadUtil {
	private static DisplayImageOptions diOptions = null;
	private static AnimateFirstDisplayListener afdListener = new AnimateFirstDisplayListener();
	private static boolean leak = true;

	/**
	 * 默认下载配置。
	 * 
	 * @param url
	 *            路径
	 * @param view
	 *            要设置的imageview
	 * @param defaultPic
	 *            未下载完毕时的图片
	 */
	public static void loadImageFromDefault(String url, ImageView view,
			int defaultPic) {

		diOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(defaultPic)
				.showImageForEmptyUri(defaultPic).bitmapConfig(Config.RGB_565)
				.showImageOnFail(defaultPic).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true).build();
		LogUtil.systemCurrentLog("imageLoadTime", "4");
		if (!ConstantValues.NO_PIC) {
			LogUtil.systemCurrentLog("imageLoadTime", "5");
			ImageLoader.getInstance().displayImage(url, view, diOptions, null);
			LogUtil.systemCurrentLog("imageLoadTime", "6");
		}
	}

	public static void loadImage(String url, ImageView view, int defaultPic,
			int width, int height) {

		Options decodingOptions = new Options();
		decodingOptions.outHeight = height;
		decodingOptions.outWidth = width;
		decodingOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		decodingOptions.inPurgeable = true;
		decodingOptions.inInputShareable = true;
		diOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(defaultPic)
				.decodingOptions(decodingOptions).bitmapConfig(Config.RGB_565)
				.showImageForEmptyUri(defaultPic).showImageOnFail(defaultPic)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.build();
		LogUtil.systemCurrentLog("imageLoadTime", "4");
		if (!ConstantValues.NO_PIC) {
			LogUtil.systemCurrentLog("imageLoadTime", "5");
			ImageLoader.getInstance().displayImage(url, view, diOptions, null);

			LogUtil.systemCurrentLog("imageLoadTime", "6");

		}

	}

	private static final String TAG = "AnimateFirstDisplayListener";

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		public static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;

				LogUtil.d(TAG, "imageBitmap:" + loadedImage.getWidth()
						+ "    getHeight:" + loadedImage.getHeight());
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
