package com.tpspring.demo.controllers;

import com.tpspring.demo.beans.*;
import com.tpspring.demo.proxies.MsCartProxy;
import com.tpspring.demo.proxies.MsOrderProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
    @Autowired
    private MsCartProxy msCartProxy;

    @Autowired
    private MsOrderProxy msOrderProxy;



    @PostMapping("/order/{cartId}/{motant}")
    public String createOrder(Model model,@PathVariable Long cartId, @PathVariable double motant) {

        ResponseEntity<OrderBean> orderCreate = msOrderProxy.createNewOrder();

        Optional<CartBean> cart = msCartProxy.getCart(cartId);

        model.addAttribute("cartId", cartId);

        model.addAttribute("motant", motant);

        model.addAttribute("products", cart.get().getProducts());

        model.addAttribute("orderId", orderCreate.getBody().getId());

        return "cartDetails";
    }



    @RequestMapping("/order")
    public String showOrders(Model model) {

        List<OrderBean> orderBeanList = msOrderProxy.getAllOrder();

        model.addAttribute("orderBeanList", orderBeanList);

        return "orderInfors";
    }

    @PostMapping("/order/{cartId}/{motant}/{orderId}")
    public String addCartToOrder(Model model, @PathVariable Long cartId, @PathVariable double motant, @PathVariable Long orderId) {

        Optional<CartBean> cart = msCartProxy.getCart(cartId);

        msOrderProxy.getOrder(orderId);

        for (int i = 0; i < cart.get().getProducts().size(); i++) {

            OrderItemBean orderItem = new OrderItemBean();
            orderItem.setPrice(cart.get().getProducts().get(i).getPrice());
            orderItem.setProductId(cart.get().getProducts().get(i).getProductId());
            orderItem.setQuantity(cart.get().getProducts().get(i).getQuantity());
            //change
            msOrderProxy.addProductToOrder(orderItem,cartId, orderId, motant);

        }

        return "orderAdded";
    }


}
