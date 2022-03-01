/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.web.bind;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;

import java.beans.PropertyEditor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author likly
 * @version 1.3.0
 **/
@ExtendWith(MockitoExtension.class)
class AutoWebBindingInitializerTest {

    @Mock
    private WebDataBinder webDataBinder;

    @Test
    void initBinder() {

        ArgumentCaptor<PropertyEditor> captor = ArgumentCaptor.forClass(PropertyEditor.class);


        AutoWebBindingInitializer initializer = new AutoWebBindingInitializer();
        initializer.initBinder(webDataBinder);

        verify(webDataBinder, only()).registerCustomEditor(any(), captor.capture());
        PropertyEditor value = captor.getValue();
        assertTrue(value instanceof StringTrimmerEditor);
    }
}