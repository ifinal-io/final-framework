package org.ifinal.finalframework.auto.service.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ServicesFiles {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private static final String ROOT = "META-INF";

    private ServicesFiles() {

    }

    static String getPath(final String path, final String serviceName) {

        return ROOT + "/" + path + "/" + serviceName;
    }

    /**
     * Reads the set of service classes from a service file.
     *
     * @param input not {@code null}. Closed after use.
     * @return a not {@code null Set} of service class names.
     * @throws IOException io exception
     */
    static Map<String, String> readServiceFile(final InputStream input) throws IOException {

        Map<String, String> serviceClasses = new HashMap<>();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(input, UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                int commentStart = line.indexOf('#');
                if (commentStart >= 0) {
                    line = line.substring(0, commentStart);
                }
                line = line.trim();
                if (!line.isEmpty()) {

                    if (line.contains("=")) {
                        final String[] split = line.split("=");
                        serviceClasses.put(split[1], split[0]);
                    } else {
                        serviceClasses.put(line, null);
                    }
                }
            }
            return serviceClasses;
        }
    }

    /**
     * Writes the set of service class names to a service file.
     *
     * @param output   not {@code null}. Not closed after use.
     * @param services a not {@code null Collection} of service class names.
     * @throws IOException io exception
     */
    static void writeServiceFile(final Map<String, String> services, final OutputStream output)
            throws IOException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, UTF_8));
        for (Map.Entry<String, String> entry : services.entrySet()) {
            if (entry.getValue() == null || entry.getValue().isEmpty()) {
                // service
                writer.write(entry.getKey());
                writer.newLine();
            } else {
                // name=service
                writer.write(entry.getValue());
                writer.write("=");
                writer.write(entry.getKey());
                writer.newLine();
            }
        }
        writer.flush();
    }

}

