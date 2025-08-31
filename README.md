<div align="center">
   <img alt="logo"  src="https://cdn.jsdelivr.net/gh/ambitiouschild/ambitiouschild/images/aioveu.png">
   <h2>aioveu-boot</h2>
</div>


![](https://raw.gitmirror.com/youlaitech/image/main/docs/rainbow.png)

<div align="center">
  <a target="_blank" href="https://aioveu.com">🖥️ 在线预览</a> |  <a target="_blank" href="https://youlai.blog.csdn.net/article/details/145178880">📑 阅读文档</a> |  <a target="_blank" href="https://www.youlai.tech/youlai-boot">🌐 官网</a>
</div>


## 📢 项目简介

基于 JDK 17、Spring Boot 3、Spring Security 6、JWT、Redis、Mybatis-Plus、Vue 3、Element-Plus 构建的前后端分离单体权限管理系统。 [Mybatis-Flex 版本](https://gitee.com/youlaiorg/youlai-boot-flex)

- **🚀 开发框架**: 使用 Spring Boot 3 和 Vue 3，以及 Element-Plus 等主流技术栈，实时更新。

- **🔐 安全认证**: 基于 Spring Security 6 原生架构，集成 JWT 令牌自动续期（无状态）和 Redis 会话多端互斥管理（实时强制离线）双重认证机制，构建企业级身份安全中枢。

- **🔑 权限管理**: 基于 RBAC 模型，实现细粒度的权限控制，涵盖接口方法和按钮级别。

- **🛠️ 功能模块**: 包括用户管理、角色管理、菜单管理、部门管理、字典管理等功能。

## 🌈 项目源码

| 项目类型       | Github                                                   | Gitee                                                   | GitCode                                                      |
| --------------| ------------------------------------------------------------  |  ------------------------------------------------------------ | ------------------------------------------------------------ |
| ✅ Java 后端  | [aioveu-boot](https://github.com/ambitiouschild/aioveu-boot) |     |         |
| vue3 前端     | [aioveu-vue3-element-admin](https://github.com/ambitiouschild/aioveu-vue3-element-admin) |  |  |
| uni-app 移动端 | [aioveu-vue3-uniapp](https://github.com/ambitiouschild/aioveu-vue3-uniapp) |  |         |



## 📚 项目文档

| 文档名称          | 访问地址                                                                                   |
|---------------|-------------------------------------------------------------------------------------------|
| 在线接口文档        | [https://www.apifox.cn/apidoc](https://www.apifox.cn/apidoc/shared-195e783f-4d85-4235-a038-eec696de4ea5) |
| 项目介绍与使用指南     | [https://www.youlai.tech/youlai-boot/](https://www.youlai.tech/youlai-boot/)              |
| 功能详解与操作手册     | [https://youlai.blog.csdn.net/article/details/145178880](https://youlai.blog.csdn.net/article/details/145178880) |
| 新手入门指南(项目0到1) | [https://youlai.blog.csdn.net/article/details/145177011](https://youlai.blog.csdn.net/article/details/145177011) |


## 📁 项目目录


<details>
<summary> 目录结构 </summary>

<br>

```
aioveu-boot
├── docker                              # Docker 目录
│   ├── docker-compose.yml              # docker-compose 脚本
├── sql                                 # SQL脚本
│   ├── mysql                           # MySQL 脚本
├── src                                 # 源码目录
│   ├── common                          # 公共模块
│   │   ├── annotation                  # 注解定义
│   │   ├── base                        # 基础类
│   │   ├── constant                    # 常量
│   │   ├── enums                       # 枚举类型
│   │   ├── exception                   # 异常处理
│   │   ├── model                       # 数据模型
│   │   ├── result                      # 结果封装
│   │   └── util                        # 工具类
│   ├── config                          # 自动装配配置
│   │   └── property                    # 配置属性目录
│   ├── core                            # 核心功能
│   │   ├── aspect                      # 切面(日志、防重提交)
│   │   ├── filter                      # 过滤器(请求日志、限流)
│   │   ├── handler                     # 处理器(数据权限、数据填充)
│   │   └── security                    # Spring Security 安全模块
│   ├── modules                         # 业务模块
│   │   ├── member                      # 会员模块【业务模块演示】
│   │   ├── order                       # 订单模块【业务模块演示】
│   │   ├── product                     # 商品模块【业务模块演示】
│   ├── shared                          # 共享模块
│   │   ├── auth                        # 认证模块
│   │   ├── file                        # 文件模块
│   │   ├── codegen                     # 代码生成模块
│   │   ├── mail                        # 邮件模块
│   │   ├── sms                         # 短信模块
│   │   └── websocket                   # WebSocket 模块
│   ├── system                          # 系统模块
│   │   ├── controller                  # 控制层
│   │   ├── converter                   # MapStruct 转换器
│   │   ├── event                       # 事件处理
│   │   ├── handler                     # 处理器
│   │   ├── listener                    # 监听器
│   │   ├── model                       # 模型层
│   │   │   ├── bo                      # 业务对象
│   │   │   ├── dto                     # 数据传输对象
│   │   │   ├── entity                  # 实体对象
│   │   │   ├── form                    # 表单对象
│   │   │   ├── query                   # 查询参数对象
│   │   │   └── vo                      # 视图对象
│   │   ├── mapper                      # 数据库访问层
│   │   └── service                     # 业务逻辑层
│   └── AioveuBootApplication           # 启动类
└── end                             
```
</details>



## 🚀 项目启动

📚 完整流程参考: [项目启动](https://www.youlai.tech/youlai-boot/1.%E9%A1%B9%E7%9B%AE%E5%90%AF%E5%8A%A8/)

1. **克隆项目**

   ```bash
   git clone https://github.com/ambitiouschild/aioveu-boot.git
   ```

2. **数据库初始化**

   执行 [01aioveu_boot.sql](sql/mysql/01aioveu_boot.sql) 脚本完成数据库创建、表结构和基础数据的初始化。

3. **修改配置**

   默认连接`可我不地可爱`线上 MySQL/Redis（仅读权限），本地开发时请修改 [application-dev.yml](src/main/resources/application-dev.yml) 中的 MySQL 和 Redis 连接信息。

4. **启动项目**

   执行 [AioveuBootApplication.java](src/main/java/com/youlai/boot/AioveuBootApplication.java) 的 main 方法完成后端项目启动；

   访问接口文档地址 [http://localhost:8989/doc.html](http://localhost:8989/doc.html) 验证项目启动是否成功。


## 🚀 项目部署

参考官方文档: [项目部署指南](https://www.youlai.tech/youlai-boot/5.%E9%A1%B9%E7%9B%AE%E9%83%A8%E7%BD%B2/)


## ✅ 项目统计






## 💖 加交流群

① 关注「可我不敌可爱」公众号，点击菜单 **交流群** 获取加群二维码（此举防止广告进群，感谢理解和支持）。

② 直接添加微信 **`ambitiouschild** 备注「前端/后端/全栈」。

| 公众号                                                       | 交流群                                                       |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img height="180px" src="https://cdn.jsdelivr.net/gh/ambitiouschild/ambitiouschild/images/ambitiouschild_Public_Platform.jpg"/> | <img src="https://cdn.jsdelivr.net/gh/ambitiouschild/ambitiouschild/images/ambitiouschild_group.jpg" height="300px"> |