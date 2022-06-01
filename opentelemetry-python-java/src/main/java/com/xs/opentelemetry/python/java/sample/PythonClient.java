package com.xs.opentelemetry.python.java.sample;

import cn.hutool.http.HttpUtil;
import io.opentelemetry.extension.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * python客户机
 *
 * @author xs
 * @date 2022/05/27
 */
@Slf4j
@Component
public class PythonClient {

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
    @WithSpan
    public String get(String param) {
        String url = providerUrl + param;
        log.info("Getting from {}", url);
        String s = HttpUtil.get(url);
        log.info("Got {}", s);
        return s;
    }
}
