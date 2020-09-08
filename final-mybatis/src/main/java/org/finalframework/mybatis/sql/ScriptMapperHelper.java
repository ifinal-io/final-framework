/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.mybatis.sql;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-05 17:04:05
 * @since 1.0
 */
public class ScriptMapperHelper {

    public static String table() {
        return "${table}";
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
                if (!selective && i != paths.length - 1) {
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
