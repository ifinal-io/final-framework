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

package org.ifinalframework.data.mybatis.reflection.factory;

import org.ifinalframework.auto.service.annotation.AutoService;
import org.ifinalframework.core.IUser;
import org.ifinalframework.data.annotation.AbsUser;

import java.util.List;

/**
 * IUserObjectFactoryInterceptor.
 *
 * @author iimik
 * @version 1.4.2
 * @since 1.4.2
 */
@AutoService(ObjectFactoryInterceptor.class)
public class IUserObjectFactoryInterceptor implements ObjectFactoryInterceptor<IUser> {
    @Override
    public IUser create(Class<IUser> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        return new AbsUser();
    }
}


