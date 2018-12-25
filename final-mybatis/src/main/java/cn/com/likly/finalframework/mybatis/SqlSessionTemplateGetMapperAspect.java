package cn.com.likly.finalframework.mybatis;

import cn.com.likly.finalframework.mybatis.handler.EnumEntityTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 21:06
 * @see SqlSessionTemplate
 * @since 1.0
 */
@Component
@Aspect
@Slf4j
public class SqlSessionTemplateGetMapperAspect {

    private static final String CUT_POINT = "execution(public * org.mybatis.spring.SqlSessionTemplate.getMapper(Class))";

    @AfterReturning(pointcut = "execution(public * org.mybatis.spring.SqlSessionTemplate.getConfiguration())", returning = "configuration")
    public void afterReturning(Configuration configuration) {
        configuration.setDefaultEnumTypeHandler(EnumEntityTypeHandler.class);
    }

}
