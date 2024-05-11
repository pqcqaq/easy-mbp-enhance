package online.zust.qcqcqc.easymbpenhance.module.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import online.zust.qcqcqc.easymbpenhance.module.demo.service.ComToGroupRelationService;
import online.zust.qcqcqc.easymbpenhance.module.demo.service.CompanyService;
import online.zust.qcqcqc.utils.annotation.MsgOnCheckError;
import online.zust.qcqcqc.utils.annotation.MtMDeepSearch;
import online.zust.qcqcqc.utils.entity.BaseEntity;

import java.io.Serial;
import java.util.List;

/**
 * @author qcqcqc
 * Date: 2024/5/11
 * Time: 下午1:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "company_group")
public class CompanyGroup extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -5174610025802100921L;

    private String name;
    @TableField(exist = false)
    @MtMDeepSearch(baseId = "companyGroupId", targetId = "companyId", relaService = ComToGroupRelationService.class, targetService = CompanyService.class)
    @MsgOnCheckError("还有公司存在该分组中，不可删除")
    private List<Company> companies;
}
