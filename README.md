# Spring Boot 服务端项目

这是一个基于 Spring Boot 的服务端项目模板。

## 技术栈

- Java 11
- Spring Boot 2.7.0
- Maven
- Lombok
- SQLite

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── server/
│   │               ├── ServerApplication.java
│   │               ├── controller/
│   │               │   └── UserController.java
│   │               ├── service/
│   │               │   └── UserService.java
│   │               ├── repository/
│   │               │   └── UserRepository.java
│   │               ├── entity/
│   │               │   └── User.java
│   │               └── config/
│   │                   ├── SQLiteConfig.java
│   │                   └── SQLiteDialect.java
│   └── resources/
│       └── application.yml
└── test/
    └── java/
```

## 数据库设计

### 用户表 (users)

| 字段名   | 类型    | 说明     |
| -------- | ------- | -------- |
| id       | bigint  | 主键     |
| username | varchar | 用户姓名 |
| phone    | varchar | 手机号   |

## 本地运行项目

1. 确保已安装 Java 11 和 Maven
2. 在项目根目录执行：
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. 使用 API 接口测试应用

## 在 Render 上部署

本项目已配置为可在 Render 上轻松部署：

1. 注册 [Render](https://render.com/) 账号
2. 创建新的 Web Service
3. 连接到 GitHub 仓库
4. 使用以下设置：
   - 环境: Java
   - 构建命令: `mvn clean package -DskipTests`
   - 启动命令: `java -jar target/server-1.0-SNAPSHOT.jar --spring.profiles.active=prod`
5. 添加磁盘存储:
   - 名称: data
   - 挂载路径: /data
   - 大小: 1GB

或者，您可以使用项目中的 `render.yaml` 文件进行一键部署。

## API 接口

### 用户管理

- `POST /api/users` - 创建新用户
- `GET /api/users` - 获取所有用户
- `GET /api/users/{id}` - 根据 ID 获取用户
- `GET /api/users/username/{username}` - 根据用户名精确查询用户
- `GET /api/users/search?name={name}` - 根据姓名模糊查询用户
- `GET /api/users/phone/{phone}` - 根据手机号获取用户
- `PUT /api/users/{id}` - 更新用户信息
- `DELETE /api/users/{id}` - 删除用户
