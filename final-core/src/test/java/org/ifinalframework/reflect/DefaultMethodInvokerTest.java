package org.ifinalframework.reflect;

import org.ifinalframework.util.Reflections;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author likly
 * @version 1.2.4
 **/
class DefaultMethodInvokerTest {

    private MethodInvoker methodInvoker = new DefaultMethodInvoker();


    private String method(int arg){
        return String.valueOf(arg);
    }



    @Test
    void invoke() {

        final Method method = Reflections.findMethod(DefaultMethodInvokerTest.class,"method",null,null);

        ReflectionUtils.makeAccessible(method);

        assertEquals("1",methodInvoker.invoke(method,this,1));
        assertEquals("1",methodInvoker.invoke(method,this,"1"));
        assertEquals("1",methodInvoker.invoke(method,this,new Object[]{1}));
        assertEquals("1",methodInvoker.invoke(method,this,new Object[]{"1"}));


    }

    @Test
    void reflect(){
        assertEquals("1",Reflections.invokeMethod(this,"method",null,1));
        assertEquals("1",Reflections.invokeMethod(this,"method",null,"1"));
    }
}