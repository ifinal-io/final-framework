package cn.com.likly.finalframework.cache.interceptor;

import cn.com.likly.finalframework.cache.CacheAnnotationParser;
import cn.com.likly.finalframework.cache.CacheOperation;
import cn.com.likly.finalframework.cache.CacheOperationInvoker;
import cn.com.likly.finalframework.cache.CacheRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:50:44
 * @since 1.0
 */
public class CacheOperationAspect<A extends Annotation, O extends CacheOperation<A>> extends CacheAspectSupport<O> {

    @SuppressWarnings("unchecked")
    public Object doAspect(ProceedingJoinPoint point, A ann, Class<A> annClass) throws Throwable {
        final CacheOperationInvoker invoker = point::proceed;
        CacheAnnotationParser<A, O> cacheAnnotationParser = CacheRegistry.getInstance().getCacheAnnotationParser(annClass);
        O operation = cacheAnnotationParser.parseCacheAnnotation(ann);
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        CacheOperationContext<O> context = getCacheOperationContext(operation, methodSignature.getMethod(), point.getArgs(), point.getTarget());
        return CacheRegistry.getInstance().getCacheOperationInvocation(context.operation().getClass()).invoke(context, invoker);
    }
}
