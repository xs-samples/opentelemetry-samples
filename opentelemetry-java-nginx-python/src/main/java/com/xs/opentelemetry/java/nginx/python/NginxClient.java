package com.xs.opentelemetry.java.nginx.python;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Nginx客户端
 *
 * @author xs
 * @date 2022/05/27
 */
@Slf4j
@Component
public class NginxClient {

    /**
     * 提供者url
     */
    @Value("${python.provider.url}")
    private String providerUrl;

    /**
     * 得到
     *
     * @param param 起源
     * @return {@link String}
     */
//    @WithSpan
    public String get(String param) {
        String s = StrUtil.EMPTY;
        String url = providerUrl + param;
        log.info("Getting from {}", url);
//获得http客户端
        HttpUriRequest request = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        //响应模型
        HttpResponse response;
        try {
            response = client.execute(request);
            //从响应模型中获取响应体
            HttpEntity responseEntity = response.getEntity();
            EntityUtils.toString(responseEntity);
            log.info("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                s = EntityUtils.toString(responseEntity);
                log.info("响应内容长度为:" + responseEntity.getContentLength());
                log.info("响应内容为:" + s);
            }
            log.info("Got {}", s);
        } catch (IOException e) {
            log.error("error:", e);
        }
        return s;
    }
}
