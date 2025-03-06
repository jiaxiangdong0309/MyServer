package com.example.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ServerApplication.class);
        Environment env = app.run(args).getEnvironment();

        String protocol = "http";
        String port = env.getProperty("server.port", "8080");
        String contextPath = env.getProperty("server.servlet.context-path", "");

        logger.info("\n----------------------------------------------------------\n\t" +
                "应用程序 '{}' 已启动! 访问地址:\n\t" +
                "本地: \t{}://localhost:{}{}\n\t" +
                "外部: \t{}://{}:{}{}\n\t" +
                "环境: \t{}\n\t" +
                "数据库URL: \t{}\n" +
                "----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            port,
            contextPath,
            protocol,
            "0.0.0.0",
            port,
            contextPath,
            env.getActiveProfiles().length > 0 ? String.join(", ", env.getActiveProfiles()) : "默认",
            env.getProperty("spring.datasource.url")
        );
    }
}