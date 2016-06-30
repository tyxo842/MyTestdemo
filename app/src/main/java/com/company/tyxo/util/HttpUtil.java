package com.company.tyxo.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUtil {

    private final static String TAG = "HttpUtil";

    /**
     * Get请求
     *
     * @param url 请求地址
     * @return 返回byte数组
     */
    public static byte[] httpGet(final String url) {
        if (url == null || url.length() == 0) {
            Log.e(TAG, "httpGet, url is null");
            return null;
        }
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setDoInput(true); //允许输入流，即允许下载
            connection.setDoOutput(true); //允许输出流，即允许上传
            connection.setUseCaches(false); //不使用缓冲
            connection.setRequestMethod("GET"); //使用get请求
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = connection.getInputStream();   //获取输入流，此时才真正建立链接
                return inputStreamToBytes(inputStream);
            } else {
                return null;
            }

        } catch (Exception e) {
            Log.e(TAG, "httpGet exception, e = " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public static byte[] httpPost(byte[] requestContent, String url) {
        if (url == null || url.length() == 0) {
            Log.e(TAG, "httpGet, url is null");
            return null;
        }
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("POST"); //使用post请求
            if (null != requestContent) {
                connection.getOutputStream().write(requestContent);
            }
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = connection.getInputStream();   //获取输入流，此时才真正建立链接
                return inputStreamToBytes(inputStream);
            } else {
                return null;
            }

        } catch (Exception e) {
            Log.e(TAG, "httpGet exception, e = " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] inputStreamToBytes(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] bys = new byte[1024];
        try {
            while ((len = inputStream.read(bys)) != -1) {
                byteArrayOutputStream.write(bys, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    ;


}
