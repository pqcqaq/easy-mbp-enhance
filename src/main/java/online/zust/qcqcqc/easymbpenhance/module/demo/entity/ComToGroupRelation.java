package online.zust.qcqcqc.easymbpenhance.module.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import online.zust.qcqcqc.utils.entity.BaseEntity;

import java.io.Serial;

/**
 * @author qcqcqc
 * Date: 2024/5/11
 * Time: 下午1:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "com_to_group_relation")
public class ComToGroupRelation extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 7180028905952210818L;
    private Long companyId;
    private Long companyGroupId;
}
