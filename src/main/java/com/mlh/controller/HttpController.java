package com.mlh.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: linghan.ma
 * @DATE: 2018/11/27
 * @description:
 */
@Controller
@RequestMapping("test")
public class HttpController {

    private static final Logger LOGGER  = LoggerFactory.getLogger(HttpController.class);


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downLoadUrl(HttpServletRequest request) throws Exception {
        String url = "https://www.baidu.com/a.pdf";
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpclient = createHttpClient(20,20,5000,5000,3000);
        HttpResponse response1 = httpclient.execute(httpGet);
        System.out.println(response1.getStatusLine());
        if(response1.getStatusLine().getStatusCode() == 200){
            LOGGER.info("CODE == 200");
        }
        HttpEntity httpEntity = response1.getEntity();
        InputStream is = httpEntity.getContent();
    }

    @RequestMapping(value = "/download3", method = RequestMethod.GET)
    public void downLoadUrl3(HttpServletRequest request,
                             HttpServletResponse response,
                             String fileUrl,
                             String fileName){
        HttpURLConnection httpURLConnection = null;
        String suffix = fileUrl.substring(fileUrl.lastIndexOf("."));
        try {
            URL url = new URL(fileUrl);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.getContent();
            InputStream inputStream = httpURLConnection.getInputStream();
            String suffix2 = httpURLConnection.getContentType(); // 应该是这个判断文件流的，万一没有后缀名
            if (StringUtils.isEmpty(suffix)) {
                suffix = "."+suffix2;
            }
            setFileDownloadHeader(request.getHeader("User-Agent"), response,fileName+suffix);
            OutputStream outputStream =response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            inputStream.close();
        }catch (Exception e){
            LOGGER.info("文件下载失败，失败原因:"+e.getMessage()+"文件url："+fileUrl);
        }
    }

    /**
     * 设置让浏览器弹出下载对话框的Header.
     *
     * @param fileName 下载后的文件名.
     */
    public static void setFileDownloadHeader(String userAgent, HttpServletResponse response, String fileName) {
        try {
            if (isMSBrowser(userAgent)) {
                response.setHeader("content-disposition",
                        "attachment;filename=\"" + java.net.URLEncoder.encode(fileName, "UTF-8") + "\"");
            } else {
                //中文文件名支持
                String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
            }

        } catch (UnsupportedEncodingException e) {
        }
    }

    //是否是IE浏览器
    public static boolean isMSBrowser(String userAgent) {
        String[] signalsOfIEBrowser = {"MSIE", "Trident", "Edge"};
        for (String signal : signalsOfIEBrowser) {
            if (userAgent.contains(signal)) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/download2", method = RequestMethod.GET)
    public void downLoadUrl2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileUrl = "https://www.baidu.com/aaa.pdf";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HttpURLConnection httpURLConnection = null;
        String message = "";
        // TODO 正常 fileUrl 是这样的  https://www.baidu.com/aaa.pdf，这边判断是否有后缀
        String suffix = fileUrl.substring(fileUrl.lastIndexOf(".") + 1);
        try {
            java.net.URL url = new URL(fileUrl);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.getContent();
            InputStream inputStream = httpURLConnection.getInputStream();
            String suffix2 = httpURLConnection.getContentType(); // 应该是这个判断文件流的，百度下看看
            if (StringUtils.isEmpty(suffix)) {
                suffix = suffix2;
            }
            String tmpDir  = System.getProperty("java.io.tmpdir");
            if (StringUtils.isEmpty(suffix)) {
                suffix = "doc";
                LOGGER.info("获取文件后缀失败" + fileUrl);
            }
            String tmppath = tmpDir + dateFormat.format(new Date()) + "\\";
            if (!new File(tmppath).exists()){
                new File(tmppath).mkdirs();
            }
            File tmpFile = new File(tmppath+"change"+ UUID.randomUUID()+ "." + suffix);
            FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
            IOUtils.copy(inputStream, fileOutputStream);
            message = "下载成功，文件路径:"+tmpFile.getAbsolutePath();
            LOGGER.info(message);
            inputStream.close();
            fileOutputStream.close();
        }catch (Exception e){
            message = "文件下载失败，失败原因:"+e.getMessage()+"文件下载路径："+fileUrl;
        }
    }

    private static HttpClient defaultClient = createHttpClient(20, 20, 5000, 5000, 3000);

    /**
     * 实例化HttpClient
     *
     * @param maxTotal
     * @param maxPerRoute
     * @param socketTimeout
     * @param connectTimeout
     * @param connectionRequestTimeout
     * @return
     */
    public static HttpClient createHttpClient(int maxTotal, int maxPerRoute, int socketTimeout, int connectTimeout,
                                              int connectionRequestTimeout) {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout).build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(maxPerRoute);
        cm.setValidateAfterInactivity(200); // 一个连接idle超过200ms,再次被使用之前,需要先做validation
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setConnectionTimeToLive(30, TimeUnit.SECONDS)
                .setRetryHandler(new StandardHttpRequestRetryHandler(3, true)) // 配置出错重试
                .setDefaultRequestConfig(defaultRequestConfig).build();
        return httpClient;
    }
}
