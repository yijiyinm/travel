package com.example.travel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author yijiyin
 */
@EnableCaching
@SpringBootApplication
@MapperScan(basePackages = {"com.example.travel.**.dao"})
public class TravelServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelServiceApplication.class, args);
	}

}
