package org.finalframework.coding.datasource.processer;

import com.google.auto.service.AutoService;
import org.finalframework.coding.Coder;
import org.finalframework.coding.datasource.annotation.DataSource;
import org.finalframework.coding.datasource.annotation.ShardingRule;
import org.finalframework.core.Assert;

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
    public static final String DATASOURCE = "DataSource";
    private final Coder coder = Coder.getDefaultCoder();
    private Filer filer;
    private Elements elements;

    private DataSourceAutoConfigurationGenerator dataSourceAutoConfigurationGenerator;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(DataSource.class.getName());
        types.add(ShardingRule.class.getName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
        this.elements = processingEnv.getElementUtils();
        this.dataSourceAutoConfigurationGenerator = new DataSourceAutoConfigurationGenerator(processingEnv, "");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        generateEntityFiles(roundEnv.getRootElements());
        processDataSources(roundEnv.getRootElements());
        return false;
    }

    private void processDataSources(Set<? extends Element> elements) {
        for (Element element : elements) {
            if (element.getAnnotation(ShardingRule.class) != null) {
                dataSourceAutoConfigurationGenerator.generate((TypeElement) element);
            }
        }
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

                    return DataSourceAutoConfigurations.builder()
                            .packageName(packageName)
                            .name(className + "AutoConfiguration")
                            .properties(className + "Properties")
                            .basePackages(dataSource.basePackages())
                            .mapperLocations(dataSource.mapperLocations())
                            .prefix(dataSource.prefix())
                            .dataSource(DATASOURCE.equalsIgnoreCase(name) ? name : name + DATASOURCE)
                            .transactionManager(name + "TransactionManager")
                            .sqlSessionFactory(name + "SqlSessionFactory")
                            .sqlSessionTemplate(name + "SqlSessionTemplate")
                            .build();

                })
                .collect(Collectors.toList())
                .forEach(it -> {
                    try {

                        DataSourceProperties dataSourceProperties = DataSourceProperties.builder()
                                .packageName(it.getPackage())
                                .name(it.getProperties())
                                .prefix(it.getPrefix())
                                .build();

                        coder.coding(dataSourceProperties, filer.createSourceFile(dataSourceProperties.getPackageName() + "." + dataSourceProperties.getName()).openWriter());
                        coder.coding(it, filer.createSourceFile(it.getPackage() + "." + it.getName()).openWriter());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });


    }


}
