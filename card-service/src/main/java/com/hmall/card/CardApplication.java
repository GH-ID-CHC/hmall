package com.hmall.card;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.hmall.card.mapper")
@SpringBootApplication
public class CardApplication {
    public static void main(String[] args) {
        SpringApplication.run(CardApplication.class, args);
    }
}