package org.ifinal.finalframework.mybatis.configuration;

import java.lang.reflect.Field;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.io.support.ServicesLoader;
import org.ifinal.finalframework.mybatis.handler.EnumTypeHandler;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;
import org.ifinal.finalframework.mybatis.resumtmap.ResultMapFactory;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * @author likly
 * @version 1.0.0
 * @see org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
 * @since 1.0.0
 */
@Slf4j
@Component
@SuppressWarnings("unused")
public class FinalMybatisConfigurationCustomizer implements ConfigurationCustomizer {

    private static final Field composites = Objects
        .requireNonNull(ReflectionUtils.findField(ResultMapping.class, "composites"));

    static {
        ReflectionUtils.makeAccessible(composites);
    }

    @Override
    public void customize(final Configuration configuration) {

        logger.info("setDefaultEnumTypeHandler:{}", EnumTypeHandler.class.getCanonicalName());
        configuration.addMapper(AbsMapper.class);
        configuration.getTypeHandlerRegistry().setDefaultEnumTypeHandler(EnumTypeHandler.class);

        ServicesLoader.load(IEntity.class)
            .stream()
            .map((String className) -> {

                try {
                    return Class.forName(className);
                } catch (ClassNotFoundException e) {
                    //ignore
                    throw new IllegalArgumentException(e);
                }
            })
            .forEach(clazz -> {
                ResultMap resultMap = ResultMapFactory.from(configuration, clazz);

                if (logger.isInfoEnabled()) {
                    logger.info("==> addResultMap:[{}],class={}", resultMap.getId(), clazz);
                }

                configuration.addResultMap(resultMap);

                resultMap.getResultMappings()
                    .stream()
                    .filter(ResultMapping::isCompositeResult)
                    .forEach(resultMapping -> {

                        ResultMap map = new ResultMap.Builder(configuration, resultMapping.getNestedResultMapId(),
                            resultMap.getType(),
                            resultMapping.getComposites()).build();
                        configuration.addResultMap(map);

                        // mybatis not support composites result mapping
                        ReflectionUtils.setField(composites, resultMapping, null);

                    });

            });

    }

}

