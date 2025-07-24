# seckill 秒杀系统

## 技术栈
- Spring Cloud (Gateway, Feign)
- Resilience4j
- RabbitMQ
- Redis
- XXL-JOB
- Nacos
- Docker/Docker Compose

## 模块说明
- gateway：统一入口，路由与鉴权
- user：用户服务，登录鉴权
- product：商品服务，库存与秒杀
- order：订单服务，消息消费与下单
- job：定时任务，清理超时订单
- common：公共模块，DTO/Feign接口等

## 启动方式
1. `docker-compose up --build` 一键启动所有服务及依赖
2. 访问 http://localhost:8085 通过网关入口调用各服务 