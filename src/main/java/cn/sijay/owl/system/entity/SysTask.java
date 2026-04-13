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

import java.time.LocalDateTime;
/**
 * 任务配置实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_task", comment = "定时任务配置表")
public class SysTask extends BaseEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 任务唯一标识符
     */
    @Column(value = "task_key", comment = "任务唯一标识符")
    @NotBlank(message = "任务唯一标识符不能为空")
    @ExcelProperty(value = "任务唯一标识符")
    private String taskKey;

    /**
     * 任务名称
     */
    @Column(value = "task_name", comment = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    @ExcelProperty(value = "任务名称")
    private String taskName;

    /**
     * 任务分组
     */
    @Column(value = "task_group", comment = "任务分组")
    @ExcelProperty(value = "任务分组")
    private String taskGroup;

    /**
     * 任务描述
     */
    @Column(value = "description", comment = "任务描述")
    @ExcelProperty(value = "任务描述")
    private String description;

    /**
     * 调度类型 (CRON, FIXED_DELAY, FIXED_RATE)
     */
    @Column(value = "schedule_type", comment = "调度类型 ")
    @NotBlank(message = "调度类型 不能为空")
    @ExcelProperty(value = "调度类型 ")
    private String scheduleType;

    /**
     * Cron 表达式
     */
    @Column(value = "cron_expression", comment = "Cron 表达式")
    @ExcelProperty(value = "Cron 表达式")
    private String cronExpression;

    /**
     * 固定延迟时间 (毫秒) (如果使用 fixedDelay)
     */
    @Column(value = "fixed_delay", comment = "固定延迟时间 ")
    @ExcelProperty(value = "固定延迟时间 ")
    private String fixedDelay;

    /**
     * 初始延迟时间 (毫秒) (如果使用 fixedDelay 或 fixedRate)
     */
    @Column(value = "initial_delay", comment = "初始延迟时间 ")
    @ExcelProperty(value = "初始延迟时间 ")
    private String initialDelay;

    /**
     * 固定频率时间 (毫秒) (如果使用 fixedRate)
     */
    @Column(value = "fixed_rate", comment = "固定频率时间 ")
    @ExcelProperty(value = "固定频率时间 ")
    private String fixedRate;

    /**
     * 是否启用
     */
    @Column(value = "enabled", comment = "是否启用")
    @ExcelProperty(value = "是否启用")
    private Boolean enabled;

    /**
     * 是否允许并发执行
     */
    @Column(value = "concurrent", comment = "是否允许并发执行")
    @ExcelProperty(value = "是否允许并发执行")
    private Boolean concurrent;

    /**
     * 任务失败时是否发送通知
     */
    @Column(value = "notify_on_failure", comment = "任务失败时是否发送通知")
    @ExcelProperty(value = "任务失败时是否发送通知")
    private Boolean notifyOnFailure;

    /**
     * Spring Bean类名 (包含包路径)
     */
    @Column(value = "bean_class", comment = "Spring Bean类名 ")
    @NotBlank(message = "Spring Bean类名 不能为空")
    @ExcelProperty(value = "Spring Bean类名 ")
    private String beanClass;

    /**
     * 执行方法名
     */
    @Column(value = "method_name", comment = "执行方法名")
    @NotBlank(message = "执行方法名不能为空")
    @ExcelProperty(value = "执行方法名")
    private String methodName;

    /**
     * 执行方法参数
     */
    @Column(value = "method_params", comment = "执行方法参数")
    @ExcelProperty(value = "执行方法参数")
    private String methodParams;

    /**
     * 接续任务id
     */
    @Column(value = "next_task_id", comment = "接续任务id")
    @NotNull(message = "接续任务id不能为空")
    @ExcelProperty(value = "接续任务id")
    private Long nextTaskId;

    /**
     * 等待成功
     */
    @Column(value = "wait_success", comment = "等待成功")
    @ExcelProperty(value = "等待成功")
    private Boolean waitSuccess;

    /**
     * 备注
     */
    @Column(value = "remark", comment = "备注")
    @ExcelProperty(value = "备注")
    private String remark;

}
