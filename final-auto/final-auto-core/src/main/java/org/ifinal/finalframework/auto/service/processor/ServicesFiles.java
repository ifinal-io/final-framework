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
 * ServicesFiles.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ServicesFiles {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private static final String ROOT = "META-INF";

    private static final String PATH_SPE = "/";

    private static final String CHAR_EQUAL = "=";

    private ServicesFiles() {

    }

    static String getPath(final String path, final String serviceName) {

        return String.join(PATH_SPE, ROOT, path, serviceName);
    }

    /**
     * Reads the set of service classes from a service file.
     *
     * @param input not {@code null}. Closed after use.
     * @return a not {@code null Set} of service class names.
     * @throws IOException io exception
     */
    static Map<String, String> readServiceFile(final InputStream input) throws IOException {

        final Map<String, String> serviceClasses = new HashMap<>();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(input, UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                final int commentStart = line.indexOf('#');
                if (commentStart >= 0) {
                    line = line.substring(0, commentStart);
                }
                line = line.trim();
                if (!line.isEmpty()) {

                    if (line.contains(CHAR_EQUAL)) {
                        final String[] split = line.split(CHAR_EQUAL);
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
                writer.write(CHAR_EQUAL);
                writer.write(entry.getKey());
                writer.newLine();
            }
        }
        writer.flush();
    }

}

