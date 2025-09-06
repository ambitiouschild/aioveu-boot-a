
//问题分析：为什么命令"倒着"执行
1.PowerShell 的执行机制：
   • PowerShell 从最后一行开始执行脚本
   • 这是为了支持管道操作和函数定义
   • 在交互式命令行中，命令是逐行执行的，但脚本文件是"倒着"执行的
3. 完成 SSH 密钥生成
   • 按 Enter 使用默认路径 (C:\Users\Administrator/.ssh/id_rsa)
   • 设置一个安全的密码（或直接按 Enter 留空）
4. 确认 SSH 主机密钥 
5. • 当提示 Are you sure you want to continue connecting时，输入 yes



```bash
# 1. 完成 SSH 密钥生成（按提示操作）
ssh-keygen -t rsa -b 4096
```

```bash
# 2. 复制公钥到远程服务器
#   手动方法：
#   a. 查看公钥内容：cat ~/.ssh/id_rsa.pub
#   b. 登录远程服务器：ssh root@aioveu.com
#   c. 添加公钥：echo "公钥内容" >> ~/.ssh/authorized_keys
```

```bash
# 3. 使用环境变量方法连接
#$env:DOCKER_HOST = "ssh://root@aioveu.com"
```


```bash
# 4.创建远程上下文
docker context create remote --docker "host=ssh://root@aioveu.com"

```

```bash
# 5.切换到远程上下文
docker context use remote
```


```bash
# 6.测试 SSH 连接
ssh root@www.aioveu.com "docker version"
```

```bash
# 1. 查看云服务器上的本地镜像（找到你要推送的镜像名和标签）
docker images
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
 





遇到了 SSH 连接反复要求输入密码的问题。这通常是由于 SSH 密钥认证没有正确设置导致的


环境变量对本地 Docker 的影响说明
当您设置 $env:DOCKER_HOST = "ssh://root@aioveu.com"时：
影响范围
1.仅限当前 PowerShell 会话：
• 这个设置只影响当前打开的 PowerShell 窗口
• 关闭窗口后，设置会自动失效
• 不会影响其他 PowerShell 窗口或命令提示符
2.不会永久覆盖本地 Docker：
• 不会修改系统环境变量
• 不会更改 Docker Desktop 的配置
• 不会影响其他用户或系统服
3.可随时恢复：
• 要恢复使用本地 Docker，只需
# 取消环境变量设置
Remove-Item Env:\DOCKER_HOST
# 或者关闭当前 PowerShell 窗口


如何验证当前使用的 Docker
# 查看当前使用的 Docker 主机
docker context ls

# 查看当前上下文
docker context show

# 检查环境变量
echo $env:DOCKER_HOST

多环境管理的最佳实践
1. 使用 Docker Context（推荐）
# 创建远程上下文
docker context create remote --docker "host=ssh://root@aioveu.com"

# 切换到远程上下文
docker context use remote

# 切换回本地上下文
docker context use default