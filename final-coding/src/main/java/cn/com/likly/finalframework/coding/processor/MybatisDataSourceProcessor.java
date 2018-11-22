package cn.com.likly.finalframework.coding.processor;

import cn.com.likly.finalframework.coding.coder.Coder;
import cn.com.likly.finalframework.coding.coder.FreemakerCoder;
import cn.com.likly.finalframework.coding.model.mybatis.MybatisDataSource;
import cn.com.likly.finalframework.mybatis.annotation.DataSource;
import com.google.auto.service.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class MybatisDataSourceProcessor extends AbstractProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MybatisDataSourceProcessor.class);
    private final Coder coder = new FreemakerCoder();
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
                    String name = className.substring(0,1).toLowerCase() + className.substring(1);

                    if(name.endsWith("DataSource")){
                        name = name.substring(0,name.indexOf("DataSource"));
                    }

                    return new MybatisDataSource(
                                    packageName,
                                    className + "Config",
                            dataSource.basePackages(),
                            dataSource.mapperLocations(),
                            dataSource.prefix(),
                                    name + "DataSource",
                                    name + "SqlSessionFactory",
                                    name + "SqlSessionTemplate"
                            );
                })
                .collect(Collectors.toList())
                .forEach(it -> {
                    try {
                        coder.coding(it, filer
                                .createSourceFile(it.getPackage() + "." +it.getName())
                                .openWriter());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });


    }


}
