package com.ilikly.finalframework.mybatis.agent;

import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.CtNewMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-23 21:31:56
 * @since 1.0
 */
public final class MybatisAgent {
    private static final Logger logger = LoggerFactory.getLogger(MybatisAgent.class);
    private static final MybatisAgent instance = new MybatisAgent();
    private boolean initFlag = false;

    public static MybatisAgent getInstance() {
        return instance;
    }

    public synchronized void agent() {
        if (initFlag) {
            logger.info("mybatis agent had done!");
            return;
        }
        logger.info("starting mybatis agent !!!");
        initFlag = true;
        try {
            ClassPool pool = new ClassPool();
            pool.appendSystemPath();

            // 定义类
            CtClass XMLMapperBuilder = pool.get("org.apache.ibatis.builder.xml.XMLMapperBuilder");
            // 需要修改的方法
            CtMethod method = XMLMapperBuilder.getDeclaredMethod("parse");
            // 修改原有的方法
            method.setName("parse$agent");
            // 创建新的方法，复制原来的方法
            CtMethod newMethod = CtNewMethod.copy(method, "parse", XMLMapperBuilder, null);
            // 注入的代码
            StringBuffer body = new StringBuffer();
            /**
             * public void parse(){
             *     if(!configuration.isResourceLoaded(resource)){
             *          com.ilikly.finalframework.mybatis.agent.XMLMapperBuilderAgent.configurationDefaultElement(parser.evalNode("/mapper"));
             *     }
             *     parse$agent();
             * }
             */
            body.append("{\n");
            body.append("if (!configuration.isResourceLoaded(resource)) {")
                    .append("com.ilikly.finalframework.mybatis.agent.XMLMapperBuilderAgent.configurationDefaultElement(parser.evalNode(\"/mapper\"));")
                    .append("}");
            // 调用原有代码，类似于method();($$)表示所有的参数
            body.append("parse$agent($$);\n");

            body.append("}");
            newMethod.setBody(body.toString());
            // 增加新方法
            XMLMapperBuilder.addMethod(newMethod);
            Class aClass = XMLMapperBuilder.toClass();
            logger.info("done mybatis agent!!!");
        } catch (Exception e) {
            logger.error("doing mybatis agent error", e);
        }
    }
}
