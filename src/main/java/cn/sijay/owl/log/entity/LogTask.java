package cn.sijay.owl.log.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 任务日志实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "log_task", comment = "定时任务执行日志表")
public class LogTask implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 关联的任务id
     */
    @Column(value = "task_id", comment = "关联的任务id")
    @NotNull(message = "关联的任务id不能为空")
    @ExcelProperty(value = "关联的任务id")
    private Long taskId;

    /**
     * 本次执行的唯一ID
     */
    @Column(value = "execution_id", comment = "本次执行的唯一ID")
    @NotBlank(message = "本次执行的唯一ID不能为空")
    @ExcelProperty(value = "本次执行的唯一ID")
    private String executionId;

    /**
     * 任务开始执行时间
     */
    @Column(value = "start_time", comment = "任务开始执行时间")
    @NotNull(message = "任务开始执行时间不能为空")
    @ExcelProperty(value = "任务开始执行时间")
    private LocalDateTime startTime;

    /**
     * 任务结束执行时间
     */
    @Column(value = "end_time", comment = "任务结束执行时间")
    @ExcelProperty(value = "任务结束执行时间")
    private LocalDateTime endTime;

    /**
     * 任务执行耗时(毫秒)
     */
    @Column(value = "duration_ms", comment = "任务执行耗时")
    @ExcelProperty(value = "任务执行耗时")
    private Long durationMs;

    /**
     * 执行状态
     */
    @Column(value = "status", comment = "执行状态")
    @NotBlank(message = "执行状态不能为空")
    @ExcelProperty(value = "执行状态")
    private String status;

    /**
     * 执行结果消息
     */
    @Column(value = "result_message", comment = "执行结果消息")
    @ExcelProperty(value = "执行结果消息")
    private String resultMessage;

    /**
     * 错误堆栈信息
     */
    @Column(value = "error_stack_trace", comment = "错误堆栈信息")
    @ExcelProperty(value = "错误堆栈信息")
    private String errorStackTrace;

    /**
     * 执行线程名
     */
    @Column(value = "thread_name", comment = "执行线程名")
    @ExcelProperty(value = "执行线程名")
    private String threadName;

    /**
     * 日志创建时间
     */
    @Column(value = "create_time", comment = "日志创建时间")
    @NotNull(message = "日志创建时间不能为空")
    private LocalDateTime createTime;

    private LocalDateTime[] startTimeRange;

}
