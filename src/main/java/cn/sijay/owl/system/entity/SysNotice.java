package cn.sijay.owl.system.entity;

import cn.sijay.owl.common.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

/**
 * 通知公告实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_notice", comment = "通知公告表")
public class SysNotice extends BaseEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 公告标题
     */
    @Column(value = "notice_title", comment = "公告标题")
    @NotBlank(message = "公告标题不能为空")
    @ExcelProperty(value = "公告标题")
    private String noticeTitle;

    /**
     * 公告类型
     */
    @Column(value = "notice_type", comment = "公告类型")
    @NotBlank(message = "公告类型不能为空")
    @ExcelProperty(value = "公告类型")
    private String noticeType;

    /**
     * 公告内容
     */
    @Column(value = "notice_content", comment = "公告内容")
    @ExcelProperty(value = "公告内容")
    private byte[] noticeContent;

    /**
     * 是否关闭
     */
    @Column(value = "closed", comment = "是否关闭")
    @ExcelProperty(value = "是否关闭")
    private Boolean closed;

    /**
     * 删除
     */
    @Column(value = "deleted", comment = "删除")
    private Boolean deleted;

}
