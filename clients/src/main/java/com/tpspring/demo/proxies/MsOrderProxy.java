package com.tpspring.demo.proxies;

import com.tpspring.demo.beans.CartItemBean;
import com.tpspring.demo.beans.OrderBean;
import com.tpspring.demo.beans.OrderItemBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ms-order", url = "localhost:8093")
public interface MsOrderProxy {

    @GetMapping(value = "/order")
    public List<OrderBean> getAllOrder();

    @PostMapping(value = "/order/{cartId}/{montant}/{a}")
    public ResponseEntity<CartItemBean> addProductToOrder(@PathVariable Long cartId, @RequestBody OrderItemBean orderItem, @PathVariable int a, @PathVariable double montant);
}
