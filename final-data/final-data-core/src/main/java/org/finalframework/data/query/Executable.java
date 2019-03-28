package org.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 23:09:27
 * @since 1.0
 */
public interface Executable<T> extends Criteriable<T, Criteria> {

    static <T> Executable<T> execute(QProperty<T> property) {
        return new ExecutableImpl<>(property);
    }


    Executable<T> date();

    Executable<T> and(T value);

    Executable<T> or(T value);

    Executable<T> xor(T value);

    Executable<T> not();
}
