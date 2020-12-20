package org.ifinal.finalframework.amp.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.annotation.data.AbsRecord;

/**
 * Application.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public class Application extends AbsRecord {

    @NotNull
    private Long project;

    @NotEmpty
    private String name;

    @NotEmpty
    private String code;

    @NotEmpty
    private String description;

}
