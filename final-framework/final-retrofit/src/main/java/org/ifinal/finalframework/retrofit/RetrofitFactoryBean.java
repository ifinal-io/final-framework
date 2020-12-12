package org.ifinal.finalframework.retrofit;


import org.springframework.beans.factory.FactoryBean;
import retrofit2.Retrofit;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class RetrofitFactoryBean<T> implements FactoryBean<T> {

    private Retrofit retrofit;
    private Class<T> service;

    public RetrofitFactoryBean() {
    }

    public RetrofitFactoryBean(final Class<T> service) {

        this.service = service;
    }

    public void setRetrofit(final Retrofit retrofit) {

        this.retrofit = retrofit;
    }

    public void setService(final Class<T> service) {

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

