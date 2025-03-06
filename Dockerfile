# 使用Maven构建阶段
FROM maven:3.8-openjdk-11 AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# 使用Java 11作为运行阶段的基础镜像
FROM openjdk:11-slim

# 设置工作目录
WORKDIR /app

# 从构建阶段复制构建好的jar文件
COPY --from=build /app/target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 设置时区
ENV TZ=Asia/Shanghai

# 运行应用
ENTRYPOINT ["java","-jar","/app/app.jar"]