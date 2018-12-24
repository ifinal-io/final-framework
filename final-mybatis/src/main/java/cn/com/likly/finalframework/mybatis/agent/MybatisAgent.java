package cn.com.likly.finalframework.mybatis.agent;

import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.CtNewMethod;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-23 21:31:56
 * @since 1.0
 */
public class MybatisAgent {
    public static void agent() {
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

            body.append("{\nlong start = System.currentTimeMillis();\n");
            body.append("if (!configuration.isResourceLoaded(resource)) {")
                    .append("cn.com.likly.finalframework.mybatis.agent.XMLMapperBuilderAgent.configurationDefaultElement(parser.evalNode(\"/mapper\"));")
                    .append("}");
            // 调用原有代码，类似于method();($$)表示所有的参数
            body.append("parse$agent($$);\n");
            body.append("System.out.println(\" take \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");

            body.append("}");
            newMethod.setBody(body.toString());
            // 增加新方法
            XMLMapperBuilder.addMethod(newMethod);
            Class aClass = XMLMapperBuilder.toClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
