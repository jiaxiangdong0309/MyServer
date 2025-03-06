#!/bin/bash
set -e

# 确保Maven Wrapper有执行权限
chmod +x mvnw

# 使用Maven Wrapper构建项目
./mvnw clean package -DskipTests

echo "构建完成！"