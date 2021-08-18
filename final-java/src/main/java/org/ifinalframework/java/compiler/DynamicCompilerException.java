/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.java.compiler;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.*;

/**
 * DynamicCompilerException.
 */
@SuppressWarnings("serial")
public class DynamicCompilerException extends RuntimeException {

    private static final long serialVersionUID = 844455388423567597L;

    private final List<Diagnostic<? extends JavaFileObject>> diagnostics;

    public DynamicCompilerException(final String message,
        final List<Diagnostic<? extends JavaFileObject>> diagnostics) {

        super(message);
        this.diagnostics = diagnostics;
    }

    public DynamicCompilerException(final Throwable cause,
        final List<Diagnostic<? extends JavaFileObject>> diagnostics) {

        super(cause);
        this.diagnostics = diagnostics;
    }

    private List<Map<String, Object>> getErrorList() {
        List<Map<String, Object>> messages = new ArrayList<>();
        if (diagnostics != null) {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
                Map<String, Object> message = new HashMap<>();
                message.put("line", diagnostic.getLineNumber());
                message.put("message", diagnostic.getMessage(Locale.ENGLISH));
                messages.add(message);
            }

        }
        return messages;
    }

    private String getErrors() {
        StringBuilder errors = new StringBuilder();

        for (Map<String, Object> message : getErrorList()) {
            for (Map.Entry<String, Object> entry : message.entrySet()) {
                Object value = entry.getValue();
                if (value != null && !value.toString().isEmpty()) {
                    errors.append(entry.getKey());
                    errors.append(": ");
                    errors.append(value);
                }
                errors.append(" , ");
            }

            errors.append("\n");
        }

        return errors.toString();

    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + getErrors();
    }

}
