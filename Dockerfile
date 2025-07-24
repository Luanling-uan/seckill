# 基础构建阶段
ARG SERVICE
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app

# 复制所有模块的pom.xml和源代码
COPY pom.xml .
COPY ${SERVICE}/pom.xml ${SERVICE}/pom.xml
COPY ${SERVICE}/src ${SERVICE}/src

# 只构建指定服务
RUN mvn clean package -pl ${SERVICE} -am -DskipTests

# 运行阶段
FROM openjdk:17-jdk-slim
WORKDIR /app

# 从构建阶段复制特定服务的jar包
COPY --from=builder /app/${SERVICE}/target/${SERVICE}-1.0-SNAPSHOT.jar app.jar

# 暴露端口（根据服务调整）
EXPOSE 8080
# 默认端口，实际使用时需根据服务修改

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]