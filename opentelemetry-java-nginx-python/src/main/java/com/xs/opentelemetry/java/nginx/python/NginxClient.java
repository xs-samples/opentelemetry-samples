package com.xs.opentelemetry.java.nginx.python;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
        CloseableHttpClient client = HttpClientBuilder.create().build();
        //创建get请求
        HttpGet httpGet = new HttpGet(url);
        //响应模型
        CloseableHttpResponse response = null;
        try {
            //有客户端指定get请求
            response = client.execute(httpGet);
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
        } finally {
            try {
                //释放资源
                if (client != null) {
                    client.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("error:", e);
            }
        }
        return s;
    }
}
