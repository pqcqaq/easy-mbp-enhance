#!/bin/bash

# 定义变量
IMAGE_NAME="easy-mbp-enhance"
LOCAL_IMAGE_DIR="/etc/images"  # 本地映射目录

# 切换到指定目录
cd /root/workspace/whitehorse-shop

# 停止和删除容器
docker stop $IMAGE_NAME
docker rm $IMAGE_NAME

# 删除镜像
docker rmi $IMAGE_NAME

# 构建镜像
docker build -f ./Dockerfile -t $IMAGE_NAME .

# 运行容器
# 运行容器
docker run -d --name $IMAGE_NAME -p 8090:8090 -v $LOCAL_IMAGE_DIR:/tmp/imgs  --network=1panel-network $IMAGE_NAME
