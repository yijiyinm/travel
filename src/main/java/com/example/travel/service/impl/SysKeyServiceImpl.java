package com.example.travel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.dao.SysKeyMapper;
import com.example.travel.dao.entity.SysKeyDO;
import com.example.travel.service.SysKeyService;
import org.springframework.stereotype.Service;

/**
 * @author yijiyin
 */
@Service("sysKeyService")
public class SysKeyServiceImpl extends ServiceImpl<SysKeyMapper, SysKeyDO> implements SysKeyService {
    @Override
    public SysKeyDO getInfoByKey(String key) {
        return getOne(Wrappers.<SysKeyDO>lambdaQuery().eq(SysKeyDO::getKeyName, key));
    }
}
