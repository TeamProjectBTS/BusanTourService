package com.example.board.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    
    @Bean
    public RestTemplate restTemplate() {
    	RestTemplate restTemplate = new RestTemplateBuilder()
          .setConnectTimeout(Duration.ofSeconds(3000)) // 필요한 경우 시간 초과 값을 조정하세요
          .setReadTimeout(Duration.ofSeconds(3000))
          .build();
        restTemplate.getMessageConverters().add(new MappingJackson2XmlHttpMessageConverter());
        return restTemplate;
    }
    
    
    
    
    
}
