/*
 * Copyright 2020-2022 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.util;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author likly
 * @version 1.2.4
 **/
class DefaultMethodInvokerTest {

    private Reflections.MethodInvoker methodInvoker = new Reflections.DefaultMethodInvoker();


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
        assertEquals("1", Reflections.invokeMethod(this, "method", null, "1"));
        assertEquals("1", Reflections.invokeMethod(this, "method", null, new Object[]{1}));
    }
}