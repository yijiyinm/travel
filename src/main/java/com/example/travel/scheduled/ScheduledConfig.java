package com.example.travel.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


/**
 * @author chenying
 * @Description TODO
 * @Date 2023/5/29 13:57
 */
@Slf4j
@Configuration
public class ScheduledConfig  {
    @Scheduled(cron = "*/15 * * * * ?")
    public void execute() {
    }

}
