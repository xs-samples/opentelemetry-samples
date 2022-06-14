package com.xs.opentelemetry.python.java.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认控制器
 *
 * @author xs
 * @date 2022/05/27
 */
@Slf4j
@RestController
public class DefaultController {

    /**
     * python服务
     */
    private PythonService pythonService;

    /**
     * 默认控制器
     *
     * @param pythonService python服务
     */
    public DefaultController(PythonService pythonService) {
        this.pythonService = pythonService;
    }

    /**
     * 问候
     *
     * @param param 参数
     * @return {@link String}
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "param", defaultValue = "default") String param) {
        try {
            log.info("Before Service Method Call");
            return pythonService.doSomeThing(param);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return e.getMessage();
        }
    }

}
