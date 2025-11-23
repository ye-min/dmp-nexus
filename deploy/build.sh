#!/bin/bash
# 构建并保存 Docker 镜像

sed -i '3c\    active: release' ./archive/src/main/resources/bootstrap.yml
sed -i '3c\    active: release' ./gateway/src/main/resources/bootstrap.yml
sed -i '3c\    active: release' ./auth/src/main/resources/bootstrap.yml
sed -i '3c\    active: release' ./users/src/main/resources/bootstrap.yml
sed -i '3c\    active: release' ./registry/src/main/resources/bootstrap.yml

# 设置变量
VERSION=1.0.0

# 清理旧的镜像文件
rm -f *.tar

# 构建项目
echo "Building project..."
mvn clean package -DskipTests

# 构建 Docker 镜像
echo "Building Docker images..."
docker build -t registry:$VERSION ./registry
docker build -t gateway:$VERSION ./gateway
docker build -t archive:$VERSION ./archive
docker build -t auth:$VERSION ./auth
docker build -t users:$VERSION ./users

# 保存镜像为文件
echo "Saving registry docker images to files..."
docker save registry:$VERSION -o ./deploy/registry-$VERSION.tar
echo "Saving gateway docker images to files..."
docker save gateway:$VERSION -o ./deploy/gateway-$VERSION.tar
echo "Saving archive docker images to files..."
docker save archive:$VERSION -o ./deploy/archive-$VERSION.tar
echo "Saving auth docker images to files..."
docker save auth:$VERSION -o ./deploy/auth-$VERSION.tar
echo "Saving users docker images to files..."
docker save users:$VERSION -o ./deploy/users-$VERSION.tar

# 创建版本文件
echo $VERSION > ./deploy/version.txt

echo "Build completed. Images saved as tar files."
