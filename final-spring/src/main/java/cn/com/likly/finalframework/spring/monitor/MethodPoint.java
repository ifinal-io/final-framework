package cn.com.likly.finalframework.spring.monitor;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-09 11:33
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class MethodPoint {

    private final Object target;
    private final Method method;
    private final Map<String, Object> args;
    private final String name;
    private final String tag;

    public Object getArg(String name) {
        return args == null ? null : args.get(name);
    }
}
