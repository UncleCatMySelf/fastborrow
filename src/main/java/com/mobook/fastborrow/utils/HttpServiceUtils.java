package com.mobook.fastborrow.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:49 2018\7\6 0006
 */
public class HttpServiceUtils {

    public static final String CHARACTER_ENCODING = "UTF-8";
    public static final String PATH_SIGN = "/";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return result;
    }

    public static String sendGetByDouban(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("ContentType","text/html;charset=UTF-8");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
//                System.err.println(line);
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
//            conn.setRequestProperty("Content-Type","text/html;charset=UTF-8");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Encoding", "gzip");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Server", "Apache-Coyote/1.1");

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static StringBuffer postServer(String urlString, JSONObject object){
        StringBuffer sb = new StringBuffer("");
        try {
            //创建连接
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.connect();
            //POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(object.toString());
            out.flush();
            out.close();
            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb;
    }


    public static InputStream postCodeServer(String urlString, Map<String, Object> params ){
        InputStream inputStream =null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(urlString);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        String body = com.alibaba.fastjson.JSON.toJSONString(params);
        StringEntity entity;
        try {
            entity = new StringEntity(body);
            entity.setContentType("image/png");
            httpPost.setEntity(entity);
            HttpResponse response;
            response = httpClient.execute(httpPost);
            inputStream = response.getEntity().getContent();
        }catch (Exception e){
            e.printStackTrace();
        }
        return inputStream;
    }


    public static String postURLServer(String urlString, Map<String, Object> params){
        String result = "";
        InputStream inputStream =null;
        BufferedReader in = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(urlString);
        httpPost.addHeader(HTTP.CONTENT_ENCODING, "gzip");
        httpPost.addHeader(HTTP.SERVER_HEADER, "Apache-Coyote/1.1");
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
        String body = com.alibaba.fastjson.JSON.toJSONString(params);
        System.err.println(body);
        StringEntity entity;
        try {
            entity = new StringEntity(body);
            httpPost.setEntity(entity);
            HttpResponse response;
            response = httpClient.execute(httpPost);
            inputStream = response.getEntity().getContent();

            in =  new BufferedReader(
                    new InputStreamReader(inputStream));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 以POST方式向指定地址发送数据包请求,并取得返回的数据包
     *
     * @param urlString
     * @param requestData
     * @return 返回数据包
     * @throws Exception
     */
    public static byte[] requestPost(String urlString, byte[] requestData)
            throws Exception {
        Properties requestProperties = new Properties();
        requestProperties.setProperty(CONTENT_TYPE,
                "application/octet-stream; charset=utf-8");

        return requestPost(urlString, requestData, requestProperties);
    }

    /**
     * 以POST方式向指定地址发送数据包请求,并取得返回的数据包
     *
     * @param urlString
     * @param requestData
     * @param requestProperties
     * @return 返回数据包
     * @throws Exception
     */
    public static byte[] requestPost(String urlString, byte[] requestData,
                                     Properties requestProperties) throws Exception {
        byte[] responseData = null;

        HttpURLConnection con = null;

        try {
            URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();

            if ((requestProperties != null) && (requestProperties.size() > 0)) {
                for (Map.Entry<Object, Object> entry : requestProperties
                        .entrySet()) {
                    String key = String.valueOf(entry.getKey());
                    String value = String.valueOf(entry.getValue());
                    con.setRequestProperty(key, value);
                }
            }

            con.setRequestMethod(METHOD_POST); // 置为POST方法

            con.setDoInput(true); // 开启输入流
            con.setDoOutput(true); // 开启输出流

            // 如果请求数据不为空，输出该数据。
            if (requestData != null) {
                DataOutputStream dos = new DataOutputStream(con
                        .getOutputStream());
                dos.write(requestData);
                dos.flush();
                dos.close();
            }

            int length = con.getContentLength();
            // 如果回复消息长度不为-1，读取该消息。
            if (length != -1) {
                DataInputStream dis = new DataInputStream(con.getInputStream());
                responseData = new byte[length];
                dis.readFully(responseData);
                dis.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.disconnect();
                con = null;
            }
        }

        return responseData;
    }

    /**
     * 以POST方式向指定地址提交表单<br>
     * arg0=urlencode(value0)&arg1=urlencode(value1)
     *
     * @param urlString
     * @param formProperties
     * @return 返回数据包
     * @throws Exception
     */
    public static byte[] requestPostForm(String urlString,
                                         Properties formProperties) throws Exception {
        Properties requestProperties = new Properties();

        requestProperties.setProperty(CONTENT_TYPE,
                "application/x-www-form-urlencoded");

        return requestPostForm(urlString, formProperties, requestProperties);
    }


    /**
     * 以POST方式向指定地址提交表单<br>
     * arg0=urlencode(value0)&arg1=urlencode(value1)
     *
     * @param urlString
     * @param formProperties
     * @param requestProperties
     * @return 返回数据包
     * @throws Exception
     */
    public static byte[] requestPostForm(String urlString,
                                         Properties formProperties, Properties requestProperties)
            throws Exception {
        StringBuilder sb = new StringBuilder();

        if ((formProperties != null) && (formProperties.size() > 0)) {
            for (Map.Entry<Object, Object> entry : formProperties.entrySet()) {
                String key = String.valueOf(entry.getKey());
                String value = String.valueOf(entry.getValue());
                sb.append(key);
                sb.append("=");
                sb.append(encode(value));
                sb.append("&");
            }
        }

        String str = sb.toString();
        str = str.substring(0, (str.length() - 1)); // 截掉末尾字符&

        return requestPost(urlString, str.getBytes(CHARACTER_ENCODING),
                requestProperties);

    }


    /**
     * url编码
     *
     * @param url
     * @return 编码后的字符串,当异常时返回原始字符串。
     */
    public static String encode(String url) {
        try {
            return URLEncoder.encode(url, CHARACTER_ENCODING);
        } catch (UnsupportedEncodingException ex) {
            return url;
        }
    }
}
