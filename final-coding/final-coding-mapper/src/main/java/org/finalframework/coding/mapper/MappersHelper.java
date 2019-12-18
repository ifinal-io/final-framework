package org.finalframework.coding.mapper;


import org.finalframework.coding.Coder;
import org.finalframework.core.Assert;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-04 13:11:45
 * @since 1.0
 */
public class MappersHelper {

    /**
     * 生成的文件路径: classpath:/META-INF/final.mappers
     */
    private static final String RESOURCE_FILE = "META-INF/final.mappers";
    private final Coder coder = Coder.getDefaultCoder();
    private final ProcessingEnvironment processingEnv;

    public MappersHelper(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public Mappers parse() {
        Mappers.Builder builder = Mappers.builder();
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/" + RESOURCE_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
            String line;
            while ((line = reader.readLine()) != null) {
                TypeElement typeElement = processingEnv.getElementUtils().getTypeElement(line.trim());
                if (typeElement != null) {
                    builder.addMapper(typeElement);
                }
            }
            reader.close();
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }
        return builder.build();
    }

    public void generate(Mappers mappers) {
        if (Assert.isEmpty(mappers.getMappers())) return;
        try {
            coder.coding(mappers, processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", RESOURCE_FILE).openWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

