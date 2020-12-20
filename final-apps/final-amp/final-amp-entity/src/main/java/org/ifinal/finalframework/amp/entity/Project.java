package org.ifinal.finalframework.amp.entity;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.annotation.data.AbsRecord;

/**
 * Project.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public class Project extends AbsRecord {

    private static final long serialVersionUID = -7794223172837005301L;

    @NotEmpty
    private String name;

    @NotEmpty
    private String code;

    @NotEmpty
    private String description;

}
