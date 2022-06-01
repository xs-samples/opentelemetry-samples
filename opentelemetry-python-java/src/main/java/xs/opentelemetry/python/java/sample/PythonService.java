package xs.opentelemetry.python.java.sample;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.extension.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * python服务
 *
 * @author xs
 * @date 2022/05/27
 */
@Slf4j
@Service
public class PythonService {
    /**
     * python客户机
     */
    private PythonClient pythonClient;

    /**
     * python服务
     *
     * @param pythonClient python客户机
     */
    public PythonService(PythonClient pythonClient) {
        this.pythonClient = pythonClient;
    }

    /**
     * 做一些事情
     *
     * @param param 起源
     * @return {@link String}
     */
    public String doSomeThing(String param) {
        log.info("Getting for {}", param);
        String result = this.pythonClient.get(param);
        doSomeWorkNewSpan();
        return result;
    }

    /**
     * 做一些工作新跨越吗
     */
    @WithSpan
    private void doSomeWorkNewSpan() {
        log.info("Doing some work In New span");
        Span span = Span.current();

        span.setAttribute("attribute.a2", "some value");

        span.addEvent("app.processing2.start", atttributes("321"));
        span.addEvent("app.processing2.end", atttributes("321"));
    }

    /**
     * atttributes
     *
     * @param id id
     * @return {@link Attributes}
     */
    private Attributes atttributes(String id) {
        return Attributes.of(AttributeKey.stringKey("app.id"), id);
    }
}
