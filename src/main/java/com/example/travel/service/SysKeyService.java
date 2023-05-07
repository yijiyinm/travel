package com.example.travel.service;

import com.example.travel.dao.entity.SysKeyDO;

/**
 * @author yijiyin
 */
public interface SysKeyService {
    /**
     * 根据key获取
     * @param key
     * @return
     */
    SysKeyDO getInfoByKey(String key);
}
