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

    private static final String ABS_MAPPER_TEMPLATE = "mapper/absMapper.jvm";
    private static final String MAPPER_TEMPLATE = "mapper/mapper.jvm";

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
        Mapper mapper = Mapper.builder()
                .packageName(packageName)
                .name(mapperName)
                .mapper(absMapperName)
                .entity(EntityFactory.create(processEnv, typeElement))
                .build();

        String absMapperClassName = mapper.getPackageName() + "." + absMapperName;
        String mapperClassName = mapper.getPackageName() + "." + mapperName;
        try {
            TypeElement absMapperTypeElement = processEnv.getElementUtils().getTypeElement(absMapperClassName);
            if (absMapperTypeElement == null) {
                coder.coding(ABS_MAPPER_TEMPLATE, mapper, processEnv.getFiler().createSourceFile(absMapperClassName).openWriter());
            }
            TypeElement mapperTypeElement = processEnv.getElementUtils().getTypeElement(mapperClassName);
            if (mapperTypeElement == null) {
                coder.coding(MAPPER_TEMPLATE, mapper, processEnv.getFiler().createSourceFile(mapperClassName).openWriter());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}

