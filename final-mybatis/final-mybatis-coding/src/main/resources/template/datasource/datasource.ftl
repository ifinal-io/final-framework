package ${package};

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = {${basePackages}}, sqlSessionTemplateRef = "${sqlSessionTemplate}", sqlSessionFactoryRef = "${sqlSessionFactory}")
public class ${name} {

    public static final String TRANSACTION_MANAGER = "${transactionManager}";

    @Bean
    @ConfigurationProperties(prefix = "${prefix}")
    public DataSource ${dataSource}() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSourceTransactionManager ${transactionManager}() {
        return new DataSourceTransactionManager(${dataSource}());
    }

    @Bean
    public SqlSessionFactory ${sqlSessionFactory}() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(${dataSource}());
        <#if mapperLocations??  && mapperLocations != "">
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("${mapperLocations}"));
        </#if>
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate ${sqlSessionTemplate}() throws Exception {
        return new SqlSessionTemplate(${sqlSessionFactory}());
    }

}
