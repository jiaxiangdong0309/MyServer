#!/bin/bash
set -e

# 创建数据目录（如果不存在）
mkdir -p /data
echo "数据目录: /data"

# 设置环境变量
export DATA_DIR=/data

# 启动应用
echo "启动应用..."
java -Dserver.port=${PORT:-8080} \
     -DDATA_DIR=$DATA_DIR \
     -jar target/server-1.0-SNAPSHOT.jar \
     --spring.profiles.active=prod