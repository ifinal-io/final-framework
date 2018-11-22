package cn.com.likly.finalframework.mybatis;

import cn.com.likly.finalframework.mybatis.handler.EnumEntityTypeHandler;
import cn.com.likly.finalframework.mybatis.mapper.AbsMapper;
import cn.com.likly.finalframework.mybatis.mapper.DefaultMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 21:06
 * @since 1.0
 */
@Component
@Aspect
@Slf4j
@ConditionalOnClass(DefaultMapper.class)
public class SqlSessionTemplateGetMapperAspect {

    private static final String CUT_POINT = "execution(public * org.mybatis.spring.SqlSessionTemplate.getMapper(Class))";

    private EntityHolderCache entityHolderCache = new EntityHolderCache();

    @SuppressWarnings("unchecked")
    @Around(CUT_POINT)
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        Class mapperClass = (Class) point.getArgs()[0];
        logger.info("==> create mapper:{}", mapperClass.getName());
        if (!AbsMapper.class.equals(mapperClass) && !AbsMapper.class.isAssignableFrom(mapperClass))
            return point.proceed();
        logger.debug("==> create mapper proxy : {}", mapperClass.getName());
        final Object target = point.proceed();
        SqlSessionTemplate template = (SqlSessionTemplate) point.getTarget();
        DefaultMapper defaultMapper = template.getMapper(DefaultMapper.class);
        return new AbsMapperProxy<>(defaultMapper, target, entityHolderCache.get(mapperClass)).getMapper();

    }

    @AfterReturning(pointcut = "execution(public * org.mybatis.spring.SqlSessionTemplate.getConfiguration())", returning = "configuration")
    public void afterReturning(Configuration configuration) {
        if (!configuration.getMapperRegistry().hasMapper(DefaultMapper.class)) {
            configuration.getTypeHandlerRegistry().setDefaultEnumTypeHandler(EnumEntityTypeHandler.class);
            configuration.getMapperRegistry().addMapper(DefaultMapper.class);
        }
    }

}
