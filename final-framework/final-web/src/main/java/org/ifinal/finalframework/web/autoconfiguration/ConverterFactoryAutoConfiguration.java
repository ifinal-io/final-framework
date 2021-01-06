package org.ifinal.finalframework.web.autoconfiguration;

import java.util.List;
import java.util.ServiceLoader;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author likly
 * @version 1.0.0
 * @see ConverterFactory
 * @since 1.0.0
 */
@Slf4j
@Component
@SuppressWarnings("unused")
public class ConverterFactoryAutoConfiguration implements WebMvcConfigurer {

    private final List<ConverterFactory<?, ?>> converterFactories;

    public ConverterFactoryAutoConfiguration(final ObjectProvider<List<ConverterFactory<?, ?>>> converterFactoriesProvider) {

        this.converterFactories = converterFactoriesProvider.getIfAvailable();
    }

    @Override
    public void addFormatters(final @NonNull FormatterRegistry registry) {

        logger.info("start register converterFactories ...");
        if (Asserts.nonEmpty(converterFactories)) {
            converterFactories.forEach(registry::addConverterFactory);
        }
        ServiceLoader.load(ConverterFactory.class).forEach(registry::addConverterFactory);
        logger.info("finish register converterFactories ...");
    }

}
