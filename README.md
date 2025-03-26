# 拼团交易系统项目 README

## 项目介绍
这是一个基于 SpringBoot 的拼团交易系统项目，旨在实现类似拼多多的拼团功能。项目采用领域驱动设计（DDD）思想进行分层架构设计，确保系统的高内聚低耦合，便于后续的维护与扩展。

## 技术栈
- **SpringBoot**：作为核心框架，用于快速开发、简化配置。
- **MyBatis**：用于持久层的开发，方便进行数据库操作。
- **MySQL**：关系型数据库，用于存储项目中的各类数据。
- **Lombok**：用于简化 Java 类的 boilerplate 代码。
- **JUnit**：用于编写测试用例，确保代码质量。

## 数据库设计

### group_buy_activity 表
用于存储拼团活动的相关信息，包括活动名称、来源、渠道、商品ID、折扣ID等。

### group_buy_discount 表
用于存储折扣相关信息，如折扣名称、描述、类型、营销优惠计划等。

## 项目结构

### Groupon-api
定义接口层，主要用于对外提供 API 接口，定义请求和响应的 DTO。

### Groupon-app
应用层，负责协调和控制业务逻辑的执行，包含应用启动相关的配置。

### Groupon-domain
领域层，包含业务逻辑和业务规则，是 DDD 的核心部分。

### Groupon-infrastructure
基础设施层，负责提供技术实现，比如数据访问、缓存、消息队列等。

### Groupon-trigger
触发器模块，可能用于定时任务、事件监听等。

### Groupon-types
定义通用的类型、枚举和异常。

## 实体类

### GroupBuyActivity
对应 `group_buy_activity` 表的实体类，包含活动相关的字段。

### GroupBuyDiscount
对应 `group_buy_discount` 表的实体类，包含折扣相关的字段。

## Mapper 接口

### IGroupBuyActivityDao
定义了对 `group_buy_activity` 表的操作方法，目前包含查询活动列表的方法。

### IGroupBuyDiscountDao
定义了对 `group_buy_discount` 表的操作方法，目前包含查询折扣列表的方法。

## MyBatis 配置
在 `resources/mybatis/mapper` 目录下，分别为两个 Mapper 接口配置了对应的 XML 文件，定义了 SQL 语句以及结果映射。

## 测试代码
提供了对两个 Mapper 接口的测试类，通过 SpringBootTest 进行集成测试，验证了查询功能的正确性。

## 运行项目
1. 确保已正确配置数据库，并根据项目中的配置文件调整数据库连接信息。
2. 使用 Maven 或 Gradle 进行项目构建，下载所需依赖。
3. 在 IDE 中运行项目的主类，启动 SpringBoot 应用。
4. 可通过 Postman 等工具对提供的 API 进行测试。
