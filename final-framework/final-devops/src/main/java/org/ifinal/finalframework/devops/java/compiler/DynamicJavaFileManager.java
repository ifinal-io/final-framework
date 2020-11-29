package org.ifinal.finalframework.devops.java.compiler;

import org.springframework.lang.NonNull;

import javax.tools.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DynamicJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private static final String[] superLocationNames = {StandardLocation.PLATFORM_CLASS_PATH.name(),
            "SYSTEM_MODULES"};
    private final PackageInternalsFinder finder;

    private final DynamicClassLoader classLoader;
    private final List<BytesJavaFileObject> byteCodes = new ArrayList<>();

    public DynamicJavaFileManager(JavaFileManager fileManager, DynamicClassLoader classLoader) {
        super(fileManager);
        this.classLoader = classLoader;

        finder = new PackageInternalsFinder(classLoader);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {

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
    public ClassLoader getClassLoader(Location location) {
        return classLoader;
    }

    @Override
    public String inferBinaryName(Location location, JavaFileObject file) {
        if (file instanceof CustomJavaFileObject) {
            return ((CustomJavaFileObject) file).binaryName();
        }

        return super.inferBinaryName(location, file);
    }

    @Override
    public Iterable<JavaFileObject> list(Location location, String packageName, Set<JavaFileObject.Kind> kinds,
                                         boolean recurse) throws IOException {
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

        public IterableJoin(Iterable<T> first, Iterable<T> next) {
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

        public IteratorJoin(Iterator<T> first, Iterator<T> next) {
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
