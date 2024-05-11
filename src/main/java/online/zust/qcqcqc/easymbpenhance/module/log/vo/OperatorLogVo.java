package online.zust.qcqcqc.easymbpenhance.module.log.vo;

import lombok.Data;
import online.zust.qcqcqc.services.module.log.enums.LogLevel;
import online.zust.qcqcqc.utils.annotation.convert.CustomConvert;

/**
 * @author qcqcqc
 * Date: 2024/4/10
 * Time: 23:34
 */
@Data
@CustomConvert
public class OperatorLogVo {
    private String id;
    private LogLevel level;
    private String msg;
    private String createTime;
    private String createBy;
    private Boolean success;
    private String cause;
    private String metadata;
}
