package org.ifinalframework.context.beans.factory.support;

import org.springframework.context.ApplicationContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author ilikly
 * @version 1.2.4
 **/
@ExtendWith(MockitoExtension.class)
class ApplicationContextBeanMethodInvokerTest {

    @InjectMocks
    private ApplicationContextBeanMethodInvoker invoker;
    @Mock
    private ApplicationContext applicationContext;

    private String targetMethod(String value) {
        return value;
    }

    @Test
    void invoke() {

        final String value = "value";
        final String methodName = "targetMethod";
        Class<?>[] parameterTypes = {String.class};
        Object[] args = {value};

        ApplicationContextBeanMethodInvokerTest bean = new ApplicationContextBeanMethodInvokerTest();
        when(applicationContext.getBean(anyString())).thenReturn(bean);
        when(applicationContext.getBean(ApplicationContextBeanMethodInvokerTest.class)).thenReturn(bean);

        //        assertEquals(value, invoker.invoke(bean, methodName, null, null));
        assertEquals(value, invoker.invoke(bean, methodName, parameterTypes, args));
        assertEquals(value, invoker.invoke(bean, methodName, parameterTypes, args));

        assertEquals(value, invoker.invoke("bean", methodName, parameterTypes, args));
        assertEquals(value, invoker.invoke(ApplicationContextBeanMethodInvokerTest.class, methodName, parameterTypes, args));
    }

}