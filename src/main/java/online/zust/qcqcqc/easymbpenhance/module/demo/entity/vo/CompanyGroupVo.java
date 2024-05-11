package online.zust.qcqcqc.easymbpenhance.module.demo.entity.vo;


import lombok.Data;
import online.zust.qcqcqc.utils.annotation.convert.CustomConvert;
import online.zust.qcqcqc.utils.annotation.convert.FromField;

import java.util.List;

/**
 * @author qcqcqc
 * Date: 2024/5/11
 * Time: 下午1:55
 */
@CustomConvert
@Data
public class CompanyGroupVo {
    private String id;
    private String createTime;
    private String updateTime;
    private String name;
    /**
     * 因为不是内置类型，所以需要自定义转换，添加@FromField注解
     */
    @FromField(fieldPath = "companies")
    private List<CompanyVo> companyList;
}
