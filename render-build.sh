#!/usr/bin/env bash
# exit on error
set -o errexit

# 确保Maven Wrapper有执行权限
chmod +x mvnw

# 使用Maven Wrapper构建项目
./mvnw clean package -DskipTests

# 确保启动脚本有执行权限
chmod +x start.sh