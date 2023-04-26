package com.example.travel.param;

import lombok.Data;

/**
 * @author yijiyin
 */
@Data
public class SelUserListParam {

    private Long size;
    private Long current;

    /**
     * 是否分销商
     */
    private Boolean distributionIs;
}
