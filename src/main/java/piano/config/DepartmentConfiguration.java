package piano.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(basePackages = {"piano.repository.department"},
        entityManagerFactoryRef = "departmentEntityManager",
        transactionManagerRef = "departmentTransactionManager")
public class DepartmentConfiguration {

    @Autowired
    private Environment env;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean departmentEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("piano.model.department");
        em.setPersistenceUnitName("departmentEntityManager");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", env.getProperty("department.datasource.hibernate.dialect"));
        properties.put("hibernate.show-sql", env.getProperty("department.datasource.jdbc.show-sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        //properties.put("spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults", false);
        //properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
        properties.put("hibernate.jdbc.lob.non_contextual_creation", true);

        em.setJpaPropertyMap(properties);
        return em;
    }


    @Bean(name = "department")
    @ConfigurationProperties("department.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name="departmentTransactionManager")
    @Autowired
    DataSourceTransactionManager departmentTransactionManager(@Qualifier("department") DataSource datasource) {
        return new DataSourceTransactionManager(datasource);
    }

    @Primary
    @Bean
    public PlatformTransactionManager departmentTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(departmentEntityManager().getObject());
        return transactionManager;
    }
}