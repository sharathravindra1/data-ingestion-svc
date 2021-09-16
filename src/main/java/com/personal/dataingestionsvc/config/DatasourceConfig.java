package com.personal.dataingestionsvc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DatasourceConfig {


    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource rdsDataSource() {
        return DataSourceBuilder.create().build();
    }

}
