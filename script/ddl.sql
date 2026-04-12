drop table if exists gen_table;
create table gen_table (
  id              bigint       not null auto_increment primary key comment '主键',
  table_name      varchar(100) not null unique comment '物理表名',
  table_comment   varchar(500) not null comment '表注释',
  module_name     varchar(50)  not null comment '模块名',
  class_name      varchar(100) not null comment '实体类名',
  class_comment   varchar(200) not null comment '实体类注释',
  function_name   varchar(100) not null comment '功能名',
  tree_table      boolean     default false comment '树表',
  tree_key        varchar(50) default '' comment '树编码字段',
  tree_parent_key varchar(50) default '' comment '树父编码字段',
  tree_label      varchar(50) default '' comment '树名称字段',
  menu_id         bigint      default 1 comment '所属菜单',
  index idx_table_name (table_name)
) comment '代码生成表信息';
drop table if exists gen_column;
create table gen_column (
  id             bigint       not null auto_increment primary key comment '主键',
  table_id       bigint       not null comment '表id',
  column_name    varchar(128) not null comment '列名',
  column_comment varchar(512) default '' comment '列注释',
  column_type    varchar(128) not null comment '数据库类型',
  java_type      varchar(64)  not null comment 'Java类型',
  java_field     varchar(128) not null comment 'Java字段名',
  primary_key    boolean      default false comment '是否主键',
  incremental    boolean      default false comment '是否自增',
  required       boolean      default false comment '是否必填',
  insertable     boolean      default true comment '是否插入',
  editable       boolean      default true comment '是否编辑',
  listable       boolean      default true comment '是否列表',
  queryable      boolean      default false comment '是否查询',
  query_type     varchar(32)  default 'EQUAL' comment '查询方式',
  html_type      varchar(32)  default 'INPUT' comment '显示类型',
  dict_type      varchar(100) default '' comment '字典类型',
  sort           int          default 0 comment '排序',
  index idx_table_id (table_id)
) comment '代码生成列信息';

drop table if exists sys_dept;
create table sys_dept (
  id            bigint not null auto_increment primary key comment '主键',
  parent_id     bigint        default 0 comment '父部门id',
  ancestors     varchar(1000) default '' comment '祖级列表',
  dept_name     varchar(30)   default '' comment '部门名称',
  dept_category bigint        default 0 comment '部门类别',
  sort          int           default 0 comment '显示顺序',
  leader        bigint        default null comment '负责人',
  phone         varchar(11)   default '' comment '联系电话',
  email         varchar(50)   default '' comment '邮箱',
  enabled       boolean       default true comment '启用',
  deleted       boolean       default false comment '删除',
  create_dept   bigint        default 1 comment '创建部门',
  create_by     bigint        default 1 comment '创建者',
  create_time   datetime      default current_timestamp comment '创建时间',
  update_by     bigint        default 1 comment '更新者',
  update_time   datetime      default current_timestamp comment '更新时间',
  index idx_parent_id (parent_id),
  index idx_deleted (deleted)
) comment '部门表';
drop table if exists sys_post;
create table sys_post (
  id            bigint      not null auto_increment primary key comment '主键',
  dept_id       bigint      not null comment '部门id',
  post_code     varchar(64) not null unique comment '岗位编码',
  post_category varchar(100) default '' comment '岗位类别编码',
  post_name     varchar(50) not null comment '岗位名称',
  sort          int         not null comment '显示顺序',
  enabled       boolean      default true comment '启用',
  deleted       boolean      default false comment '删除',
  create_dept   bigint       default 1 comment '创建部门',
  create_by     bigint       default 1 comment '创建者',
  create_time   datetime     default current_timestamp comment '创建时间',
  update_by     bigint       default 1 comment '更新者',
  update_time   datetime     default current_timestamp comment '更新时间',
  index idx_dept_id (dept_id),
  index idx_deleted (deleted)
) comment '岗位表';
drop table if exists sys_role;
create table sys_role (
  id                  bigint       not null auto_increment primary key comment '主键',
  role_name           varchar(30)  not null comment '角色名称',
  role_code           varchar(100) not null unique comment '角色权限字符串',
  sort                int          not null                                         default 100 comment '显示顺序',
  data_scope          enum ('ALL', 'CUSTOM', 'DEPT_ONLY', 'DEPT_AND_CHILD', 'SELF') default 'SELF' comment '数据权限',
  menu_check_strictly boolean                                                       default true comment '菜单树选择项关联显示',
  dept_check_strictly boolean                                                       default true comment '部门树选择项关联显示',
  enabled             boolean                                                       default true comment '启用',
  deleted             boolean                                                       default false comment '删除',
  create_dept         bigint                                                        default 1 comment '创建部门',
  create_by           bigint                                                        default 1 comment '创建者',
  create_time         datetime                                                      default current_timestamp comment '创建时间',
  update_by           bigint                                                        default 1 comment '更新者',
  update_time         datetime                                                      default current_timestamp comment '更新时间',
  index idx_deleted (deleted)
) comment '角色表';
drop table if exists sys_menu;
create table sys_menu (
  id           bigint      not null auto_increment primary key comment '主键',
  menu_name    varchar(50) not null comment '菜单名称',
  parent_id    bigint       default 0 comment '父菜单id',
  sort         int          default 0 comment '显示顺序',
  path         varchar(200) default '' comment '路由地址',
  component    varchar(255) default '' comment '组件路径',
  query_param  varchar(255) default '' comment '路由参数',
  foreign_link boolean      default false comment '是否为外链',
  cached       boolean      default true comment '是否缓存',
  menu_type    varchar(10)  default 'DIRECTORY' comment '菜单类型',
  visible      boolean      default true comment '显示',
  enabled      boolean      default true comment '启用',
  perms        varchar(100) default '' comment '权限标识',
  icon         varchar(100) default '#' comment '菜单图标',
  create_dept  bigint       default 1 comment '创建部门',
  create_by    bigint       default 1 comment '创建者',
  create_time  datetime     default current_timestamp comment '创建时间',
  update_by    bigint       default 1 comment '更新者',
  update_time  datetime     default current_timestamp comment '更新时间',
  index idx_parent_id (parent_id)
) comment '菜单表';
drop table if exists sys_user;
create table sys_user (
  id          bigint       not null auto_increment primary key comment '主键',
  dept_id     bigint                default 0 comment '部门id',
  username    varchar(20)  not null unique comment '用户账号',
  nickname    varchar(30)           default '' comment '用户昵称',
  email       varchar(50)           default '' comment '邮箱',
  phone       varchar(11)           default '' comment '手机号',
  gender      varchar(8)            default 'UNKNOWN' comment '性别',
  avatar      bigint                default 0 comment '头像地址',
  password    varchar(100) not null default '' comment '密码',
  mfa_enabled boolean               default false comment '是否启用MFA',
  totp_secret varchar(255) comment 'totp密钥',
  enabled     boolean               default true comment '启用',
  deleted     boolean               default false comment '删除',
  create_dept bigint                default 1 comment '创建部门',
  create_by   bigint                default 1 comment '创建者',
  create_time datetime              default current_timestamp comment '创建时间',
  update_by   bigint                default 1 comment '更新者',
  update_time datetime              default current_timestamp comment '更新时间',
  index idx_dept_id (dept_id),
  index idx_deleted (deleted)
) comment '用户表';
drop table if exists sys_user_online;
create table sys_user_online (
  id               bigint auto_increment primary key comment '主键',
  user_id          bigint   not null comment '用户id',
  username         varchar(50)  default '' comment '用户账号',
  dept_name        varchar(50)  default '' comment '部门名称',
  login_ip         varchar(128) default '' comment '登录ip',
  login_location   varchar(255) default '' comment '登录地点',
  browser          varchar(50)  default '' comment '浏览器',
  os               varchar(50)  default '' comment '操作系统',
  login_time       datetime not null comment '登录时间',
  last_access_time datetime not null comment '最后访问时间',
  expire_time      datetime not null comment '过期时间',
  index idx_user_id (user_id),
  index idx_last_access_time (last_access_time)
) comment '在线用户表';
drop table if exists sys_user_mfa_recovery_codes;
create table sys_user_mfa_recovery_codes (
  id          bigint auto_increment primary key comment '主键',
  user_id     bigint      not null comment '用户id',
  code        varchar(10) not null comment '备用验证码',
  used        boolean  default false comment '已用',
  used_time   datetime default null comment '使用时间',
  create_time datetime default current_timestamp comment '创建时间',
  index idx_user_id (user_id),
  index idx_code (code)
) comment 'MFA备用验证码表';
drop table if exists sys_user_role;
create table sys_user_role (
  user_id bigint not null comment '用户id',
  role_id bigint not null comment '角色id',
  primary key (user_id, role_id)
) comment '用户角色关联表';
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id bigint not null comment '角色id',
  menu_id bigint not null comment '菜单id',
  primary key (role_id, menu_id)
) comment '角色菜单关联表';
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id bigint not null comment '角色id',
  dept_id bigint not null comment '部门id',
  primary key (role_id, dept_id)
) comment '角色部门关联表';
drop table if exists sys_user_post;
create table sys_user_post (
  user_id bigint not null comment '用户id',
  post_id bigint not null comment '岗位id',
  primary key (user_id, post_id)
) comment '用户岗位关联表';
drop table if exists sys_dict_type;
create table sys_dict_type (
  id          bigint not null auto_increment primary key comment '主键',
  type_name   varchar(100) default '' comment '字典名称',
  type_code   varchar(100) default '' unique comment '字典编码',
  create_dept bigint       default 1 comment '创建部门',
  create_by   bigint       default 1 comment '创建者',
  create_time datetime     default current_timestamp comment '创建时间',
  update_by   bigint       default 1 comment '更新者',
  update_time datetime     default current_timestamp comment '更新时间'
) comment '字典类型表';
drop table if exists sys_dict_data;
create table sys_dict_data (
  id           bigint       not null auto_increment primary key comment '主键',
  dict_type_id bigint       not null comment '字典类型id',
  dict_code    varchar(100) not null default '' comment '字典编码',
  dict_label   varchar(100)          default '' comment '字典标签',
  dict_value   varchar(100)          default '' comment '字典键值',
  sort         int                   default 0 comment '字典排序',
  css_class    varchar(100)          default '' comment '样式属性',
  list_class   varchar(100)          default '' comment '表格回显样式',
  defaulted    boolean               default false comment '是否默认',
  enabled      boolean               default true comment '启用',
  create_dept  bigint                default 1 comment '创建部门',
  create_by    bigint                default 1 comment '创建者',
  create_time  datetime              default current_timestamp comment '创建时间',
  update_by    bigint                default 1 comment '更新者',
  update_time  datetime              default current_timestamp comment '更新时间',
  index idx_dict_type_id (dict_type_id),
  index idx_dict_code (dict_code)
) comment '字典数据表';
drop table if exists sys_config;
create table sys_config (
  id           bigint not null auto_increment primary key comment '主键',
  config_name  varchar(100) default '' comment '参数名称',
  config_key   varchar(100) default '' unique comment '参数键名',
  config_value varchar(500) default '' comment '参数键值',
  create_dept  bigint       default 1 comment '创建部门',
  create_by    bigint       default 1 comment '创建者',
  create_time  datetime     default current_timestamp comment '创建时间',
  update_by    bigint       default 1 comment '更新者',
  update_time  datetime     default current_timestamp comment '更新时间'
) comment '参数配置表';
drop table if exists sys_notice;
create table sys_notice (
  id             bigint       not null auto_increment primary key comment '主键',
  notice_title   varchar(100) not null comment '公告标题',
  notice_type    varchar(32)  not null comment '公告类型',
  notice_content blob comment '公告内容',
  closed         boolean  default false comment '是否关闭',
  deleted        boolean  default false comment '删除',
  create_dept    bigint   default 1 comment '创建部门',
  create_by      bigint   default 1 comment '创建者',
  create_time    datetime default current_timestamp comment '创建时间',
  update_by      bigint   default 1 comment '更新者',
  update_time    datetime default current_timestamp comment '更新时间',
  index idx_deleted (deleted)
) comment '通知公告表';
drop table if exists sys_message;
create table sys_message (
  id              bigint      not null auto_increment primary key comment '主键',
  message_title   varchar(50) not null comment '消息标题',
  message_content text comment '消息内容',
  message_type    varchar(32)          default 'SYSTEM' comment '消息类型',
  sender          bigint      not null default 1 comment '发送者',
  recipient       bigint      not null default 1 comment '接收者',
  has_read        boolean              default false comment '已读',
  deleted         boolean              default false comment '删除',
  create_dept     bigint               default 1 comment '创建部门',
  create_by       bigint               default 1 comment '创建者',
  create_time     datetime             default current_timestamp comment '创建时间',
  update_by       bigint               default 1 comment '更新者',
  update_time     datetime             default current_timestamp comment '更新时间',
  index idx_sender (sender),
  index idx_recipient (recipient),
  index idx_has_read (has_read),
  index idx_create_time (create_time)
) comment '消息表';

drop table if exists log_access;
create table log_access (
  id              bigint not null auto_increment primary key comment '主键',
  user_id         bigint       default 0 comment '用户id',
  title           varchar(50)  default '' comment '模块标题',
  operate_type    varchar(20)  default '' comment '业务类型',
  method          varchar(100) default '' comment '方法名称',
  request_method  varchar(10)  default '' comment '请求方式',
  access_username varchar(50)  default '' comment '访问人员',
  access_url      varchar(255) default '' comment '请求url',
  access_ip       varchar(128) default '' comment '主机地址',
  access_location varchar(255) default '' comment '访问地点',
  access_param    text comment '请求参数',
  json_result     text comment '返回参数',
  status          int          default 200 comment '访问状态',
  error_msg       text comment '错误消息',
  access_time     datetime     default current_timestamp comment '访问时间',
  cost_time       bigint       default 0 comment '消耗时间',
  index idx_user_id (user_id),
  index idx_access_username (access_username),
  index idx_log_access_business_type (operate_type),
  index idx_log_access_status (status),
  index idx_log_access_access_time (access_time)
) comment '访问日志表';
drop table if exists log_login;
create table log_login (
  id         bigint not null auto_increment primary key comment '主键',
  user_id    bigint       default 0 comment '用户id',
  username   varchar(50)  default '' comment '用户账号',
  login_ip   varchar(128) default '' comment '登录ip地址',
  browser    varchar(50)  default '' comment '浏览器类型',
  os         varchar(50)  default '' comment '操作系统',
  succeeded  boolean      default false comment '登录状态',
  message    varchar(255) default '' comment '提示消息',
  login_time datetime     default current_timestamp comment '登录时间',
  index idx_user_id (user_id),
  index idx_login_time (login_time)
) comment '登录日志表';

drop table if exists file_storage;
create table file_storage (
  id            bigint       not null auto_increment primary key comment '主键',
  file_name     varchar(255) not null default '' comment '文件名',
  original_name varchar(255) not null default '' comment '原名',
  file_suffix   varchar(10)  not null default '' comment '文件后缀名',
  file_size     bigint                default 0 comment '文件大小(字节)',
  content_type  varchar(100)          default '' comment 'MIME类型',
  path          varchar(500) not null comment '存储路径',
  deleted       boolean               default false comment '删除',
  create_dept   bigint                default 1 comment '创建部门',
  create_time   datetime              default current_timestamp comment '创建时间',
  create_by     bigint                default 0 comment '上传人',
  update_time   datetime              default current_timestamp comment '更新时间',
  update_by     bigint                default 0 comment '更新人',
  index idx_create_by (create_by),
  index idx_create_time (create_time),
  index idx_deleted (deleted)
) comment '文件存储表';
drop table if exists file_oss_storage;
create table file_oss_storage (
  id            bigint       not null auto_increment primary key comment '主键',
  file_name     varchar(255) not null default '' comment '文件名',
  original_name varchar(255) not null default '' comment '原名',
  file_suffix   varchar(10)  not null default '' comment '文件后缀名',
  file_size     bigint                default 0 comment '文件大小(字节)',
  content_type  varchar(100)          default '' comment 'MIME类型',
  url           varchar(500) not null comment 'url地址',
  service       varchar(20)  not null default 'minio' comment '服务商',
  deleted       boolean               default false comment '删除',
  create_dept   bigint                default 1 comment '创建部门',
  create_time   datetime              default current_timestamp comment '创建时间',
  create_by     bigint                default 0 comment '上传人',
  update_time   datetime              default current_timestamp comment '更新时间',
  update_by     bigint                default 0 comment '更新人',
  index idx_create_by (create_by),
  index idx_create_time (create_time),
  index idx_deleted (deleted)
) comment 'OSS对象存储表';

drop table if exists sys_task;
create table sys_task (
  id                bigint auto_increment primary key comment '主键',
  task_key          varchar(255)                               not null unique comment '任务唯一标识符',
  task_name         varchar(255)                               not null comment '任务名称',
  task_group        varchar(255) default 'default' comment '任务分组',
  description       text comment '任务描述',
  schedule_type     enum ('CRON', 'FIXED_DELAY', 'FIXED_RATE') not null comment '调度类型 (CRON, FIXED_DELAY, FIXED_RATE)',
  cron_expression   varchar(255) comment 'Cron 表达式',
  fixed_delay       long comment '固定延迟时间 (毫秒) (如果使用 fixedDelay)',
  initial_delay     long comment '初始延迟时间 (毫秒) (如果使用 fixedDelay 或 fixedRate)',
  fixed_rate        long comment '固定频率时间 (毫秒) (如果使用 fixedRate)',
  enabled           boolean      default true comment '是否启用',
  concurrent        boolean      default false comment '是否允许并发执行',
  notify_on_failure boolean      default true comment '任务失败时是否发送通知',
  bean_class        varchar(500)                               not null comment 'Spring Bean类名 (包含包路径)',
  method_name       varchar(255)                               not null comment '执行方法名',
  method_params     json         default null comment '执行方法参数', -- 可选
  next_task_id      bigint                                     not null comment '接续任务id',
  wait_success      boolean      default false comment '等待成功',
  create_dept       bigint       default 1 comment '创建部门',
  create_by         bigint       default 1 comment '创建者',
  create_time       datetime     default current_timestamp comment '创建时间',
  update_by         bigint       default 1 comment '更新者',
  update_time       datetime     default current_timestamp comment '更新时间',
  remark            varchar(500) default '' comment '备注',
  index idx_task_key (task_key),
  index idx_task_group (task_group),
  index idx_enabled (enabled)
) comment ='定时任务配置表';
drop table if exists log_task;
create table log_task (
  id                bigint auto_increment primary key comment '主键',
  task_id           bigint                                not null comment '关联的任务id',
  execution_id      varchar(255)                          not null comment '本次执行的唯一ID',
  start_time        datetime                              not null comment '任务开始执行时间',
  end_time          datetime comment '任务结束执行时间',
  duration_ms       bigint comment '任务执行耗时 (毫秒)',
  status            enum ('RUNNING', 'SUCCESS', 'FAILED') not null default 'RUNNING' comment '执行状态',
  result_message    text comment '执行结果消息',
  error_stack_trace text comment '错误堆栈信息 (如果失败)',
  thread_name       varchar(255) comment '执行线程名',
  create_time       datetime                              not null default current_timestamp comment '日志创建时间',
  index idx_task_id (task_id),
  index idx_execution_id (execution_id),
  index idx_start_time (start_time),
  index idx_status (status)
) comment ='定时任务执行日志表';