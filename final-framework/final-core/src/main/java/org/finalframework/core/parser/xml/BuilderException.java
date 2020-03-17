package org.finalframework.core.parser.xml;


/**
 * @author likly
 * @version 1.0
 * @date 2019-12-03 11:08:14
 * @since 1.0
 */
public class BuilderException extends RuntimeException {

    private static final long serialVersionUID = -3885164021020443281L;

    public BuilderException() {
        super();
    }

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(Throwable cause) {
        super(cause);
    }
}

