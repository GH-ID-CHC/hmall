package com.hmall.api.client;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * Author: CHAI
 * Date: 2023/11/12
 */
@FeignClient("trade-service")
public interface TradeClient {

    /**
     * 标记订单已支付
     * @param orderId
     */
    @ApiOperation("标记订单已支付")
    @PutMapping("/orders/{orderId}")
    void markOrderPaySuccess(@PathVariable("orderId") Long orderId);
}
