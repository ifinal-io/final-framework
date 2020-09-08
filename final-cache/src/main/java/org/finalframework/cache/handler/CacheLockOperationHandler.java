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

package org.finalframework.cache.handler;

import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheLockException;
import org.finalframework.cache.annotation.CacheLock;
import org.finalframework.cache.operation.CacheLockOperation;
import org.finalframework.core.Asserts;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @see CacheLock
 * @since 1.0
 */
@SpringComponent
public class CacheLockOperationHandler extends AbsCacheOperationHandlerSupport implements OperationHandler<Cache, CacheLockOperation> {

    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final String LOCK = "lock";

    @Override
    public Void before(Cache cache, OperationContext<CacheLockOperation> context) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, null, null);
        final CacheLockOperation operation = context.operation();
        final Object key = generateKey(operation.key(), operation.delimiter(), context.metadata(), evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache action generate null key, action=" + operation);
        }
        final Object value = Asserts.isEmpty(operation.value()) ? key : generateValue(operation.value(), context.metadata(), evaluationContext);

        Long ttl;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        Object expired = generateExpire(operation.expire(), context.metadata(), evaluationContext);

        if (expired != null) {
            if (expired instanceof Date) {
                ttl = ((Date) expired).getTime() - System.currentTimeMillis();
            } else {
                throw new IllegalArgumentException("unSupport expire type: " + expired.getClass());
            }
        } else {
            ttl = operation.ttl();
            timeUnit = operation.timeUnit();
        }

        final Long sleep = operation.sleep();
        context.addAttribute(KEY, key);
        context.addAttribute(VALUE, value);
        int retry = 0;
        do {
            logger.info("==> try to lock: key={},value={},ttl={},timeUnit={},retry={}", key, value, ttl, timeUnit, retry);
            final boolean lock = cache.lock(key, value, ttl, timeUnit);
            logger.info("<== lock result: {}", lock);
            if (lock) {
                context.addAttribute(LOCK, true);
                return null;
            }

            if (sleep != null && sleep > 0L) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    logger.error("retry sleep error,key={},value={}", key, value, e);
                }
            }

            retry++;
        } while (retry <= operation.retry());

        context.addAttribute(LOCK, false);
        throw new CacheLockException(String.format("failure to lock key=%s,value=%s", key, value));
    }

    @Override
    public void after(Cache cache, OperationContext<CacheLockOperation> context, Object result, Throwable throwable) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());

        final Object key = context.getAttribute(KEY);
        final Object value = context.getAttribute(VALUE);
        final Boolean lock = context.getAttribute(LOCK);

        if (Boolean.TRUE.equals(lock)) {
            logger.info("==> try to unlock: key={},value={}", key, value);
            final boolean unlock = cache.unlock(key, value);
            logger.info("<== result: {}", unlock);
        }
    }

}
