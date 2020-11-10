package org.finalframework.auto.service.processor;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-25 19:40:16
 * @since 1.0
 */
public final class ServicesFiles {
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String ROOT = "META-INF";

    private ServicesFiles() {
    }

    static String getPath(String path, String serviceName) {
        return ROOT + "/" + path + "/" + serviceName;
    }

    /**
     * Reads the set of service classes from a service file.
     *
     * @param input not {@code null}. Closed after use.
     * @return a not {@code null Set} of service class names.
     * @throws IOException
     */
    static Map<String, String> readServiceFile(InputStream input) throws IOException {
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
     * @throws IOException
     */
    static void writeServiceFile(Map<String, String> services, OutputStream output)
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

