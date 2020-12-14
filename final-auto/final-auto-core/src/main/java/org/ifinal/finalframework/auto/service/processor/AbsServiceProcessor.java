package org.ifinal.finalframework.auto.service.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * AbsServiceProcessor.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class AbsServiceProcessor extends AbstractProcessor {

    private static final String DEFAULT_SERVICE_PATH = "services";

    /**
     * service:instance:name.
     */
    private final Map<String, Map<String, String>> services = new HashMap<>();

    private final Map<String, String> paths = new HashMap<>();

    @Override
    public final boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            generateServiceFiles();
            return false;
        } else {
            return doProcess(annotations, roundEnv);
        }
    }

    private void generateServiceFiles() {

        final Filer filer = processingEnv.getFiler();

        for (Map.Entry<String, Map<String, String>> entry : services.entrySet()) {
            final String providerInterface = entry.getKey();
            final String resourceFile = ServicesFiles.getPath(this.paths.get(providerInterface), providerInterface);
            log("Working on resource file: " + resourceFile);
            try {
                final Map<String, String> allServices = new TreeMap<>(readOldService(filer, resourceFile));
                allServices.putAll(entry.getValue());
                log("New service file contents: " + allServices);
                final FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "",
                    resourceFile);
                final OutputStream out = fileObject.openOutputStream();
                ServicesFiles.writeServiceFile(allServices, out);
                out.close();
                log("Wrote to: " + fileObject.toUri());
            } catch (final IOException e) {
                fatalError("Unable to create " + resourceFile + ", " + e);
                return;
            }
        }
    }

    protected abstract boolean doProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv);

    protected void log(final String msg) {

        if (processingEnv.getOptions().containsKey("debug")) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
        }
    }

    private Map<String, String> readOldService(final Filer filer, final String resourceFile) {

        try {
            // would like to be able to print the full path
            // before we attempt to get the resource in case the behavior
            // of filer.getResource does change to match the spec, but there's
            // no good way to resolve CLASS_OUTPUT without first getting a resource.
            final FileObject existingFile = filer.getResource(StandardLocation.CLASS_OUTPUT, "",
                resourceFile);
            log("Looking for existing resource file at " + existingFile.toUri());
            final Map<String, String> oldServices = ServicesFiles.readServiceFile(existingFile.openInputStream());
            log("Existing service entries: " + oldServices);
            return oldServices;
        } catch (IOException e) {
            // According to the javadoc, Filer.getResource throws an exception
            // if the file doesn't already exist.  In practice this doesn't
            // appear to be the case.  Filer.getResource will happily return a
            // FileObject that refers to a non-existent file but will throw
            // IOException if you try to open an input stream for it.
            log("Resource file did not already exist.");
            return Collections.emptyMap();
        }
    }

    protected void fatalError(final String msg) {

        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "FATAL ERROR: " + msg);
    }

    protected final void addService(final @NonNull TypeElement service, final @NonNull TypeElement instance) {

        addService(service, instance, null, null);
    }

    protected final void addService(final @NonNull TypeElement service, final @NonNull TypeElement instance,
        final @Nullable String name, final @Nullable String path) {

        final String serviceName = service.getQualifiedName().toString();
        final Map<String, String> instances = this.services.computeIfAbsent(serviceName, key -> new HashMap<>());
        instances.put(instance.getQualifiedName().toString(), name);
        this.paths.putIfAbsent(serviceName, Optional.ofNullable(path).orElse(DEFAULT_SERVICE_PATH));
    }

    @SuppressWarnings("unused")
    protected void error(final String msg, final Element element, final AnnotationMirror annotation) {

        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, element, annotation);
    }

}



