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

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = {${basePackages}}, sqlSessionFactoryRef = "${sqlSessionTemplate}")
public class ${name} {
    @Bean
    @ConfigurationProperties(prefix = "${prefix}")
    public DataSource ${dataSource}() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory ${sqlSessionFactory}(@Qualifier("${dataSource}") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("${mapperLocations}"));
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate ${sqlSessionTemplate}(@Qualifier("${sqlSessionFactory}") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
