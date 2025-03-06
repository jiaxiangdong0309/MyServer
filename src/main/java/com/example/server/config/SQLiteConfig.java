package com.example.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class SQLiteConfig {

    private static final Logger logger = LoggerFactory.getLogger(SQLiteConfig.class);

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Bean
    public DataSource dataSource() {
        // 确保数据库文件存在
        ensureDatabaseExists();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl(databaseUrl);
        return dataSource;
    }

    private void ensureDatabaseExists() {
        String url = databaseUrl.replace("jdbc:sqlite:", "");
        File dbFile = new File(url);

        logger.info("检查数据库文件: {}", url);

        if (dbFile.exists()) {
            logger.info("数据库文件已存在: {}", url);
        } else {
            logger.info("数据库文件不存在，将创建新文件: {}", url);

            // 确保父目录存在
            File parentDir = dbFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                logger.info("创建数据库目录: {}", parentDir.getAbsolutePath());
                parentDir.mkdirs();
            }

            // 创建空数据库文件
            try {
                // 尝试创建文件
                if (dbFile.createNewFile()) {
                    logger.info("成功创建空数据库文件: {}", url);
                } else {
                    logger.warn("无法创建数据库文件: {}", url);
                }

                // 尝试建立数据库连接以初始化SQLite数据库
                try (Connection conn = DriverManager.getConnection(databaseUrl)) {
                    if (conn != null) {
                        logger.info("成功连接到新创建的数据库");
                    }
                } catch (SQLException e) {
                    logger.error("连接到新创建的数据库失败: {}", e.getMessage(), e);
                }
            } catch (IOException e) {
                logger.error("创建数据库文件失败: {}", e.getMessage(), e);
                throw new RuntimeException("无法创建数据库文件", e);
            }
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.example.server.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true); // 启用DDL生成
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "com.example.server.config.SQLiteDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        // 确保在启动时验证和创建表结构
        properties.setProperty("javax.persistence.schema-generation.database.action", "create");
        em.setJpaProperties(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}