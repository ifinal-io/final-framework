package com.ilikly.finalframework.logging.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-12 17:30:43
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class Logger {
    private final String name;
    private final String level;


}
