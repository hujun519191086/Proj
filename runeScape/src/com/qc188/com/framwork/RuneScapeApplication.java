package com.qc188.com.framwork;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.qc188.com.util.DensityUtil;

public class RuneScapeApplication extends Application {
	@Override
	public void onCreate() {
		DensityUtil.setContext(getApplicationContext());

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheExtraOptions(DensityUtil.getWidthPixels(), DensityUtil.getWidthPixels()).memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		super.onCreate();
	}
}
