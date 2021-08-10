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

package org.ifinalframework.context.result;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.ifinalframework.context.exception.ServiceException;
import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Supplier;

/**
 * DefaultResultSupplier.
 *
 * @author likly
 * @version 1.2.2
 * @since 1.2.2
 */
@Slf4j
public class SimpleResultSupplier implements ResultSupplier {

    @Resource
    @Setter
    private List<ResultConsumer<?>> resultConsumers;

    @Override
    public <T> Result<T> get(Supplier<T> supplier) {
        final Result<T> result = doGet(supplier);
        doConsumers(result);
        return result;
    }

    private <T> Result<T> doGet(Supplier<T> supplier) {
        try {
            return R.success(supplier.get());
        } catch (ServiceException e) {
            logger.warn("result supplier error: ", e);
            return R.failure(e.getStatus(), e.getDescription(), e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("result supplier exception: ", e);
            return R.failure(500, e.getMessage());
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void doConsumers(Result result) {
        if (CollectionUtils.isEmpty(resultConsumers)) {
            return;
        }

        for (ResultConsumer<?> consumer : resultConsumers) {
            if (consumer.test(result)) {
                consumer.accept(result);
            }
        }

    }
}
