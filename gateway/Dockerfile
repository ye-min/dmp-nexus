FROM eclipse-temurin:17-jdk

# 设置工作目录
WORKDIR /app

# 将本地的 JAR 文件复制到 Docker 镜像中
COPY target/*.jar /app/gateway.jar

EXPOSE 9999

ENV TZ=Asia/Shanghai

ENV JAVA_OPTS="-Xms32m -Xmx64m -XX:+UseG1GC"

# 定义容器启动命令
CMD ["java", "-jar", "gateway.jar"]