

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

