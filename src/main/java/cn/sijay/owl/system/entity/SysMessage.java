package cn.sijay.owl.system.entity;

import cn.sijay.owl.common.entity.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

/**
 * 系统消息实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_message", comment = "消息表")
public class SysMessage extends BaseEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 消息标题
     */
    @Column(value = "message_title", comment = "消息标题")
    @NotBlank(message = "消息标题不能为空")
    @ExcelProperty(value = "消息标题")
    private String messageTitle;

    /**
     * 消息内容
     */
    @Column(value = "message_content", comment = "消息内容")
    @ExcelProperty(value = "消息内容")
    private String messageContent;

    /**
     * 消息类型
     */
    @Column(value = "message_type", comment = "消息类型")
    @ExcelProperty(value = "消息类型")
    private String messageType;

    /**
     * 发送者
     */
    @Column(value = "sender", comment = "发送者")
    @NotNull(message = "发送者不能为空")
    @ExcelProperty(value = "发送者")
    private Long sender;

    /**
     * 接收者
     */
    @Column(value = "recipient", comment = "接收者")
    @NotNull(message = "接收者不能为空")
    @ExcelProperty(value = "接收者")
    private Long recipient;

    /**
     * 已读
     */
    @Column(value = "has_read", comment = "已读")
    @ExcelProperty(value = "已读")
    private Boolean hasRead;

    /**
     * 删除
     */
    @Column(value = "deleted", comment = "删除")
    private Boolean deleted;

}
