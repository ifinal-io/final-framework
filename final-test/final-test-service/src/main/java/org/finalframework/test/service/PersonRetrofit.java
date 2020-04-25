package org.finalframework.test.service;

import org.finalframework.data.result.Result;
import org.finalframework.retrofit.annotation.Retrofit;
import org.finalframework.test.entity.Person;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-25 17:40:20
 * @since 1.0
 */
@Retrofit
public interface PersonRetrofit {

    @GET("/query")
    Call<Result<Person>> findById(@Query("id") Long id);

}
