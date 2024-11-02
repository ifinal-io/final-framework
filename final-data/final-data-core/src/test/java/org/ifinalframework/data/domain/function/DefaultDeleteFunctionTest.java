/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.domain.function;

import org.ifinalframework.core.IUser;
import org.ifinalframework.data.annotation.AbsEntity;
import org.ifinalframework.data.query.PageQuery;
import org.ifinalframework.data.repository.Repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DefaultDeleteFunctionTest
 *
 * @author iimik
 * @since 1.5.5
 **/
@ExtendWith(MockitoExtension.class)
class DefaultDeleteFunctionTest {

    @Mock
    private Repository<Long, AbsEntity> repository;



    @Test
    void delete() {

        final DefaultDeleteFunction<Long, AbsEntity,Object, IUser<Long>> function = new DefaultDeleteFunction<>(repository);

        Integer delete = function.delete(null, 1L, null);
        Mockito.verify(repository,Mockito.only()).delete(1L);

    }

    @Test
    void delete_by_ids() {

        final DefaultDeleteFunction<Long, AbsEntity,Object, IUser<Long>> function = new DefaultDeleteFunction<>(repository);

        function.delete(null, Arrays.asList(1L),null);
        Mockito.verify(repository,Mockito.only()).delete(Arrays.asList(1L));

    }

    @Test
    void delete_by_query(){
        final DefaultDeleteFunction<Long, AbsEntity,Object, IUser<Long>> function = new DefaultDeleteFunction<>(repository);

        final PageQuery query = new PageQuery();
        function.delete(null, query,null);
        Mockito.verify(repository,Mockito.only()).delete(query);
    }
}