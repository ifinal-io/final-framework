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

package org.ifinalframework.data.domain.action;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.ifinalframework.core.IAudit;
import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IEnum;
import org.ifinalframework.core.IQuery;
import org.ifinalframework.core.IUser;
import org.ifinalframework.core.IView;
import org.ifinalframework.data.annotation.DomainResource;
import org.ifinalframework.data.annotation.YN;
import org.ifinalframework.data.domain.AbsUpdateDeleteDomainActionDispatcher;
import org.ifinalframework.data.domain.DefaultUpdateDomainActionDispatcherFactory;
import org.ifinalframework.data.domain.DeleteDomainActionDispatcher;
import org.ifinalframework.data.domain.DomainNameHelper;
import org.ifinalframework.data.domain.DomainSpiMatcher;
import org.ifinalframework.data.domain.InsertDomainActionDispatcher;
import org.ifinalframework.data.domain.SelectDomainDispatcher;
import org.ifinalframework.data.domain.SimpleNameDomainSpiMatcher;
import org.ifinalframework.data.domain.UpdateDomainActionDispatcher;
import org.ifinalframework.data.domain.UpdateDomainActionDispatcherFactory;
import org.ifinalframework.data.domain.function.DefaultDeleteFunction;
import org.ifinalframework.data.domain.function.DefaultSelectCountFunction;
import org.ifinalframework.data.domain.function.DefaultSelectFunction;
import org.ifinalframework.data.domain.function.DefaultSelectOneFunction;
import org.ifinalframework.data.domain.function.DefaultUpdateAuditStatusFunction;
import org.ifinalframework.data.domain.function.DefaultUpdateFunction;
import org.ifinalframework.data.domain.function.DefaultUpdateLockedFunction;
import org.ifinalframework.data.domain.function.DefaultUpdateSortFunction;
import org.ifinalframework.data.domain.function.DefaultUpdateStatusFunction;
import org.ifinalframework.data.domain.function.DefaultUpdateYnFunction;
import org.ifinalframework.data.domain.model.AuditValue;
import org.ifinalframework.data.domain.model.SortValue;
import org.ifinalframework.data.domain.spi.LoggerAfterConsumer;
import org.ifinalframework.data.repository.Repository;
import org.ifinalframework.data.spi.AfterConsumer;
import org.ifinalframework.data.spi.AfterReturningConsumer;
import org.ifinalframework.data.spi.AfterThrowingConsumer;
import org.ifinalframework.data.spi.BiAfterReturningConsumer;
import org.ifinalframework.data.spi.BiAfterThrowingConsumer;
import org.ifinalframework.data.spi.BiConsumer;
import org.ifinalframework.data.spi.BiValidator;
import org.ifinalframework.data.spi.Consumer;
import org.ifinalframework.data.spi.DefaultUpdateAuditStatusPreValidator;
import org.ifinalframework.data.spi.DeleteFunction;
import org.ifinalframework.data.spi.Filter;
import org.ifinalframework.data.spi.Function;
import org.ifinalframework.data.spi.PreInsertFunction;
import org.ifinalframework.data.spi.QueryConsumer;
import org.ifinalframework.data.spi.SelectFunction;
import org.ifinalframework.data.spi.SpiAction;
import org.ifinalframework.data.spi.UpdateConsumer;
import org.ifinalframework.data.spi.UpdateFunction;
import org.ifinalframework.data.spi.UpdateProperty;
import org.ifinalframework.data.spi.exception.UpdatePropertyNotMatchedException;
import org.ifinalframework.util.Proxies;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DefaultDomainActionsFactory
 *
 * @author iimik
 * @since 1.5.2
 **/
@Slf4j
@RequiredArgsConstructor
public class DefaultDomainActionsFactory<K extends Serializable, T extends IEntity<K>, U extends IUser<?>>
        implements DomainActionsFactory<K, T> {

    private final Class<U> userClass;
    private final ApplicationContext applicationContext;

    private final DomainSpiMatcher domainSpiMatcher = new SimpleNameDomainSpiMatcher();
    private final UpdateDomainActionDispatcherFactory updateDomainActionDispatcherFactory = new DefaultUpdateDomainActionDispatcherFactory();

    private final LoggerAfterConsumer loggerAfterConsumer;

    @Override
    public DomainActions create(Repository<K, T> repository) {

        final DomainActions.DomainActionsBuilder builder = DomainActions.builder();

        final Map<String, DomainAction> domainActionMap = new LinkedHashMap<>();

        builder.repository(repository);

        ResolvableType repositoryResolvableType = ResolvableType.forClass(AopUtils.getTargetClass(repository)).as(Repository.class);
        Class<?> entityClass = Objects.requireNonNull(repositoryResolvableType.resolveGeneric(1));
        builder.entityClass(entityClass);
        ClassLoader classLoader = entityClass.getClassLoader();

        final DomainResource domainResource = AnnotationUtils.findAnnotation(entityClass, DomainResource.class);
        // entity
        final Map<Class<?>, Class<?>> domainEntityClassMap = new LinkedHashMap<>();
        builder.domainEntityClasses(domainEntityClassMap);

        // query
        final Map<Class<?>, Class<?>> domainQueryMap = new LinkedHashMap<>();
        builder.domainQueryClasses(domainQueryMap);
        final String queryPackage = DomainNameHelper.domainQueryPackage(entityClass);
        final String defaultQueryName = DomainNameHelper.domainQueryName(entityClass);
        final String defaultQueryClassName = String.join(".", queryPackage, defaultQueryName);
        final Class<?> defaultqueryClass = ClassUtils.resolveClassName(defaultQueryClassName, classLoader);
        domainQueryMap.put(IView.class, defaultqueryClass);

        final InsertDomainActionDispatcher<K, T, U> insertAction = buildCreateAction(repository, entityClass, domainResource);
        domainEntityClassMap.put(IView.Create.class, insertAction.getDomainEntityClass());
        domainQueryMap.put(IView.Create.class, insertAction.getDomainQueryClass());
        domainActionMap.put(SpiAction.Type.CREATE.name(), insertAction);

        // delete
        final DeleteDomainActionDispatcher<K, T, IQuery, U> deleteDomainActionByQuery
                = buildDeleteActionByQuery(repository, classLoader, queryPackage, entityClass, defaultqueryClass);
        domainEntityClassMap.put(IView.Delete.class, deleteDomainActionByQuery.getDomainEntityClass());
        domainQueryMap.put(IView.Delete.class, deleteDomainActionByQuery.getDomainQueryClass());
        domainActionMap.put(SpiAction.Type.DELETE_BY_QUERY.name(), deleteDomainActionByQuery);

        Class<?> idClass = Objects.requireNonNull(repositoryResolvableType.resolveGeneric());

        final DeleteDomainActionDispatcher<K, T, K, U> deleteByIdDomainAction = buildDeleteActionById(repository, entityClass, idClass);
        domainActionMap.put(SpiAction.Type.DELETE_BY_ID.name(), deleteByIdDomainAction);

        // export
        final SelectDomainDispatcher<K, T, IQuery, U, List<T>> exportActionByQuery
                = buildExportActionByQuery(repository, classLoader, queryPackage, entityClass, defaultqueryClass);
        domainEntityClassMap.put(IView.Export.class, exportActionByQuery.getDomainEntityClass());
        domainQueryMap.put(IView.Export.class, exportActionByQuery.getDomainQueryClass());
        domainActionMap.put(SpiAction.Type.EXPORT_BY_QUERY.name(), exportActionByQuery);


        // list
        final SelectDomainDispatcher<K, T, IQuery, U, List<T>> listQueryDomainAction
                = buildListActionByQuery(repository, classLoader, queryPackage, entityClass, defaultqueryClass);
        domainEntityClassMap.put(IView.List.class, listQueryDomainAction.getDomainEntityClass());
        domainQueryMap.put(IView.List.class, listQueryDomainAction.getDomainQueryClass());
        domainActionMap.put(SpiAction.Type.LIST_BY_QUERY.name(), listQueryDomainAction);

        // detail
        final SelectDomainDispatcher<K, T, IQuery, U, T> detailSelectActionByQuery
                = buildDetailSelectActionByQuery(repository, classLoader, queryPackage, entityClass, defaultqueryClass);
        domainEntityClassMap.put(IView.Detail.class, detailSelectActionByQuery.getDomainEntityClass());
        domainQueryMap.put(IView.Detail.class, detailSelectActionByQuery.getDomainQueryClass());
        domainActionMap.put(SpiAction.Type.DETAIL_BY_QUERY.name(), detailSelectActionByQuery);

        final SelectDomainDispatcher<K, T, K, U, T> detailSelectActionById = buildDetailSelectActionById(repository, idClass, entityClass);
        domainActionMap.put(SpiAction.Type.DETAIL_BY_ID.name(), detailSelectActionById);

        // count
        final SelectDomainDispatcher<K, T, IQuery, U, Long> countSelectActionByQuery
                = buildCountSelectActionByQuery(repository, classLoader, queryPackage, entityClass, defaultqueryClass);
        domainEntityClassMap.put(IView.Count.class, countSelectActionByQuery.getDomainEntityClass());
        domainQueryMap.put(IView.Count.class, countSelectActionByQuery.getDomainQueryClass());
        domainActionMap.put(SpiAction.Type.COUNT_BY_QUERY.name(), countSelectActionByQuery);

        // new update
        final MultiValueMap<String, UpdateProperty<T>> updatePropertiesById = new LinkedMultiValueMap<>();
        final MultiValueMap<String, UpdateProperty<T>> updatePropertiesByQuery = new LinkedMultiValueMap<>();

        applicationContext.getBeanProvider(ResolvableType.forClassWithGenerics(UpdateProperty.class, entityClass))
                .orderedStream()
                .forEach(it -> {

                    UpdateProperty<T> updateProperty = (UpdateProperty<T>) it;
                    final String property = updateProperty.getProperty();

                    final Class<?> targetClass = AopUtils.getTargetClass(it);
                    final ResolvableType resolvableType = ResolvableType.forClass(targetClass);
                    if (it instanceof UpdateConsumer<?, ?, ?> updateConsumer) {

                        final Class<?> targetEntityClass = resolvableType.as(UpdateConsumer.class).resolveGeneric();
                        final Class<?> targetValueClass = resolvableType.as(UpdateConsumer.class).resolveGeneric(1);

                        if (Objects.isNull(property) && entityClass != targetValueClass) {
                            throw new UpdatePropertyNotMatchedException(String.format("%s for null property must be target value class: %s", targetClass.getSimpleName(), entityClass.getSimpleName()));
                        }

                        updatePropertiesById.add(property, updateProperty);
                        updatePropertiesByQuery.add(property, updateProperty);

                    } else if (it instanceof UpdateFunction<?, ?, ?, ?, ?> updateFunction) {
                        final ResolvableType updateFunctionResolvableType = resolvableType.as(UpdateFunction.class);
                        final Class<?> p1Class = updateFunctionResolvableType.resolveGeneric(1);

                        if (Long.class.equals(p1Class)) {
                            updatePropertiesById.add(property, updateProperty);
                        } else if (IQuery.class.isAssignableFrom(p1Class)) {
                            updatePropertiesByQuery.add(property, updateProperty);
                        }

                    }


                    logger.info("UpdateProperty {} -> {}", entityClass.getSimpleName(), targetClass);
                });


        // update by id
        for (Map.Entry<String, List<UpdateProperty<T>>> entry : updatePropertiesById.entrySet()) {
            final String property = entry.getKey();
            final List<UpdateProperty<T>> updateProperties = entry.getValue();
            final UpdateAction updateAction = updateDomainActionDispatcherFactory.create(property, repository, updateProperties);
            final String key = String.join("#", "UPDATE_BY_ID", property);
            domainActionMap.put(key, updateAction);
        }
        // update by query
        for (Map.Entry<String, List<UpdateProperty<T>>> entry : updatePropertiesByQuery.entrySet()) {
            final String property = entry.getKey();
            final List<UpdateProperty<T>> updateProperties = entry.getValue();
            final UpdateAction updateAction = updateDomainActionDispatcherFactory.create(property, repository, updateProperties);
            final String key = String.join("#", "UPDATE_BY_QUERY", property);
            domainActionMap.put(key, updateAction);
        }

        if (!domainActionMap.containsKey("UPDATE_BY_ID")) {
            domainActionMap.put("UPDATE_BY_ID", updateDomainActionDispatcherFactory.create(null, repository, Collections.emptyList()));
        }
        if (!domainActionMap.containsKey("UPDATE_BY_ID#property")) {
            domainActionMap.put("UPDATE_BY_ID#property", updateDomainActionDispatcherFactory.create("property", repository, Collections.emptyList()));
        }


        //        // update
        //        final UpdateDomainActionDispatcher<K, T, K, Boolean, T, U> updateByIdDomainAction
        //                = buildUpdateActionById(repository, entityClass, idClass);
        //        domainActionMap.put(SpiAction.Type.UPDATE_BY_ID, updateByIdDomainAction);
        //
        //        // update yn
        //
        //        final UpdateDomainActionDispatcher<K, T, K, YN, YN, U> updateYnActionById
        //                = buildUpdateYnActionById(repository, entityClass, idClass);
        //        domainActionMap.put(SpiAction.Type.UPDATE_YN_BY_ID, updateYnActionById);
        //
        //
        //        // update status
        //        if (IStatus.class.isAssignableFrom(entityClass)) {
        //            final Class<?> statusClass = ResolvableType.forClass(entityClass).as(IStatus.class).resolveGeneric();
        //
        //            final UpdateDomainActionDispatcher<K, T, K, IEnum<?>, IEnum<?>, U> updateStatusActionById
        //                    = buildUpdateStatusActionById(repository, entityClass, idClass, statusClass);
        //            domainActionMap.put(SpiAction.Type.UPDATE_STATUS_BY_ID, updateStatusActionById);
        //        }
        //        // update locked
        //        if (ILock.class.isAssignableFrom(entityClass)) {
        //
        //            final UpdateDomainActionDispatcher<K, T, K, Boolean, Boolean, U> updateLockedActionById
        //                    = buildUpdateLockedActionById(repository, entityClass, idClass);
        //            domainActionMap.put(SpiAction.Type.UPDATE_LOCKED_BY_ID, updateLockedActionById);
        //        }
        //
        //        if (IAudit.class.isAssignableFrom(entityClass)) {
        //            final UpdateDomainActionDispatcher<K, T, K, IAudit.AuditStatus, AuditValue, U> updateAuditStatusActionById
        //                    = buildUpdateAuditStatusActionById(repository, entityClass, idClass);
        //            domainActionMap.put(SpiAction.Type.UPDATE_AUDIT_STATUS_BY_ID, updateAuditStatusActionById);
        //        }
        //
        //        // sort
        //        if (ISort.class.isAssignableFrom(entityClass)) {
        //            final UpdateDomainActionDispatcher<K, T, Void, Void, List<SortValue<K>>, U> updateSortAction
        //                    = buildUpdateSortAction(repository, entityClass, idClass);
        //            domainActionMap.put(SpiAction.Type.SORT, updateSortAction);
        //        }


        builder.domainActions(domainActionMap);

        return builder.build();
    }

    private UpdateDomainActionDispatcher<K, T, Void, Void, List<SortValue<K>>, U> buildUpdateSortAction(
            Repository<K, T> repository,
            Class<?> entityClass, Class<?> idClass) {

        // List<SortValue<K>>
        ResolvableType sortType = ResolvableType.forClassWithGenerics(List.class, ResolvableType.forClassWithGenerics(SortValue.class, idClass));

        //UpdateFunction<Entity,Void,Void,Map<K,Integer>,User>
        final UpdateFunction<T, Void, Void, List<SortValue<K>>, U> updateAuditStatusByIdFunction
                = (UpdateFunction<T, Void, Void, List<SortValue<K>>, U>) applicationContext.getBeanProvider(
                        ResolvableType.forClassWithGenerics(
                                UpdateFunction.class,
                                ResolvableType.forClass(entityClass),
                                ResolvableType.forClass(Void.class),
                                ResolvableType.forClass(Void.class),
                                sortType,
                                ResolvableType.forClass(userClass)
                        ))
                .getIfAvailable(() -> new DefaultUpdateSortFunction<>(repository));
        final UpdateDomainActionDispatcher<K, T, Void, Void, List<SortValue<K>>, U> updateAuditStatusActionById
                = new UpdateDomainActionDispatcher<>(SpiAction.SORT, repository, updateAuditStatusByIdFunction);
        acceptUpdateDomainAction(updateAuditStatusActionById, SpiAction.SORT,
                ResolvableType.forClass(entityClass),
                ResolvableType.forClass(idClass),
                sortType,
                ResolvableType.forClass(userClass)
        );
        return updateAuditStatusActionById;
    }


    private UpdateDomainActionDispatcher<K, T, K, IAudit.AuditStatus, AuditValue, U> buildUpdateAuditStatusActionById(
            Repository<K, T> repository,
            Class<?> entityClass, Class<?> idClass) {
        //UpdateFunction<Entity,ID,IAudit.AuditStatus,AuditValue,User>
        final UpdateFunction<T, K, IAudit.AuditStatus, AuditValue, U> updateAuditStatusByIdFunction
                = (UpdateFunction<T, K, IAudit.AuditStatus, AuditValue, U>) applicationContext.getBeanProvider(
                        ResolvableType.forClassWithGenerics(
                                UpdateFunction.class,
                                ResolvableType.forClass(entityClass),
                                ResolvableType.forClass(idClass),
                                ResolvableType.forClass(IAudit.AuditStatus.class),
                                ResolvableType.forClass(AuditValue.class),
                                ResolvableType.forClass(userClass)
                        ))
                .getIfAvailable(() -> new DefaultUpdateAuditStatusFunction<>(repository));
        final UpdateDomainActionDispatcher<K, T, K, IAudit.AuditStatus, AuditValue, U> updateAuditStatusActionById
                = new UpdateDomainActionDispatcher<>(SpiAction.UPDATE_AUDIT_STATUS, repository, updateAuditStatusByIdFunction);
        acceptUpdateDomainAction(updateAuditStatusActionById, SpiAction.UPDATE_AUDIT_STATUS,
                entityClass, idClass, AuditValue.class, userClass);
        return updateAuditStatusActionById;
    }

    private UpdateDomainActionDispatcher<K, T, K, Boolean, Boolean, U> buildUpdateLockedActionById(Repository<K, T> repository,
                                                                                                   Class<?> entityClass,
                                                                                                   Class<?> idClass) {
        //UpdateFunction<Entity,ID,Boolean,Boolean,User>
        final UpdateFunction<T, K, Boolean, Boolean, U> updateLockedByIdFunction
                = (UpdateFunction<T, K, Boolean, Boolean, U>) applicationContext.getBeanProvider(
                        ResolvableType.forClassWithGenerics(
                                UpdateFunction.class,
                                ResolvableType.forClass(entityClass),
                                ResolvableType.forClass(idClass),
                                ResolvableType.forClass(Boolean.class),
                                ResolvableType.forClass(Boolean.class),
                                ResolvableType.forClass(userClass)
                        ))
                .getIfAvailable(() -> new DefaultUpdateLockedFunction<>(repository));
        final UpdateDomainActionDispatcher<K, T, K, Boolean, Boolean, U> updateLockedActionById
                = new UpdateDomainActionDispatcher<>(SpiAction.UPDATE_LOCKED, repository, updateLockedByIdFunction);
        acceptUpdateDomainAction(updateLockedActionById, SpiAction.UPDATE_LOCKED, entityClass, idClass, Boolean.class, userClass);
        return updateLockedActionById;
    }

    private UpdateDomainActionDispatcher<K, T, K, IEnum<?>, IEnum<?>, U> buildUpdateStatusActionById(Repository<K, T> repository,
                                                                                                     Class<?> entityClass,
                                                                                                     Class<?> idClass,
                                                                                                     Class<?> statusClass) {
        //UpdateFunction<Entity,ID,Status,User>
        final UpdateFunction<T, K, IEnum<?>, IEnum<?>, U> updateStatusByIdFunction
                = (UpdateFunction<T, K, IEnum<?>, IEnum<?>, U>) applicationContext.getBeanProvider(
                        ResolvableType.forClassWithGenerics(
                                UpdateFunction.class,
                                ResolvableType.forClass(entityClass),
                                ResolvableType.forClass(idClass),
                                ResolvableType.forClass(statusClass),
                                ResolvableType.forClass(statusClass),
                                ResolvableType.forClass(userClass)
                        ))
                .getIfAvailable(() -> new DefaultUpdateStatusFunction<>(repository));

        final UpdateDomainActionDispatcher<K, T, K, IEnum<?>, IEnum<?>, U> updateStatusActionById
                = new UpdateDomainActionDispatcher<>(SpiAction.UPDATE_STATUS, repository, updateStatusByIdFunction);
        acceptUpdateDomainAction(updateStatusActionById, SpiAction.UPDATE_STATUS, entityClass, idClass, statusClass, userClass);
        return updateStatusActionById;
    }

    private UpdateDomainActionDispatcher<K, T, K, YN, YN, U> buildUpdateYnActionById(Repository<K, T> repository,
                                                                                     Class<?> entityClass,
                                                                                     Class<?> idClass) {
        //UpdateFunction<Entity,ID,YN,User>
        final UpdateFunction<T, K, YN, YN, U> updateYnByIdFunction
                = (UpdateFunction<T, K, YN, YN, U>) applicationContext.getBeanProvider(
                        ResolvableType.forClassWithGenerics(
                                UpdateFunction.class,
                                ResolvableType.forClass(entityClass),
                                ResolvableType.forClass(idClass),
                                ResolvableType.forClass(YN.class),
                                ResolvableType.forClass(YN.class),
                                ResolvableType.forClass(userClass)
                        ))
                .getIfAvailable(() -> new DefaultUpdateYnFunction<>(repository));

        final UpdateDomainActionDispatcher<K, T, K, YN, YN, U> updateYnActionById
                = new UpdateDomainActionDispatcher<>(SpiAction.UPDATE_YN, repository, updateYnByIdFunction);
        acceptUpdateDomainAction(updateYnActionById, SpiAction.UPDATE_YN, entityClass, idClass, YN.class, userClass);
        return updateYnActionById;
    }

    private UpdateDomainActionDispatcher<K, T, K, Boolean, T, U> buildUpdateActionById(Repository<K, T> repository,
                                                                                       Class<?> entityClass,
                                                                                       Class<?> idClass) {
        // BiUpdateFunction<Entity,ID,Boolean,Entity,User>
        final UpdateFunction<T, K, Boolean, T, U> updateByIdFunction
                = (UpdateFunction<T, K, Boolean, T, U>) applicationContext.getBeanProvider(
                ResolvableType.forClassWithGenerics(
                        UpdateFunction.class,
                        ResolvableType.forClass(entityClass),
                        ResolvableType.forClass(idClass),
                        ResolvableType.forClass(Boolean.class),
                        ResolvableType.forClass(entityClass),
                        ResolvableType.forClass(userClass)
                )
        ).getIfAvailable(() -> new DefaultUpdateFunction<>(repository));

        final UpdateDomainActionDispatcher<K, T, K, Boolean, T, U> updateByIdDomainAction
                = new UpdateDomainActionDispatcher<>(SpiAction.UPDATE, repository, updateByIdFunction);
        acceptUpdateDomainAction(updateByIdDomainAction, SpiAction.UPDATE, entityClass, idClass, entityClass, userClass);
        return updateByIdDomainAction;
    }

    private SelectDomainDispatcher<K, T, IQuery, U, Long> buildCountSelectActionByQuery(Repository<K, T> repository,
                                                                                        ClassLoader classLoader,
                                                                                        String queryPackage,
                                                                                        Class<?> entityClass,
                                                                                        Class<?> defaultqueryClass) {
        final Class<?> countQueryClass = resolveClass(classLoader,
                queryPackage + "." + DomainNameHelper.domainQueryName(entityClass, IView.Count.class), defaultqueryClass);

        // SelectFunction<Query,User,Long>
        final SelectFunction<IQuery, U, Long> selectCountFunction
                = (SelectFunction<IQuery, U, Long>) applicationContext.getBeanProvider(
                        ResolvableType.forClassWithGenerics(
                                SelectFunction.class,
                                ResolvableType.forClass(countQueryClass),
                                ResolvableType.forClass(userClass),
                                ResolvableType.forClass(Long.class)
                        ))
                .getIfAvailable(() -> new DefaultSelectCountFunction<>(repository));
        final SelectDomainDispatcher<K, T, IQuery, U, Long> detailSelectActionByQuery
                = new SelectDomainDispatcher<>(SpiAction.COUNT, selectCountFunction);
        detailSelectActionByQuery.setView(IView.Count.class);
        detailSelectActionByQuery.setDomainQueryClass(countQueryClass);
        detailSelectActionByQuery.setPreQueryConsumer(getSpiComposite(SpiAction.COUNT, SpiAction.Advice.PRE,
                QueryConsumer.class, countQueryClass, userClass));
        detailSelectActionByQuery.setPostConsumer(getSpiComposite(SpiAction.COUNT, SpiAction.Advice.POST,
                Consumer.class, entityClass, userClass));
        detailSelectActionByQuery.setPostQueryConsumer(getSpiComposite(SpiAction.COUNT, SpiAction.Advice.POST,
                BiConsumer.class, entityClass, countQueryClass, userClass));
        // PostQueryFunction<Long,IQuery,IUser>
        applicationContext.getBeanProvider(ResolvableType.forClassWithGenerics(Function.class,
                ResolvableType.forClass(Long.class),
                ResolvableType.forClass(countQueryClass),
                ResolvableType.forClass(userClass)
        )).ifAvailable(postQueryFunction -> detailSelectActionByQuery.setPostQueryFunction((Function<Long, IQuery, U>) postQueryFunction));
        return detailSelectActionByQuery;
    }

    private SelectDomainDispatcher<K, T, K, U, T> buildDetailSelectActionById(Repository<K, T> repository,
                                                                              Class<?> idClass, Class<?> entityClass) {
        // SelectFunction<Long,User,Entity>
        final SelectFunction<K, U, T> selectOneFunctionById
                = (SelectFunction<K, U, T>) applicationContext.getBeanProvider(
                        ResolvableType.forClassWithGenerics(
                                SelectFunction.class,
                                ResolvableType.forClass(idClass),
                                ResolvableType.forClass(userClass),
                                ResolvableType.forClass(entityClass)
                        ))
                .getIfAvailable(() -> new DefaultSelectOneFunction<>(repository, IView.Detail.class));
        final SelectDomainDispatcher<K, T, K, U, T> detailSelectActionById
                = new SelectDomainDispatcher<>(SpiAction.DETAIL, selectOneFunctionById);
        detailSelectActionById.setView(IView.Detail.class);
        detailSelectActionById.setPostConsumer(getSpiComposite(SpiAction.DETAIL, SpiAction.Advice.POST,
                Consumer.class, entityClass, userClass));
        return detailSelectActionById;
    }

    private SelectDomainDispatcher<K, T, IQuery, U, T> buildDetailSelectActionByQuery(Repository<K, T> repository, ClassLoader classLoader,
                                                                                      String queryPackage, Class<?> entityClass,
                                                                                      Class<?> defaultqueryClass) {
        final Class<?> detailQueryClass = resolveClass(classLoader,
                queryPackage + "." + DomainNameHelper.domainQueryName(entityClass, IView.Detail.class), defaultqueryClass);

        // SelectFunction<Query,User,Entity>
        final SelectFunction<IQuery, U, T> selectOneFunctionByQuery
                = (SelectFunction<IQuery, U, T>) applicationContext.getBeanProvider(
                        ResolvableType.forClassWithGenerics(
                                SelectFunction.class,
                                ResolvableType.forClass(detailQueryClass),
                                ResolvableType.forClass(userClass),
                                ResolvableType.forClass(entityClass)
                        ))
                .getIfAvailable(() -> new DefaultSelectOneFunction<>(repository, IView.Detail.class));
        final SelectDomainDispatcher<K, T, IQuery, U, T> detailSelectActionByQuery
                = new SelectDomainDispatcher<>(SpiAction.DETAIL, selectOneFunctionByQuery);
        detailSelectActionByQuery.setView(IView.Detail.class);
        detailSelectActionByQuery.setDomainQueryClass(detailQueryClass);
        detailSelectActionByQuery.setPreQueryConsumer(getSpiComposite(SpiAction.DETAIL, SpiAction.Advice.PRE,
                QueryConsumer.class, detailQueryClass, userClass));
        detailSelectActionByQuery.setPostConsumer(getSpiComposite(SpiAction.DETAIL, SpiAction.Advice.POST,
                Consumer.class, entityClass, userClass));
        detailSelectActionByQuery.setPostQueryConsumer(getSpiComposite(SpiAction.DETAIL, SpiAction.Advice.POST,
                BiConsumer.class, entityClass, detailQueryClass, userClass));
        // PostQueryFunction<T,IQuery,IUser>
        applicationContext.getBeanProvider(ResolvableType.forClassWithGenerics(Function.class,
                ResolvableType.forClass(entityClass),
                ResolvableType.forClass(detailQueryClass),
                ResolvableType.forClass(userClass)
        )).ifAvailable(postQueryFunction -> detailSelectActionByQuery.setPostQueryFunction((Function<T, IQuery, U>) postQueryFunction));
        return detailSelectActionByQuery;
    }

    private SelectDomainDispatcher<K, T, IQuery, U, List<T>> buildExportActionByQuery(Repository<K, T> repository, ClassLoader classLoader,
                                                                                      String queryPackage, Class<?> entityClass,
                                                                                      Class<?> defaultqueryClass) {
        final Class<?> listQueryClass = resolveClass(classLoader,
                queryPackage + "." + DomainNameHelper.domainQueryName(entityClass, IView.Export.class), defaultqueryClass);

        // SelectFunction<Query,User,List<Entity>>
        final SelectFunction<IQuery, U, List<T>> listSelectFunction
                = (SelectFunction<IQuery, U, List<T>>) applicationContext.getBeanProvider(
                ResolvableType.forClassWithGenerics(
                        SelectFunction.class,
                        ResolvableType.forClass(listQueryClass),
                        ResolvableType.forClass(userClass),
                        ResolvableType.forClassWithGenerics(List.class, entityClass)
                )
        ).getIfAvailable(() -> new DefaultSelectFunction<>(repository, IView.List.class));

        final SelectDomainDispatcher<K, T, IQuery, U, List<T>> listQueryDomainAction
                = new SelectDomainDispatcher<>(SpiAction.LIST, listSelectFunction);
        listQueryDomainAction.setView(IView.List.class);
        listQueryDomainAction.setDomainQueryClass(listQueryClass);
        listQueryDomainAction.setPreQueryConsumer(getSpiComposite(SpiAction.EXPORT, SpiAction.Advice.PRE,
                QueryConsumer.class, listQueryClass, userClass));
        listQueryDomainAction.setPostQueryConsumer(getSpiComposite(SpiAction.EXPORT, SpiAction.Advice.POST,
                BiConsumer.class, entityClass, listQueryClass, userClass));
        listQueryDomainAction.setBiAfterThrowingConsumer(getSpiComposite(SpiAction.EXPORT, SpiAction.Advice.AFTER_THROWING,
                BiAfterThrowingConsumer.class, entityClass, listQueryClass, userClass));
        listQueryDomainAction.setBiAfterReturningConsumer(getSpiComposite(SpiAction.EXPORT, SpiAction.Advice.AFTER_RETURNING,
                BiAfterReturningConsumer.class, entityClass, listQueryClass, userClass));
        // PostQueryFunction<List<T>,IQuery,IUser>
        applicationContext.getBeanProvider(ResolvableType.forClassWithGenerics(Function.class,
                ResolvableType.forClassWithGenerics(List.class, entityClass),
                ResolvableType.forClass(listQueryClass),
                ResolvableType.forClass(userClass)
        )).ifAvailable(postQueryFunction -> listQueryDomainAction.setPostQueryFunction((Function<List<T>, IQuery, U>) postQueryFunction));
        return listQueryDomainAction;
    }

    private SelectDomainDispatcher<K, T, IQuery, U, List<T>> buildListActionByQuery(Repository<K, T> repository, ClassLoader classLoader,
                                                                                    String queryPackage, Class<?> entityClass,
                                                                                    Class<?> defaultqueryClass) {
        final Class<?> listQueryClass = resolveClass(classLoader,
                queryPackage + "." + DomainNameHelper.domainQueryName(entityClass, IView.List.class), defaultqueryClass);

        // SelectFunction<Query,User,List<Entity>>
        final SelectFunction<IQuery, U, List<T>> listSelectFunction
                = (SelectFunction<IQuery, U, List<T>>) applicationContext.getBeanProvider(
                ResolvableType.forClassWithGenerics(
                        SelectFunction.class,
                        ResolvableType.forClass(listQueryClass),
                        ResolvableType.forClass(userClass),
                        ResolvableType.forClassWithGenerics(List.class, entityClass)
                )
        ).getIfAvailable(() -> new DefaultSelectFunction<>(repository, IView.List.class));

        final SelectDomainDispatcher<K, T, IQuery, U, List<T>> listQueryDomainAction
                = new SelectDomainDispatcher<>(SpiAction.LIST, listSelectFunction);
        listQueryDomainAction.setView(IView.List.class);
        listQueryDomainAction.setDomainQueryClass(listQueryClass);
        listQueryDomainAction.setPreQueryConsumer(getSpiComposite(SpiAction.LIST, SpiAction.Advice.PRE,
                QueryConsumer.class, listQueryClass, userClass));
        listQueryDomainAction.setPostQueryConsumer(getSpiComposite(SpiAction.LIST, SpiAction.Advice.POST,
                BiConsumer.class, entityClass, listQueryClass, userClass));
        listQueryDomainAction.setBiAfterThrowingConsumer(getSpiComposite(SpiAction.LIST, SpiAction.Advice.AFTER_THROWING,
                BiAfterThrowingConsumer.class, entityClass, listQueryClass, userClass));
        listQueryDomainAction.setBiAfterReturningConsumer(getSpiComposite(SpiAction.LIST, SpiAction.Advice.AFTER_RETURNING,
                BiAfterReturningConsumer.class, entityClass, listQueryClass, userClass));
        // PostQueryFunction<List<T>,IQuery,IUser>
        applicationContext.getBeanProvider(ResolvableType.forClassWithGenerics(Function.class,
                ResolvableType.forClassWithGenerics(List.class, entityClass),
                ResolvableType.forClass(listQueryClass),
                ResolvableType.forClass(userClass)
        )).ifAvailable(postQueryFunction -> listQueryDomainAction.setPostQueryFunction((Function<List<T>, IQuery, U>) postQueryFunction));
        return listQueryDomainAction;
    }

    private DeleteDomainActionDispatcher<K, T, K, U> buildDeleteActionById(Repository<K, T> repository, Class<?> entityClass,
                                                                           Class<?> idClass) {
        //DeleteFunction<Entity,DeleteQuery,User>
        final DeleteFunction<T, K, U> deleteFunctionById = (DeleteFunction<T, K, U>) applicationContext.getBeanProvider(
                        ResolvableType.forClassWithGenerics(
                                DeleteFunction.class,
                                ResolvableType.forClass(entityClass),
                                ResolvableType.forClass(idClass),
                                ResolvableType.forClass(userClass)
                        ))
                .getIfAvailable(() -> new DefaultDeleteFunction<>(repository));

        final DeleteDomainActionDispatcher<K, T, K, U> deleteByIdDomainAction
                = new DeleteDomainActionDispatcher<>(SpiAction.DELETE, repository, deleteFunctionById);
        deleteByIdDomainAction.setView(IView.Delete.class);
        deleteByIdDomainAction.setPreConsumer(getSpiComposite(SpiAction.DELETE, SpiAction.Advice.PRE,
                Consumer.class, entityClass, userClass));
        deleteByIdDomainAction.setPostConsumer(getSpiComposite(SpiAction.DELETE, SpiAction.Advice.POST,
                Consumer.class, entityClass, userClass));
        deleteByIdDomainAction.setAfterConsumer(getSpiComposite(SpiAction.DELETE, SpiAction.Advice.AFTER,
                AfterConsumer.class, entityClass, idClass, Void.class, Integer.class, userClass));
        return deleteByIdDomainAction;
    }

    private DeleteDomainActionDispatcher<K, T, IQuery, U> buildDeleteActionByQuery(Repository<K, T> repository, ClassLoader classLoader,
                                                                                   String queryPackage, Class<?> entityClass,
                                                                                   Class<?> defaultqueryClass) {
        final Class<?> deleteQueryClass = resolveClass(classLoader,
                queryPackage + "." + DomainNameHelper.domainQueryName(entityClass, IView.Delete.class), defaultqueryClass);
        //DeleteFunction<Entity,DeleteQuery,User>
        final DeleteFunction<T, IQuery, U> deleteFunctionByQuery = (DeleteFunction<T, IQuery, U>) applicationContext.getBeanProvider(
                        ResolvableType.forClassWithGenerics(
                                DeleteFunction.class,
                                ResolvableType.forClass(entityClass),
                                ResolvableType.forClass(deleteQueryClass),
                                ResolvableType.forClass(userClass)
                        ))
                .getIfAvailable(() -> new DefaultDeleteFunction<>(repository));

        final DeleteDomainActionDispatcher<K, T, IQuery, U> deleteActionByQuery
                = new DeleteDomainActionDispatcher<>(SpiAction.DELETE, repository, deleteFunctionByQuery);
        deleteActionByQuery.setView(IView.Delete.class);
        deleteActionByQuery.setDomainQueryClass(deleteQueryClass);
        deleteActionByQuery.setPreQueryConsumer(getSpiComposite(SpiAction.DELETE, SpiAction.Advice.PRE,
                QueryConsumer.class, deleteQueryClass, userClass));
        deleteActionByQuery.setPreConsumer(getSpiComposite(SpiAction.DELETE, SpiAction.Advice.PRE,
                Consumer.class, entityClass, userClass));
        deleteActionByQuery.setPostConsumer(getSpiComposite(SpiAction.DELETE, SpiAction.Advice.POST,
                Consumer.class, entityClass, userClass));
        deleteActionByQuery.setPostQueryConsumer(getSpiComposite(SpiAction.DELETE, SpiAction.Advice.POST,
                BiConsumer.class, entityClass, deleteQueryClass, userClass));
        deleteActionByQuery.setAfterConsumer(getSpiComposite(SpiAction.DELETE, SpiAction.Advice.AFTER,
                AfterConsumer.class, entityClass, deleteQueryClass, Void.class, Integer.class, userClass));
        return deleteActionByQuery;
    }

    private InsertDomainActionDispatcher<K, T, U> buildCreateAction(
            Repository<K, T> repository, Class<?> entityClass, DomainResource domainResource) {
        // create
        String dtoClassName = DomainNameHelper.modelClassName(entityClass, IView.Create.class.getSimpleName());
        InsertDomainActionDispatcher<K, T, U> insertDomainActionDispatcher
                = new InsertDomainActionDispatcher<>(repository, domainResource.insertIgnore());

        if (ClassUtils.isPresent(dtoClassName, entityClass.getClassLoader())) {
            Class<?> dtoClass = ClassUtils.resolveClassName(dtoClassName, entityClass.getClassLoader());
            insertDomainActionDispatcher.setDomainEntityClass(dtoClass);
            PreInsertFunction preInsertFunction = (PreInsertFunction) applicationContext.getBeanProvider(
                            ResolvableType.forClassWithGenerics(PreInsertFunction.class, dtoClass, userClass, entityClass))
                    .getObject();
            insertDomainActionDispatcher.setPreInsertFunction(preInsertFunction);
            insertDomainActionDispatcher.setDomainEntityClass(dtoClass);
        }

        // PreInsert
        insertDomainActionDispatcher.setPreInsertFilter(getSpiComposite(SpiAction.CREATE, SpiAction.Advice.PRE,
                Filter.class, entityClass, userClass));
        insertDomainActionDispatcher.setPreInsertConsumer(getSpiComposite(SpiAction.CREATE, SpiAction.Advice.PRE,
                Consumer.class, entityClass, userClass));
        // PostInsert
        insertDomainActionDispatcher.setPostInsertConsumer(getSpiComposite(SpiAction.CREATE, SpiAction.Advice.POST,
                Consumer.class, entityClass, userClass));
        insertDomainActionDispatcher.setAfterThrowingInsertConsumer(getSpiComposite(SpiAction.CREATE, SpiAction.Advice.AFTER_THROWING,
                AfterThrowingConsumer.class, entityClass, userClass));
        insertDomainActionDispatcher.setAfterReturningInsertConsumer(getSpiComposite(SpiAction.CREATE, SpiAction.Advice.AFTER_RETURNING,
                AfterReturningConsumer.class, entityClass, Integer.class, userClass));
        insertDomainActionDispatcher.setAfterConsumer(getSpiComposite(SpiAction.CREATE, SpiAction.Advice.AFTER,
                AfterConsumer.class, entityClass, Void.class, Void.class, Integer.class, userClass));
        return insertDomainActionDispatcher;
    }

    private void acceptUpdateDomainAction(AbsUpdateDeleteDomainActionDispatcher action, SpiAction spiAction,
                                          Class<?> entityClass, Class<?> paramClass,
                                          Class<?> valueClass, Class<U> userClass) {
        action.setPreUpdateValidator(getSpiComposite(spiAction, SpiAction.Advice.PRE,
                BiValidator.class, entityClass, valueClass, userClass));
        action.setPreUpdateConsumer(getSpiComposite(spiAction, SpiAction.Advice.PRE,
                UpdateConsumer.class, entityClass, valueClass, userClass));
        action.setPostUpdateConsumer(getSpiComposite(spiAction, SpiAction.Advice.POST,
                UpdateConsumer.class, entityClass, valueClass, userClass));
        action.setAfterConsumer(getSpiComposite(spiAction, SpiAction.Advice.AFTER,
                AfterConsumer.class, entityClass, paramClass, Void.class, Integer.class, userClass));
    }


    private void acceptUpdateDomainAction(AbsUpdateDeleteDomainActionDispatcher action, SpiAction spiAction,
                                          ResolvableType entityClass, ResolvableType paramClass,
                                          ResolvableType valueClass, ResolvableType userClass) {
        action.setPreUpdateValidator(getSpiComposite(spiAction, SpiAction.Advice.PRE,
                BiValidator.class, entityClass, valueClass, userClass));
        action.setPreUpdateConsumer(getSpiComposite(spiAction, SpiAction.Advice.PRE,
                UpdateConsumer.class, entityClass, valueClass, userClass));
        action.setPostUpdateConsumer(getSpiComposite(spiAction, SpiAction.Advice.POST,
                UpdateConsumer.class, entityClass, valueClass, userClass));
        action.setAfterConsumer(getSpiComposite(spiAction, SpiAction.Advice.AFTER,
                AfterConsumer.class, entityClass, paramClass, ResolvableType.forClass(Void.class), ResolvableType.forClass(Integer.class), userClass));
    }


    private static Class<?> resolveClass(ClassLoader classLoader, String className, Class<?> defaultClass) {
        if (ClassUtils.isPresent(className, classLoader)) {
            return ClassUtils.resolveClassName(className, classLoader);
        }
        return defaultClass;
    }

    @SuppressWarnings("unchecked")
    private <E> E getSpiComposite(SpiAction action, SpiAction.Advice advice, Class<E> type, Class<?>... generics) {
        List beans = getBeansOf(action, advice, type, generics);
        if (CollectionUtils.isEmpty(beans) && type == Filter.class) {
            beans = Collections.singletonList((Filter) (action1, entity, user) -> true);
        }

        if (type == BiValidator.class && generics[1] == AuditValue.class) {
            beans.add(new DefaultUpdateAuditStatusPreValidator<>());
        }


        if (type == AfterConsumer.class) {
            beans.add(loggerAfterConsumer);
        }

        return (E) Proxies.composite(type, beans);
    }

    private <E> E getSpiComposite(SpiAction action, SpiAction.Advice advice, Class<E> type, ResolvableType... generics) {
        List beans = getBeansOf(action, advice, type, generics);
        if (CollectionUtils.isEmpty(beans) && type == Filter.class) {
            beans = Collections.singletonList((Filter) (action1, entity, user) -> true);
        }

        if (type == BiValidator.class && generics[1].getRawClass() == AuditValue.class) {
            beans.add(new DefaultUpdateAuditStatusPreValidator<>());
        }


        if (type == AfterConsumer.class) {
            beans.add(loggerAfterConsumer);
        }

        return (E) Proxies.composite(type, beans);
    }

    private List getBeansOf(SpiAction action, SpiAction.Advice advice, Class<?> type, ResolvableType... generics) {
        return applicationContext.getBeanProvider(ResolvableType.forClassWithGenerics(type, generics))
                .orderedStream()
                .filter(it -> {

                    final boolean matches = domainSpiMatcher.matches(it, action, advice);
                    if (matches) {
                        logger.info("found type={} for action={} with advice={}", type, action, advice);
                    }
                    return matches;

                })
                .collect(Collectors.toList());
    }

    private List getBeansOf(SpiAction action, SpiAction.Advice advice, Class<?> type, Class<?>... generics) {
        return applicationContext.getBeanProvider(ResolvableType.forClassWithGenerics(type, generics))
                .orderedStream()
                .filter(it -> {

                    final boolean matches = domainSpiMatcher.matches(it, action, advice);
                    if (matches) {
                        logger.info("found type={} for action={} with advice={}", type, action, advice);
                    }
                    return matches;

                })
                .collect(Collectors.toList());
    }
}
