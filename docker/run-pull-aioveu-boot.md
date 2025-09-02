


```bash
# 1. 查看云服务器上的本地镜像（找到你要推送的镜像名和标签）
docker images
```

```bash
# 2. 给本地镜像打远程标签（原镜像名:标签 → 远程仓库地址/用户名/镜像名:版本）
docker tag 756a87f57763  crpi-s90cufgtjv4fnw98.cn-shanghai.personal.cr.aliyuncs.com/ambitiouschild/aioveu-boot:v1.2.0
```

```bash
# 3. 登录远程仓库
docker login --username=可我不敌可爱 crpi-s90cufgtjv4fnw98.cn-shanghai.personal.cr.aliyuncs.com
```


```bash
# 4. 推送标签后的镜像
docker push crpi-s90cufgtjv4fnw98.cn-shanghai.personal.cr.aliyuncs.com/ambitiouschild/aioveu-boot:v1.2.0

```

```bash
# 5.本地拉取远程仓库的镜像
docker pull crpi-s90cufgtjv4fnw98.cn-shanghai.personal.cr.aliyuncs.com/ambitiouschild/aioveu-boot:v1.2.0
```


```bash
# 6.拉取后打短标签
docker tag crpi-s90cufgtjv4fnw98.cn-shanghai.personal.cr.aliyuncs.com/ambitiouschild/aioveu-boot:v1.2.0 aioveu-boot:v1.2.0
```

```bash
# 7. 部署容器
docker compose -f ./docker-compose98.yml -p aioveu-boot up -d

```

```bash
# 8. 查看日志
docker compose -p aioveu-boot logs -f

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
