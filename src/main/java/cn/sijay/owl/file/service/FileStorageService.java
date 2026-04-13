package cn.sijay.owl.file.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.file.entity.FileStorage;
import cn.sijay.owl.file.mapper.FileStorageMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.file.entity.table.FileStorageTableDef.FILE_STORAGE;

/**
 * 文件存储服务类
 * 提供文件存储的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FileStorageService extends ServiceImpl<FileStorageMapper, FileStorage> implements IService<FileStorage> {
    private final FileStorageMapper fileStorageMapper;

    /**
     * 分页查询文件存储
     *
     * @param pageQuery 分页参数
     * @param fileStorage 查询条件
     * @return 文件存储分页数据
     */
    public Page<FileStorage> page(PageQuery pageQuery, FileStorage fileStorage) {
        return page(pageQuery.build(), pageQuery.setOrder(query(fileStorage)));
    }

    /**
     * 构建查询条件
     *
     * @param fileStorage 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(FileStorage fileStorage) {
        QueryWrapper query = query();
        query.and(FILE_STORAGE.FILE_NAME.like(fileStorage.getFileName()));
        query.and(FILE_STORAGE.ORIGINAL_NAME.like(fileStorage.getOriginalName()));
        query.and(FILE_STORAGE.FILE_SUFFIX.like(fileStorage.getFileSuffix()));
        return query;
    }

    /**
     * 查询文件存储列表(不分页)
     *
     * @param fileStorage 查询条件
     * @return 文件存储列表
     */
    public List<FileStorage> list(FileStorage fileStorage) {
        return list(query(fileStorage));
    }

    /**
     * 删除文件存储
     *
     * @param id 文件存储ID
     * @return 删除结果
     * @throws ServiceException 当文件存储不存在时抛出异常
     */
    public boolean delete(Long id) {
        FileStorage user = getById(id);
        if (user == null) {
            throw new ServiceException(FileStorageService.class, "主键为{}的文件存储不存在", id);
        }
        return removeById(id);
    }
}
