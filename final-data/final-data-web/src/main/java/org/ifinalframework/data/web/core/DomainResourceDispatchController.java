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

package org.ifinalframework.data.web.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.ifinalframework.context.FinalContext;
import org.ifinalframework.context.exception.BadRequestException;
import org.ifinalframework.context.exception.InternalServerException;
import org.ifinalframework.context.expression.Spel;
import org.ifinalframework.core.IAudit;
import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IEnum;
import org.ifinalframework.core.IQuery;
import org.ifinalframework.core.IStatus;
import org.ifinalframework.core.ITenant;
import org.ifinalframework.core.IUser;
import org.ifinalframework.core.IView;
import org.ifinalframework.data.annotation.YN;
import org.ifinalframework.data.domain.DomainService;
import org.ifinalframework.data.domain.action.DeleteAction;
import org.ifinalframework.data.domain.action.InsertAction;
import org.ifinalframework.data.domain.action.SelectAction;
import org.ifinalframework.data.domain.action.UpdateAction;
import org.ifinalframework.data.domain.excel.ClassPathDomainResourceExcelExportProvider;
import org.ifinalframework.data.domain.excel.DomainResourceExcelExportProvider;
import org.ifinalframework.data.domain.excel.ExcelExportService;
import org.ifinalframework.data.domain.model.AuditValue;
import org.ifinalframework.data.domain.model.SortValue;
import org.ifinalframework.data.query.PageQuery;
import org.ifinalframework.data.security.DomainResourceAuth;
import org.ifinalframework.data.spi.SpiAction;
import org.ifinalframework.json.Json;
import org.ifinalframework.poi.Excels;
import org.ifinalframework.poi.WorkbookWriter;
import org.ifinalframework.poi.model.Excel;
import org.ifinalframework.poi.model.Sheet;
import org.ifinalframework.web.annotation.bind.RequestAction;
import org.ifinalframework.web.annotation.bind.RequestEntity;
import org.ifinalframework.web.annotation.bind.RequestQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 领域资源标识派发控制器.
 *
 * <h4>支持的操作</h4>
 * <table>
 *     <thead>
 *         <tr>
 *             <td>URL</td>
 *             <td>Method</td>
 *             <td>Query</td>
 *             <td>描述</td>
 *         </tr>
 *     </thead>
 *     <tbody>
 *         <tr>
 *             <td>{@code GET /{prefix}/{resource}}</td>
 *             <td>{@link #query(String, IQuery, SelectAction, IUser)}</td>
 *             <td>{@code {Entity}ListQuery }</td>
 *             <td>列表查询</td>
 *         </tr>
 *         <tr>
 *             <td>{@code GET /{prefix}/{resource}/detail}</td>
 *             <td>{@link #detail(String, IQuery, SelectAction, IUser)}</td>
 *             <td>{@code {Entity}DetailQuery }</td>
 *             <td>列表详情查询</td>
 *         </tr>
 *         <tr>
 *             <td>{@code GET /{prefix}/{resource}/{id}}</td>
 *             <td>{@link #detail(String, Long, SelectAction, IUser)}</td>
 *             <td></td>
 *             <td>ID详情查询</td>
 *         </tr>
 *         <tr>
 *             <td>{@code DELETE /{prefix}/{resource}}</td>
 *             <td>{@link #delete(String, IQuery, DeleteAction, IUser)}</td>
 *             <td>{@code {Entity}DeleteQuery }</td>
 *             <td>批量删除</td>
 *         </tr>
 *         <tr>
 *             <td>{@code DELETE /{prefix}/{resource}/{id} }</td>
 *             <td>{@link #delete(String, Long, DeleteAction, IUser)}</td>
 *             <td></td>
 *             <td>ID删除</td>
 *         </tr>
 *     </tbody>
 * </table>
 *
 * @author iimik
 * @version 1.4.2
 * @since 1.4.2
 */
@Configuration
@Transactional
@RestController
@Validated
@RequestMapping("${final.data.web.domain-resource.prefix:/api}/{resource}")
@ConditionalOnWebApplication
public class DomainResourceDispatchController {
    private static final Logger logger = LoggerFactory.getLogger(DomainResourceDispatchController.class);

    private DomainResourceExcelExportProvider domainResourceExcelExportProvider = new ClassPathDomainResourceExcelExportProvider();
    @Resource
    private ExcelExportService excelExportService;

    /**
     * 列表查询
     */
    @GetMapping
    @DomainResourceAuth(action = SpiAction.LIST)
    public Object query(@PathVariable String resource,
                        @Valid @RequestQuery(view = IView.List.class) IQuery query,
                        @RequestAction(action = "LIST_BY_QUERY") SelectAction selectAction,
                        IUser<?> user) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> query={}", Json.toJson(query));
        }

        try {
            setFinalContext(query);
            return processResult(selectAction.select(query, user));
        } finally {
            clearFinalContext(query);
        }

    }

    /**
     * 导出
     *
     * @since 1.5.2
     */
    @GetMapping("/export")
    @DomainResourceAuth(action = SpiAction.EXPORT)
    public Object export(@PathVariable String resource, @Valid @RequestQuery(view = IView.Export.class) IQuery query,
                         @RequestAction(action = "EXPORT_BY_QUERY") SelectAction selectAction,
                         IUser<?> user, DomainService<Long, IEntity<Long>, IUser<?>> domainService,
                         HttpServletResponse response) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("==> query={}", Json.toJson(query));
        }

        try {
            setFinalContext(query);

            if (query instanceof PageQuery pageQuery) {
                pageQuery.setCount(false);
                pageQuery.setPage(null);
                pageQuery.setSize(null);
            }

            final Map<String, Object> context = new LinkedHashMap<>();
            context.put("query", query);
            context.put("user", user);

            final Excel excel = domainResourceExcelExportProvider.getResourceExcel(resource, domainService.entityClass());

            final String fileName = (StringUtils.hasText(excel.getName())
                    ? Spel.getValue(excel.getName(), context, String.class)
                    : domainService.entityClass().getSimpleName()) + "." + excel.getVersion().name().toLowerCase();

            final Object result = processResult(selectAction.select(query, user));

            final WorkbookWriter workbookWriter = Excels.newWriter(excel, context);

            if (result instanceof List<?> list) {
                workbookWriter.append(list);
            } else if (result instanceof Map<?, ?> map) {
                for (int i = 0; i < excel.getSheets().size(); i++) {
                    final Sheet sheet = excel.getSheets().get(i);
                    final Object list = map.get(sheet.getName());
                    workbookWriter.append(i, (List<?>) list);
                }
            } else {
                throw new InternalServerException("不支持的导出结果类型");
            }

            return excelExportService.export(fileName, workbookWriter, response);

        } finally {
            clearFinalContext(query);
        }
    }


    /**
     * 详情
     */
    @GetMapping("/detail")
    @DomainResourceAuth(action = SpiAction.DETAIL)
    public Object detail(@PathVariable String resource,
                         @Valid @RequestQuery(view = IView.Detail.class) IQuery query,
                         @RequestAction(action = "DETAIL_BY_QUERY") SelectAction selectAction,
                         IUser<?> user) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> query={}", Json.toJson(query));
        }
        try {
            setFinalContext(query);
            final Object result = selectAction.select(query, user);
            return processResult(result);
        } finally {
            clearFinalContext(query);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @DomainResourceAuth(action = SpiAction.DETAIL)
    public Object detail(@PathVariable String resource, @PathVariable Long id,
                         @RequestAction(action = "DETAIL_BY_ID") SelectAction selectAction,
                         IUser<?> user) {
        return processResult(selectAction.select(id, user));
    }

    // delete

    /**
     * 批量删除
     */
    @DeleteMapping
    @DomainResourceAuth(action = SpiAction.DELETE)
    public Object delete(@PathVariable String resource, @Valid @RequestQuery(view = IView.Delete.class) IQuery query,
                         @RequestAction(action = "DELETE_BY_QUERY") DeleteAction deleteAction,
                         IUser<?> user) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> query={}", Json.toJson(query));
        }
        try {
            setFinalContext(query);
            return processResult(deleteAction.delete(query, user));
        } finally {
            clearFinalContext(query);
        }
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/{id}")
    @DomainResourceAuth(action = SpiAction.DELETE)
    public Object delete(@PathVariable String resource, @PathVariable Long id,
                         @RequestAction(action = "DELETE_BY_ID") DeleteAction deleteAction,
                         IUser<?> user) {
        return processResult(deleteAction.delete(id, user));
    }


    /**
     * 创建
     */
    @PostMapping
    @Validated({IView.Create.class})
    @DomainResourceAuth(action = SpiAction.CREATE)
    public Object create(@PathVariable String resource,
                         @Validated({IView.Create.class}) @Valid @RequestEntity(view = IView.Create.class) Object requestEntity,
                         @RequestAction(action = "CREATE") InsertAction insertAction,
                         IUser<?> user) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> entity={}", Json.toJson(requestEntity));
        }

        try {
            setFinalContext(requestEntity);
            return insertAction.insert(requestEntity, user);
        } finally {
            clearFinalContext(requestEntity);
        }
    }

    /**
     * 更新（覆盖）
     *
     * @param resource
     * @param id
     * @param requestEntity
     * @param updateAction
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    @Validated(IView.Update.class)
    @DomainResourceAuth(action = SpiAction.UPDATE)
    public Object update(@PathVariable String resource, @PathVariable Long id,
                         @Valid @RequestEntity(view = IView.Update.class) Object requestEntity,
                         @RequestAction(action = "UPDATE_BY_ID") UpdateAction updateAction,
                         IUser<?> user) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> entity={}", Json.toJson(requestEntity));
        }

        try {
            setFinalContext(requestEntity);

            if (requestEntity instanceof IEntity<?> entity) {
                return processResult(updateAction.update(id, false, entity, user));
            }

            throw new BadRequestException("unsupported update requestEntity of " + requestEntity);
        } finally {
            clearFinalContext(requestEntity);
        }

    }

    /**
     * 更新（有选择地）
     *
     * @param resource
     * @param id
     * @param requestEntity
     * @param updateAction
     * @param user
     * @return
     */
    @PatchMapping("/{id}")
    @Validated(IView.Patch.class)
    @DomainResourceAuth(action = SpiAction.UPDATE)
    public Object patch(@PathVariable String resource, @PathVariable Long id,
                        @Valid @RequestEntity(view = IView.Update.class) Object requestEntity,
                        @RequestAction(action = "UPDATE_BY_ID") UpdateAction updateAction,
                        IUser<?> user) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> entity={}", Json.toJson(requestEntity));
        }
        try {
            setFinalContext(requestEntity);

            if (requestEntity instanceof IEntity<?> entity) {
                return processResult(updateAction.update(id, true, entity, user));
            }

            throw new BadRequestException("unsupported update requestEntity of " + requestEntity);
        } finally {
            clearFinalContext(requestEntity);
        }
    }

    /**
     * 修改状态
     *
     * @param resource
     * @param id
     * @param status
     * @param updateAction
     * @param user
     * @param domainService
     * @return
     */
    // status
    @PatchMapping("/{id}/status")
    @DomainResourceAuth(action = SpiAction.UPDATE_STATUS)
    public Object status(@PathVariable String resource, @PathVariable Long id, @RequestParam String status,
                         @RequestAction(action = "UPDATE_BY_ID#status") UpdateAction updateAction,
                         IUser<?> user, DomainService<Long, IEntity<Long>, IUser<?>> domainService) {

        if (logger.isDebugEnabled()) {
            logger.debug("==> status={}", status);
        }

        Class<IEntity<Long>> entityClass = domainService.entityClass();

        if (!IStatus.class.isAssignableFrom(entityClass)) {
            throw new BadRequestException("resource is not supports status");
        }

        Class<? extends IEnum<?>> statusClass =
                (Class<? extends IEnum<?>>) ResolvableType.forClass(entityClass).as(IStatus.class).resolveGeneric();
        final IEnum<?> statusValue = Arrays.stream(statusClass.getEnumConstants())
                .filter(it -> it.getCode().toString().equals(status))
                .findFirst().orElse(null);


        if (Objects.isNull(statusValue)) {
            throw new BadRequestException("not status of " + status);
        }

        return processResult(updateAction.update(id, null, statusValue, user));

    }

    /**
     * 审核
     *
     * @param resource
     * @param id           the id of audit entity.
     * @param auditValue   the audit value.
     * @param updateAction the update action.
     * @param user         the operator.
     * @return
     * @see #cancel(String, Long, String, UpdateAction, IUser)
     */
    @PatchMapping("/{id}/audit")
    @DomainResourceAuth(action = SpiAction.UPDATE_AUDIT_STATUS)
    public Object audit(@PathVariable String resource, @PathVariable Long id, @Valid @RequestBody AuditValue auditValue,
                        @RequestAction(action = "UPDATE_BY_ID#audit-status") UpdateAction updateAction,
                        IUser<?> user) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> auditValue={}", Json.toJson(auditValue));
        }
        if (Objects.equals(auditValue.getStatus(), IAudit.AuditStatus.CANCELED)) {
            throw new BadRequestException("审核不能撤销");
        }
        return processResult(updateAction.update(id, null, auditValue, user));
    }

    /**
     * 取消
     *
     * @param resource
     * @param id
     * @param content
     * @param updateAction
     * @param user
     * @return
     */
    @PatchMapping("/{id}/cancel")
    @DomainResourceAuth(action = SpiAction.UPDATE_AUDIT_STATUS)
    public Object cancel(@PathVariable String resource, @PathVariable Long id, @RequestParam String content,
                         @RequestAction(action = "UPDATE_BY_ID#audit-status") UpdateAction updateAction,
                         IUser<?> user) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> content={}", content);
        }
        final AuditValue auditValue = new AuditValue();
        auditValue.setStatus(IAudit.AuditStatus.CANCELED);
        auditValue.setContent(content);
        return processResult(updateAction.update("audit-status", id, auditValue, user));
    }


    // lock

    /**
     * 锁定
     *
     * @param resource
     * @param id
     * @param updateAction
     * @param user
     * @return
     * @see org.ifinalframework.core.ILock
     * @see #unlock(String, Long, UpdateAction, IUser)
     */
    @PatchMapping("/{id}/lock")
    @DomainResourceAuth(action = SpiAction.UPDATE_LOCKED)
    public Object lock(@PathVariable String resource, @PathVariable Long id,
                       @RequestAction(action = "UPDATE_BY_ID#locked") UpdateAction updateAction,
                       IUser<?> user) {
        return processResult(updateAction.update("locked", id, false, true, user));
    }

    /**
     * 解锁
     *
     * @param resource
     * @param id
     * @param updateAction
     * @param user
     * @return
     * @see #lock(String, Long, UpdateAction, IUser)
     */
    @PatchMapping("/{id}/unlock")
    @DomainResourceAuth(action = SpiAction.UPDATE_LOCKED)
    public Object unlock(@PathVariable String resource, @PathVariable Long id,
                         @RequestAction(action = "UPDATE_BY_ID#locked") UpdateAction updateAction,
                         IUser<?> user) {
        return processResult(updateAction.update("unlocked", id, true, false, user));
    }


    /**
     * update yn
     *
     * @param resource
     * @param id           the id of entity.
     * @param yn           the yn value.
     * @param updateAction the update action.
     * @param user         the operator.
     * @return
     * @see #disable(String, Long, UpdateAction, IUser)
     * @see #enable(String, Long, UpdateAction, IUser)
     */
    // yn
    @PatchMapping("/{id}/yn")
    @DomainResourceAuth(action = SpiAction.UPDATE_YN)
    public Object yn(@PathVariable String resource, @PathVariable Long id, @RequestParam YN yn,
                     @RequestAction(action = "UPDATE_BY_ID#yn") UpdateAction updateAction,
                     IUser<?> user) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> yn={}", yn);
        }

        YN current = null;
        switch (yn) {
            case YES -> current = YN.NO;
            case NO -> current = YN.YES;
            default -> throw new BadRequestException("不支持的操作");
        }

        return processResult(updateAction.update("yn", id, current, yn, user));
    }

    /**
     * @param resource
     * @param id
     * @param updateAction
     * @param user
     * @return
     * @see #yn(String, Long, YN, UpdateAction, IUser)
     */
    @PutMapping("/{id}/disable")
    @DomainResourceAuth(action = SpiAction.UPDATE_YN)
    public Object disable(@PathVariable String resource, @PathVariable Long id,
                          @RequestAction(action = "UPDATE_BY_ID#yn") UpdateAction updateAction,
                          IUser<?> user) {
        return yn(resource, id, YN.YES, updateAction, user);
    }

    /**
     * @param resource
     * @param id
     * @param updateAction
     * @param user
     * @return
     * @see #yn(String, Long, YN, UpdateAction, IUser)
     */
    @PutMapping("/{id}/enable")
    @DomainResourceAuth(action = SpiAction.UPDATE_YN)
    public Object enable(@PathVariable String resource, @PathVariable Long id,
                         @RequestAction(action = "UPDATE_BY_ID#yn") UpdateAction updateAction,
                         IUser<?> user) {
        return yn(resource, id, YN.NO, updateAction, user);
    }

    /**
     * @param resource
     * @param id
     * @param property
     * @param value
     * @param updateAction
     * @param user
     * @return
     * @since 1.5.6
     */
    @PatchMapping("/{id}/{property}")
    public Object property(@PathVariable String resource, @PathVariable Long id, @PathVariable String property,
                           @RequestBody String value,
                           @RequestAction(action = "UPDATE_BY_ID") UpdateAction updateAction,
                           IUser<?> user) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> property={}, value={}", property, Json.toJson(value));
        }


        return processResult(updateAction.update(property, id, null, value, user));
    }

    @PatchMapping("/sort")
    @DomainResourceAuth(action = SpiAction.SORT)
    public Object sort(@PathVariable String resource, @RequestBody List<SortValue<Long>> sort,
                       IUser<?> user, DomainService<Long, IEntity<Long>, IUser<?>> domainService) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> sort={}", Json.toJson(sort));
        }
        return processResult(domainService.sort(sort, user));
    }

    // count
    @GetMapping("/count")
    @DomainResourceAuth(action = SpiAction.COUNT)
    public Object count(@PathVariable String resource, @RequestQuery(view = IView.Count.class) IQuery query,
                        IUser<?> user, DomainService<Long, IEntity<Long>, IUser<?>> domainService) {
        if (logger.isDebugEnabled()) {
            logger.debug("==> query={}", Json.toJson(query));
        }
        try {
            setFinalContext(query);
            return processResult(domainService.count(query, user));
        } finally {
            clearFinalContext(query);
        }
    }

    private <R> R processResult(R result) {
        if (logger.isDebugEnabled()) {
            logger.debug("<== {}", Json.toJson(result));
        }
        return result;
    }

    private void setFinalContext(Object value) {
        if (value instanceof ITenant tenant) {
            if (Objects.nonNull(tenant.getTenant())) {
                FinalContext.TENANT.set(tenant.getTenant());
            }
        }
    }

    private void clearFinalContext(Object value) {
        if (value instanceof ITenant) {
            FinalContext.TENANT.remove();
        }
    }


}


