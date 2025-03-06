#!/bin/bash
set -e

# 创建数据目录（如果不存在）
mkdir -p /data

# 启动应用
java -Dserver.port=${PORT:-8080} -jar target/server-1.0-SNAPSHOT.jar --spring.profiles.active=prod