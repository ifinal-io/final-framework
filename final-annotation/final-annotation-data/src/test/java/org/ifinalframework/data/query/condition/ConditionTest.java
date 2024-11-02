/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.query.condition;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import org.springframework.util.StringUtils;

import org.ifinalframework.data.annotation.Metadata;
import org.ifinalframework.data.query.CriterionAttributes;
import org.ifinalframework.data.query.QueryProvider;
import org.ifinalframework.velocity.Velocities;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ConditionTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
class ConditionTest {

    protected BoundSql create(CriterionAttributes criterionAttributes){
        StringBuilder sql = new StringBuilder();

        sql.append("<script>");
        final Metadata params = new Metadata();
        params.setColumn(criterionAttributes.getColumn());
        params.setValue("value");
        params.setAndOr(criterionAttributes.getAndor());
        final String eval = Velocities.eval(criterionAttributes.getExpression(), params);
        sql.append(eval);
        sql.append("</script>");

        String script = sql.toString();

        SqlSource sqlSource = new XMLLanguageDriver().createSqlSource(new Configuration(), script, Map.class);
        return sqlSource.getBoundSql(criterionAttributes);
    }

    protected String sql(CriterionAttributes criterionAttributes){
        final BoundSql boundSql = create(criterionAttributes);
        String sql = boundSql.getSql();
        final MetaObject metaObject = new Configuration().newMetaObject(criterionAttributes);

        final SqlPreparedStatement preparedStatement = new SqlPreparedStatement(sql);

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);

            Object parameter = null;

            if (metaObject.hasGetter(parameterMapping.getProperty())) {
                parameter = metaObject.getValue(parameterMapping.getProperty());
            } else if (boundSql.hasAdditionalParameter(parameterMapping.getProperty())) {
                parameter = boundSql.getAdditionalParameter(parameterMapping.getProperty());
            }
            setParameter(preparedStatement, parameterMapping.getTypeHandler(), i, parameter, Objects.isNull(parameter) ? JdbcType.NULL : parameterMapping.getJdbcType());
        }
        return preparedStatement.toString();
    }

    @SneakyThrows
    private static void setParameter(PreparedStatement ps, TypeHandler typeHandler, int i, Object parameter, JdbcType jdbcType) {
        typeHandler.setParameter(ps, i, parameter, jdbcType);
    }

    private static class SqlPreparedStatement implements PreparedStatement {
        private String sql;

        public SqlPreparedStatement(String sql) {
            this.sql = sql;
        }

        private void setParameter(Object obj) {
            if (obj instanceof String) {
                if (((String) obj).contains("$")) {
                    obj = StringUtils.replace((String) obj, "$", "\\$");
                }
                // if we have a String, include '' in the saved value
                sql = sql.replaceFirst("\\?", "'" + obj + "'");
            } else if (obj instanceof Date) {
                sql = sql.replaceFirst("\\?", "'" + obj + "'");
            } else {
                if (obj == null) {
                    // convert null to the string null
                    sql = sql.replaceFirst("\\?", "null");
                } else {
                    // unknown object (includes all Numbers), just call toString
                    sql = sql.replaceFirst("\\?", obj.toString());
                }
            }
        }

        @Override
        public ResultSet executeQuery() throws SQLException {
            return null;
        }

        @Override
        public int executeUpdate() throws SQLException {
            return 0;
        }

        @Override
        public void setNull(int parameterIndex, int sqlType) throws SQLException {
            setParameter(null);
        }

        @Override
        public void setBoolean(int parameterIndex, boolean x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setByte(int parameterIndex, byte x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setShort(int parameterIndex, short x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setInt(int parameterIndex, int x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setLong(int parameterIndex, long x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setFloat(int parameterIndex, float x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setDouble(int parameterIndex, double x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setString(int parameterIndex, String x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setBytes(int parameterIndex, byte[] x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setDate(int parameterIndex, Date x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setTime(int parameterIndex, Time x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
            setParameter(x);
        }

        @Override
        public void clearParameters() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
            setParameter(x);
        }

        @Override
        public void setObject(int parameterIndex, Object x) throws SQLException {
            setParameter(x);
        }

        @Override
        public boolean execute() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addBatch() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setRef(int parameterIndex, Ref x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setBlob(int parameterIndex, Blob x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setClob(int parameterIndex, Clob x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setArray(int parameterIndex, Array x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public ResultSetMetaData getMetaData() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setURL(int parameterIndex, URL x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public ParameterMetaData getParameterMetaData() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setRowId(int parameterIndex, RowId x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNString(int parameterIndex, String value) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNClob(int parameterIndex, NClob value) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {

        }

        @Override
        public void setClob(int parameterIndex, Reader reader) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNClob(int parameterIndex, Reader reader) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public ResultSet executeQuery(String sql) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int executeUpdate(String sql) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getMaxFieldSize() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setMaxFieldSize(int max) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getMaxRows() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setMaxRows(int max) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setEscapeProcessing(boolean enable) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getQueryTimeout() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setQueryTimeout(int seconds) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void cancel() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clearWarnings() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setCursorName(String name) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean execute(String sql) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public ResultSet getResultSet() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getUpdateCount() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean getMoreResults() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setFetchDirection(int direction) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getFetchDirection() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setFetchSize(int rows) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getFetchSize() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getResultSetConcurrency() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getResultSetType() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addBatch(String sql) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clearBatch() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int[] executeBatch() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Connection getConnection() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean getMoreResults(int current) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public ResultSet getGeneratedKeys() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int executeUpdate(String sql, String[] columnNames) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean execute(String sql, int[] columnIndexes) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean execute(String sql, String[] columnNames) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getResultSetHoldability() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isClosed() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPoolable(boolean poolable) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isPoolable() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void closeOnCompletion() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isCloseOnCompletion() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return sql;
        }
    }

}