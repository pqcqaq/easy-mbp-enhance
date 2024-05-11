package online.zust.qcqcqc.easymbpenhance.module.attachments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.zust.qcqcqc.services.entity.dto.PageResult;
import online.zust.qcqcqc.services.exception.ServiceException;
import online.zust.qcqcqc.services.module.attachment.entity.Attachment;
import online.zust.qcqcqc.services.module.attachment.entity.dto.FileUpload;
import online.zust.qcqcqc.services.module.attachment.exception.AttachmentServiceException;
import online.zust.qcqcqc.services.module.attachment.service.AttachmentService;
import online.zust.qcqcqc.services.module.log.annotation.OperationLog;
import online.zust.qcqcqc.utils.exception.DependencyCheckException;
import online.zust.qcqcqc.utils.threads.Promise;
import online.zust.qcqcqc.utils.threads.Tasks;
import online.zust.qcqcqc.utils.utils.BeanConvertUtils;
import online.zust.qcqcqc.easymbpenhance.module.attachments.entity.vo.AttachmentVo;
import online.zust.qcqcqc.easymbpenhance.utils.StringUploadUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author pqcmm
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MyAttachmentsServiceImpl {
    private final AttachmentService attachmentService;

    @OperationLog("'上传文件' + #file.fileName")
    public void uploadFile(FileUpload file) {
        attachmentService.saveFileToAttachment(file);
    }

    public PageResult<AttachmentVo> pageAttachment(Integer page, Integer size, String tag, String keyword) {
        LambdaQueryWrapper<Attachment> like = new LambdaQueryWrapper<Attachment>().like(
                        (keyword != null && !keyword.trim().isEmpty())
                        , Attachment::getOriginalFilename, keyword)
                .orderByDesc(Attachment::getCreateTime);
        like.eq(StringUploadUtils.isParamConcluded(tag), Attachment::getTag, tag);
        Page<Attachment> pageResult = attachmentService.pageByLambda(page, size, like);
        List<Attachment> records = pageResult.getRecords();
        List<AttachmentVo> list = Tasks.startWithMultiThreadsSync(records, attachment -> {
            return BeanConvertUtils.objectConvent(attachment, AttachmentVo.class);
        });
        return new PageResult<>(pageResult, list);
    }

    @OperationLog("'上传文件' + #file.originalFilename")
    public AttachmentVo saveFileToAttachment(MultipartFile file) {
        try {
            Attachment attachment = attachmentService.saveFileToAttachment(file);
            return BeanConvertUtils.objectConvent(attachment, AttachmentVo.class);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new ServiceException("文件上传失败");
        }
    }

    @OperationLog("'删除文件:' + #id")
    public boolean checkAndRemove(Long id) {
        Attachment attachment = attachmentService.getById(id);
        if (attachment == null) {
            throw new AttachmentServiceException("文件不存在");
        }
        // 检查是否存在依赖（交给框架解决）
        boolean b = attachmentService.removeById(id);
        // 删除文件
        attachmentService.deleteByFileName(attachment.getOriginalFilename());
        return b;
    }

    @OperationLog("'上传文件并转换为附件：' + #returnValue.originalFilename")
    public AttachmentVo upload2attachment(FileUpload file) {
        // 上传文件
        Attachment attachment = attachmentService.saveFileToAttachment(file);
        return BeanConvertUtils.objectConvent(attachment, AttachmentVo.class);
    }

    @OperationLog("'清理无关附件：' + #returnValue ")
    public String clearUnrelatedAttachments() {
        List<Attachment> attachments = attachmentService.list();
        AtomicLong count = new AtomicLong();
        AtomicLong undel = new AtomicLong();
        List<Promise<Object>> promises = Tasks.startWithMultiThreadsAsync(attachments, attachment -> {
            try {
                attachmentService.removeById(attachment.getId());
                attachmentService.deleteByFileName(attachment.getOriginalFilename());
                count.getAndIncrement();
            } catch (ServiceException | DependencyCheckException e) {
                undel.getAndIncrement();
            }
            return null;
        });
        Tasks.awaitAll(promises);
        return undel.get() + "个文件正在使用，清理完成:" + count.get() + "个文件";
    }
}
