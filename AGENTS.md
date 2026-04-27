# 项目概述

本项目是一个前后端分离的全栈应用，后端使用 **Spring Boot + MyBatis-Flex**，前端使用 **Vue 3 + Vite + TypeScript**。后端提供 RESTful API，前端通过组件化架构消费这些接口。

# 技术栈

## 后端

- Java 21
- Spring Boot 3.x
- MyBatis-Flex（ORM 与查询构造器）
- MySQL（关系型数据库）
- Redis / redisson（缓存及缓存操作库）
- Maven（构建工具）
- Lombok（减少样板代码）
- Sa-Token（认证鉴权）
- 工具类库（Apache commons、guava）
- springdoc（接口文档）
- fesod-sheet（excel操作库）

## 前端

- Vue 3（组合式 API，`<script setup>` 语法）
- Vite 8
- TypeScript
- Vue Router 5
- Pinia 3（状态管理）
- alova 3.5（HTTP 客户端）
- UnoCSS / Element Plus（UI框架）
- unplugin-auto-import / unplugin-vue-components（自动导入）
- crypto-js / sm-crypto / jsencrypt（加解密操作）
- dayjs（日期时间工具类）
- echarts（图表工具类）
- vxe-table 4（复杂表格工具）
- pnpm (软件包管理器)

# 项目结构

```
project-root/
├── src/main/java/com/sijay/owl/  # Spring Boot 应用
│   ├── sys/                      # sys模块    
│   │   ├── controller/           # REST 控制器
│   │   ├── entity/               # 数据库实体
│   │   ├── mapper/               # MyBatis-Flex 映射器接口
│   │   └── service/              # 业务逻辑实现
│   ├── .../                      #其他模块       
│   └── common/                   # 通用工具、统一响应包装、异常处理、Spring 配置（CORS、安全、MyBatis 等）
├── src/main/resources/
│   ├── application.yml
│   └── mapper/                   # MyBatis XML 文件（必要的时候可以添加，按模块划分）
└── pom.xml
├── ui/                           # Vue 3 应用
│   ├── src/
│   │   ├── api/                  # alova 请求模块（按模块划分，文件使用实体类名小驼峰格式）
│   │   ├── views/                # 页面级组件（按模块划分，文件使用实体类名小驼峰格式）
│   │   ├── components/           # 可复用的 UI 组件
│   │   ├── router/               # Vue Router 配置
│   │   ├── stores/               # Pinia 存储
│   │   ├── utils/                # 工具函数与请求拦截器
│   │   ├── types/                # TypeScript 类型定义（按模块划分，文件使用实体类名小驼峰格式）
│   │   └── App.vue
│   ├── vite.config.ts
│   ├── tsconfig.json
│   └── package.json
└── AGENTS.md                     # 本文件
```

# 常用命令

## 后端

- **构建：** `mvn clean package -DskipTests`
- **运行测试：** `mvn test`
- **启动开发服务：** `mvn spring-boot:run`
    - 默认端口：`9528`
    - API 基础路径：`http://localhost:9528/api`

## 前端

- **安装依赖：** `cd ui && pnpm install`
- **启动开发服务：** `cd ui && pnpm run dev`
- **生产构建：** `cd ui && pnpm run build`
- **代码检查：** `cd ui && pnpm run lint`
- **类型检查：** `cd ui && npx vue-tsc --noEmit`

## 前后端联调代理

Vite 开发服务器通过 `vite.config.ts` 将 `/api` 请求代理到 `http://localhost:9528`。

# 编码约定

## 通用

- 保持简洁，可读性优先于炫技。
- 变量、函数和类名要具有描述性。
- 仅对复杂逻辑或公开 API 编写注释。
- 始终优雅地处理错误，并提供有意义的提示信息。

## 后端（Java）

### 分层职责

- **Controller** → 继承`BaseController`，校验输入，调用 `Service`，返回统一的 `Result<T>` 对象（格式：`{ code, message, data, total }`）。
- **Service** → 包含业务逻辑，继承 MyBatis-Flex 的 `ServiceImpl<Mapper,实体类>`，实现MyBatis-Flex 的 `IService<实体类>`，调用 `Mapper`，优先使用`QueryWrapper`及APT生成的字段。
- **Mapper** → 继承 MyBatis-Flex 的 `BaseMapper<实体类>`。优先使用内置的查询包装器，非必要不写 XML。
- **Entity** → 简单的 POJO，使用 `@Table("表名")` 标注表名，字段映射为驼峰命名。
- 使用`@RequiredArgsConstructor`进行构造器注入，禁止在字段上使用 `@Autowired`。

### MyBatis-Flex 示例

```java
// 使用查询包装器（推荐）
List<User> users = userMapper.selectListByQuery(
        QueryWrapper.create()
            .where(USER.NAME.like("John"))
            .orderBy(USER.ID.asc())
    );

// 实体定义
@ExcelIgnoreUnannotated
@Table(value = "user", comment = "用户表")
@Data
public class User {
    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    @ExcelProperty(value = "主键")
    private Long id;
    /**
     * 姓名
     */
    @Column(value = "name", comment = "姓名")
    @ExcelProperty(value = "姓名")
    private String name;
    /**
     * 年龄
     */
    @Column(value = "age", comment = "年龄")
    @ExcelProperty(value = "年龄")
    private Integer age;
}
```

### 统一返回格式

务必使用统一的 `Result` 包装类：

```java
public record Result<T>(
    // 状态码 200 表示成功，非 200 表示失败
    int code,
    // 响应消息 成功或失败的提示信息
    String message,
    // 响应数据 具体的业务数据
    T data,
    // 总数 用于分页场景，表示总记录数
    Long total
) {
}
// 控制器示例：调用BaseController中的方法进行包装返回
// 如：return success(userService.getById(1L));

```

## 前端（Vue 3 + TypeScript）

### 组合式 API

- 统一使用 `<script setup lang="ts">`。
- 使用 `defineProps<T>()` 和 `defineEmits<T>()` 定义属性和事件，类型使用 TypeScript 接口。
- 基本类型的响应式数据用 `ref`，尽量不使用 `reactive`。

### 文件命名

- 组件：`views/` 和 `components/` 下使用大驼峰命名，如 `UserTable.vue`
- API 模块：`api/` 下使用小驼峰，如 `userApi.ts`
- 状态管理：`stores/` 下使用小驼峰，如 `userStore.ts`
- 工具/类型：`utils/`、`types/` 下使用小驼峰

### 状态管理

- 优先使用 Pinia 的组合式 API 风格（`defineStore` 内使用 `setup` 函数）。
- 能用组件局部状态的情况，不要使用全局状态。

### API 调用

- 在 `utils/request.ts` 中创建 alova 实例，并配置拦截器以处理错误和注入 token。
- 按`.agents/skills/alova-client-usage`中的skills进行alova的使用
- 按业务域在 `api/` 文件中组织请求：

```ts
// api/userApi.ts
import request from '@/utils/request'
import type {User} from '@/types/user'

export const userApi = {
    getUserList: (params: any) => request.get<Result<User>>('/users', params),
    createUser: (data: UserForm) => request.post('/users', data)
}
```

### 样式

- 尽可能使用 scoped 样式。
- 若使用 UnoCSS，遵循工具优先的方式；自定义 CSS 使用语义化 class 名。

# 新增功能的开发流程

1. **数据库：** 新建或修改表结构，然后更新后端实体类。
2. **后端：** 创建 `Entity` → `Mapper`（继承 `BaseMapper`）→ `Service`（继承 MyBatis-Flex 的 `ServiceImpl<Mapper,实体类>`，实现MyBatis-Flex 的 `IService<实体类>`）→ `Controller`（REST
   接口，继承`BaseController`，返回 `Result`）。
3. **前端：** 添加 TypeScript 类型 → API 函数 →（可选）Pinia 存储 → 页面/组件。
4. **集成：** 按需更新 `vite.config.ts` 代理配置，并进行前后端联调测试。

# 对话与注释语言

- **所有由 AI 生成的对话、解释以及代码注释都必须使用中文。**
- 变量名、函数名、类名、接口名、数据库字段名等标识符使用英文

# 注意事项

- 尊重现有的目录结构，不要随意增加新的顶级包或文件夹。
- 数据库表结构变更时，需手动编写 SQL 迁移脚本。
- 修改任何文件前，务必先理解现有代码的上下文。如果不确定，先说明计划再动手。
- 不要去进行git提交操作
- 从仓库根目录开始，并依赖根清单和工作区配置来确定结构。
- 使用 Glob 和 Grep（基于 rg）进行文件发现和内容搜索。
- 在假定工作流之前，先读取仓库本地的 OpenCode 配置（opencode.json）。
- 编辑必须使用 apply_patch；避免临时编辑器。
- 使用 TODO 系统（todowrite）跟踪任务；一次限制一个进行中的任务。
- 包管理器使用pnpm。
- 遵循仓库的验证顺序（lint -> typecheck -> test），除非文档另有说明。
- 识别主要目录和入口点（apps/、libs/ 等）以尊重模块边界。
- 不要提交机密信息（忽略 .env 和 credentials.*）；从更改中清除敏感数据。
- 当配置与文档文字冲突时，优先使用可执行配置（opencode.json、scripts）。
- 如果约定不明确，提出一个澄清性问题，而不是猜测。