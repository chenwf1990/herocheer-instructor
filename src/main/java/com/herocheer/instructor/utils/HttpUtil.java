package com.herocheer.instructor.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

/**
 * @Author: wuyizhou
 * @Description: http工具类
 * @Version: 1.0
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final HttpGet NONE_GET = null;
    private static final HttpPost NONE_POST = null;

    /**
     * 用于接收服务端返回的cookies数据
     */
    private static List<Cookie> cookies;

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();


    /**
     * 发送HTTP请求，请求方式可以是GET，也可以是POST，并且需要指定相应的ResponseHandler
     *
     * @param httpGet         如果是GET请求，则传入HttpGet对象，如果为null，表示不使用GET请求
     * @param httpPost        如果是POST请求，则传入HttpPost对象，如果为null，表示不使用POST请求
     * @param responseHandler ResponseHandler响应处理对象
     * @param <T>             响应处理对象中可通过泛型来指定返回的具体数据类型
     * @return 通过在ResponseHandler中指定的具体类型来返回响应数据
     */
    private static <T> T send(HttpGet httpGet, HttpPost httpPost, ResponseHandler<T> responseHandler) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        T t = null;
        try {
            if (httpGet != null) {
                httpGet.setConfig(requestConfig);
                t = httpClient.execute(httpGet, responseHandler);
            } else {
                httpPost.setConfig(requestConfig);
                t = httpClient.execute(httpPost, responseHandler);
            }
        } catch (IOException e) {
            logger.error("http client execute error: {}", e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("http client close error: {}", e.getMessage());
            }
        }
        return t;
    }

    /**
     * 获取服务端返回的cookie数据
     *
     * @return 服务端返回的cookie数据
     */
    public static List<Cookie> getCookies() {
        return cookies;
    }

    /**
     * 发送HTTP请求，请求方式可以是GET，也可以是POST，并且需要指定相应的ResponseHandler
     *
     * @param httpGet         如果是GET请求，则传入HttpGet对象，如果为null，表示不使用GET请求
     * @param httpPost        如果是POST请求，则传入HttpPost对象，如果为null，表示不使用POST请求
     * @param cookieList      需要提交到服务端的cookie数据
     * @param getCookie       是否需要从服务端获取cookie数据
     * @param responseHandler ResponseHandler响应处理对象
     * @param <T>             响应处理对象中可通过泛型来指定返回的具体数据类型
     * @return 通过在ResponseHandler中指定的具体类型来返回响应数据，并获取由服务端返回的cookie数据到cookies成员变量中
     */
    private static <T> T send(HttpGet httpGet, HttpPost httpPost, List<Cookie> cookieList, boolean getCookie, ResponseHandler<T> responseHandler) {
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        if (cookieList != null) {
            for (Cookie cookie : cookieList) {
                cookieStore.addCookie(cookie);
            }
        }
        T t = null;
        try {
            if (httpGet != null) {
                httpGet.setConfig(requestConfig);
                t = httpClient.execute(httpGet, responseHandler);
            } else {
                httpPost.setConfig(requestConfig);
                t = httpClient.execute(httpPost, responseHandler);
            }
            if (getCookie) {
                cookies = cookieStore.getCookies();
            }
        } catch (IOException e) {
            logger.error("http client execute error: {}", e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("http client close error: {}", e.getMessage());
            }
        }
        return t;
    }



    private static StringEntity addParams(JSONObject params) {
        String paramsStr = params.toString();
//        logger.info("请求参数为：{}", paramsStr);
        StringEntity entity = new StringEntity(paramsStr, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
//        entity.setContentType("application/x-www-form-urlencoded");
        return entity;
    }

    private static RequestConfig setRequestConfig(Integer timeout) {
        RequestConfig requestConfig;
        if (timeout == null || timeout == 0) {
            requestConfig = RequestConfig.custom().build();
        } else {
            requestConfig = RequestConfig.custom().setSocketTimeout(timeout)
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout).build();
        }
        return requestConfig;
    }

    public static JSONObject httpPost(String url, JSONObject params, Map<String, Object> headers) {
        return httpPost(url, params, headers, 0);
    }

    public static JSONObject httpPost(String url, JSONObject params, Map<String, Object> headers, Integer timeout) {

        logger.info("0 RequestUtils-httpPost 请求地址- {}", url);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(setRequestConfig(timeout)).setMaxConnTotal(100).setMaxConnPerRoute(100).build();

        HttpPost postMethod = new HttpPost(url);

        if (null != params && params.size() > 0) {
            postMethod.setEntity(addParams(params));
        }

        if (null != headers && headers.size() > 0) {
            for (String key : headers.keySet()) {
                postMethod.setHeader(key, String.valueOf(headers.get(key)));
            }
        }

        try {
            CloseableHttpResponse response = httpClient.execute(postMethod);
            int httpCode = response.getStatusLine().getStatusCode();
            String resEntity = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject result = new JSONObject();
            if (HttpStatus.SC_OK == httpCode) {
                result.put("code", 0);
                result.put("result", resEntity);
                return result;
            } else {
                result.put("code", 1);
                result.put("result", resEntity);
                return result;
            }
        } catch (Exception e) {
            logger.info("7 RequestUtils-httpPost 请求失败，请根据日志顺序排查。", e);
            return null;
        }
    }

    //视频下载
    public static boolean httpDownload(String httpUrl, String saveFile) {
        // 1.下载网络文件
        int byteRead;
        URL url;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return false;
        }
        //3.写入文件
        FileOutputStream fs = null;
        InputStream inStream = null;
        try {
            //2.获取链接
            URLConnection conn = url.openConnection();
            //3.输入流
            inStream = conn.getInputStream();
            //3.写入文件
            fs = new FileOutputStream(saveFile);

            byte[] buffer = new byte[1024];
            while ((byteRead = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(null != fs){
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != inStream){
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//    public static void main(String[] args) {
//        try {
//            HashMap<String, String> map =new HashMap<String, String>();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String  endtime = sdf.format(new Date());
//            long currentTime = System.currentTimeMillis() ;
//            currentTime=currentTime-30*60*1000;
//            String starttime = sdf.format(new Date(currentTime));
//            map.put("deviceid","JYZ18050020005");
//            map.put("id","1");
//            Map<String, String> headerParams=new HashMap<String, String>();
//            headerParams.put("username","acrel");
//            headerParams.put("password","acrel001");
//            //请求获取数据
//            map.put("starttime",starttime);
//            map.put("endtime",endtime);
//             String result=HttpUtils.doPost("http://118.31.62.159:80/"+"api/DataInterface/GetEvent",map,headerParams);
//            result= result.substring(1,result.length()-1);
//            JSONObject obj = JSONObject.parseObject(StringEscapeUtils.unescapeJava(result));
//            System.out.println(obj);
//        }catch (Exception e){
//            System.out.println(e.toString());
//        }
//    }
    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @return
     */
    public static JSONObject httpPost(String url, String jsonParam, String input_charset){
        //post请求返回结果
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonResult = null;

        try {
            HttpPost method = new HttpPost(url);
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam, input_charset);
                entity.setContentEncoding(input_charset);
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, input_charset);
            /**请求发送成功，并得到响应**/
            String str = "";
            try {
                /**读取服务器返回过来的json字符串数据**/
                str = EntityUtils.toString(result.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.parseObject(str);
            } catch (Exception e) {
                logger.error("post请求提交失败:" + url, e);
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    /**
     * @Author wangjian
     * @Date 2019-08-13 17:54
     * https post 请求
     */
    public static JSONObject httpsPost(String url, String outputStr) {
        return httpsRequest(url, "POST", outputStr, null);
    }
    public static JSONObject httpsGet(String url) {
        return httpsRequest(url, "GET", null, null);
    }

    /**
     * @Author wangjian
     * @Date 2019-08-13 17:52
     * https 请求
     */
    public static JSONObject httpsRequest(String url, String method, String outputStr, Map<String, String> headers) {
        StringBuffer stringBuffer = new StringBuffer();
        HttpsURLConnection httpsConn =null;
        BufferedReader bufferedReader=null;
        try {
            //创建SSLContext
            //SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] tm = {new MyX509TrustManager()};
            //初始化
            sslContext.init(null, tm, new SecureRandom());
            //创建SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL serverUrl = new URL(url);

            httpsConn = (HttpsURLConnection) serverUrl.openConnection();
            //允许从服务器上获取数据
            httpsConn.setDoInput(true);
            //允许向服务器传输数据
            httpsConn.setDoOutput(true);
            httpsConn.setRequestMethod(method.toUpperCase());
            //设置当前实列使用的SSLSocketFactory
            httpsConn.setSSLSocketFactory(ssf);
            // 设置连接主机服务器超时时间：15000毫秒
            httpsConn.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            httpsConn.setReadTimeout(60000);
            //设置请求头参数
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpsConn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            //httpsConn.setRequestProperty("content-type", "application/json");
            //必须设置false,否则会自动redirect到重定向后的地址
            //conn.setInstanceFollowRedirects(false);

            //建立连接
            if ("GET".equalsIgnoreCase(method)) {
                httpsConn.connect();
            }

            //发送参数
            if (outputStr != null) {
                OutputStream os = httpsConn.getOutputStream();
                os.write(outputStr.getBytes("UTF-8"));
                os.close();
            }

            bufferedReader = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(), "UTF-8"));
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }

        } catch (Exception e) {
            logger.error("https请求异常信息！", e);
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //关闭远程连接
            if(httpsConn!=null){
                httpsConn.disconnect();
            }
        }

        return JSON.parseObject(stringBuffer.toString());
    }

    static class MyX509TrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /**
     * 获取接口推送数据
     * @method getResult
     * @param request
     * @author songst
     * @date 2019年5月6日 下午12:26:09
     * @return String
     */
    public static String getResult(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String s = null;
            while ((s = reader.readLine()) != null) {
                stringBuilder.append(s);
            }
        } catch (IOException e) {
            logger.error("接收访客机推送数据处理错误！",e);
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
