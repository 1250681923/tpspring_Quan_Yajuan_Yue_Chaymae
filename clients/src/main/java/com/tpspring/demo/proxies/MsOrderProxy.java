package com.tpspring.demo.proxies;

import com.tpspring.demo.beans.CartBean;
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
import java.util.Optional;

@FeignClient(name = "ms-order", url = "localhost:8093")
public interface MsOrderProxy {

    @PostMapping(value = "/order")
    public ResponseEntity<OrderBean> createNewOrder();


    @GetMapping(value = "/order/{orderId}")
    public Optional<OrderBean> getOrder(@PathVariable Long orderId);

    @GetMapping(value = "/order")
    public List<OrderBean> getAllOrder();

    @PostMapping(value = "/order/{cartId}/{motant}/{orderId}")
    public ResponseEntity<OrderItemBean> addProductToOrder( @RequestBody OrderItemBean orderItem,@PathVariable Long cartId, @PathVariable Long orderId, @PathVariable double motant);
}
