package cn.com.likly.finalframework.mybatis.converter;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:12
 * @since 1.0
 */
public class DefaultNameConverter implements NameConverter {
    @Override
    public String convert(String name) {
        return name;
    }
}
