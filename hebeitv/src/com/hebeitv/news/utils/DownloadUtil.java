package com.hebeitv.news.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.Environment;
import android.os.Looper;

public class DownloadUtil
{
    private static String localPath = Environment.getExternalStorageDirectory() + "/hebeitv/download";

    public static String download(String url, OnDownloadFinishCallBack callBack)
    {

        String dirName = localPath;
        File f = new File(dirName);
        if (!f.exists())
        {
            f.mkdir();
        }
        String fileName = url.substring(url.lastIndexOf("/"));
        File tempFile = new File(localPath + fileName);
        if (tempFile.exists())
        {
            return tempFile.toString();
        }
        new Thread(new Runna(url, callBack, tempFile)).start();
        return null;
    }

    private static class Runna implements Runnable
    {
        private String url;
        private OnDownloadFinishCallBack callBack;
        private File fileName;

        public Runna(String url, OnDownloadFinishCallBack callBack, File fileName)
        {
            this.url = url;
            this.callBack = callBack;
            this.fileName = fileName;
        }

        @Override
        public void run()
        {
            URL uRL;
            try
            {
                uRL = new URL(url);
                // 打开连接
                URLConnection con = uRL.openConnection();
                // 输入流
                InputStream is = con.getInputStream();

                fileName.createNewFile();

                int len = 0;
                byte[] bs = new byte[1024 * 256];
                OutputStream os = new FileOutputStream(fileName);
                // 开始读取
                while ((len = is.read(bs)) != -1)
                {
                    os.write(bs, 0, len);
                }

                if (callBack != null)
                {
                    Looper.prepare();
                    callBack.onFinish(fileName);
                    Looper.loop();
                }
                // 完毕，关闭所有链接
                os.close();
                is.close();

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static interface OnDownloadFinishCallBack
    {
        public void onFinish(File obj);
    }
}
