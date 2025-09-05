
#  Docker Compose 安装中间件 MySQL、Redis、Minio、Xxl-Job

## 安装  cd  /opt/aioveu-boot/docker


```bash
#docker-compose -f ./docker-compose.yml -p aioveu-boot up -d

 docker compose -f ./docker-compose.yml -p aioveu-boot up -d

```
```bash
docker pull openjdk:17-jdk-slim
```

# - p aioveu-boot 指定命名空间，避免与其他容器冲突，这里方便管理，统一管理和卸载

## 卸载


```bash 
docker-compose -f ./docker-compose.yml -p aioveu-boot down
```

```bash
# 9.切换回本地上下文
docker context use default
```

```bash
# 10.切换回本地上下文执行验证
docker context show  # 应显示 "default"
```

```bash
# 11.显示本地容器而非远程
docker ps -a 
```