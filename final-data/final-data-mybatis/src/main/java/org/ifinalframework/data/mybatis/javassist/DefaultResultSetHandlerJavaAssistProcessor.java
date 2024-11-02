/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.mybatis.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.ClassFile;

import org.ifinalframework.auto.service.annotation.AutoService;
import org.ifinalframework.javassist.JavaAssistProcessor;

import org.apache.ibatis.executor.loader.ResultLoaderMap;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetWrapper;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.MetaObject;

import lombok.extern.slf4j.Slf4j;

/**
 * DefaultResultSetHandlerJavaAssistProcessor.
 *
 * @author iimik
 * @version 1.5.1
 * @see DefaultResultSetHandler
 * @since 1.5.1
 */
@Slf4j
@AutoService(JavaAssistProcessor.class)
public class DefaultResultSetHandlerJavaAssistProcessor implements JavaAssistProcessor {


    @Override
    public void process(ClassPool classPool) throws Throwable {
        logger.debug("start modify class: DefaultResultSetHandler");
        final CtClass ctClass = classPool.get("org.apache.ibatis.executor.resultset.DefaultResultSetHandler");
        if (ctClass.isFrozen()) {
            return;
        }
        modifyMethodApplyPropertyMappings(ctClass);
        if (ClassFile.MAJOR_VERSION > ClassFile.JAVA_8) {
            final Class<?> newClass = ctClass.toClass(ResultSetHandler.class);
        } else {
            final Class<?> newClass = ctClass.toClass();
        }
        logger.debug("finish modify class: DefaultResultSetHandler");
    }

    /**
     * @see DefaultResultSetHandler#applyPropertyMappings(ResultSetWrapper, ResultMap, MetaObject, ResultLoaderMap, String)
     */
    private void modifyMethodApplyPropertyMappings(CtClass ctClass) throws Throwable {
        final CtMethod method = ctClass.getDeclaredMethod("applyPropertyMappings");
        method.setBody(
                """
                    {       
                        final java.util.List mappedColumnNames = $1.getMappedColumnNames($2, $5);
                        boolean foundValues = false;
                        final java.util.List propertyMappings = $2.getPropertyResultMappings();
                        for (int i = 0; i < propertyMappings.size(); i++) {
                            org.apache.ibatis.mapping.ResultMapping propertyMapping 
                                = (org.apache.ibatis.mapping.ResultMapping)propertyMappings.get(i);
                            String column = prependPrefix(propertyMapping.getColumn(), $5);
                            if (propertyMapping.getNestedResultMapId() != null) {
                                // the user added a column attribute to a nested result map, ignore it
                                column = null;
                            }
                            if (propertyMapping.isCompositeResult()
                                    || column != null && mappedColumnNames.contains(column.toUpperCase(java.util.Locale.ENGLISH))
                                    || propertyMapping.getResultSet() != null) {
                                Object value = null;
                                if (propertyMapping.isCompositeResult()) {
                                    final java.util.List resultMappings = propertyMapping.getComposites();
                                    final org.apache.ibatis.mapping.ResultMap resultMap2 = new org.apache.ibatis.mapping.ResultMap$Builder(
                                            this.configuration, propertyMapping.getJavaType().getSimpleName(),
                                            propertyMapping.getJavaType(), resultMappings, java.lang.Boolean.FALSE
                                    ).build();
                                    value = getRowValue($1, resultMap2, propertyMapping.getColumnPrefix());
                                } else {
                                    value = getPropertyMappingValue($1.getResultSet(), $3, propertyMapping, $4, $5);
                                }
                                // issue #541 make property optional
                                final String property = propertyMapping.getProperty();
                                if (property == null) {
                                    continue;
                                }
                                if (value == DEFERRED) {
                                    foundValues = true;
                                    continue;
                                }
                                if (value != null) {
                                    foundValues = true;
                                }
                                if (value != null
                                        || $0.configuration.isCallSettersOnNulls() && !$3.getSetterType(property).isPrimitive()) {
                                    // gcode issue #377, call setter on nulls (value is not 'found')
                                    $3.setValue(property, value);
                                }
                            }
                        }
                        return foundValues;
                    }
                """
        );


    }
}
