package com.example.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.travel.dto.DistributionDTO;

import java.util.List;

/**
 * @author yijiyin
 */
public interface DistributionAuditService {

    /**
     * 分销商申请
     * @param distributionDTO
     * @param openId
     * @return
     */
    Boolean distributionRequest(DistributionDTO distributionDTO,String openId);

    /**
     * 查询申请列表
     * @param distributionDTO
     * @return
     */
    Page<DistributionDTO> getDistributionList(DistributionDTO distributionDTO);

    /**
     * 分销商审核
     * @param id
     * @param status 审核状态
     * @return
     */
    Boolean distributionAudit(Long id,Integer status);
}
