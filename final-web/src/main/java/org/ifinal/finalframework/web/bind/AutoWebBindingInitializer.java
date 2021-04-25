package org.ifinal.finalframework.web.bind;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

/**
 * AutoWebBindingInitializer.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class AutoWebBindingInitializer implements WebBindingInitializer {

    @Override
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
