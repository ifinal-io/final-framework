package org.finalframework.data.query.operation;


/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:29:21
 * @since 1.0
 */
public interface JsonOperation extends Operation {
    Operation JSON_EXTRACT = new SimpleOperation("JSON_EXTRACT");
    Operation JSON_CONTAINS = new SimpleOperation("JSON_CONTAINS");
    Operation JSON_UNQUOTE = new SimpleOperation("JSON_UNQUOTE");
    Operation JSON_KEYS = new SimpleOperation("JSON_KEYS");
    Operation JSON_DEPTH = new SimpleOperation("JSON_DEPTH");
    Operation JSON_LENGTH = new SimpleOperation("JSON_LENGTH");
}

