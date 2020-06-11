package org.finalframework.mybatis.sql.provider;


import java.util.Collection;
import java.util.Map;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.annotation.Version;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;
import org.finalframework.data.query.UpdateSetOperation;
import org.finalframework.mybatis.scripting.builder.TrimNodeBuilder;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.finalframework.mybatis.sql.ScriptMapperHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <pre>
 *     <code>
 *         <script>
 *             <trim prefix="UPDATE">
 *                  ${table}
 *             </trim>
 *             <set>
 *
 *             </set>
 *             <where>
 *                 id in
 *                 <foreach collections="ids" item="id" open="(" close=")>#{id}</foreach>"
 *             </where>
 *         </script>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-04-15 00:23:52
 * @see org.finalframework.mybatis.mapper.AbsMapper#update(String, Class, IEntity, Update, boolean, Collection, Query)
 * @since 1.0
 */
public class UpdateSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    /**
     * @see org.finalframework.mybatis.mapper.AbsMapper#update(String, Class, IEntity, Update, boolean, Collection,
     * Query)
     */
    public String update(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(Node script, Document document, Map<String, Object> parameters, ProviderContext context) {

        final Object ids = parameters.get("ids");
        final Object query = parameters.get("query");
        final Class<?> view = (Class<?>) parameters.get("view");
        final boolean selective = Boolean.TRUE.equals(parameters.get("selective"));

        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);

        final ScriptMapperHelper helper = new ScriptMapperHelper(document);

        /*
         * <trim prefix="UPDATE">
         *      ${table}
         * </trim>
         */
        final Element update = helper.trim().prefix("UPDATE").build();
        update.appendChild(helper.table());
        script.appendChild(update);

        Element set = document.createElement("set");

        if (parameters.containsKey("entity") && parameters.get("entity") != null) {
            properties.stream()
                    .filter(property -> property.isWriteable() && property.hasView(view))
                    .forEach(property -> {

                        final StringBuilder builder = new StringBuilder();
                        builder.append(property.getColumn()).append(" = ");
                        builder.append("#{entity.").append(property.getPath());

                        builder.append(",javaType=").append(property.getType().getCanonicalName());

                        final Class<? extends TypeHandler> typeHandler = getTypeHandler(property);
                        if (typeHandler != null) {
                            builder.append(",typeHandler=").append(typeHandler.getCanonicalName());
                        }

                        builder.append("},");
                        final Node value = helper.cdata(builder.toString());

                        final String test = helper.formatTest("entity", property.getPath(), selective);
                        final Element trim = helper.trim().build();

                        if (test == null) {
                            trim.appendChild(value);
                        } else {
                            final Element ifNotNull = document.createElement("if");
                            ifNotNull.setAttribute("test", test);
                            ifNotNull.appendChild(value);
                            trim.appendChild(ifNotNull);
                        }
                        set.appendChild(trim);

                    });
        } else if (parameters.containsKey("update") && parameters.get("update") != null) {
            final Update updates = (Update) parameters.get("update");
            int index = 0;
            for (UpdateSetOperation updateSetOperation : updates) {
                updateSetOperation.apply(set, String.format("update[%d]", index++));
            }
        }

        properties.stream()
                .filter(property -> property.getProperty().hasAnnotation(Version.class))
                .findFirst()
                .ifPresent(property -> {
                    final Node version = helper.trim().build();
                    version.appendChild(helper.cdata(String.format("%s = %s + 1", property.getColumn(), property.getColumn())));
                    set.appendChild(version);
                });

        script.appendChild(set);

        if (ids != null) {
            script.appendChild(where(document, whereIdsNotNull(document)));
        } else if (query instanceof Query) {
            ((Query) query).apply(script, "query");
        }


    }


}

