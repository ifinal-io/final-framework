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

package org.finalframework.retrofit;


import org.springframework.beans.factory.FactoryBean;
import retrofit2.Retrofit;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-25 15:45:25
 * @since 1.0
 */
public class RetrofitFactoryBean<T> implements FactoryBean<T> {

    private Retrofit retrofit;
    private Class<T> service;

    public RetrofitFactoryBean() {
    }

    public RetrofitFactoryBean(Class<T> service) {
        this.service = service;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void setService(Class<T> service) {
        this.service = service;
    }

    @Override
    public T getObject() throws Exception {
        return retrofit.create(service);
    }

    @Override
    public Class<?> getObjectType() {
        return service;
    }
}

