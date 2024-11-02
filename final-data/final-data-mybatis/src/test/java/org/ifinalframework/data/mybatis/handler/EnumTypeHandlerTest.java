package org.ifinalframework.data.mybatis.handler;

import org.ifinalframework.data.annotation.YN;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * EnumTypeHandlerTest.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
@ExtendWith(MockitoExtension.class)
class EnumTypeHandlerTest {

    private final TypeHandler<YN> handler = new EnumTypeHandler<>(YN.class);

    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;
    @Mock
    private CallableStatement cs;

    @Test
    @SneakyThrows
    void setNullParameter() {
        handler.setParameter(ps, 1, null, JdbcType.INTEGER);
        verify(ps, only()).setNull(anyInt(), anyInt());
    }

    @Test
    @SneakyThrows
    void setNonNullParameter() {
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        assertDoesNotThrow(() -> handler.setParameter(ps, 1, YN.YES, JdbcType.INTEGER));
        verify(ps, only()).setObject(anyInt(), captor.capture());
        assertEquals(YN.YES.getCode(), captor.getValue());
    }

    @Test
    @SneakyThrows
    void getNullableResult() {
        when(rs.getString(1)).thenReturn(YN.YES.getCode().toString());
        YN result = handler.getResult(rs, 1);
        assertEquals(YN.YES, result);

        when(rs.getString("yn")).thenReturn(YN.YES.getCode().toString());
        result = handler.getResult(rs, "yn");
        assertEquals(YN.YES, result);

        when(cs.getString(1)).thenReturn(YN.YES.getCode().toString());
        assertEquals(YN.YES, handler.getResult(cs, 1));

    }

}