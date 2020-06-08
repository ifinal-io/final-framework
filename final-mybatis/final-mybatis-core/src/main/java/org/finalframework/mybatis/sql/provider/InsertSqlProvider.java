package org.finalframework.mybatis.sql.provider;


import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.Query;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.finalframework.mybatis.sql.ScriptMapperHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 *     <code>
 *         <script>
 *             <trim prefix="INSERT INFO | INSERT IGNORE INTO | REPLACE INTO">
 *                 ${table}
 *             </trim>
 *             <trim prefix="(" suffix=")">
 *                  columns
 *             </trim>
 *             <foreach collection="list" item="entity" open="VALUES">
 *
 *             </foreach>
 *         </script>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-04-15 00:23:52
 * @see org.finalframework.mybatis.mapper.AbsMapper#insert(String, Class, boolean, Collection)
 * @see org.finalframework.mybatis.mapper.AbsMapper#replace(String, Class, Collection)
 * @see org.finalframework.mybatis.mapper.AbsMapper#save(String, Class, Collection)
 * @since 1.0
 */
public class InsertSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    /**
     * @param context
     * @param parameters
     * @return
     * @see org.finalframework.mybatis.mapper.AbsMapper#insert(String, Class, boolean, Collection)
     */
    public String insert(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    /**
     * @param context
     * @param parameters
     * @return
     * @see org.finalframework.mybatis.mapper.AbsMapper#replace(String, Class, Collection)
     */
    public String replace(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    /**
     * @param context
     * @param parameters
     * @return
     * @see org.finalframework.mybatis.mapper.AbsMapper#save(String, Class, Collection)
     */
    public String save(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(Node script, Document document, Map<String, Object> parameters, ProviderContext context) {
        final String insertPrefix = getInsertPrefix(context.getMapperMethod(), Boolean.TRUE.equals(parameters.get("ignore")));

        Class<?> view = (Class<?>) parameters.get("view");


        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);

        final ScriptMapperHelper helper = new ScriptMapperHelper(document);

        /*
         *  <trim prefix="INSERT INFO | INSERT IGNORE INTO | REPLACE INTO">
         *      ${table}
         *  </trim>
         */
        final Element insert = helper.trim().prefix(insertPrefix).build();
        insert.appendChild(helper.cdata("${table}"));
        script.appendChild(insert);

        /*
         * <trim prefix="(" suffix=")">
         *      columns
         * </trim>
         */
        final Element columns = helper.trim().prefix("(").suffix(")").build();
        columns.appendChild(helper.cdata(
                properties.stream()
                        .filter(property -> property.isWriteable() && property.hasView(view))
                        .map(QProperty::getColumn)
                        .collect(Collectors.joining(","))
        ));
        script.appendChild(columns);


        final Element foreach = helper.foreach().collection("list").item("entity").open("VALUES").build();

        properties.stream()
                .filter(property -> property.isWriteable() && property.hasView(view))
                .forEach(property -> {
                    foreach.appendChild(helper.bind(property.getName(), helper.formatBindValue("entity", property.getPath())));
                });

        final Element values = helper.trim().prefix("(").suffix(")").build();

        values.appendChild(helper.cdata(
                properties.stream()
                        .filter(property -> property.isWriteable() && property.hasView(view))
                        .map(property -> {
                            final StringBuilder builder = new StringBuilder();

                            builder.append("#{").append(property.getName());
                            // javaType
                            builder.append(",javaType=").append(property.getType().getCanonicalName());
                            // typeHandler
                            final Class<? extends TypeHandler> typeHandler = getTypeHandler(property);
                            if (typeHandler != null) {
                                builder.append(",typeHandler=").append(typeHandler.getCanonicalName());
                            }
                            builder.append("}");

                            return builder.toString();
                        })
                        .collect(Collectors.joining(","))));

        foreach.appendChild(values);


        script.appendChild(foreach);


    }


    private String getInsertPrefix(Method method, boolean ignore) {
        switch (method.getName()) {
            case "insert":
                return ignore ? "INSERT IGNORE INTO" : "INSERT INTO";
            case "replace":
                return "REPLACE INTO";
            case "save":
                return "INSERT INFO";
        }
        throw new IllegalArgumentException();
    }


}

