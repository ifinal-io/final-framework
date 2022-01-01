/*
 * Copyright 2020-2022 the original author or authors.
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
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.ConfigurationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;

/**
 * A tool for {@code velocity} template language.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
public final class Velocities {


    private static final ToolManager toolManager;

    private static final ContextFactory contextFactory;

    static {

        setLoggerLevel("org.apache", Level.ERROR);

        Velocity.init();

        toolManager = new ToolManager();
        toolManager.configure(ConfigurationUtils.getDefaultTools());
        toolManager.getToolboxFactory().createToolbox(Scope.APPLICATION);

        contextFactory = new ToolContextFactory(toolManager);
    }


    private static void setLoggerLevel(String name, Level level) {
        Logger logger = LoggerFactory.getLogger(name);
        if (logger instanceof ch.qos.logback.classic.Logger) {
            ((ch.qos.logback.classic.Logger) logger).setLevel(level);
        }
    }

    public static String getValue(final String express, final Object params) {
        try {
            Context context = contextFactory.create(params);
            StringWriter writer = new StringWriter();
            Velocity.evaluate(context, writer, "velocity", express);
            return writer.toString();
        } catch (Exception e) {
            throw e;
        }
    }

}

