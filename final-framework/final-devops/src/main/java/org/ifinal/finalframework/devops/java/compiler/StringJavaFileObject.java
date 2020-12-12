package org.ifinal.finalframework.devops.java.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class StringJavaFileObject extends SimpleJavaFileObject {
    private final String contents;

    public StringJavaFileObject(final String className, final String contents) {

        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.contents = contents;
    }

    @Override
    public CharSequence getCharContent(final boolean ignoreEncodingErrors) throws IOException {

        return contents;
    }

}
