package org.ifinal.finalframework.data.query;

/**
 * QueryProvider.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface QueryProvider {

    String where();

    String orders();

    String limit();

}
