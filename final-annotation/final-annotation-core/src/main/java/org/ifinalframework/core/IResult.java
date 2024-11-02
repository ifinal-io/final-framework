/*
 * Copyright 2020-2021 the original author or authors.
 *
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
 *
 */

package org.ifinalframework.core;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * The interface of response data holder which impl the {@linkplain IData data} holder.  when {@link #isSuccess()} return {@code true} the
 * response successful, otherwise use {@linkplain #getCode()} or {@linkplain #getMessage()} for more info. The code and message could be
 * throw by a {@linkplain Exception exception} which impl the interface of {@link IException}.
 *
 *
 * <p>Code Example</p>
 *
 * <pre class="code">
 *      IResult result = getResult();
 *      if(result.isSuccess()){
 *           // deal data when success
 *          Object data = result.getData();
 *      }else{
 *          // deal message when failure
 *          logger.error("code={},message={}",result.getCode(),result.getMessage());
 *      }
 * </pre>
 *
 * <p>Json Example</p>
 * <pre class="code">
 *      {
 *           "status":null,
 *           "description":null,
 *           "code":null,
 *           "message":null,
 *           "data":null,
 *           "pagination":null,
 *           "trace":null,
 *           "timestamp":1600249564385,
 *           "duration":null,
 *           "address":null,
 *           "locale":null,
 *           "timeZone":null,
 *           "operator":null,
 *           "view":null,
 *           "exception":null,
 *           "success":false
 *      }
 * </pre>
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IResult<T> extends IData<T> {

    /**
     * return the status of this response.
     *
     * @return the status of this response.
     * @see ResponseStatus#getCode()
     */
    @NonNull
    Integer getStatus();

    /**
     * return the description of status.
     *
     * @return the description of status.
     * @see ResponseStatus#getDesc()
     */
    @NonNull
    String getDescription();

    /**
     * return the code of this response if response is not success.
     *
     * @return the code of this response if response is not success.
     * @see #getMessage()
     * @see IException#getCode()
     */
    @Nullable
    String getCode();

    /**
     * return the message for code.
     *
     * @return the message for code.
     * @see #getCode()
     * @see IException#getMessage()
     */
    @Nullable
    String getMessage();

    /**
     * return the {@linkplain IPagination pagination} when query with {@linkplain Pageable page}.
     *
     * @return the {@linkplain IPagination pagination}.
     * @see Pageable
     */
    @Nullable
    IPagination getPagination();

    /**
     * @return total of the data.
     * @see #getPagination()
     * @since 1.4.0
     */
    @Nullable
    default Long getTotal() {
        return Optional.ofNullable(getPagination()).map(IPagination::getTotal).orElse(null);
    }

    /**
     * return {@literal true} if this result response success, otherwise {@literal false}.
     *
     * @return {@literal true} if this result response success, otherwise {@literal false}.
     */
    default boolean isSuccess() {
        return ResponseStatus.SUCCESS.getCode().equals(getStatus());
    }

    /**
     * return {@literal true} if has more data.
     *
     * @return {@literal true} if has more data.
     */
    default boolean hasMore() {
        final IPagination pagination = getPagination();
        return pagination != null && !Boolean.TRUE.equals(pagination.getLastPage());
    }

}
