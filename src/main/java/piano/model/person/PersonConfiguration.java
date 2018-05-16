package piano.model.person;

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
@EnableJpaRepositories(basePackages = "piano.model.person",
            entityManagerFactoryRef = "personEntityManagerFactory")
             // transactionManagerRef = "personTransactionManager")
@EntityScan(value = "piano.model.person")
public class PersonConfiguration {

    private static final String PIANO_MODEL_PERSON = "piano.model.person";
    public static final String MYSQL_5_DIALECT = "MySQL5Dialect";

    @Bean(name = "entityManagerFactory")
    @Autowired
    public LocalContainerEntityManagerFactoryBean personEntityManagerFactory() {
        return ModelUtils.entityManager(dataSource(), PIANO_MODEL_PERSON, MYSQL_5_DIALECT);
    }

    @Bean(name = "person")
    @Autowired
    @ConfigurationProperties("person")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

/*    @Bean(name="transactionManager")
    @Autowired
    DataSourceTransactionManager personTransactionManager(@Qualifier ("person") DataSource datasource) {
        return new DataSourceTransactionManager(datasource);
    }*/

   /* @Bean(name="transactionManager")
    @Autowired
    public PlatformTransactionManager personTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(personEntityManagerFactory().getObject());
        return transactionManager;
    }*/
}