INSERT INTO sys_user (id, username, real_name, password)
VALUES (1, 'admin', '管理员', '$2b$12$RaBkMzfs5s7kWWaOxO4vLu6bf6HCkL8.s9U76B1wCuyym6YT2lxqq');

insert into sys_menu (ID, MENU_NAME, PARENT_ID, SORT, PATH, COMPONENT, QUERY_PARAM, FOREIGN_LINK, CACHED, MENU_TYPE, VISIBLE, ENABLED, PERMS, ICON, ACTIVE_MENU)
values (1, '系统管理', 0, 1, 'system', null, null, false, true, 'DIRECTORY', true, true, null, 'Menu', ''),
       (2, '系统工具', 0, 4, 'tool', null, null, false, true, 'DIRECTORY', true, true, '', 'Menu', ''),
       (3, '测试目录', 0, 100, 'testMenu', null, null, false, true, 'DIRECTORY', true, true, null, 'Menu', ''),
       (4, '配置管理', 1, 1, 'sys-config', '/system/sys-config', null, false, true, 'MENU', true, true, 'system:sysConfig:query', 'Menu', ''),
       (6, '菜单管理', 1, 3, 'sys-menu', '/system/sys-menu', null, false, true, 'MENU', true, true, 'system:SysMenu:query', 'Menu', ''),
       (7, '角色管理', 1, 4, 'sys-role', '/system/sys-role', null, false, true, 'MENU', true, true, 'system:SysRole:query', 'Menu', ''),
       (8, '用户管理', 1, 5, 'sys-user', '/system/sys-user', null, false, true, 'MENU', true, true, 'system:SysUser:query', 'Menu', ''),
       (9, 'bing', 1, 9, 'https://bing.com', null, null, true, true, 'DIRECTORY', true, true, '', 'Menu', ''),
       (10, '配置管理-新增', 4, 1, '', null, null, false, true, 'BUTTON', true, true, 'system:sysConfig:save', '', ''),
       (11, '配置管理-修改', 4, 2, '', null, null, false, true, 'BUTTON', true, true, 'system:sysConfig:save', '', ''),
       (12, '配置管理-删除', 4, 3, '', null, null, false, true, 'BUTTON', true, true, 'system:sysConfig:delete', '', ''),
       (13, '配置管理-导入', 4, 4, '', null, null, false, true, 'BUTTON', true, true, 'system:sysConfig:import', '', ''),
       (14, '配置管理-导出', 4, 5, '', null, null, false, true, 'BUTTON', true, true, 'system:sysConfig:export', '', ''),
       (20, '菜单管理-删除', 6, 3, '', null, null, false, true, 'BUTTON', true, true, 'system:SysMenu:delete', '', ''),
       (21, '菜单管理-导出', 6, 5, '', null, null, false, true, 'BUTTON', true, true, 'system:SysMenu:export', '', ''),
       (22, '菜单管理-新增', 6, 1, '', null, null, false, true, 'BUTTON', true, true, 'system:SysMenu:save', '', ''),
       (23, '菜单管理-导入', 6, 4, '', null, null, false, true, 'BUTTON', true, true, 'system:SysMenu:import', '', ''),
       (24, '菜单管理-修改', 6, 2, '', null, null, false, true, 'BUTTON', true, true, 'system:SysMenu:save', '', ''),
       (25, '角色管理-导入', 7, 4, '', null, null, false, true, 'BUTTON', true, true, 'system:SysRole:import', '', ''),
       (26, '角色管理-删除', 7, 3, '', null, null, false, true, 'BUTTON', true, true, 'system:SysRole:delete', '', ''),
       (27, '角色管理-修改', 7, 2, '', null, null, false, true, 'BUTTON', true, true, 'system:SysRole:save', '', ''),
       (28, '角色管理-新增', 7, 1, '', null, null, false, true, 'BUTTON', true, true, 'system:SysRole:save', '', ''),
       (29, '角色管理-导出', 7, 5, '', null, null, false, true, 'BUTTON', true, true, 'system:SysRole:export', '', ''),
       (30, '用户管理-修改', 8, 2, '', null, null, false, true, 'BUTTON', true, true, 'system:SysUser:save', '', ''),
       (31, '用户管理-导入', 8, 4, '', null, null, false, true, 'BUTTON', true, true, 'system:SysUser:import', '', ''),
       (32, '用户管理-新增', 8, 1, '', null, null, false, true, 'BUTTON', true, true, 'system:SysUser:save', '', ''),
       (33, '用户管理-删除', 8, 3, '', null, null, false, true, 'BUTTON', true, true, 'system:SysUser:delete', '', ''),
       (34, '用户管理-导出', 8, 5, '', null, null, false, true, 'BUTTON', true, true, 'system:SysUser:export', '', ''),
       (35, '代码生成', 2, 2, 'gen', '/gen/index', null, false, true, 'MENU', true, true, 'tool:gen:list', 'Menu', ''),
       (36, '修改生成配置', 2, 2, 'gen-edit/index/:tableId', '/gen/EditTable', null, false, false, 'MENU', false, true, 'tool:gen:edit', 'Menu', '/tool/gen'),
       (37, '测试菜单外链', 3, 1, 'https://www.baidu.com/', null, null, true, true, 'MENU', true, true, null, 'Menu', ''),
       (38, '测试目录外链', 3, 2, 'https://www.zhihu.com/', null, null, true, true, 'DIRECTORY', true, true, null, 'Menu', '');


