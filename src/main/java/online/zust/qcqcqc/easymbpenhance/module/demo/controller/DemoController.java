package online.zust.qcqcqc.easymbpenhance.module.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import online.zust.qcqcqc.easymbpenhance.module.demo.entity.CompanyGroup;
import online.zust.qcqcqc.easymbpenhance.module.demo.entity.vo.CompanyGroupVo;
import online.zust.qcqcqc.easymbpenhance.module.demo.service.CompanyGroupService;
import online.zust.qcqcqc.services.entity.dto.PageResult;
import online.zust.qcqcqc.services.module.log.annotation.OperationLog;
import online.zust.qcqcqc.utils.threads.Tasks;
import online.zust.qcqcqc.utils.utils.BeanConvertUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qcqcqc
 * Date: 2024/5/11
 * Time: 下午1:47
 */
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    private final CompanyGroupService companyGroupService;

    /**
     * 分页查询公司组
     *
     * @param page 页码
     * @param size 每页大小
     * @return 公司组列表
     */
    @GetMapping("/companyGroup")
    @OperationLog("'分页查询公司组，分页参数为：' + #page + ' ' + #size")
    public PageResult<CompanyGroupVo> pageCompanyGroup(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        Page<CompanyGroup> comToGroupRelationPage = companyGroupService.pageByLambda(page, size, null);
        List<CompanyGroup> records = comToGroupRelationPage.getRecords();
        List<CompanyGroupVo> companyGroupVos = Tasks.startWithMultiThreadsSync(records, comToGroupRelation -> {
            return BeanConvertUtils.objectConvent(comToGroupRelation, CompanyGroupVo.class);
        });
        return new PageResult<>(comToGroupRelationPage, companyGroupVos);
    }
}
