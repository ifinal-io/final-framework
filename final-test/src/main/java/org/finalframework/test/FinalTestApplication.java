package org.finalframework.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:13
 * @since 1.0
 */
@SpringBootApplication()
public class FinalTestApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
//
//        RequestJsonParamHandlerRegistry.getInstance()
//                .register(JSONObject.class, new RequestJsonParamHandler<JSONObject>() {
//                    @Override
//                    public JSONObject convert(String source) {
//                        return JSON.parseObject(source);
//                    }
//                });


        final long start = System.currentTimeMillis();
        SpringApplication.run(FinalTestApplication.class, args);
        final long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
