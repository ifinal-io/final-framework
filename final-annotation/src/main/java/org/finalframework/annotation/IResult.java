package org.finalframework.annotation;

import org.finalframework.annotation.result.ResponseStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * The interface of response data holder which impl the {@linkplain IData data} holder.  when {@link #isSuccess()} return {@code true}
 * the response successful, otherwise use {@linkplain #getCode()} or {@linkplain #getMessage()} for more info. The code and message
 * could be throw by a {@linkplain Exception exception} which impl the interface of {@link IException}.
 *
 * <h4>Code Example</h4>
 *
 * <pre>
 *     <code>
 *         IResult result = getResult();
 *         if(result.isSuccess()){
 *              // deal data when success
 *             Object data = result.getData();
 *         }else{
 *             // deal message when failure
 *             logger.error("code={},message={}",result.getCode(),result.getMessage());
 *         }
 *     </code>
 * </pre>
 *
 * <h4>Json Example</h4>
 * <pre>
 *     <code>
 *         {
 *              "status":null,
 *              "description":null,
 *              "code":null,
 *              "message":null,
 *              "data":null,
 *              "pagination":null,
 *              "trace":null,
 *              "timestamp":1600249564385,
 *              "duration":null,
 *              "address":null,
 *              "locale":null,
 *              "timeZone":null,
 *              "operator":null,
 *              "view":null,
 *              "exception":null,
 *              "success":false
 *          }
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-08-25 13:29:07
 * @see org.finalframework.annotation.result.Result
 * @since 1.0
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
     * return {@literal true} if this result response success, otherwise {@literal false}.
     *
     * @return {@literal true} if this result response success, otherwise {@literal false}.
     * @see ResponseStatus#SUCCESS
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
        return getPagination() != null && !Boolean.TRUE.equals(getPagination().getLastPage());
    }

}
