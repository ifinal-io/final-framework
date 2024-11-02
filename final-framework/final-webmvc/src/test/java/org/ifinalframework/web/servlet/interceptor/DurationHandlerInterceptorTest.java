package org.ifinalframework.web.servlet.interceptor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DurationHandlerInterceptorTest {

    @InjectMocks
    private DurationHandlerInterceptor interceptor;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;


    @Test
    void preHandle() {

        Mockito.when(request.getRequestURI()).thenReturn("/helllo");
        boolean handle = interceptor.preHandle(request, response, null);
        assertTrue(handle);

    }

    @Test
    void postHandle() {
        Mockito.when(request.getRequestURI()).thenReturn("/hello");
        Mockito.when(request.getAttribute(DurationHandlerInterceptor.DURATION_START_ATTRIBUTE)).thenReturn(System.currentTimeMillis());
        interceptor.postHandle(request, response, null, null);
    }
}