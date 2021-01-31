package org.ifinal.finalframework.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.spi.ContextAware;
import ch.qos.logback.core.spi.LifeCycle;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.OptionHelper;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.ifinal.finalframework.auto.spring.factory.annotation.SpringApplicationListener;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.unit.DataSize;

/**
 * LoggingExtApplicationListener.
 *
 * @author likly
 * @version 1.0.0
 * @see LoggingApplicationListener
 * @since 1.0.0
 */
@SpringApplicationListener
public class LoggingExtApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final String CONSOLE_LOG_PATTERN = "%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){faint} "
        + "%clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} "
        + "%clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} "
        + "%clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}";

    private static final String FILE_LOG_PATTERN = "%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}} "
        + "${LOG_LEVEL_PATTERN:-%5p} ${PID:- } --- [%t] %-40.40logger{39} : %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}";

    private static final DataSize MAX_FILE_SIZE = DataSize.ofMegabytes(10);

    private static final Integer MAX_FILE_HISTORY = 7;

    private static final ConfigurationPropertyName LOGGING_LOGGER = ConfigurationPropertyName.of("logging.logger");

    private static final Bindable<Map<String, String>> STRING_LOGLOGGER_MAP = Bindable.mapOf(String.class, String.class);

    private static final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    private PropertyResolver patterns;

    @Override
    public void onApplicationEvent(final ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();

        this.patterns = getPatternsResolver(environment);

        final String filePath = this.patterns.getProperty("logging.file.path", "logs");

        Binder binder = Binder.get(environment);

        Map<String, String> loggers = binder.bind(LOGGING_LOGGER, STRING_LOGLOGGER_MAP).orElse(Collections.emptyMap());

        for (final Entry<String, String> entry : loggers.entrySet()) {
            Logger logger = (Logger) LoggerFactory.getLogger(entry.getKey());
            logger.addAppender(fileAppender(new File(filePath, entry.getValue()).getPath()));
        }

    }

    private PropertyResolver getPatternsResolver(final Environment environment) {
        if (environment == null) {
            return new PropertySourcesPropertyResolver(null);
        }
        if (environment instanceof ConfigurableEnvironment) {
            PropertySourcesPropertyResolver resolver = new PropertySourcesPropertyResolver(
                ((ConfigurableEnvironment) environment).getPropertySources());
            resolver.setIgnoreUnresolvableNestedPlaceholders(true);
            return resolver;
        }
        return environment;
    }

    private Appender<ILoggingEvent> fileAppender(final String logFile) {
        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        String logPattern = this.patterns.getProperty("logging.pattern.file", FILE_LOG_PATTERN);
        encoder.setPattern(OptionHelper.substVars(logPattern, context));
        appender.setEncoder(encoder);
        start(encoder);
        appender.setFile(logFile);
        setRollingPolicy(appender, logFile);

        appender.setName(logFile.toUpperCase(Locale.ROOT));

        start(appender);

        return appender;
    }

    private void setRollingPolicy(final RollingFileAppender<ILoggingEvent> appender, String logFile) {
        SizeAndTimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new SizeAndTimeBasedRollingPolicy<>();
        rollingPolicy.setContext(context);
        rollingPolicy.setCleanHistoryOnStart(
            this.patterns.getProperty("logging.file.clean-history-on-start", Boolean.class, false));
        rollingPolicy.setFileNamePattern(
            logFile + this.patterns.getProperty("logging.pattern.rolling-file-suffix", ".%d{yyyy-MM-dd}.%i.gz"));
        setMaxFileSize(rollingPolicy, getDataSize("logging.file.max-size", MAX_FILE_SIZE));
        rollingPolicy
            .setMaxHistory(this.patterns.getProperty("logging.file.max-history", Integer.class, MAX_FILE_HISTORY));
        DataSize totalSizeCap = getDataSize("logging.file.total-size-cap",
            DataSize.ofBytes(CoreConstants.UNBOUNDED_TOTAL_SIZE_CAP));
        rollingPolicy.setTotalSizeCap(new FileSize(totalSizeCap.toBytes()));
        appender.setRollingPolicy(rollingPolicy);
        rollingPolicy.setParent(appender);
        start(rollingPolicy);
    }

    private void setMaxFileSize(final SizeAndTimeBasedRollingPolicy<ILoggingEvent> rollingPolicy, final DataSize maxFileSize) {
        try {
            rollingPolicy.setMaxFileSize(new FileSize(maxFileSize.toBytes()));
        } catch (NoSuchMethodError ex) {
            // Logback < 1.1.8 used String configuration
            Method method = ReflectionUtils.findMethod(SizeAndTimeBasedRollingPolicy.class, "setMaxFileSize",
                String.class);
            ReflectionUtils.invokeMethod(method, rollingPolicy, String.valueOf(maxFileSize.toBytes()));
        }
    }

    private DataSize getDataSize(final String property, final DataSize defaultSize) {
        String value = this.patterns.getProperty(property);
        if (value == null) {
            return defaultSize;
        }
        try {
            return DataSize.parse(value);
        } catch (IllegalArgumentException ex) {
            FileSize fileSize = FileSize.valueOf(value);
            return DataSize.ofBytes(fileSize.getSize());
        }
    }

    void start(final LifeCycle lifeCycle) {
        if (lifeCycle instanceof ContextAware) {
            ((ContextAware) lifeCycle).setContext(context);
        }
        lifeCycle.start();
    }

}
