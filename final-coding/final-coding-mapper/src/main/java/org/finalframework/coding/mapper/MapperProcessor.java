package org.finalframework.coding.mapper;

import com.google.auto.service.AutoService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.EntityFactory;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.builder.FinalXmlMapperBuilder;
import org.finalframework.coding.mapper.builder.XmlMapperBuilder;
import org.finalframework.coding.mapper.xml.Association;
import org.finalframework.core.configuration.Configuration;
import org.finalframework.data.repository.Repository;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
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
import java.util.List;
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
    private static final String ABS_MAPPER = AbsMapper.class.getCanonicalName();
//    private final Coder coder = Coder.getDefaultCoder();
    private final Set<Element> mapperElements = new HashSet<>();
    private Filer filer;
    private Elements elementsUtils;
    private Types types;
    private XmlMapperBuilder xmlMapperBuilder;
    private TypeElement absMapperElement;
    private DeclaredType absMapperType;
    private TypeElement repositoryTypeElement;

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

    public static String formatXml(Document document) {
        try {// 格式化输出格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            StringWriter writer = new StringWriter();
            // 格式化输出流
            XMLWriter xmlWriter = new XMLWriter(writer, format);
            // 将document写入到输出流
            xmlWriter.write(document);
            xmlWriter.close();
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        elementsUtils = processingEnv.getElementUtils();
        types = processingEnv.getTypeUtils();
        this.xmlMapperBuilder = new FinalXmlMapperBuilder(processingEnv);
        Configuration.getInstance().load(processingEnv);
        this.repositoryTypeElement = elementsUtils.getTypeElement(Repository.class.getCanonicalName());
        this.absMapperElement = elementsUtils.getTypeElement(ABS_MAPPER);
        absMapperType = (DeclaredType) absMapperElement.asType();
        Utils.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
//            generateMapperFiles(mapperElements);
            generateMapperFiles2(mapperElements);
        } else {
            mapperElements.addAll(roundEnv.getElementsAnnotatedWith(Mapper.class));
        }
        return false;
    }

    private void generateMapperFiles2(Set<? extends Element> elements) {
        elements.stream()
                .map(it -> ((TypeElement) it))
                .forEach(it -> {
                    final String mapperClassName = it.getQualifiedName().toString();
                    final String resourceFile = mapperClassName.replace(".", "/") + ".xml";
                    XPathParser parser = null;
                    try {
                        try {
                            // would like to be able to print the full path
                            // before we attempt to get the resource in case the behavior
                            // of filer.getResource does change to match the spec, but there's
                            // no good way to resolve CLASS_OUTPUT without first getting a resource.
                            FileObject existingFile = filer.getResource(StandardLocation.CLASS_PATH, "", resourceFile);
//                        logger.info("========Resource file is already exist. {}", resourceFile);
                            info("Looking for existing resource file at " + existingFile.toUri());
                            parser = new XPathParser(existingFile.openInputStream());
//                            document = documentBuilder.parse(existingFile.openInputStream());
                        } catch (Exception e) {
                            // According to the javadoc, Filer.getResource throws an exception
                            // if the file doesn't already exist.  In practice this doesn't
                            // appear to be the case.  Filer.getResource will happily return a
                            // FileObject that refers to a non-existent file but will throw
                            // IOException if you try to open an input stream for it.
                            info("Resource file did not already exist.");
                            parser = new XPathParser(getMapperTemplate(mapperClassName));
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    error("isSubtype:"  + types.isSubtype(it.asType(),types.erasure(absMapperType)));
//                    error("isAssignable:"  + types.isAssignable(it.asType(),types.erasure(absMapperType)));
//                    error("isSameType:"  + types.isSameType(it.asType(),types.erasure(absMapperType)));

                    List<? extends TypeMirror> interfaces = it.getInterfaces();


                    for (TypeMirror item : interfaces) {
                        StringBuilder builder = new StringBuilder();
                        builder.append("mapper=").append(it.asType().toString()).append("\n");
                        builder.append("absMapper=").append(types.erasure(absMapperType).toString()).append("\n");
                        builder.append("isAbsMapper=").append(types.isSubtype(types.erasure(it.asType()), types.erasure(absMapperType))).append("\n");
                        builder.append("interface=").append(item.toString()).append("\n");
                        builder.append("subType=").append(types.erasure(item).toString()).append("\n");
                        builder.append("parentType=").append(types.erasure(absMapperType).toString()).append("\n");
                        builder.append("isAssignable=").append(types.isAssignable(item, types.erasure(absMapperType))).append("\n");
                        builder.append("isSubtype=").append(types.isSubtype(item, types.erasure(absMapperType))).append("\n");
                        builder.append("isSameType=").append(types.isSameType(item, types.erasure(absMapperType))).append("\n");
//                        error(builder.toString());
//                        info(builder.toString());
                    }


                    if (parser != null) {
                        XNode mapper = parser.evalNode("//mapper");
                        Document document = mapper.getNode().getOwnerDocument();

                        DeclaredType absMapperElement = findAbsMapperElement(it);

                        if(absMapperElement != null){

                            List<? extends TypeMirror> typeArguments = absMapperElement.getTypeArguments();
                            DeclaredType entityType = (DeclaredType) typeArguments.get(1);
                            TypeElement entityTypeElement = (TypeElement) entityType.asElement();
                            Entity<Property> entity = EntityFactory.create(processingEnv, entityTypeElement);

//                        error("views:"+entity.getProperty("stringList").getViews());

                            for (Property property : entity) {
                                StringBuilder builder = new StringBuilder();
                                builder.append("element=").append(property.getElement().toString()).append("\n");
                                builder.append("type=").append(property.getElement().asType().toString()).append("\n");
                                builder.append("name=").append(property.getName()).append("\n");
                                builder.append("kind=").append(property.getElement().getKind()).append("\n");
                                builder.append("isEnum=").append(Utils.getInstance().isEnum(property.getElement())).append("\n");
                                builder.append("isReference=").append(property.isReference()).append("\n");
                                builder.append("elementClass=").append(property.getElement().getClass()).append("\n");
                                builder.append("views=").append(property.getViews()).append("\n");
                                if (property.isReference()) {
                                    builder.append("association=").append(Association.from(property, property.toEntity()).getElements().size()).append("\n");
                                }

//                            error(builder.toString());
                            }
                            xmlMapperBuilder.build(mapper.getNode(), document, it, entity);
                        }

                        String mapperContent = buildMapperContent(document);
                        try {
                            FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", resourceFile);
                            Writer writer = fileObject.openWriter();
                            writer.write(mapperContent);
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            error("Generate mapper error" + e.getMessage());
                        }
                        info(mapperContent);
                    }

                });


    }

    private DeclaredType findAbsMapperElement(TypeElement element) {
        for (TypeMirror item : element.getInterfaces()) {

            if(item.toString().startsWith(ABS_MAPPER)){
                return (DeclaredType) item;
            }

            if (types.isSameType(types.erasure(item), types.erasure(absMapperType))) {
                return (DeclaredType) item;
            }
        }
        return null;
    }

    private String buildMapperContent(Document document) {
        String result = null;
        if (document != null) {
            try {
                StringWriter strWtr = new StringWriter();
                StreamResult strResult = new StreamResult(strWtr);
                TransformerFactory tfac = TransformerFactory.newInstance();
                DocumentType doctype = document.getImplementation().createDocumentType("DOCTYPE", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");

                javax.xml.transform.Transformer t = tfac.newTransformer();
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,
                t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
                t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
                // text
                t.setOutputProperty(
                        "{http://xml.apache.org/xslt}indent-amount", "4");
                document.setXmlStandalone(true);
                t.transform(new DOMSource(document.getDocumentElement()),
                        strResult);
                result = strResult.getWriter().toString();
                strWtr.close();
            } catch (Exception e) {
                System.err.println("XML.toString(Document): " + e);
            }
        }

        return result;

    }

    private void loggerMapper(Document document) {
        error(buildMapperContent(document));
    }

    private String getMapperTemplate(String mapper) {
        System.out.println("========================================================");
        String format = String.format("<mapper namespace=\"%s\">\n" +
                "</mapper>", mapper);
        System.out.println(format);
        return format;
    }

    private void info(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }

    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }

    private void warn(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, msg);
    }

}
