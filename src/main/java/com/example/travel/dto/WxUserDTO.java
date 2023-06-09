package com.example.travel.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author yijiyin
 */
@Data
public class WxUserDTO {
    /**
     * 分销商编码
     */
    private String fxsCode;

    /**
     * 分销商申请状态 1 待审核 2，审核通过 3 审核失败 ，4 未申请
     */
    private Integer fxsRequestStatus;

    /**
     * 绑定分销商code
     */
    private String bindfxsCode;
}
