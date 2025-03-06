#!/bin/bash
set -e

# 设置环境变量
export PORT=8080
export SPRING_PROFILES_ACTIVE=prod
export DATA_DIR=/tmp/data

# 创建数据目录（如果不存在）
mkdir -p $DATA_DIR
echo "数据目录: $DATA_DIR"

# 启动应用
echo "启动应用..."
java -Dserver.port=$PORT \
     -DDATA_DIR=$DATA_DIR \
     -jar target/server-1.0-SNAPSHOT.jar \
     --spring.profiles.active=$SPRING_PROFILES_ACTIVE