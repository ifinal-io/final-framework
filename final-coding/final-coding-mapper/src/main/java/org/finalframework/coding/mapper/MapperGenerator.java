package org.finalframework.coding.mapper;


import org.finalframework.coding.entity.EntityFactory;
import org.finalframework.coding.generator.TemplateCodeGenerator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-28 09:30:39
 * @since 1.0
 */
public class MapperGenerator extends TemplateCodeGenerator {

    private static final String MAPPER_PACKAGE_PATH = "dao.mapper";

    public MapperGenerator(ProcessingEnvironment processEnv) {
        super(processEnv, MAPPER_PACKAGE_PATH);
    }

    @Override
    public Void generate(TypeElement typeElement) {

        final String packageName = packageNameGenerator.generate(typeElement);

        Mapper mapper = Mapper.builder()
                .packageName(packageName)
                .name(typeElement.getSimpleName().toString() + "Mapper")
                .entity(EntityFactory.create(processEnv, typeElement))
                .build();

        try {
            String mapperName = mapper.getPackageName() + "." + mapper.getName();
            TypeElement mapperTypeElement = processEnv.getElementUtils().getTypeElement(mapperName);
            if (mapperTypeElement == null) {
                coder.coding(mapper, processEnv.getFiler().createSourceFile(mapperName).openWriter());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}

