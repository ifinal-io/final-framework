/*
 * Copyright 2020-2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.velocity;

import ch.qos.logback.classic.Level;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.StringWriter;
import java.io.Writer;

/**
 * A tool for {@code velocity} template language.
 *
 * @author iimik
 * @version 1.0.0
 * @see org.apache.velocity.tools.ToolContext
 * @see Velocity
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
public final class Velocities {

    private static final ContextFactory contextFactory;

    static {
        setLoggerLevel("org.apache", Level.ERROR);
        Velocity.init();
        contextFactory = new ToolContextFactory();
    }


    private static void setLoggerLevel(String name, Level level) {
        Logger logger = LoggerFactory.getLogger(name);
        if (logger instanceof ch.qos.logback.classic.Logger) {
            ((ch.qos.logback.classic.Logger) logger).setLevel(level);
        }
    }

    /**
     * return the eval value from {@code express} with {@code params}.
     *
     * @param express eval express.
     * @param params  eval params.
     * @since 1.2.4
     */
    public static String eval(@NonNull String express, @Nullable Object params) {
        final Context context = contextFactory.create(params);
        return eval(express, context);
    }

    /**
     * return the eval value from {@code express} with {@link Context}.
     *
     * @param express eval express.
     * @param context eval params.
     * @see Velocity#evaluate(Context, Writer, String, String)
     * @since 1.2.4
     */
    public static String eval(@NonNull String express, @NonNull Context context) {
        final StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "velocity", express);
        return writer.toString();
    }

    @Deprecated
    public static String getValue(final String express, final Object params) {
        return eval(express, params);
    }

}

