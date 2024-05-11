package online.zust.qcqcqc.easymbpenhance.module.demo.entity.vo;

import lombok.Data;
import online.zust.qcqcqc.services.module.user.entity.vo.UserVo;
import online.zust.qcqcqc.utils.annotation.convert.CustomConvert;
import online.zust.qcqcqc.utils.annotation.convert.FromField;

import java.util.List;

/**
 * @author qcqcqc
 * Date: 2024/5/11
 * Time: 下午1:56
 */
@CustomConvert
@Data
public class CompanyVo {
    private String id;
    private String name;

    /**
     * 自定义转换
     * 这里从Company实体类的image字段中获取url字段的值
     */
    @FromField(fieldPath = "image.url")
    private String imageUrl;
    private String createTime;
    private String updateTime;
    private String address;

    /**
     * 自定义数组类型转换
     * 因为是非内置类型，所以需要自定义转换，添加@FromField注解
     */
    @FromField(fieldPath = "managers")
    private List<UserVo> managers;
}
