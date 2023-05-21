package com.example.travel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.travel.aop.Authority;
import com.example.travel.aop.AuthorityType;
import com.example.travel.cache.CacheManager;
import com.example.travel.dto.DistributionDTO;
import com.example.travel.service.DistributionAuditService;
import com.example.travel.util.BaseRespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yijiyin
 */
@Slf4j
@RestController
@RequestMapping("/distribution")
@Authority(authoritytype = AuthorityType.CHECK_LOGIN)
public class DistributionAuditController {

    @Autowired
    private DistributionAuditService distributionAuditService;

    /**
     * 分销商申请
     * @return
     */
    @PostMapping("distributionRequest")
    public BaseRespResult distributionRequest(@RequestBody DistributionDTO distributionDTO, HttpServletRequest request) {
        // 获取 用户openId 传入
        String tokeninfo = request.getHeader("tokeninfo");

        String openId = CacheManager.get(tokeninfo);
        boolean ret = distributionAuditService.distributionRequest(distributionDTO,openId);
        if (ret){
            return BaseRespResult.successResult("申请成功");
        }
        return BaseRespResult.errorResult("申请失败");
    }

    /**
     * 查询申请列表
     */
    @PostMapping("getDistributionList")
    public BaseRespResult getDistributionList(@RequestBody DistributionDTO distributionDTO){
        Page<DistributionDTO> distributionDTOS = distributionAuditService.getDistributionList(distributionDTO);
        return BaseRespResult.successResult(distributionDTOS);
    }

    /**
     * 分销商审核
     * @return
     */
    @PostMapping("distributionAudit")
    public BaseRespResult distributionAudit(@RequestBody DistributionDTO distributionDTO) {
        boolean ret = distributionAuditService.distributionAudit(distributionDTO.getId(),distributionDTO.getStatus());
        if (ret){
            return BaseRespResult.successResult("审核成功");
        }
        return BaseRespResult.errorResult("审核失败");
    }

}
