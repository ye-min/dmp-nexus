#!/bin/bash
# 构建并保存 Docker 镜像

sed -i '3c\    active: test' ./beacon/src/main/resources/bootstrap.yml
sed -i '3c\    active: test' ./config-center/src/main/resources/bootstrap.yml
sed -i '3c\    active: test' ./gateway/src/main/resources/bootstrap.yml
sed -i '3c\    active: test' ./gps/src/main/resources/bootstrap.yml
sed -i '3c\    active: test' ./message/src/main/resources/bootstrap.yml
sed -i '3c\    active: test' ./registry/src/main/resources/bootstrap.yml
sed -i '3c\    active: test' ./transit/src/main/resources/bootstrap.yml
sed -i '3c\    active: test' ./catalogue/src/main/resources/bootstrap.yml
