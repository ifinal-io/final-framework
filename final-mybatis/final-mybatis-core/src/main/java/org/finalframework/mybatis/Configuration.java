package org.finalframework.mybatis;

import lombok.Data;
import org.finalframework.data.mapping.converter.CameHump2UnderlineNameConverter;
import org.finalframework.data.mapping.converter.NameConverter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 16:09:06
 * @since 1.0
 */
@Data
public class Configuration {

    private static final Configuration instance = new Configuration();
    private final NameConverter defaultNameConverter = new CameHump2UnderlineNameConverter();
    private NameConverter tableNameConverter = defaultNameConverter;
    private NameConverter columnNameConverter = defaultNameConverter;

    public static Configuration getInstance() {
        return instance;
    }
}
