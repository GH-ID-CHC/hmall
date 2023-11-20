package com.hmall.api.client;
/**
 * Author: CHAI
 * Date: 2023/11/12
 */


import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @program: hmall
 * @author:
 * @create: 2023-11-12 16:09
 **/
@FeignClient("item-service")
public interface ItemClient {

    @GetMapping("/items")
    List<ItemDTO> queryItemByIds(@RequestParam("ids") Collection<Long> ids);

    /**
     * 根据id批量查询商品
     * @param ids
     * @return {@link List}<{@link ItemDTO}>
     */
    @GetMapping("/items/{id}")
    List<ItemDTO> queryItemByIds(@RequestParam("ids") List<Long> ids);

    /**
     * 批量扣减库存
     * @param items
     */
    @PutMapping("/items/stock/deduct")
    void deductStock(@RequestBody List<OrderDetailDTO> items);
}
