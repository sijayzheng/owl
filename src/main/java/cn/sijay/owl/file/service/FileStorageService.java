package cn.sijay.owl.file.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.file.dto.FileStorageQuery;
import cn.sijay.owl.file.entity.FileStorage;
import cn.sijay.owl.file.mapper.FileStorageMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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
     * @param pageQuery            分页参数
     * @param fileStorageQuery 查询条件
     * @return 文件存储分页数据
     */
    public Page<FileStorage> page(PageQuery pageQuery, FileStorageQuery fileStorageQuery) {
        return page(pageQuery.build(), pageQuery.setOrder(query(fileStorageQuery)));
    }

    /**
     * 构建查询条件
     *
     * @param fileStorageQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(FileStorageQuery fileStorageQuery) {
        QueryWrapper query = query();
        query.and(FILE_STORAGE.ORIGINAL_NAME.like(fileStorageQuery.originalName()));
        query.and(FILE_STORAGE.FILE_SUFFIX.like(fileStorageQuery.fileSuffix()));
        query.and(FILE_STORAGE.CONTENT_TYPE.like(fileStorageQuery.contentType()));
        return query;
    }

    /**
     * 查询文件存储列表
     *
     * @param fileStorageQuery 查询条件
     * @return 文件存储列表
     */
    public List<FileStorage> list(FileStorageQuery fileStorageQuery) {
        return list(query(fileStorageQuery));
    }

    /**
     * 校验并保存文件存储
     *
     * @param fileStorage 文件存储实体
     * @return 保存结果
     */
    public boolean validSave(FileStorage fileStorage) {
        return saveOrUpdate(fileStorage);
    }
}
