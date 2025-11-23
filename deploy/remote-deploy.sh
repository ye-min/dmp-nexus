#!/bin/bash
# 远程服务器上的部署脚本
set -eo pipefail

# 设置变量
DEPLOY_PATH="/workspace/dmp-nexus"
IMAGES_PATH="$DEPLOY_PATH/image"

readonly ENV_FILE=".env"

extract_version() {
    local var_name=$1
    local line
    line=$(grep -m1 -E "^${var_name}=" "$ENV_FILE") || {
        echo "错误：${var_name} 未在 $ENV_FILE 中定义"
        exit 1
    }
    echo "$line" | cut -d= -f2- | sed -e 's/^["'\'']//' -e 's/["'\'']$//'
}

REGISTRY_VERSION=$(extract_version "REGISTRY_VERSION")
AUTH_VERSION=$(extract_version "AUTH_VERSION")
GATEWAY_VERSION=$(extract_version "GATEWAY_VERSION")
USERS_VERSION=$(extract_version "USERS_VERSION")
ARCHIVE_VERSION=$(extract_version "ARCHIVE_VERSION")

# 加载 Docker 镜像
echo "Loading Docker images..."
docker load -i $IMAGES_PATH/registry-$REGISTRY_VERSION.tar
docker load -i $IMAGES_PATH/auth-$AUTH_VERSION.tar
docker load -i $IMAGES_PATH/users-$USERS_VERSION.tar
docker load -i $IMAGES_PATH/gateway-$GATEWAY_VERSION.tar
docker load -i $IMAGES_PATH/archive-$ARCHIVE_VERSION.tar

# 更新 docker-compose.yml 中的版本
#echo "Updating docker-compose.yml..."
#sed -i "s/VERSION/$VERSION/g" $DEPLOY_PATH/docker-compose.yml

# 停止并删除旧容器
echo "Stopping old containers..."
cd $DEPLOY_PATH
docker compose down

sudo rm -f "$DEPLOY_PATH/log/registry/*.*.log"
sudo rm -f "$DEPLOY_PATH/log/auth/*.*.log"
sudo rm -f "$DEPLOY_PATH/log/users/*.*.log"
sudo rm -f "$DEPLOY_PATH/log/archive/*.*.log"
sudo rm -f "$DEPLOY_PATH/log/gateway/*.*.log"

# 启动新容器
echo "Starting new containers..."
docker compose up -d

# 清理旧镜像
echo "Cleaning up old images..."
docker image prune -f

echo "Remote deployment completed."
