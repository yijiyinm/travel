package com.example.travel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.dao.DistributionAuditMapper;
import com.example.travel.dao.entity.DistributionAuditDO;
import com.example.travel.dao.entity.OrderDO;
import com.example.travel.dao.entity.UserDO;
import com.example.travel.dto.DistributionDTO;
import com.example.travel.enums.OrderStatusEnum;
import com.example.travel.service.DistributionAuditService;
import com.example.travel.service.OrderService;
import com.example.travel.util.GenerateCodeUtil;
import com.wechat.pay.java.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yijiyin
 */
@Slf4j
@Service("distributionAuditService")
public class DistributionAuditServiceImpl extends ServiceImpl<DistributionAuditMapper,DistributionAuditDO> implements DistributionAuditService {

    @Autowired
    private UserService userService;
    @Override
    public Boolean distributionRequest(DistributionDTO distributionDTO,String openId) {
        try {
            DistributionAuditDO auditDO = new DistributionAuditDO();
            auditDO.setOpenId(openId);
            auditDO.setPhone(distributionDTO.getPhone());
            auditDO.setRemark(distributionDTO.getRemark());
            auditDO.setStatus(OrderStatusEnum.WAIT_PAY.getStatus());
            auditDO.insert();
            return true;
        } catch (Exception e) {
            log.error("分销申请失败");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Page<DistributionDTO> getDistributionList(DistributionDTO distributionDTO) {
        try {
            Page<DistributionAuditDO> page = new Page(distributionDTO.getCurrent(),distributionDTO.getSize());
            Page<DistributionAuditDO> auditDOPage = page(page,Wrappers.<DistributionAuditDO>lambdaQuery()
                    .eq(distributionDTO.getStatus()!=null,DistributionAuditDO::getStatus,distributionDTO.getStatus())
                    .like(distributionDTO.getPhone()!=null,DistributionAuditDO::getPhone,distributionDTO.getPhone())
                    .orderByDesc(DistributionAuditDO::getCreateDate));

            List<DistributionAuditDO> distributionAuditDOS = auditDOPage.getRecords();

            List<DistributionDTO> distributionDTOS = new ArrayList<>();
            for (DistributionAuditDO auditDO : distributionAuditDOS){
                DistributionDTO dto = new DistributionDTO();
                dto.setPhone(auditDO.getPhone());
                dto.setRemark(auditDO.getRemark());
                dto.setStatus(auditDO.getStatus());
                dto.setCreateDate(auditDO.getCreateDate());
                dto.setId(auditDO.getId());
                distributionDTOS.add(dto);
            }

            Page<DistributionDTO> distributionDTOPage = new Page<>();
            distributionDTOPage.setTotal(auditDOPage.getTotal());
            distributionDTOPage.setRecords(distributionDTOS);
            return distributionDTOPage;
        } catch (Exception e) {
            log.error("分销列表查询异常");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean distributionAudit(Long id, Integer status) {
        try {
            DistributionAuditDO distributionAuditDO = getById(id);
            if (OrderStatusEnum.ALREADY_PAY.getStatus().equals(status)){
                // 生成分销商编号 将分销商手机号存入小程序用户表内
                String code ="FX"+GenerateCodeUtil.createCode(3)+distributionAuditDO.getPhone();
                UserDO userDO = userService.getUserInfoByOpenId(distributionAuditDO.getOpenId());
                if (StringUtils.isNotEmpty(userDO.getFxsCode())) {
                    userDO.setFxsIs(true);
                    userDO.setPhone(distributionAuditDO.getPhone());
                    //userDO.updateById();
                    userService.updateByIds(userDO);
                } else {
                    userService.updateFXS(distributionAuditDO.getOpenId(),code,distributionAuditDO.getPhone());
                }
            } else if (OrderStatusEnum.FAILURE_PAY.getStatus().equals(status)){
            } else {
                log.error("审核状态错误");
                return false;
            }
            distributionAuditDO.setStatus(status);
            distributionAuditDO.updateById();
            return true;
        } catch (Exception e) {
            log.error("分销商审核失败");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public DistributionDTO getListByOpenId(String openId) {

        List<DistributionAuditDO> distributionAuditDOS = list(Wrappers.<DistributionAuditDO>lambdaQuery()
                .eq(DistributionAuditDO::getOpenId,openId)
                .orderByDesc(DistributionAuditDO::getCreateDate));
        if (distributionAuditDOS != null && distributionAuditDOS.size() > 0) {
            DistributionDTO dto = new DistributionDTO();
            dto.setStatus(distributionAuditDOS.get(0).getStatus());
            return dto;
        }
        return null;
    }
}
