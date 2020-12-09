package org.ifinal.finalframework.monitor.action;

import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.monitor.annotation.ActionMonitor;
import org.ifinal.finalframework.monitor.annotation.MonitorLevel;

import java.util.Map;

/**
 * Action 上下文，描述一个 Action 的动作
 *
 * @author likly
 * @version 1.0.0
 * @see ActionMonitor
 * @since 1.0.0
 */
@Getter
@Setter
public class Action {

    /**
     * 名称
     *
     * @see ActionMonitor#name()
     * @see ActionMonitor#value()
     */
    private String name;
    /**
     * 类型
     *
     * @see ActionMonitor#type()
     */
    private String type;
    /**
     * 动作
     *
     * @see ActionMonitor#code()
     */
    private String code;
    /**
     * 级别
     *
     * @see ActionMonitor#level()
     */
    private MonitorLevel level;
    /**
     * 目标
     *
     * @see ActionMonitor#target()
     */
    private Object target;
    /**
     * 属性
     */
    private Map<String, Object> attributes;
    /**
     * 异常
     */
    private Throwable exception;
    /**
     * 追踪
     */
    private String trace;
    /**
     * 时间戳
     */
    private Long timestamp;
}
