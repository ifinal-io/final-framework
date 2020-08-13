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

package org.finalframework.logging.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.finalframework.logging.converter.ILoggingEvent2LoggingEventConverter;
import org.finalframework.logging.entity.LoggingEvent;

/**
 * @author likly
 * @version 1.0AppenderBase
 * @date 2019-01-16 21:17:21
 * @since 1.0
 */
public abstract class AbsAppender extends AppenderBase<ILoggingEvent> {

    private ILoggingEvent2LoggingEventConverter converter = new ILoggingEvent2LoggingEventConverter();

    @Override
    protected void append(ILoggingEvent eventObject) {
        LoggingEvent loggingEvent = converter.convert(eventObject);
        append(loggingEvent);
    }

    protected abstract void append(LoggingEvent event);
}
