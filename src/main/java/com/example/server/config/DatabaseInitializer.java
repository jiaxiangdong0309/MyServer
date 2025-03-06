package com.example.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class DatabaseInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Bean
    public CommandLineRunner initDatabase(DataSource dataSource) {
        return args -> {
            String dbPath = databaseUrl.replace("jdbc:sqlite:", "");
            File dbFile = new File(dbPath);

            logger.info("数据库初始化开始");
            logger.info("数据库URL: {}", databaseUrl);
            logger.info("数据库文件路径: {}", dbPath);
            logger.info("数据库文件是否存在: {}", dbFile.exists());

            if (dbFile.exists()) {
                logger.info("数据库文件大小: {} 字节", dbFile.length());
            } else {
                logger.warn("数据库文件不存在，这可能会导致问题");
            }

            try (Connection connection = dataSource.getConnection()) {
                logger.info("成功连接到数据库");

                // 检查users表是否存在
                boolean tableExists = false;
                try {
                    DatabaseMetaData metaData = connection.getMetaData();
                    ResultSet tables = metaData.getTables(null, null, "users", null);
                    tableExists = tables.next();
                    logger.info("通过元数据检查: users表是否存在: {}", tableExists);
                } catch (Exception e) {
                    logger.warn("检查表是否存在时出错: {}", e.getMessage());
                }

                // 无论表是否存在，都尝试创建（如果不存在）
                logger.info("尝试创建users表（如果不存在）");
                try (Statement statement = connection.createStatement()) {
                    // 创建users表
                    statement.execute(
                        "CREATE TABLE IF NOT EXISTS users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "username TEXT NOT NULL, " +
                        "phone TEXT NOT NULL" +
                        ")"
                    );
                    logger.info("CREATE TABLE IF NOT EXISTS语句执行成功");
                }

                // 再次检查表是否存在
                try {
                    DatabaseMetaData metaData = connection.getMetaData();
                    ResultSet tables = metaData.getTables(null, null, "users", null);
                    boolean tableExistsNow = tables.next();
                    logger.info("创建后再次检查: users表是否存在: {}", tableExistsNow);

                    if (!tableExistsNow) {
                        logger.error("尝试创建表后，表仍然不存在！");
                    }
                } catch (Exception e) {
                    logger.warn("再次检查表是否存在时出错: {}", e.getMessage());
                }

                // 检查表是否创建成功并打印记录数
                try {
                    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
                    logger.info("users表中有 {} 条记录", count);

                    // 如果表是空的，可以添加一条测试数据
                    if (count == 0 && !tableExists) {
                        logger.info("添加一条测试数据到users表");
                        jdbcTemplate.update(
                            "INSERT INTO users (username, phone) VALUES (?, ?)",
                            "测试用户", "13800138000"
                        );
                        logger.info("测试数据添加成功");
                    }
                } catch (Exception e) {
                    logger.error("查询或操作users表失败: {}", e.getMessage());

                    // 尝试直接执行SQL查询
                    try (Statement statement = connection.createStatement()) {
                        ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='users'");
                        if (rs.next()) {
                            logger.info("通过sqlite_master查询确认users表存在");
                        } else {
                            logger.error("通过sqlite_master查询确认users表不存在");
                        }
                    } catch (Exception ex) {
                        logger.error("直接查询sqlite_master失败: {}", ex.getMessage());
                    }
                }

                logger.info("数据库初始化完成");

            } catch (SQLException e) {
                logger.error("数据库初始化失败: {}", e.getMessage(), e);
                throw new RuntimeException("数据库初始化失败", e);
            }
        };
    }
}