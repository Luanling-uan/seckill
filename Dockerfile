# 关键：在构建阶段前定义 ARG，确保整个构建过程可见
ARG SERVICE

# 基础构建阶段
FROM maven:3.8.5-openjdk-17 AS builder
# 重新声明 ARG（因为每个 FROM 会创建新上下文，需重新传递）
ARG SERVICE
WORKDIR /app

# 复制根 pom.xml
COPY pom.xml .

# 复制指定服务模块的文件（使用正确的相对路径）
COPY ${SERVICE}/pom.xml ${SERVICE}/pom.xml
COPY ${SERVICE}/src ${SERVICE}/src

# 构建指定服务
RUN mvn clean package -pl ${SERVICE} -am -DskipTests

# 运行阶段
FROM openjdk:17-jdk-slim
# 如需在运行阶段使用，同样需要声明
ARG SERVICE
WORKDIR /app

# 复制构建好的 jar 包
COPY --from=builder /app/${SERVICE}/target/${SERVICE}-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]