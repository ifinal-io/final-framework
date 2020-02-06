package org.finalframework.coding.datasource.processer;

import com.google.auto.service.AutoService;
import org.finalframework.coding.Coder;
import org.finalframework.coding.datasource.annotation.DataSource;
import org.finalframework.coding.datasource.annotation.ShardingRule;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private final Set<Element> dataSourceElements = new HashSet<>();

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
        if (roundEnv.processingOver()) {
            processDataSources(dataSourceElements);
        } else {
            dataSourceElements.addAll(roundEnv.getElementsAnnotatedWith(DataSource.class));
        }
        return false;
    }

    private void processDataSources(Set<? extends Element> elements) {
        for (Element element : elements) {
            if (element.getAnnotation(ShardingRule.class) != null) {
                dataSourceAutoConfigurationGenerator.generate((TypeElement) element);
            }
        }
    }


}
