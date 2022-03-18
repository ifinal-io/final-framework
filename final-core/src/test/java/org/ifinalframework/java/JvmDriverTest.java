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

package org.ifinalframework.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * JvmDriverTest.
 *
 * @author ilikly
 * @version 1.3.0
 * @since 1.3.0
 */
@Slf4j
class JvmDriverTest {

    String say(){
        return "hello";
    }

    @Test
    void dump(){
        byte[] dump = JvmDriver.dump(Jvm.class);
        assertNotNull(dump);
    }

    @Test
    void jad(){
        logger.info(JvmDriver.jad(Jvm.class));
    }

    @Test
    void compile(){
        byte[] bytes = JvmDriver.compile(Jvm.class, JvmDriver.jad(Jvm.class));
        assertNotNull(bytes);
    }

//    @Test
    void redefine(){

        Jvm jvm = new Jvm();

        try {
            assertEquals("hello",jvm.say());
            Class<Jvm> clazz = Jvm.class;
            String source = JvmDriver.jad(clazz);
            String replace = source.replace("hello", "word");
            JvmDriver.redefine(Jvm.class, replace);
            assertEquals("word",jvm.say());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}