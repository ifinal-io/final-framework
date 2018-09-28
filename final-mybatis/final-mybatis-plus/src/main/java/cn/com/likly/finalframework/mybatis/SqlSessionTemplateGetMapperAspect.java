package cn.com.likly.finalframework.mybatis;

import cn.com.likly.finalframework.mybatis.mapper.AbsMapper;
import cn.com.likly.finalframework.mybatis.mapper.DefaultMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 21:06
 * @since 1.0
 */
@Component
@Aspect
@Slf4j
public class SqlSessionTemplateGetMapperAspect {

    private static final String CUT_POINT = "execution(public * org.mybatis.spring.SqlSessionTemplate.getMapper(Class))";

    @Resource
    private DefaultMapper defaultMapper;
    @Resource
    private EntityHolderCache entityHolderCache;

    @SuppressWarnings("unchecked")
    @Around(CUT_POINT)
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        Class mapperClass = (Class) point.getArgs()[0];

        if (!AbsMapper.class.equals(mapperClass) && !AbsMapper.class.isAssignableFrom(mapperClass))
            return point.proceed();
        logger.debug("==> create mapper proxy : {}", mapperClass.getName());
        return new AbsMapperProxy<>(defaultMapper, point.proceed(), entityHolderCache.get(mapperClass)).getMapper();

    }

}
