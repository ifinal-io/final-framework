/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-04 17:59:04
 * @since 1.0
 */
@Slf4j
public class ClassLoaderTest {
    private static final String JS_ENGINE_NAME = "nashorn";
    private final ScriptEngineManager sem = new ScriptEngineManager();
    private final ScriptEngine engine = sem.getEngineByName(JS_ENGINE_NAME);


    @Test
    public void test() {
        ClassLoader loader = ClassLoaderTest.class.getClassLoader();

        while (loader != null) {
            logger.info(loader.toString());
            loader = loader.getParent();
        }
    }

    @Test
    public void test2() {
        invokeFunctionDemo();
    }

    public boolean invokeFunctionDemo() {
        logger.info("---          invokeFunction         ---");
        boolean result = true;
        try {
            engine.put("msg", "hello world!");
            String str = "var user = {name:'张三',age:18,city:['陕西','台湾']};";
            engine.eval(str);

            logger.info("Get msg={}", engine.get("msg"));
            //获取变量
            engine.eval("var sum = eval('1 + 2 + 3*4');");
            //调用js的eval的方法完成运算
            logger.info("get sum={}", engine.get("sum"));

            Map<String, Object> msg = new HashMap<>();
            msg.put("temperature", 125);
            msg.put("humidity", 20);
            msg.put("voltage", 220);
            msg.put("electricity", 13);

            Map<String, Object> metadata = new HashMap<>();
            metadata.put("deviceName", "空气质量检测器01");
            metadata.put("contacts", "张三");

            Map<String, Object> msgType = new HashMap<>();
            //msgType.put("type", "deviceTelemetryData");
            msgType.put("type", "deviceTelemetryData1");

            //定义函数
            String func = "var result = true; \r\n" +
                    "if (msgType.type = 'deviceTelemetryData') { \r\n" +
                    "   if (msg.temperature >0 && msg.temperature < 33) { \n       result = true ;}  \n" +
                    "   else { \n       result = false;}  \n" +
                    "} else { \n     result = false;  \n" +
                    "     var errorMsg = msgType.type + ' is not deviceTelemetryData';  \n" +
                    "     print(msgType.type) \n } \n\n" +
                    "return result";
            logger.info("func = {}", func);
            engine.eval("function filter(msg, metadata, msgType){ " + func + "}");
            // 执行js函数
            Invocable jsInvoke = (Invocable) engine;
            Object obj = jsInvoke.invokeFunction("filter", msg, metadata, msgType);
            //方法的名字，参数
            logger.info("function result={}", obj);
            result = (Boolean) obj;
        } catch (Exception ex) {
            logger.warn("exception", ex);
            result = false;
        }

        return result;
    }
}

