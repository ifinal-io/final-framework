package org.ifinal.finalframework.context.exception.result;


import java.io.Serializable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class InvokeContext implements Serializable {
    private static final long serialVersionUID = -5632242854340769286L;

    private String trace;

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
}
