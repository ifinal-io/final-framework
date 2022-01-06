package org.ifinalframework.reflect;

import org.ifinalframework.json.Json;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author likly
 * @version 1.2.4
 **/
public class DefaultMethodInvoker implements MethodInvoker {
    @Override
    @Nullable
    public Object invoke(@NonNull Method method,@NonNull Object target,@Nullable Object... args) {

        if(Objects.isNull(args) || args.length == 0){
            return ReflectionUtils.invokeMethod(method,target);
        }

        Object[] arguments = new Object[args.length];
        Type[] types = method.getGenericParameterTypes();
        for (int i = 0; i < arguments.length; i++) {
            Object arg = args[i];
            if(arg instanceof String){
                arguments[i] = Json.toObject((String) arg,types[i]);
            }else {
                arguments[i] = arg;
            }
        }
        return ReflectionUtils.invokeMethod(method,target,arguments);

    }
}
