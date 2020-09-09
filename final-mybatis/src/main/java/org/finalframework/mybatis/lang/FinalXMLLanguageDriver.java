

package org.finalframework.mybatis.lang;


import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.PropertyParser;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.Configuration;
import org.finalframework.mybatis.builder.xml.XMLMapperEntityResolver;
import org.finalframework.mybatis.scripting.xmltags.XMLScriptBuilder;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-18 15:46:59
 * @since 1.0
 */
public class FinalXMLLanguageDriver extends RawLanguageDriver {
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        return new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
    }

    public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }

    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        if (script.startsWith("<script>")) {
            XPathParser parser = new XPathParser(script, false, configuration.getVariables(), new XMLMapperEntityResolver());
            return this.createSqlSource(configuration, parser.evalNode("/script"), parameterType);
        } else {
            script = PropertyParser.parse(script, configuration.getVariables());
            TextSqlNode textSqlNode = new TextSqlNode(script);
            return (SqlSource) (textSqlNode.isDynamic() ? new DynamicSqlSource(configuration, textSqlNode) : new RawSqlSource(configuration, script, parameterType));
        }
    }


}

