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

    private static final String MAPPER_PREFIX = "Abs";
    private static final String MAPPER_SUFFIX = "Mapper";

    public MapperGenerator(ProcessingEnvironment processEnv) {
        super(processEnv, MAPPER_PACKAGE_PATH);
    }

    @Override
    public Void generate(TypeElement typeElement) {

        final String packageName = packageNameGenerator.generate(typeElement);
        String mapperName = typeElement.getSimpleName().toString() + MAPPER_SUFFIX;
        String absMapperName = MAPPER_PREFIX + mapperName;

        String mapperClassName = packageName + "." + mapperName;

        TypeElement mapperTypeElement = processEnv.getElementUtils().getTypeElement(mapperClassName);

        Mapper mapper = Mapper.builder()
                .packageName(packageName)
                .name(mapperTypeElement == null ? mapperName : absMapperName)
                .entity(EntityFactory.create(processEnv, typeElement))
                .build();


        try {
            String mapperFileName = mapper.getPackageName() + "." + mapper.getName();
            TypeElement mapperElement = processEnv.getElementUtils().getTypeElement(mapperFileName);
            if (mapperElement == null) {
                coder.coding(mapper, processEnv.getFiler().createSourceFile(mapperFileName).openWriter());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}

