package com.ilikly.finalframework.coding.plugins.mybatis;

import com.google.auto.service.AutoService;
import com.ilikly.finalframework.coding.coder.Coder;
import com.ilikly.finalframework.coding.coder.FreeMakerCoder;
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
    private static final String ABSMAPPER = "com.ilikly.finalframework.mybatis.mapper.AbsMapper";
    private final Coder coder = new FreeMakerCoder();
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
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            generateEntityFiles(mapperElements);
        } else {
            mapperElements.addAll(roundEnv.getElementsAnnotatedWith(Mapper.class));
        }
        return true;
    }

    private void generateEntityFiles(Set<? extends Element> elements) {
        elements.stream()
//                .peek(it -> {
//                    logger.info("mapper:{}",((Element) it).toString());
//                    List<? extends TypeMirror> interfaces = ((TypeElement) it).getInterfaces();
//                    DeclaredType absMapper = (DeclaredType) interfaces.get(0);
//                    TypeMirror typeMirror = absMapper.getTypeArguments().get(1);
//
//                    try {
//                        Class<?> clazz = Class.forName(typeMirror.toString());
//                        logger.info("find mapper entity class:{}",clazz);
//                    } catch (ClassNotFoundException e) {
//                        logger.error("",e);
//                    }
//
//                    logger.info("entity:{}", typeMirror.toString());
//                    TypeElement typeElement = (TypeElement) ((DeclaredType) interfaces.get(0)).asElement();
//                    TypeParameterElement parameterElement = typeElement.getTypeParameters().get(1);
//                    logger.info("typeElement:{}",parameterElement.toString());
//                })
//                .filter(mapper-> types.isAssignable(types.erasure(mapper.asType()),
//                        elementsUtils.getTypeElement(ABSMAPPER).asType()))
                .filter(mapper -> {
                    boolean assignable = types.isAssignable(types.erasure(mapper.asType()), elementsUtils.getTypeElement(ABSMAPPER).asType());
//                    logger.info("-----------------------result:{}", assignable);
                    return true;
                })
                .map(it -> ((TypeElement) it).getQualifiedName().toString())
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
        return String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n" +
                "<mapper namespace=\"%s\">\n" +
                "</mapper>", mapper);
    }

    private void buildMapper(XPathParser xPathParser) throws IOException {
//        XPathParser xPathParser = new XPathParser(mapperObject.openInputStream());
        XNode mapperNode = xPathParser.evalNode("/mapper");
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
            document.setXmlStandalone(true);
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
    }


    private void info(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }

    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }

}
