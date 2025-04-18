package com.lot.server.config;

import com.lot.server.part.domain.entity.PartStatus;
import com.lot.server.part.mapper.PartStatusTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.getTypeHandlerRegistry()
                .register(PartStatus.class, JdbcType.VARCHAR, PartStatusTypeHandler.class);
    }
}