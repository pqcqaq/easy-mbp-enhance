package online.zust.qcqcqc.easymbpenhance.module.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import online.zust.qcqcqc.utils.entity.BaseEntity;

import java.io.Serial;

/**
 * @author qcqcqc
 * Date: 2024/5/11
 * Time: 下午1:52
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "company_manager")
@Data
public class CompanyManager extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 5216735745719885808L;

    private Long companyId;
    private Long userId;
}
