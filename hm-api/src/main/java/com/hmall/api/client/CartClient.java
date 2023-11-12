package com.hmall.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * Author: CHAI
 * Date: 2023/11/12
 */
@FeignClient("cart-service")
public interface CartClient {

    /**
     * 批量删除购物车中商品
     * @param ids
     */
    @DeleteMapping("/carts")
    void removeByItemIds(@RequestParam("ids") Collection<Long> ids);
}
