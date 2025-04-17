package com.lot.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lot.server.part.mapper")
@MapperScan("com.lot.server.product.mapper")
@MapperScan("com.lot.server.activity.mapper")
@MapperScan("com.lot.server.employee.mapper")
@MapperScan("com.lot.server.login.mapper")
public class IoTServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IoTServerApplication.class, args);
    }
}

