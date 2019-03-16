package org.finalframework.logging.converter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.finalframework.core.converter.Converter;
import org.finalframework.logging.entity.LoggingEvent;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-16 21:01:01
 * @since 1.0
 */
public class ILoggingEvent2LoggingEventConverter implements Converter<ILoggingEvent, LoggingEvent> {
    private static String host;
    private static Integer pid;

    static {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            host = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            //ignore
            host = null;
        }
        pid = getProcessID();
    }

    public static int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println(runtimeMXBean.getName());
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0]);
    }

    @Override
    public LoggingEvent convert(ILoggingEvent event) {
        final LoggingEvent result = new LoggingEvent();
        result.setHost(host);
        result.setPid(pid);
        result.setTimestamp(new Date(event.getTimeStamp()));
        result.setLevel(getLevel(event));
        result.setThread(event.getThreadName());
        result.setLogger(event.getLoggerName());
        result.setMethod(getMethod(event));
        result.setLineNumber(getLineNumber(event));
        result.setMessage(event.getFormattedMessage());
        result.setTrace(event.getMDCPropertyMap().get("trace"));
        if (event.getThrowableProxy() != null) {
            result.setException(event.getThrowableProxy().toString());
        }
        return result;
    }

    private String getLevel(ILoggingEvent event) {
        return event.getLevel() == null ? "INFO" : event.getLevel().levelStr;
    }

    private String getMethod(ILoggingEvent event) {
        StackTraceElement[] cda = event.getCallerData();
        if (cda != null && cda.length > 0) {
            return cda[0].getMethodName();
        } else {
            return null;
        }
    }

    private Integer getLineNumber(ILoggingEvent event) {
        StackTraceElement[] cda = event.getCallerData();
        if (cda != null && cda.length > 0) {
            return cda[0].getLineNumber();
        } else {
            return null;
        }
    }

}
