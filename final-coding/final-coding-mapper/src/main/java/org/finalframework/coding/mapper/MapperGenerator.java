package org.finalframework.coding.mapper;


import org.finalframework.coding.entity.EntityFactory;
import org.finalframework.coding.generator.AbsTemplateCodeGenerator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-28 09:30:39
 * @since 1.0
 */
public class MapperGenerator extends AbsTemplateCodeGenerator {
    public MapperGenerator(ProcessingEnvironment processEnv) {
        super(processEnv, "dao.mapper");
    }

    @Override
    public void generate(TypeElement typeElement) {

        final String packageName = packageNameGenerator.generate(typeElement);

        Mapper mapper = Mapper.builder()
                .packageName(packageName)
                .name(typeElement.getSimpleName().toString() + "Mapper")
                .entity(EntityFactory.create(processEnv, typeElement))
                .build();

        try {
            coder.coding(mapper, processEnv.getFiler().createSourceFile(mapper.getPackageName() + "." + mapper.getName()).openWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

