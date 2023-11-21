package com.hmall.card.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: hmall
 * @author:
 * @create: 2023-11-21 22:01
 **/
@Data
@Component
@ConfigurationProperties(prefix = "hm.cart")
public class CartProperties {

    /**
     * 购物车商品数量上限
     */
    private Integer maxAmount;

}
