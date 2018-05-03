package cn.tlrfid.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import cn.tlrfid.framework.ConstantValues;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

public class BitmapUtil {
	
	/**
	 * 从资源中获取Bitmap
	 * 
	 * @param context
	 * @param resource
	 * @return
	 */
	public static Bitmap getBitmap(Context context, int resource) {
		Resources res = context.getResources();
		return BitmapFactory.decodeResource(res, resource);
	}
	
	/**
	 * Bitmap → byte[]
	 * 
	 * @param bm
	 * @return
	 */
	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	
	public static Bitmap createBitmapForColor(int color, int width, int height) {
		int[] rgb = new int[width * height];
		for (int i = 0; i < rgb.length; i++) {
			rgb[i] = color;
		}
		return Bitmap.createBitmap(rgb, width, height, Config.ARGB_4444);
	}
	
	public static Drawable createColorDrawabe(int color) {
		return new BitmapDrawable(createBitmapForColor(color, 1, 1));
	}
	
	/**
	 * byte[] → Bitmap
	 * 
	 * @param b
	 * @return
	 */
	public Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}
	
	/**
	 * Bitmap缩放
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}
	
	/**
	 * 将Drawable转化为Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}
		// 取 drawable 的长宽
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		
		// 取 drawable 的颜色格式
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}
	
	/**
	 * 获得圆角图片
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		
		return output;
	}
	
	/**
	 * 获得带倒影的图片
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		
		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w, h / 2, matrix, false);
		
		Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2), Config.ARGB_8888);
		
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);
		
		canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);
		
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight()
				+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, h, w, bitmapWithReflection.getHeight() + reflectionGap, paint);
		
		return bitmapWithReflection;
	}
	
	/**
	 * Drawable缩放
	 * 
	 * @param drawable
	 * @param w
	 * @param h
	 * @return
	 */
	public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		// drawable转换成bitmap
		Bitmap oldbmp = drawableToBitmap(drawable);
		// 创建操作图片用的Matrix对象
		Matrix matrix = new Matrix();
		// 计算缩放比例
		float sx = ((float) w / width);
		float sy = ((float) h / height);
		// 设置缩放比例
		matrix.postScale(sx, sy);
		// 建立新的bitmap，其内容是对原bitmap的缩放后的图
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
		return new BitmapDrawable(newbmp);
	}
	
	/**
	 * 组合涂鸦图片和源图片
	 * 
	 * @param src 源图片
	 * @param watermark 涂鸦图片
	 * @return
	 */
	public static Bitmap doodle(ImageView iv, Bitmap src, Bitmap watermark, int x, int y) {
		// 另外创建一张图片
		Bitmap newb = Bitmap.createBitmap(iv.getWidth(), iv.getHeight(), Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		
		System.out.println(iv.getWidth() + "=======" + iv.getHeight());
		Canvas canvas = new Canvas(newb);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		System.out.println(canvas.getHeight() + "-" + canvas.getWidth());
		
		RectF rectf = new RectF(0, 0, iv.getWidth(), iv.getHeight());
		
		Paint paint = new Paint();
		paint.setAntiAlias(true); // 抗锯齿
		canvas.drawBitmap(src, null, rectf, null);
		// canvas.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入原图片src
		
		// canvas.drawBitmap(watermark, (src.getWidth() - watermark.getWidth()) / 2,
		// (src.getHeight() - watermark.getHeight()) / 2, null); // 涂鸦图片画到原图片中间位置
		
		System.out.println("x=" + x + "y=" + y);
		
		canvas.drawBitmap(watermark, x, y, null); //
		
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		watermark.recycle();
		watermark = null;
		return newb;
	}
	
	/**
	 * 上传bitmap
	 * 
	 * @param bitmap
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String sendData(Bitmap bitmap, String fileName) throws Exception {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			// here, change it to your php;
			
			HttpPost httpPost = new HttpPost(ConstantValues.UP_LOAD_IMAGE);
			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 80, bos);
			byte[] data = bos.toByteArray();
			
			entity.addPart("file", new ByteArrayBody(data, fileName + ".jpg"));
			
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost, localContext);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),
					HTTP.UTF_8));
			String line = reader.readLine();
			return line;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
