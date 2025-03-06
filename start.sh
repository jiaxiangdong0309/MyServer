#!/bin/bash
set -e

# 使用当前目录下的data文件夹作为数据目录
DATA_DIR=./data
echo "数据目录: $DATA_DIR"

# 设置环境变量
export DATA_DIR

# 启动应用
echo "启动应用..."
java -Dserver.port=${PORT:-8080} \
     -DDATA_DIR=$DATA_DIR \
     -jar target/server-1.0-SNAPSHOT.jar \
     --spring.profiles.active=prod