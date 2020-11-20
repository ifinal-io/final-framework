package org.finalframework.example.dao.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.finalframework.annotation.query.Equal;
import org.finalframework.annotation.query.Or;
import org.finalframework.annotation.query.PageQuery;
import org.finalframework.example.dao.mapper.AbsPersonMapper;
import org.finalframework.example.dao.mapper.PersonMapper;
import org.finalframework.json.Json;
import org.finalframework.mybatis.sql.provider.SqlProviderHelper;

import java.util.HashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/18 10:12:12
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonQuery extends PageQuery {
    @Equal
    private String name;

    @Or
    private OrQuery orQuery;

    @Data
    static class OrQuery {
        @Equal
        private String password;
        @Equal
        private Boolean admin;
    }

    public static void main(String[] args) {

        PersonMapper mapper;


        HashMap<String, Object> parameters = new HashMap<>();
        PersonQuery query = new PersonQuery();
        query.setName("name");
        OrQuery orQuery = new OrQuery();
        orQuery.setAdmin(false);
        orQuery.setPassword("password");
        query.setOrQuery(orQuery);
        System.out.println(Json.toJson(query));

        parameters.put(SqlProviderHelper.PARAMETER_NAME_QUERY, query);
        System.out.println(SqlProviderHelper.sql(AbsPersonMapper.class, "selectIds", parameters));
    }

}
