/*
 * Copyright 2020-2021 the original author or authors.
 *
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

package org.ifinalframework.monitor.action;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import org.ifinalframework.util.Asserts;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Primary
@Component
public class ActionRecorder implements Recorder {

    private final List<ActionListener> listeners = new ArrayList<>();

    public ActionRecorder(final ObjectProvider<List<ActionListener>> handlerProvider) {

        final List<ActionListener> handlers = handlerProvider.getIfAvailable();
        if (Asserts.nonEmpty(handlers)) {
            this.listeners.addAll(handlers);
        }
    }

    @Override
    public void record(final Action action) {

        listeners.forEach(item -> item.onAction(action));
    }

}
