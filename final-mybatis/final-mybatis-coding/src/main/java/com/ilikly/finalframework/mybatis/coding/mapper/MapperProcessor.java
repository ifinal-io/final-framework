package com.ilikly.finalframework.mybatis.coding.mapper;

import com.google.auto.service.AutoService;
import com.ilikly.finalframework.coding.Coder;
import com.ilikly.finalframework.data.mapping.Dialect;
import com.ilikly.finalframework.data.mapping.Entity;
import com.ilikly.finalframework.data.mapping.generator.ColumnGeneratorRegistry;
import com.ilikly.finalframework.mybatis.builder.DefaultXMLMapperBuilder;
import com.ilikly.finalframework.mybatis.generator.BaseColumnGenerator;
import com.ilikly.finalframework.mybatis.generator.DefaultColumnGeneratorModule;
import com.ilikly.finalframework.mybatis.mapper.AbsMapper;
import com.ilikly.finalframework.test.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.w3c.dom.Document;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Generate mapper.xml file of the mapper which annotated by {@link Mapper}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @see MapperXml
 * @since 1.0
 */
@AutoService(Processor.class)
@SuppressWarnings("unused")
public class MapperProcessor extends AbstractProcessor {
    //    private static final Logger logger = LoggerFactory.getLogger(MapperProcessor.class);
    private static final String ABS_MAPPER = AbsMapper.class.getName();
    private final Coder coder = Coder.getDefaultCoder();
    private final Set<Element> mapperElements = new HashSet<>();
    private Filer filer;
    private Elements elementsUtils;
    private Types types;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Mapper.class.getName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        elementsUtils = processingEnv.getElementUtils();
        types = processingEnv.getTypeUtils();
        ColumnGeneratorRegistry.getInstance().setDefaultColumnGenerator(BaseColumnGenerator.INSTANCE);
        ColumnGeneratorRegistry.getInstance().registerColumnModule(Dialect.DEFAULT, new DefaultColumnGeneratorModule());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            generateMapperFiles(mapperElements);
        } else {
            mapperElements.addAll(roundEnv.getElementsAnnotatedWith(Mapper.class));
        }
        return true;
    }

    private void generateMapperFiles(Set<? extends Element> elements) {
        elements.stream()
                .map(it -> ((TypeElement) it).getQualifiedName().toString())
//                .forEach(it -> {
//                    final String resourceFile = it.replace(".", "/") + ".xml";
//                    try {
//                        // would like to be able to print the full path
//                        // before we attempt to get the resource in case the behavior
//                        // of filer.getResource does change to match the spec, but there's
//                        // no good way to resolve CLASS_OUTPUT without first getting a resource.
//                        FileObject existingFile = filer.getResource(StandardLocation.CLASS_PATH, "", resourceFile);
//
////                        logger.info("========Resource file is already exist. {}", resourceFile);
//                        info("Looking for existing resource file at " + existingFile.toUri());
//                        generateMapper(new XPathParser(existingFile.openInputStream()));
//                    } catch (IOException e) {
////                        logger.error("Resource file found exception.{}",resourceFile,e);
//                        // According to the javadoc, Filer.getResource throws an exception
//                        // if the file doesn't already exist.  In practice this doesn't
//                        // appear to be the case.  Filer.getResource will happily return a
//                        // FileObject that refers to a non-existent file but will throw
//                        // IOException if you try to open an input stream for it.
//                        info("Resource file did not already exist.");
////                        logger.info("========Resource file did not already exist. {}", resourceFile);
//
//                        generateMapper(new XPathParser(getMapperTemplate(it)));
//                    }
//                });
//
                .filter(it -> {
                    final String resourceFile = it.replace(".", "/") + ".xml";
                    try {
                        // would like to be able to print the full path
                        // before we attempt to get the resource in case the behavior
                        // of filer.getResource does change to match the spec, but there's
                        // no good way to resolve CLASS_OUTPUT without first getting a resource.
                        FileObject existingFile = filer.getResource(StandardLocation.CLASS_PATH, "", resourceFile);

//                        logger.info("========Resource file is already exist. {}", resourceFile);
                        info("Looking for existing resource file at " + existingFile.toUri());
                        return false;
                    } catch (IOException e) {
//                        logger.error("Resource file found exception.{}",resourceFile,e);
                        // According to the javadoc, Filer.getResource throws an exception
                        // if the file doesn't already exist.  In practice this doesn't
                        // appear to be the case.  Filer.getResource will happily return a
                        // FileObject that refers to a non-existent file but will throw
                        // IOException if you try to open an input stream for it.
                        info("Resource file did not already exist.");
//                        logger.info("========Resource file did not already exist. {}", resourceFile);
                        return true;
                    }
                })
                .forEach(it -> {
                    try {
//                        buildMapper(new XPathParser(getMapperTemplate(it)));
                        final String resourceFile = it.replace(".", "/") + ".xml";
                        FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", resourceFile);
                        coder.coding(new MapperXml(it), fileObject.openWriter());
                        info("Create mapper.xml of mapper:" + it);
                    } catch (Exception e) {
//                        logger.error("Create mapper.xml error of mapper:{}", it, e);
                        error("Create mapper.xml error of mapper:" + it + "," + e.getMessage());
                    }
                });
    }

    private String getMapperTemplate(String mapper) {
        System.out.println("========================================================");
        String format = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n" +
                "<mapper namespace=\"%s\">\n" +
                "</mapper>", mapper);
        System.out.println(format);
        return format;
    }

    private void generateMapper(XPathParser xPathParser) {
        XNode mapperNode = xPathParser.evalNode("/mapper");
        String mapper = mapperNode.getStringAttribute("namespace");
        String mapperContent = buildMapper(xPathParser);
        int mapperIndex = mapperContent.indexOf("<mapper");
        mapperContent = mapperContent.substring(mapperIndex);
        System.out.println(":::::::" + mapperContent);
        final String resourceFile = mapper.replace(".", "/") + ".xml";
        try {
            FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", resourceFile);
            Writer writer = fileObject.openWriter();
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            writer.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n");
            writer.write(mapperContent);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            error("Create mapper.xml error of mapper:" + mapper + "," + e.getMessage());
        }
    }

    private String buildMapper(XPathParser xPathParser) {
        XNode mapperNode = xPathParser.evalNode("/mapper");
        Entity<Person> entity = Entity.from(Person.class);
        new DefaultXMLMapperBuilder(Dialect.DEFAULT, mapperNode, entity);
//        return mapperNode.toString();
        Document document = mapperNode.getNode().getOwnerDocument();
        String result = null;

        StringWriter strWtr = new StringWriter();
        StreamResult strResult = new StreamResult(strWtr);
        TransformerFactory tfac = TransformerFactory.newInstance();
        try {
            javax.xml.transform.Transformer t = tfac.newTransformer();
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,
            // text
            t.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            document.setXmlStandalone(false);
            t.transform(new DOMSource(document.getDocumentElement()),
                    strResult);
        } catch (Exception e) {
            System.err.println("XML.toString(Document): " + e);
        }
        result = strResult.getWriter().toString();
        try {
            strWtr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        logger.info(result);
        return result;
    }


    private void info(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }

    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }

}
