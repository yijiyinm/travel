package com.example.travel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.dao.TouristMapper;
import com.example.travel.dto.TouristDTO;
import com.example.travel.dao.entity.TouristDO;
import com.example.travel.service.TouristService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yijiyin
 */
@Service("touristService")
public class TouristServiceImpl extends ServiceImpl<TouristMapper,TouristDO> implements TouristService {
    @Override
    public Boolean addTourist(TouristDTO touristDTO, String openId) {
        try {
            TouristDO touristDO = new TouristDO();
            touristDO.setName(touristDTO.getName());
            touristDO.setCardId(touristDTO.getCardId());
            touristDO.setPhone(touristDTO.getPhone());
            touristDO.setOpenId(openId);
            touristDO.insert();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加游客信息错误：{}",e);
            return false;
        }
        return true;
    }

    @Override
    public List<TouristDTO> getTouristList(String openId) {
        try {
            List<TouristDTO> touristDTOS = new ArrayList<>();
            List<TouristDO> touristDOS = list(Wrappers.<TouristDO>lambdaQuery().eq(TouristDO::getOpenId, openId)
            .eq(TouristDO::getValid,false));
            touristDOS.forEach(item ->{
                TouristDTO touristDTO = new TouristDTO();
                touristDTO.setCardId(item.getCardId());
                touristDTO.setName(item.getName());
                touristDTO.setPhone(item.getPhone());
                touristDTO.setId(item.getId());
                touristDTOS.add(touristDTO);
            });
            return touristDTOS;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询游客列表错误");
        }
        return null;
    }

    @Override
    public Boolean updateTourist(TouristDTO touristDTO) {
        try {
            TouristDO touristDO = new TouristDO();
            touristDO.setId(touristDTO.getId());
            touristDO.setName(touristDTO.getName());
            touristDO.setCardId(touristDTO.getCardId());
            touristDO.setPhone(touristDTO.getPhone());
            touristDO.updateById();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改游客信息错误：{}",e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteTourist(Long id) {
        try {
            TouristDO touristDO = new TouristDO();
            touristDO.setId(id);
            touristDO.setValid(true);
            touristDO.updateById();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除游客信息错误：{}",e);
            return false;
        }
        return true;
    }

    @Override
    public TouristDTO getTouristById(Long id) {
        TouristDO touristDO = getOne(Wrappers.<TouristDO>lambdaQuery().eq(TouristDO::getId, id));
        if (touristDO != null)  {
            TouristDTO touristDTO = new TouristDTO();
            touristDTO.setCardId(touristDO.getCardId());
            touristDTO.setName(touristDO.getName());
            touristDTO.setPhone(touristDO.getPhone());
            touristDTO.setId(touristDO.getId());
            return touristDTO;
        }
        return null;
    }
}
