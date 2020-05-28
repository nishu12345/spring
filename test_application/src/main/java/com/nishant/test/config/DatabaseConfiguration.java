package com.nishant.test.config;

import com.nishant.test.common.Constants;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.function.Supplier;

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Value("${test.db.url}")
    private String url;

    @Value("${test.db.username}")
    private String username;

    @Value("${test.db.password}")
    private String password;

    @Value("${test.db.driver}")
    private String driver;

    @Value("${test.db.dialect}")
    private String dialect;

    @Value("${test.db.show.sql}")
    private String showSql;

    @Value("${test.db.ddl.auto}")
    private String ddlAuto;

    private Supplier<Properties> hibernateProperties = () -> {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.hbm2ddl.auto", ddlAuto);
        return properties;
    };

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan(Constants.DatabaseConstants.ENTITIES_PACKAGES);
        sessionFactory.setHibernateProperties(hibernateProperties.get());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
}
