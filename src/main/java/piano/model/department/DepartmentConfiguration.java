package piano.model.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import piano.model.ModelUtils;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "piano.model.department",
            entityManagerFactoryRef = "departmentEntityManagerFactory")
             // transactionManagerRef = "transactionManager")
@EntityScan(value = "piano.model.department")
public class DepartmentConfiguration {

    private static final String PIANO_MODEL_DEPARTMENT = "piano.model.department";
    public static final String POSTGRESQL_DIALECT = "PostgreSQLDialect";

    @Bean(name = "entityManagerFactory")
    @Autowired
    public LocalContainerEntityManagerFactoryBean departmentEntityManagerFactory() {
        return ModelUtils.entityManager(dataSource(), PIANO_MODEL_DEPARTMENT, POSTGRESQL_DIALECT);
    }

    @Bean(name = "department")
    @Autowired
    @ConfigurationProperties("department")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

/*    @Bean(name="transactionManager")
    @Autowired
    DataSourceTransactionManager transactionManager(@Qualifier("department") DataSource datasource) {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name="transactionManager")
    @Autowired
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(departmentEntityManagerFactory().getObject());
        return transactionManager;
    }*/
}