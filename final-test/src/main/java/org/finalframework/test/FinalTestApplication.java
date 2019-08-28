package org.finalframework.test;

import org.finalframework.monitor.annotation.OperationAction;
import org.finalframework.test.service.PersonServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:13
 * @since 1.0
 */
@SpringBootApplication()
public class FinalTestApplication extends SpringBootServletInitializer {
    public static void main(String[] args) throws NoSuchMethodException {
//
//        RequestJsonParamHandlerRegistry.getInstance()
//                .register(JSONObject.class, new RequestJsonParamHandler<JSONObject>() {
//                    @Override
//                    public JSONObject convert(String source) {
//                        return JSON.parseObject(source);
//                    }
//                });

        Method findById = PersonServiceImpl.class.getMethod("findById", Long.class);
        Collection<OperationAction> operationAnnotation = new FinalOperationAnnotationFinder<>(OperationAction.class).findOperationAnnotation(findById);
        System.out.println(operationAnnotation);


//        final long start = System.currentTimeMillis();
//        SpringApplication.run(FinalTestApplication.class, args);
//        final long end = System.currentTimeMillis();
//        System.out.println(end - start);
    }
}
