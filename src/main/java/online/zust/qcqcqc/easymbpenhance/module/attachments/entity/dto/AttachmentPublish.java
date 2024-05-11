package online.zust.qcqcqc.easymbpenhance.module.attachments.entity.dto;

import lombok.Data;

/**
 * @author pqcmm
 * @date 2024/03
 * @time 16-38-04
 */
@Data
public class AttachmentPublish {
    private String id;
    private String originalFilename;
    private String url;
    private String md5;
    private String tag;
    private String createTime;
}
