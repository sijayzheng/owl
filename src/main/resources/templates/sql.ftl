INSERT INTO owl.sys_menu (menu_name, parent_id, sort, path, component, menu_type, perms, icon) VALUES
('${classComment}', 1, 0, '${path}', '${moduleName}/${functionName}', 'MENU', '', 'Menu');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

INSERT INTO owl.sys_menu (menu_name, parent_id, sort, path, component, menu_type, perms, icon) VALUES
('${classComment}-查询', @parentId, 0, '', '', 'BUTTON', '${moduleName}:${functionName}:query', '#'),
('${classComment}-修改', @parentId, 1, '', '', 'BUTTON', '${moduleName}:${functionName}:add', '#'),
('${classComment}-修改', @parentId, 2, '', '', 'BUTTON', '${moduleName}:${functionName}:update', '#'),
('${classComment}-删除', @parentId, 3, '', '', 'BUTTON', '${moduleName}:${functionName}:delete', '#'),
('${classComment}-导入', @parentId, 4, '', '', 'BUTTON', '${moduleName}:${functionName}:import', '#'),
('${classComment}-导出', @parentId, 5, '', '', 'BUTTON', '${moduleName}:${functionName}:export', '#');







