package org.finalframework.coding.entity;


import org.finalframework.coding.Coder;
import org.finalframework.core.Assert;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.StandardLocation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 将实现了{@link org.finalframework.data.entity.IEntity}接口的JavaBean对象生成到{@link EntitiesHelper#RESOURCE_FILE}文件中，以供其他处理使用。
 * 如编译时生成{@link org.finalframework.data.query.QEntity}等。
 *
 * @author likly
 * @version 1.0
 * @date 2019-11-04 13:11:45
 * @since 1.0
 */
public class EntitiesHelper {

    /**
     * 生成的文件路径: classpath/META-INF/final.entities
     */
    private static final String RESOURCE_FILE = "META-INF/final.entities";
    private final Coder coder = Coder.getDefaultCoder();
    private final ProcessingEnvironment processingEnv;

    public EntitiesHelper(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public Entities parse() {
        Entities.Builder builder = Entities.builder();
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/" + RESOURCE_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.addEntity(processingEnv.getElementUtils().getTypeElement(line.trim()));
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return builder.build();
    }

    public void generate(Entities entities) {
        if (Assert.isEmpty(entities.getEntities())) return;
        try {
            coder.coding(entities, processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", RESOURCE_FILE).openWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

