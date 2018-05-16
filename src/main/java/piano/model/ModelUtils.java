package piano.model;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ModelUtils {

    private static final Logger LOG = Logger.getLogger(ModelUtils.class.getName());
    private static final String ENTITY_MANAGER_FACTORY = "entityManagerFactory";

    public static LocalContainerEntityManagerFactoryBean entityManager(DataSource dataSource, String entityPackage, String dialect){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(entityPackage);
        LOG.info("=====================================");
        LOG.info("entityPackage = " + entityPackage);
        LOG.info("=====================================");
        em.setPersistenceUnitName(ENTITY_MANAGER_FACTORY);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(new ModelUtils().properties(dialect));
        em.getJpaPropertyMap().keySet().forEach(key -> LOG.info(" >>>  " + key + "   ==> " + em.getJpaPropertyMap().get(key)));
        return em;
    }

    private Map<String, Object> properties(String dialect){
        HashMap<String, Object> properties = new HashMap<>();
        String protoDialect = "org.hibernate.dialect.";
        properties.put("hibernate.dialect", protoDialect+dialect);
        properties.put("hibernate.show-sql", System.getProperty("spring.jpa.properties.hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.jdbc.lob.non_contextual_creation", true);
        LOG.info("Dialect: " + System.getProperty("department.datasource.hibernate.dialect"));
        return properties;
    }
}
