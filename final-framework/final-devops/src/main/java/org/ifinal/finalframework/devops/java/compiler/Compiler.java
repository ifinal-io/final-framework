package org.ifinal.finalframework.devops.java.compiler;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class Compiler {
    private final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
    private final StandardJavaFileManager standardFileManager;
    private final List<String> options = new ArrayList<>();
    private final DynamicClassLoader dynamicClassLoader;

    private final Collection<JavaFileObject> compilationUnits = new ArrayList<>();
    private final List<Diagnostic<? extends JavaFileObject>> errors = new ArrayList<>();
    private final List<Diagnostic<? extends JavaFileObject>> warnings = new ArrayList<>();

    public Compiler(ClassLoader classLoader) {
        if (javaCompiler == null) {
            throw new IllegalStateException(
                    "Can not load JavaCompiler from javax.tools.ToolProvider#getSystemJavaCompiler(),"
                            + " please confirm the application running in JDK not JRE.");
        }
        standardFileManager = javaCompiler.getStandardFileManager(null, null, null);

        options.add("-Xlint:unchecked");
        dynamicClassLoader = new DynamicClassLoader(classLoader);
    }

    public void addSource(String className, String source) {
        addSource(new StringJavaFileObject(className, source));
    }

    public void addSource(JavaFileObject javaFileObject) {
        compilationUnits.add(javaFileObject);
    }


    public DynamicClassLoader compile() {

        errors.clear();
        warnings.clear();

        JavaFileManager fileManager = new DynamicJavaFileManager(standardFileManager, dynamicClassLoader);

        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, fileManager, collector, options, null,
                compilationUnits);

        try {

            if (!compilationUnits.isEmpty()) {
                boolean result = task.call();

                if (!result || !collector.getDiagnostics().isEmpty()) {

                    for (Diagnostic<? extends JavaFileObject> diagnostic : collector.getDiagnostics()) {
                        switch (diagnostic.getKind()) {
                            case NOTE:
                            case MANDATORY_WARNING:
                            case WARNING:
                                warnings.add(diagnostic);
                                break;
                            case OTHER:
                            case ERROR:
                            default:
                                errors.add(diagnostic);
                                break;
                        }

                    }

                    if (!errors.isEmpty()) {
                        throw new DynamicCompilerException("Compilation Error", errors);
                    }
                }
            }

            return dynamicClassLoader;
        } catch (Exception e) {
            throw new DynamicCompilerException(e, errors);
        } finally {
            compilationUnits.clear();

        }

    }


    public Map<String, Class<?>> build() throws ClassNotFoundException {
        DynamicClassLoader classLoader = compile();
        return classLoader.getClasses();

    }

    public Map<String, byte[]> buildByteCodes() {
        return compile().getByteCodes();
    }

    private List<String> diagnosticToString(List<Diagnostic<? extends JavaFileObject>> diagnostics) {

        List<String> diagnosticMessages = new ArrayList<>();

        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
            diagnosticMessages.add(
                    "line: " + diagnostic.getLineNumber() + ", message: " + diagnostic.getMessage(Locale.US));
        }

        return diagnosticMessages;

    }

    public List<String> getErrors() {
        return diagnosticToString(errors);
    }

    public List<String> getWarnings() {
        return diagnosticToString(warnings);
    }

    public ClassLoader getClassLoader() {
        return dynamicClassLoader;
    }
}
