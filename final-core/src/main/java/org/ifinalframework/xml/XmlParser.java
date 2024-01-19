package org.ifinalframework.xml;

/**
 * @author iimik
 * @version 1.2.4
 **/
@FunctionalInterface
public interface XmlParser<S> {

    Element parse(S source);

}
