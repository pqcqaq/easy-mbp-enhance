package online.zust.qcqcqc.easymbpenhance.module.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import online.zust.qcqcqc.easymbpenhance.module.demo.service.CompanyMangerService;
import online.zust.qcqcqc.services.module.attachment.entity.Attachment;
import online.zust.qcqcqc.services.module.attachment.service.impl.AttachmentsServiceImpl;
import online.zust.qcqcqc.services.module.user.entity.User;
import online.zust.qcqcqc.services.module.user.service.impl.UserServiceImpl;
import online.zust.qcqcqc.utils.annotation.MtMDeepSearch;
import online.zust.qcqcqc.utils.annotation.OtODeepSearch;
import online.zust.qcqcqc.utils.entity.BaseEntity;

import java.io.Serial;
import java.util.List;

/**
 * @author qcqcqc
 * Date: 2024/5/11
 * Time: 下午1:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "company")
public class Company extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -3312096225703137705L;

    private String name;
    private String address;
    private Long imageId;

    /**
     * 查询关联的管理员
     */
    @TableField(exist = false)
    @MtMDeepSearch(baseId = "companyId", targetId = "userId", relaService = CompanyMangerService.class, targetService = UserServiceImpl.class)
    private List<User> managers;

    /**
     * 查询关联的图片
     */
    @TableField(exist = false)
    @OtODeepSearch(baseId = "imageId", service = AttachmentsServiceImpl.class)
    private Attachment image;
}
