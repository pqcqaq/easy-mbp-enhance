package online.zust.qcqcqc.easymbpenhance.module.attachments.entity.vo;

import lombok.Data;
import online.zust.qcqcqc.utils.annotation.convert.CustomConvert;
import online.zust.qcqcqc.utils.annotation.convert.RequireDefault;

import java.util.Date;

/**
 * @author qcqcqc
 * @date 2024/03
 * @time 18-26-13
 */
@Data
@CustomConvert
public class AttachmentVo {
    private Long id;
    private String originalFilename;
    private String url;
    private String md5;
    private String tag;
    private Long createBy;
    private Date createTime;
    /**
     * 是否允许用户操作，主要是前端界面上的按钮是否可用
     */
    @RequireDefault(value = "true")
    private boolean operable;
}
