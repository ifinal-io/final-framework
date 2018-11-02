package cn.com.likly.finalframework.data.mybatis.mapping;

import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.mybatis.factory.ResultMapFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 21:52
 * @since 1.0
 */
@Component
@Slf4j
public class ResultMapCache {

    private final Map<Entity, ResultMap> cache = new LinkedHashMap<>();

    @Resource
    private ResultMapFactory resultMapFactory;

    public ResultMap get(Entity holder, Configuration configuration) {

        if (cache.containsKey(holder)) {
            return cache.get(holder);
        }

        ResultMap resultMap = resultMapFactory.create(holder, configuration);
        cache.put(holder, resultMap);
        return resultMap;

    }

}
