package org.finalframework.coding.datasource.processer;


import org.finalframework.coding.Coder;
import org.finalframework.coding.datasource.spring.metadata.DataSourceAutoConfigurationMetaData;
import org.finalframework.core.generator.Generator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-17 02:09:30
 * @since 1.0
 */
public class DataSourceAutoConfigurationGenerator implements Generator<TypeElement, DataSourceAutoConfigurationMetaData> {
    protected final Coder coder = Coder.getDefaultCoder();
    protected final ProcessingEnvironment processEnv;
    private final DataSourceAutoConfigurationFactory factory;

    public DataSourceAutoConfigurationGenerator(ProcessingEnvironment processEnv) {
        this.processEnv = processEnv;
        factory = new DataSourceAutoConfigurationFactory(processEnv);
    }

    @Override
    public DataSourceAutoConfigurationMetaData generate(TypeElement element) {
        DataSourceAutoConfiguration configuration = factory.create(element);
        try {
            TypeElement typeElement = processEnv.getElementUtils().getTypeElement(configuration.getName());
            if (typeElement == null) {
                JavaFileObject sourceFile = processEnv.getFiler().createSourceFile(configuration.getName());
                coder.coding(configuration, sourceFile.openWriter());
            }
        } catch (Exception e) {
//            processEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
        return new DataSourceAutoConfigurationMetaData(configuration);
    }
}

