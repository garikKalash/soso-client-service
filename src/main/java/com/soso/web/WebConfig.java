package com.soso.web;

import com.soso.models.ServiceInfo;
import com.soso.service.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.*;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@EnableWebMvc
@PropertySource(value = { "classpath:application.properties" })
public class WebConfig
        extends WebMvcConfigurerAdapter {

    @Bean
    public DataSource dataSource() {
        ClientService clientService = new ClientService(1);
        ServiceInfo serviceInfo = clientService.getDestinationService();

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(serviceInfo.getDbConnectionMetaData().getDriverClassName());
        ds.setUrl(serviceInfo.getDbConnectionMetaData().getUrl());
        ds.setUsername(serviceInfo.getDbConnectionMetaData().getUsername());
        ds.setPassword(serviceInfo.getDbConnectionMetaData().getPassword());
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
    }

}
