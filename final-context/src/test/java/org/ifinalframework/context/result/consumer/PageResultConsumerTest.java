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

package org.ifinalframework.context.result.consumer;

import com.github.pagehelper.Page;

import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PageResultConsumerTest.
 *
 * @author ilikly
 * @version 1.2.2
 * @since 1.2.2
 */
@ExtendWith(MockitoExtension.class)
class PageResultConsumerTest {
    @InjectMocks
    private PageResultConsumer pageResultConsumer;

    @Test
    void accept() {
        final Page data = new Page(1, 1);
        data.setTotal(2);
        data.add("haha");
        final Result result = R.success(data);
        pageResultConsumer.accept(result);
        assertEquals(2, result.getPagination().getTotal());
    }

    @Test
    void should_be_false_when_result_data_is_null() {
        assertFalse(pageResultConsumer.test(R.success()));
    }

    @Test
    void should_be_false_when_result_data_is_not_page() {
        assertFalse(pageResultConsumer.test(R.success(1)));
    }

    @Test
    void should_be_true_when_result_data_is_page() {
        final Page data = new Page();
        data.add("haha");
        assertTrue(pageResultConsumer.test(R.success(data)));
    }
}
