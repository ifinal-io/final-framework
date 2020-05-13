package org.finalframework.document.api.service.converter;

import org.finalframework.document.api.controller.EntityApiController;
import org.finalframework.document.api.entity.ApiDocuments;
import org.junit.jupiter.api.Test;

/**
 * @author sli
 * @version 1.0
 * @date 2020-05-13 21:57:17
 * @since 1.0
 */
class RestController2ApiDocumentsConverterTest {

    private final RestController2ApiDocumentsConverter converter = new RestController2ApiDocumentsConverter();


    @Test
    public void testEntityApiController() {
        ApiDocuments apiDocuments = converter.convert(EntityApiController.class);
        System.out.println(apiDocuments);
    }
}