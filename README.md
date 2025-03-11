# Smart-Factory (伏尔甘智能工厂)

## 项目介绍
伏尔甘智能工厂是一个基于SpringBoot 3.x的现代化工厂管理系统，采用模块化架构设计，集成了多种先进技术栈，旨在提供一个高效、可靠、安全的智能工厂解决方案。

## 系统架构

### 后端架构
项目采用多模块架构设计，各模块职责分明：

- **vulcan-admin**: 系统启动模块
  - 作为应用程序的统一入口点
  - 集成各个功能模块
  - 提供系统启动和初始化功能

- **vulcan-framework**: 框架核心模块
  - 提供基础框架配置
  - 异常处理机制
  - 核心配置管理

- **vulcan-common**: 公共基础模块
  - 通用工具类库
  - 公共注解
  - 基础实体类
  - 共享服务接口

- **vulcan-system**: 系统管理模块
  - 核心业务实体管理
  - 系统服务实现
  - 数据访问层
  - 业务逻辑处理

- **vulcan-auth**: 认证授权模块
  - 用户认证
  - 权限管理
  - 安全控制

- **vulcan-cache**: 缓存处理模块
  - 缓存策略实现
  - 数据缓存管理
  - 性能优化

- **vulcan-mq**: 消息队列模块
  - 消息队列集成
  - 异步处理
  - 事件驱动

- **vulcan-ui**: 前端界面模块
  - 用户界面实现
  - 前端路由管理
  - 状态管理
  - API集成

### 前端架构
采用现代化的前端技术栈：

- 基于Vue 3.2.4构建
- 使用Vite 5.2.0作为构建工具
- 采用TypeScript开发
- 使用Naive UI组件库
- Pinia状态管理
- Vue Router路由管理

## 技术栈详情

### 后端技术栈
| 技术栈 | 版本 | 说明 |
|:----:|:----:|:----:|
| SpringBoot | 3.2.4 | 应用基础框架 |
| SpringData JPA | - | 数据访问层 |
| Dynamic Datasource | 4.3.0 | 动态数据源 |
| MySQL | 8.0.27 | 数据库 |
| Redis | 7 | 缓存数据库 |
| Jedis | 5.1.2 | Redis客户端 |
| Druid | 1.2.22 | 数据库连接池 |
| sa-token | 1.37.0 | 认证授权框架 |
| Lombok | - | 代码简化工具 |

### 前端技术栈
| 技术栈 | 版本 | 说明 |
|:----:|:----:|:----:|
| Vue | 3.2.4 | 前端框架 |
| Vite | 5.2.0 | 构建工具 |
| Node | 20.12.2 | 运行环境 |
| pnpm | 9.0.5 | 包管理器 |
| Naive UI | 2.38.1 | UI组件库 |

## 项目特性
- 🚀 基于最新的Spring Boot 3.x版本开发
- 📦 模块化架构设计，易于扩展
- 🔐 完善的权限认证系统
- 💾 支持动态数据源配置
- 🔄 集成消息队列，支持异步处理
- 🎯 高性能缓存支持
- 🎨 现代化的前端界面
- 📱 响应式设计，支持多端适配

## 环境要求
- JDK 21+
- MySQL 8.0+
- Redis 7.0+
- Node.js 20+
- pnpm 9.0+

## 快速开始
1. 克隆项目
```bash
git clone https://github.com/your-username/Smart-Factory.git
```

2. 后端启动
```bash
cd Smart-Factory
mvn clean install
cd vulcan-admin
mvn spring-boot:run
```

3. 前端启动
```bash
cd vulcan-ui
pnpm install
pnpm dev
```

## 贡献指南
欢迎提交问题和改进建议。如果您想贡献代码，请遵循以下步骤：
1. Fork 本仓库
2. 创建您的特性分支
3. 提交您的改动
4. 推送到您的分支
5. 创建Pull Request

## 许可证
本项目采用 [LICENSE](LICENSE) 许可证。