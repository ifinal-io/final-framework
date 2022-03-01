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

package org.ifinalframework.web.servlet.response.advice;

import lombok.SneakyThrows;
import org.ifinalframework.core.result.Responsible;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author likly
 * @version 1.3.0
 **/
@ExtendWith(MockitoExtension.class)
class ResponsibleResponseBodyAdviceTest {

    @InjectMocks
    private ResponsibleResponseBodyAdvice responsibleResponseBodyAdvice;

    @Mock
    private Responsible responsible;
    @Mock
    private ServerHttpRequest request;
    @Mock
    private ServerHttpResponse response;


    @Test
    @SneakyThrows
    void doBeforeBodyWrite() {

        when(responsible.getStatus()).thenReturn(404);

        when(request.getURI()).thenReturn(new URI(""));

        ArgumentCaptor<HttpStatus> captor = ArgumentCaptor.forClass(HttpStatus.class);

        responsibleResponseBodyAdvice.beforeBodyWrite(responsible, null, null, null, request, response);

        verify(response, only()).setStatusCode(captor.capture());

        HttpStatus httpStatus = captor.getValue();
        assertEquals(404, httpStatus.value());


    }
}