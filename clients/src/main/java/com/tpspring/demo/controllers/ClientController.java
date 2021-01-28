package com.tpspring.demo.controllers;

import com.tpspring.demo.beans.ProductBean;
import com.tpspring.demo.proxies.MsCartProxy;
import com.tpspring.demo.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private MsProductProxy msProductProxy;

    @Autowired
    private MsCartProxy msCartProxy;

    @RequestMapping("/")
    public String index(Model model) {

        List<ProductBean> products =  msProductProxy.list();

        model.addAttribute("products", products);

        return "index";
    }

    @RequestMapping("/product-detail/{id}")
    public String showDetails(Model model,@PathVariable long id) {

        Optional<ProductBean> product = msProductProxy.get(id);

        String illustration = product.get().getIllustration();
        String name = product.get().getName();
        Double price = product.get().getPrice();
        String description =  product.get().getDescription();

        Long productid = product.get().getId();

        model.addAttribute("name", name);
        model.addAttribute("illustration", illustration);
        model.addAttribute("description", description);
        model.addAttribute("price", price);
        model.addAttribute("productid", productid);

        return "produitDetails";
    }

    @RequestMapping("/product-detail/{id}/{cartId}")
    public String showDetailsWithCart(Model model,@PathVariable long id,@PathVariable long cartId ) {

        Optional<ProductBean> product = msProductProxy.get(id);

        String illustration = product.get().getIllustration();
        String name = product.get().getName();
        Double price = product.get().getPrice();
        String description =  product.get().getDescription();

        Long productId = product.get().getId();

        model.addAttribute("name", name);
        model.addAttribute("illustration", illustration);
        model.addAttribute("description", description);
        model.addAttribute("price", price);
        model.addAttribute("productId", productId);
        model.addAttribute("cartId", cartId);


        return "produitDetails";
    }


}
