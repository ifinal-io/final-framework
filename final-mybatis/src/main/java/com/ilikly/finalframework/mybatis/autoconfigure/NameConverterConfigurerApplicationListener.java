package com.ilikly.finalframework.mybatis.autoconfigure;

import com.ilikly.finalframework.coding.plugins.spring.annotation.ApplicationEventListener;
import com.ilikly.finalframework.data.mapping.converter.NameConverter;
import com.ilikly.finalframework.data.mapping.converter.NameConverterRegister;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 20:56:33
 * @since 1.0
 */
@ApplicationEventListener
public class NameConverterConfigurerApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private static final String TABLE_CONVERTER = "final.data.name-converter.table-converter";
    private static final String COLUMN_CONVERTER = "final.data.name-converter.column-converter";


    private void init(ConfigurableEnvironment environment) {
        final String tableConverterClassName = environment.getProperty(TABLE_CONVERTER);
        final String columnConverterClassName = environment.getProperty(COLUMN_CONVERTER);

        final NameConverter tableConverter = initNameConverter(tableConverterClassName);
        if (tableConverter != null) {
            NameConverterRegister.getInstance().setTableConverter(tableConverter);
        }

        final NameConverter columnConverter = initNameConverter(columnConverterClassName);
        if (columnConverter != null) {
            NameConverterRegister.getInstance().setColumnConverter(columnConverter);
        }


    }

    private NameConverter initNameConverter(String converter) {
        try {
            if (converter == null) return null;
            Class<?> nameConverterClass = Class.forName(converter);
            return (NameConverter) nameConverterClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        init(event.getEnvironment());
    }
}
