package com.fireyao;

import com.alibaba.druid.pool.DruidDataSource;
import com.fireyao.aop.EncoreableIntroducer;
import com.fireyao.aop.Minstrel;
import com.fireyao.bean.*;
import org.hibernate.spatial.dialect.postgis.PostgisDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;

/**
 * 相当于applicationContext.xml
 * Created by lly on 2017/8/31
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = {"com.fireyao.repository"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@PropertySource(value = {"classpath:db.properties", "classpath:hibernate.properties", "classpath:app.properties"})
@ComponentScan(basePackages = "com.fireyao",
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION, value = EnableWebMvc.class
                )})
@EnableAspectJAutoProxy(proxyTargetClass = true)
/**
 *   proxyTargetClass = true ==> 使用cglib代理
 *   proxyTargetClass = false(默认) ==> 使用JDK代理
 */
public class RootConfig {


    @Autowired
    Environment environment;

    @Value("${disc.title}")
    private String TITLE;

    @Value("${disc.artist}")
    private String ARTIST;

    @Value(value = "${db.driver:org.postgresql.Driver}")
    private String DRIVERCLASSNAME;

    @Value("${db.username}")
    private String USERNAME;

    @Value("${db.password}")
    private String PASSWORD;

    @Value("${db.jdbcURL}")
    private String URL;

    @Value("${hibernate.hbm2dll.create_namespaces}")
    private String CREATE_NAMESPACES;

    @Value("${hibernate.hbm2ddl.auto}")
    private String HBM2DDL_AUTO;

    @Value("${hibernate.show_sql}")
    private String SHOW_SQL;

    @Value("${hibernate.format_sql}")
    private String FORMAT_SQL;

    @Value("${hibernate.generate_statistics}")
    private String GENERATE_STATISTICS;

    @Bean(name = "dataSource")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(DRIVERCLASSNAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        /*  配置初始化大小、最小、最大*/
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(20);
        /* 配置获取连接等待超时的时间*/
        dataSource.setMaxWait(30000);
        /*配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒*/
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        /*配置一个连接在池中最小生存的时间，单位是毫秒*/
        dataSource.setMinEvictableIdleTimeMillis(300000);
        /*申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效*/
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery("select 1");
        return dataSource;
    }


    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DruidDataSource dataSource,
                                                                       HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManagerFactory.setPackagesToScan("com.fireyao.domain");

        /*指定JPA属性；如Hibernate中指定是否显示SQL的是否显示、方言等*/
        Map<String, Object> jpaProp = new HashMap();
        jpaProp.put("hibernate.dialect", new PostgisDialect());
        jpaProp.put("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
        jpaProp.put("hibernate.show_sql", SHOW_SQL);
        jpaProp.put("hibernate.generate_statistics", GENERATE_STATISTICS);
        jpaProp.put("hibernate.format_sql", FORMAT_SQL);
        jpaProp.put("hibernate.hbm2dll.create_namespaces", CREATE_NAMESPACES);
        entityManagerFactory.setJpaPropertyMap(jpaProp);
        return entityManagerFactory;
    }

    /**
     * 事务管理器
     *
     * @param entityManagerFactory
     * @return
     */
    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }


    @Bean
    public EncoreableIntroducer encoreableIntroducer() {
        return new EncoreableIntroducer();
    }

    @Bean
    public Minstrel minstrel() {
        return new Minstrel();
    }

    @Bean
    public Quest quest() {
        return new SlayDragonQuest();
    }

    @Bean(name = "knight")
    public Knights knights() {
        return new BraveKnight(quest());
    }

    @Bean(name = "express")
    public ExpressiveBean expressiveBean() {
//        return new ExpressiveBean(environment.getProperty("disc.title"),
//                environment.getProperty("disc.artist"));
        return new ExpressiveBean(TITLE, ARTIST);
    }
}
