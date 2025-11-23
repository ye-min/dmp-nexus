#!/bin/bash
# 部署脚本：传输镜像并在远程服务器上部署

# 设置变量
SERVER_IP="remote-dmp"
SERVER_USER="root"
SERVER_PATH="/workspace/dmp-nexus"
#VERSION=$(cat ./deploy/version.txt)

# 初始化标志
TRANSFER_IMAGE=0

# 解析参数
while [[ $# -gt 0 ]]; do
    case "$1" in
        --image)
            TRANSFER_IMAGE=1
            shift
            ;;
        *)
            echo "Unknown parameter: $1"
            exit 1
            ;;
    esac
done

# 传输 Docker 镜像文件（仅在 --image 参数存在时执行）
if [ $TRANSFER_IMAGE -eq 1 ]; then
    echo "Transferring Docker image files..."
    scp ./deploy/*.tar $SERVER_USER@$SERVER_IP:$SERVER_PATH/image
    # scp ./deploy/gateway-1.0.0.tar $SERVER_USER@$SERVER_IP:$SERVER_PATH/image
    # scp ./deploy/archive-1.0.0.tar $SERVER_USER@$SERVER_IP:$SERVER_PATH/image
    # scp ./deploy/auth-1.0.0.tar $SERVER_USER@$SERVER_IP:$SERVER_PATH/image
    # scp ./deploy/users-1.0.0.tar $SERVER_USER@$SERVER_IP:$SERVER_PATH/image
    # scp ./deploy/registry-1.0.0.tar $SERVER_USER@$SERVER_IP:$SERVER_PATH/image
fi

# 创建远程目录
#echo "Creating remote directory..."
#ssh $SERVER_USER@$SERVER_IP "mkdir -p $SERVER_PATH/image"

# 传输 Docker 镜像文件
echo "Transferring Docker image files..."
scp ./deploy/.env $SERVER_USER@$SERVER_IP:$SERVER_PATH/
scp ./deploy/docker-compose.yml $SERVER_USER@$SERVER_IP:$SERVER_PATH/
scp ./deploy/remote-deploy.sh $SERVER_USER@$SERVER_IP:$SERVER_PATH/
scp ./deploy/nginx/conf.d/dmp.conf $SERVER_USER@$SERVER_IP:$SERVER_PATH/nginx/conf.d/

# 执行远程部署脚本
echo "Executing remote deployment script..."
ssh $SERVER_USER@$SERVER_IP "cd $SERVER_PATH && chmod +x remote-deploy.sh && ./remote-deploy.sh"

echo "Deployment completed."
