package cn.sijay.owl.file.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.file.entity.FileOssStorage;
import cn.sijay.owl.file.mapper.FileOssStorageMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.file.entity.table.FileOssStorageTableDef.FILE_OSS_STORAGE;

/**
 * OSS服务类
 * 提供OSS的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FileOssStorageService extends ServiceImpl<FileOssStorageMapper, FileOssStorage> implements IService<FileOssStorage> {
    private final FileOssStorageMapper fileOssStorageMapper;

    /**
     * 分页查询OSS
     *
     * @param pageQuery      分页参数
     * @param fileOssStorage 查询条件
     * @return OSS分页数据
     */
    public Page<FileOssStorage> page(PageQuery pageQuery, FileOssStorage fileOssStorage) {
        return page(pageQuery.build(), pageQuery.setOrder(query(fileOssStorage)));
    }

    /**
     * 构建查询条件
     *
     * @param fileOssStorage 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(FileOssStorage fileOssStorage) {
        QueryWrapper query = query();
        query.and(FILE_OSS_STORAGE.FILE_NAME.like(fileOssStorage.getFileName()));
        query.and(FILE_OSS_STORAGE.ORIGINAL_NAME.like(fileOssStorage.getOriginalName()));
        query.and(FILE_OSS_STORAGE.FILE_SUFFIX.like(fileOssStorage.getFileSuffix()));
        return query;
    }

    /**
     * 查询OSS列表
     *
     * @param fileOssStorage 查询条件
     * @return OSS列表
     */
    public List<FileOssStorage> list(FileOssStorage fileOssStorage) {
        return list(query(fileOssStorage));
    }

}
