package com.ilikly.finalframework.mybatis.coding.datasource.processer;

import com.google.auto.service.AutoService;
import com.ilikly.finalframework.coding.Coder;
import com.ilikly.finalframework.coding.FreeMakerCoder;
import com.ilikly.finalframework.core.Assert;
import com.ilikly.finalframework.mybatis.coding.datasource.DataSource;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @since 1.0
 */
@AutoService(Processor.class)
@SuppressWarnings("unused")
public class DataSourceProcessor extends AbstractProcessor {
    private final Coder coder = new FreeMakerCoder();
    private Filer filer;
    private Elements elements;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(DataSource.class.getName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elements = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generateEntityFiles(roundEnv.getElementsAnnotatedWith(DataSource.class));
        return true;
    }

    private void generateEntityFiles(Set<? extends Element> elements) {
        elements
                .stream()
                .map(it -> {
                    DataSource dataSource = it.getAnnotation(DataSource.class);

                    final String packageName = this.elements.getPackageOf(it).getQualifiedName().toString();
                    final String className = it.getSimpleName().toString();
                    String name = Assert.isEmpty(dataSource.name()) ? className.substring(0, 1).toLowerCase() + className.substring(1)
                            : dataSource.name();

                    if (name.endsWith("DataSource")) {
                        name = name.substring(0, name.indexOf("DataSource"));
                    }

                    return DataSourceModel.builder()
                            .packageName(packageName)
                            .name(className + "Config")
                            .basePackages(dataSource.basePackages())
                            .mapperLocations(dataSource.mapperLocations())
                            .prefix(dataSource.prefix())
                            .dataSource(name + "DataSource")
                            .transactionManager(name + "TransactionManager")
                            .sqlSessionFactory(name + "SqlSessionFactory")
                            .sqlSessionTemplate(name + "SqlSessionTemplate")
                            .build();

                })
                .collect(Collectors.toList())
                .forEach(it -> {
                    try {
                        coder.coding(it, filer
                                .createSourceFile(it.getPackage() + "." + it.getName())
                                .openWriter());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });


    }


}
