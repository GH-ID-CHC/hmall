package com.hmall.card;
import com.hmall.api.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.hmall.card.mapper")
@SpringBootApplication
@EnableFeignClients(basePackages = "com.hmall.api.client",defaultConfiguration = DefaultFeignConfig.class)
public class CardApplication {
    public static void main(String[] args) {
        SpringApplication.run(CardApplication.class, args);
    }
/*    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }*/
}