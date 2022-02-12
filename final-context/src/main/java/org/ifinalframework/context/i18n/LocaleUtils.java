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

package org.ifinalframework.context.i18n;

import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author likly
 * @version 1.3.0
 * @see LocaleContextHolder
 **/
public abstract class LocaleUtils {

    public static TimeZone getTimeZone() {
        return LocaleContextHolder.getTimeZone();
    }

    public static ZoneId getZoneId() {
        return getTimeZone().toZoneId();
    }

    public static LocalDateTime getZoneLocalDateTime(LocalDateTime localDateTime, ZoneId targetZoneId) {
        return getZoneLocalDateTime(localDateTime, getZoneId(), targetZoneId);
    }

    public static LocalDateTime getZoneLocalDateTime(LocalDateTime localDateTime, ZoneId sourceZoneId, ZoneId targetZoneId) {
        return localDateTime.atZone(sourceZoneId).withZoneSameInstant(targetZoneId).toLocalDateTime();
    }

}
