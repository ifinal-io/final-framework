package org.ifinal.finalframework.devops.java.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import org.springframework.lang.NonNull;

public class DynamicJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private static final String[] superLocationNames = {StandardLocation.PLATFORM_CLASS_PATH.name(),
        "SYSTEM_MODULES"};

    private final PackageInternalsFinder finder;

    private final DynamicClassLoader classLoader;

    private final List<BytesJavaFileObject> byteCodes = new ArrayList<>();

    public DynamicJavaFileManager(final JavaFileManager fileManager, final DynamicClassLoader classLoader) {

        super(fileManager);
        this.classLoader = classLoader;

        finder = new PackageInternalsFinder(classLoader);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(final Location location, final String className, final JavaFileObject.Kind kind, final FileObject sibling) {

        for (BytesJavaFileObject byteCode : byteCodes) {
            if (byteCode.getClassName().equals(className)) {
                return byteCode;
            }
        }

        BytesJavaFileObject innerClass = new BytesJavaFileObject(className);
        byteCodes.add(innerClass);
        classLoader.registerCompiledSource(innerClass);
        return innerClass;

    }

    @Override
    public ClassLoader getClassLoader(final Location location) {

        return classLoader;
    }

    @Override
    public String inferBinaryName(final Location location, final JavaFileObject file) {

        if (file instanceof CustomJavaFileObject) {
            return ((CustomJavaFileObject) file).binaryName();
        }

        return super.inferBinaryName(location, file);
    }

    @Override
    public Iterable<JavaFileObject> list(final Location location, final String packageName, final Set<JavaFileObject.Kind> kinds,
        final boolean recurse) throws IOException {

        if (location instanceof StandardLocation) {
            String locationName = ((StandardLocation) location).name();
            for (String name : superLocationNames) {
                if (name.equals(locationName)) {
                    return super.list(location, packageName, kinds, recurse);
                }
            }
        }

        // merge JavaFileObjects from specified ClassLoader
        if (location == StandardLocation.CLASS_PATH && kinds.contains(JavaFileObject.Kind.CLASS)) {
            return new IterableJoin<>(super.list(location, packageName, kinds, recurse),
                finder.find(packageName));
        }

        return super.list(location, packageName, kinds, recurse);
    }

    static class IterableJoin<T> implements Iterable<T> {

        private final Iterable<T> first;

        private final Iterable<T> next;

        IterableJoin(final Iterable<T> first, final Iterable<T> next) {

            this.first = first;
            this.next = next;
        }

        @Override
        @NonNull
        public Iterator<T> iterator() {
            return new IteratorJoin<>(first.iterator(), next.iterator());
        }

    }

    static class IteratorJoin<T> implements Iterator<T> {

        private final Iterator<T> first;

        private final Iterator<T> next;

        IteratorJoin(final Iterator<T> first, final Iterator<T> next) {

            this.first = first;
            this.next = next;
        }

        @Override
        public boolean hasNext() {
            return first.hasNext() || next.hasNext();
        }

        @Override
        public T next() {
            if (first.hasNext()) {
                return first.next();
            }
            return next.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

    }

}
