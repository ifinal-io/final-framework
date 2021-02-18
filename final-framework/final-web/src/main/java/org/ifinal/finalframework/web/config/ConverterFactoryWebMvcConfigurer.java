package org.ifinal.finalframework.web.config;

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
 * An {@link WebMvcConfigurer} auto-detects {@link ConverterFactory} beans to {@link WebMvcConfigurer#addFormatters(FormatterRegistry)}.
 * Also support SPI from {@link ServiceLoader}.
 *
 * @author likly
 * @version 1.0.0
 * @see ConverterFactory
 * @see FormatterRegistry
 * @see ServiceLoader
 * @since 1.0.0
 */
@Slf4j
@Component
@SuppressWarnings("unused")
public class ConverterFactoryWebMvcConfigurer implements WebMvcConfigurer {

    private final List<ConverterFactory<?, ?>> converterFactories;

    public ConverterFactoryWebMvcConfigurer(final ObjectProvider<List<ConverterFactory<?, ?>>> converterFactoriesProvider) {
        this.converterFactories = converterFactoriesProvider.getIfAvailable();
    }

    @Override
    public void addFormatters(final @NonNull FormatterRegistry registry) {

        logger.info("start register converterFactories ...");
        if (Asserts.nonEmpty(converterFactories)) {
            converterFactories.forEach(item -> this.addConverterFactory(registry, item));
        }
        ServiceLoader.load(ConverterFactory.class).forEach(item -> this.addConverterFactory(registry, item));
        logger.info("finish register converterFactories ...");
    }

    private void addConverterFactory(final FormatterRegistry registry, final ConverterFactory<?, ?> factory) {
        if (logger.isInfoEnabled()) {
            logger.info("addConverterFactory: {}", factory.getClass().getName());
        }
        registry.addConverterFactory(factory);
    }

}
