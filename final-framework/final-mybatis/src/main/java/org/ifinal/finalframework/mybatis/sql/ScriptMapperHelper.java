package org.ifinal.finalframework.mybatis.sql;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class ScriptMapperHelper {

    private static final String TABLE = "${table}";

    private ScriptMapperHelper() {
    }

    public static String table() {
        return TABLE;
    }

    public static String cdata(String data) {
        return String.format("<![CDATA[%s]]>", data);
    }

    public static String bind(String name, String value) {
        return String.format("<bind name=\"%s\" value=\"%s\"/>", name, value);
    }


    public static String formatBindValue(String prefix, String path) {
        if (path.contains(".")) {
            final String[] paths = path.split("\\.");
            List<String> isNulls = new ArrayList<>(paths.length);

            final StringBuilder builder = new StringBuilder();
            builder.append(prefix);
            for (String item : paths) {
                builder.append(".").append(item);
                isNulls.add(builder.toString());
            }


            final String isNull = isNulls.stream().map(item -> String.format("%s == null", item))
                    .collect(Collectors.joining(" || "));
            return String.format("%s ? null : %s.%s", isNull, prefix, path);
        }
        return String.format("%s.%s", prefix, path);
    }

    public static String formatTest(String prefix, String path, boolean selective) {

        if (path.contains(".")) {
            final String[] paths = path.split("\\.");
            List<String> notNulls = new ArrayList<>(paths.length);

            final StringBuilder builder = new StringBuilder();
            builder.append(prefix);

            for (int i = 0; i < paths.length; i++) {
                String item = paths[i];
                if (!selective && i == paths.length - 1) {
                    continue;
                }
                builder.append(".").append(item);
                notNulls.add(builder.toString());
            }
            return notNulls.stream().map(item -> String.format("%s != null", item))
                    .collect(Collectors.joining(" and "));
        } else {
            if (selective) {
                return String.format("%s.%s != null", prefix, path);
            } else {
                return null;
            }
        }

    }

}

