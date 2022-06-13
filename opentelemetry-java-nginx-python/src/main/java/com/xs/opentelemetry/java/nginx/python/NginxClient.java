package com.xs.opentelemetry.java.nginx.python;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
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
        HttpUriRequest request = new HttpPost(url);
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

    public void post() {
        String url = "http://10.100.57.130:8080/execute";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity("{\n" +
                "    \"componentMethodId\": \"b4ed0ab2-e008-4fac-89e9-e5b3e9722b8e\",\n" +
                "    \"componentMethodVersion\": \"1.0.0\",\n" +
                "    \"componentUrl\": [\n" +
                "        \"file:/root/componentTest/df674660-983d-479d-bbfd-beaf32b399a7/b4ed0ab2-e008-4fac-89e9-e5b3e9722b8e/1.0.0/4e0c4006-bfd9-4a4f-9d43-5daceb7db411/hello.20220613150200.jar\",\n" +
                "        \"https://img.ipaas.clickpaas.com/component/df674660-983d-479d-bbfd-beaf32b399a7/1.0.0/b4ed0ab2-e008-4fac-89e9-e5b3e9722b8e/1.0.0/Python\",\n" +
                "        \"http://ipaas-runtime-agent.dev3.svc.cluster.local/component/container/endpoint/df674660-983d-479d-bbfd-beaf32b399a7\"\n" +
                "    ],\n" +
                "    \"componentUrlPath\": [\n" +
                "        \"file:/root/componentTest/df674660-983d-479d-bbfd-beaf32b399a7/b4ed0ab2-e008-4fac-89e9-e5b3e9722b8e/1.0.0/4e0c4006-bfd9-4a4f-9d43-5daceb7db411/hello.20220613150200.jar\",\n" +
                "        \"https://img.ipaas.clickpaas.com/component/df674660-983d-479d-bbfd-beaf32b399a7/1.0.0/b4ed0ab2-e008-4fac-89e9-e5b3e9722b8e/1.0.0/Python\",\n" +
                "        \"http://ipaas-runtime-agent.dev3.svc.cluster.local/component/container/endpoint/df674660-983d-479d-bbfd-beaf32b399a7\"\n" +
                "    ],\n" +
                "    \"developmentType\": 200,\n" +
                "    \"enabled\": true,\n" +
                "    \"generalParameter\": \"{\\\"image\\\":\\\"https://private-cdn.successchannel.net/D26311/D29411/20220117170913xbEZet.jpeg?attname=sds.jpeg&fileId=751b6ff1-109e-428b-816b-517edb46a681&ext=jpeg&e=1642410864&token=UGO03TdqmN3jUcuks2ycsIa_f1WZNdd6LzhYwQfD:HuVmrEEjgyWEXAc7txxnEW_IO9o=\\\",\\\"data\\\":[{\\\"enterpriseId\\\":\\\"4651\\\",\\\"id\\\":\\\"18c4aa6f-37cf-4e08-b05e-272976406e59\\\",\\\"createdTime\\\":\\\"2022-01-17 17:07:13\\\",\\\"modifiedTime\\\":\\\"2022-01-17 17:07:13\\\",\\\"isDeleted\\\":\\\"0\\\",\\\"createUserId\\\":\\\"D29411\\\",\\\"createDepartmentId\\\":\\\"8851\\\",\\\"companyId\\\":\\\"D4501\\\",\\\"tenantId\\\":\\\"D3000501\\\",\\\"standardCollectionId\\\":\\\"O56161\\\",\\\"filePath\\\":\\\"https://private-cdn.successchannel.net/D26311/D29411/20220117170712WCIDfd.md?attname=%E5%87%BD%E6%95%B0%E6%96%87%E6%A1%A3.md&fileId=18c4aa6f-37cf-4e08-b05e-272976406e59&ext=md&e=1642410864&token=UGO03TdqmN3jUcuks2ycsIa_f1WZNdd6LzhYwQfD:qeBxRGakv5HfUfKmzNb1jnjX3ds=\\\",\\\"fileName\\\":\\\"函数文档.md\\\",\\\"serverType\\\":\\\"QINIU\\\",\\\"filePermissionType\\\":\\\"PRIVATE\\\",\\\"fieldId\\\":\\\"O901670\\\",\\\"expireTime\\\":0.0,\\\"fileExt\\\":\\\"md\\\",\\\"accountId\\\":\\\"D26311\\\",\\\"size\\\":3067.0,\\\"formatFileSize\\\":\\\"3.00 KB\\\"},{\\\"enterpriseId\\\":\\\"4651\\\",\\\"id\\\":\\\"ccd9cc49-123c-413f-8398-d1bb433ffcaf\\\",\\\"createdTime\\\":\\\"2022-01-17 17:07:38\\\",\\\"modifiedTime\\\":\\\"2022-01-17 17:07:38\\\",\\\"isDeleted\\\":\\\"0\\\",\\\"createUserId\\\":\\\"D29411\\\",\\\"createDepartmentId\\\":\\\"8851\\\",\\\"companyId\\\":\\\"D4501\\\",\\\"tenantId\\\":\\\"D3000501\\\",\\\"standardCollectionId\\\":\\\"O56161\\\",\\\"filePath\\\":\\\"https://private-cdn.successchannel.net/D26311/D29411/20220117170737lPCaTf.yaml?attname=calico-3.9.2.yaml&fileId=ccd9cc49-123c-413f-8398-d1bb433ffcaf&ext=yaml&e=1642410864&token=UGO03TdqmN3jUcuks2ycsIa_f1WZNdd6LzhYwQfD:z1LHB6JwdtRApsZ65kJHB3sTUTI=\\\",\\\"fileName\\\":\\\"calico-3.9.2.yaml\\\",\\\"serverType\\\":\\\"QINIU\\\",\\\"filePermissionType\\\":\\\"PRIVATE\\\",\\\"fieldId\\\":\\\"O901670\\\",\\\"expireTime\\\":0.0,\\\"fileExt\\\":\\\"yaml\\\",\\\"accountId\\\":\\\"D26311\\\",\\\"size\\\":20636.0,\\\"formatFileSize\\\":\\\"20.15 KB\\\"}]}\",\n" +
                "    \"methodName\": \"hello\",\n" +
                "    \"name\": \"python 上传文件测试 \",\n" +
                "    \"overridePackages\": [],\n" +
                "    \"properties\": {\n" +
                "        \"language\": \"python\",\n" +
                "        \"updateTime\": \"2022-01-17 16:51:51\",\n" +
                "        \"version\": \"3.8\"\n" +
                "    },\n" +
                "    \"type\": 100,\n" +
                "    \"version\": \"1.0.0\"\n" +
                "}", ContentType.create("application/json", "utf-8")));
        HttpClient client = new DefaultHttpClient();
        try {
            client.execute(httpPost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
