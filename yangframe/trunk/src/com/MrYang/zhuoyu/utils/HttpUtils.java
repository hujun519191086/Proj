package com.MrYang.zhuoyu.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import android.text.TextUtils;

/**
 * 联网工具,获取Json 和 上传文件
 * 
 * @author jieranyishen
 * 
 */
public class HttpUtils
{
    private static int connectionTimeout = 10 * 1000;
    private static int requestTimeout = 15 * 1000;

    /**
     * 设置联网的超时时间
     * 
     * @param connection
     *            连接超时时间 默认 10秒
     * @param request
     *            相应超时时间 默认 15秒
     */
    public static void setTimeout(int connection, int request)
    {
        connectionTimeout = connection;
        requestTimeout = request;
    }

    public static String getJson_Asyn(String path)
    {
        try
        {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(connectionTimeout);
            conn.setReadTimeout(requestTimeout);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            int response = conn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK)
            {
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                int len = 0;
                byte[] buffer = new byte[2048];

                InputStream in = conn.getInputStream();
                while ((len = in.read(buffer)) != -1)
                {
                    bout.write(buffer, 0, len);
                }
                String result = new String(bout.toByteArray(), "utf-8");
                in.close();
                return result;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    
    /**
     * 上传文档
     * 
     * @param fileName
     *            完整路径名称
     * @return
     * @throws Exception
     */
    public static String uploadFile(String uploadUrl, String fileName) throws Exception
    {
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();

            
            if(TextUtils.isEmpty(uploadUrl)){
                throw new IllegalArgumentException("没有设置上传的url值!!!");
            }
            
            HttpPost httpPost = new HttpPost(uploadUrl);
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] data = bos.toByteArray();

            entity.addPart("file", new ByteArrayBody(data, fileName));

            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), HTTP.UTF_8));
            String line = reader.readLine();
            return line;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

}
