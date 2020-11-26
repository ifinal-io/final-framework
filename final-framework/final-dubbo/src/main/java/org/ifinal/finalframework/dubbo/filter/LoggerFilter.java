package org.ifinal.finalframework.dubbo.filter;


import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.ifinal.finalframework.dubbo.annotation.AutoFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Activate(
        group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER}
)
@AutoFilter("logger")
@SuppressWarnings("unused")
public class LoggerFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) {
        final Logger logger = LoggerFactory.getLogger(invoker.getInterface());
        try {
            if (logger.isInfoEnabled()) {
                logger.info("#{}, args={}", invocation.getMethodName(), invocation.getArguments());
            }
            final Result result = invoker.invoke(invocation);
            if (logger.isInfoEnabled()) {
                logger.info("#{}, result={}", invocation.getMethodName(), result.getValue());
            }
            return result;

        } catch (RpcException e) {
            throw e;
        } catch (Exception e) {
            throw new RpcException(e);
        }
    }
}

