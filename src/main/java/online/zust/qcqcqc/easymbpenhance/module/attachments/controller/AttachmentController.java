package online.zust.qcqcqc.easymbpenhance.module.attachments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.zust.qcqcqc.services.entity.dto.PageResult;
import online.zust.qcqcqc.services.entity.dto.Result;
import online.zust.qcqcqc.services.module.attachment.entity.dto.FileUpload;
import online.zust.qcqcqc.easymbpenhance.module.attachments.entity.vo.AttachmentVo;
import online.zust.qcqcqc.easymbpenhance.module.attachments.service.impl.MyAttachmentsServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pqcmm
 */
@RestController
@RequestMapping("/attachment")
@Slf4j
@RequiredArgsConstructor
public class AttachmentController {
    private final MyAttachmentsServiceImpl attachmentsService;

    /**
     * 上传文件
     *
     * @param file base64, fileName, fileType, tag
     * @return success
     */
    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestBody FileUpload file) {
        attachmentsService.uploadFile(file);
        return Result.success("success");
    }

    /**
     * 上传文件并返回附件信息
     *
     * @param file base64, fileName, fileType, tag
     * @return 附件信息
     */
    @PostMapping("/upload2attachment")
    public Result<AttachmentVo> upload2attachment(@RequestBody FileUpload file) {
        return Result.success(attachmentsService.upload2attachment(file));
    }

    /**
     * 上传文件并返回附件信息
     *
     * @param file base64, fileName, fileType, tag
     * @return 附件信息
     */
    @PostMapping("/uploadMultiPart2attachment")
    public Result<AttachmentVo> upload2attachment(MultipartFile file) {
        return Result.success(attachmentsService.saveFileToAttachment(file));
    }

    /**
     * 分页查询附件
     *
     * @param page    页码
     * @param size    每页大小
     * @param keyword 关键字
     * @return 附件列表
     */
    @GetMapping("/page")
    public Result<PageResult<AttachmentVo>> pageAttachment(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam String tag,
            @RequestParam(required = false) String keyword) {
        return Result.success(attachmentsService.pageAttachment(page, size, tag, keyword));
    }

    /**
     * 删除附件
     *
     * @param id 附件id
     * @return success
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteAttachment(@PathVariable Long id) {
        attachmentsService.checkAndRemove(id);
        // 要检查是否存在关联的图片关系
        return Result.success("删除成功");
    }

    /**
     * 清除无关联的附件
     *
     * @return success
     */
    @DeleteMapping("/clearUnrelatedAttachments")
    public Result<String> clearUnrelatedAttachments() {
        String msg = attachmentsService.clearUnrelatedAttachments();
        return Result.success(msg);
    }
}
