# 使用Java 11作为基础镜像
FROM openjdk:11-slim

# 设置工作目录
WORKDIR /app

# 复制构建好的jar文件
COPY target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 设置时区
ENV TZ=Asia/Shanghai

# 运行应用
ENTRYPOINT ["java","-jar","/app/app.jar"]