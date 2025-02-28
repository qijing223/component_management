package com.lot.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lot.server.component.mapper")
public class IoTServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IoTServerApplication.class, args);
    }
}

