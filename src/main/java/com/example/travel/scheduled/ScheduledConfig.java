package com.example.travel.scheduled;

import com.alibaba.fastjson.JSON;
import com.example.travel.dao.entity.OrderDO;
import com.example.travel.dto.SelectOrderDTO;
import com.example.travel.enums.OrderStatusEnum;
import com.example.travel.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author chenying
 * @Description TODO
 * @Date 2023/5/29 13:57
 */
@Slf4j
@Configuration
public class ScheduledConfig  {
    private static DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private OrderService orderService;
    @Scheduled(cron = "*/15 * * * * ?")
    public void execute() {
        log.info("订单定时任务开始");
        SelectOrderDTO selectOrderDTO = new SelectOrderDTO();
        selectOrderDTO.setOrderStatus(OrderStatusEnum.WAIT_PAY.getStatus());
        List<OrderDO> allOrder = orderService.getAllOrder(selectOrderDTO);
        for (OrderDO orderDO:allOrder){

            try {
                Date createDate = orderDO.getCreateDate();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
                if(createDate.getTime()<calendar.getTime().getTime()){
                    log.error("订单过期了 {}", JSON.toJSONString(orderDO));
                    orderDO.setStatus(OrderStatusEnum.CANCEL_STATUS.getStatus());
                    orderDO.updateById();
                    continue;

                }
                if(OrderStatusEnum.ALREADY_PAY.getStatus().equals(orderDO.getStatus())){

                }
                String chuXingDate = orderDO.getChuXingDate();
                String format = fmt.format(new Date());
                if(chuXingDate.compareTo(format)>=0){
                    log.error("订单已经出行chuXingDate  {}", chuXingDate);
                    orderDO.setStatus(OrderStatusEnum.END_PAY.getStatus());
                    orderDO.updateById();
                }
            }catch (Exception e){
                e.printStackTrace();
                log.info("订单定时任务异常 {}",JSON.toJSONString(orderDO));
            }

        }
        log.info("订单定时任务结束");
    }

}
