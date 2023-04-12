package com.example.travel.order.service;

import com.example.travel.order.dto.TouristDTO;

import java.util.List;

/**
 * @author yijiyin
 */
public interface TouristService {
    /**
     * 新增游客信息
     * @param touristDTO
     * @param openId
     * @return
     */
    Boolean addTourist(TouristDTO touristDTO, String openId);

    /**
     * 查询用户游客列表
     * @param id
     * @return
     */
    List<TouristDTO> getTouristList(String openId);

    /**
     * 修改游客信息
     * @param touristDTO
     * @return
     */
    Boolean updateTourist(TouristDTO touristDTO);

    /**
     * 删除游客信息
     * @param id
     * @return
     */
    Boolean deleteTourist(Long id);

    /**
     * 根据id查询游客信息
     * @param id
     * @return
     */
    TouristDTO getTouristById(Long id);


}
