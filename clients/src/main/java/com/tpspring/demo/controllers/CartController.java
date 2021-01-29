package com.tpspring.demo.controllers;

import com.tpspring.demo.beans.CartBean;
import com.tpspring.demo.beans.CartItemBean;
import com.tpspring.demo.beans.ProductBean;
import com.tpspring.demo.proxies.MsCartProxy;
import com.tpspring.demo.proxies.MsProductProxy;
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
public class CartController {

    @Autowired
    private MsProductProxy msProductProxy;

    @Autowired
    private MsCartProxy msCartProxy;


    @RequestMapping("/cart/{cartId}")
    public String showDetails(Model model, @PathVariable long cartId) {

        Optional<CartBean> cart = msCartProxy.getCart(cartId);

        double motant = 0.0;

        for (int i = 0; i < cart.get().getProducts().size(); i++) {
            long ProductId = cart.get().getProducts().get(i).getProductId();
            Optional<ProductBean> productBean = msProductProxy.get(ProductId);
            motant += productBean.get().getPrice() * cart.get().getProducts().get(i).getQuantity();
        }


        int a =1;

        model.addAttribute("products", cart.get().getProducts());
        model.addAttribute("cartId", cartId);
        model.addAttribute("motant", motant);

        return "cartDetails";
    }

    @PostMapping("/cart")
    public String createCart(Model model) {

        ResponseEntity<CartBean> cartCreate = msCartProxy.createNewCart();

        Optional<CartBean> cart = msCartProxy.getCart(cartCreate.getBody().getId());

        long cartId = cart.get().getId();

        model.addAttribute("cartId", cartId);

        List<ProductBean> products = msProductProxy.list();

        model.addAttribute("products", products);

        return "index";
    }


    @PostMapping("/cart/{productId}/{cartId}")
    public String addProductToCart(Model model, @PathVariable Long productId, @PathVariable Long cartId, @RequestParam("quantity") int quantity) {

        Optional<CartBean> cart = msCartProxy.getCart(cartId);
        Optional<ProductBean> product = msProductProxy.get(productId);
// Everytime we create an item, we give it a new id. So we dont have to comparer with other item to combine items with same product
        //1. add an item. find the item in the list
        //1.1 item exsite before
//        for(int i = 0; i<cart.get().getProducts().size(); i++){
//            if(cart.get().getProducts().get(i).getProductId() == productId){
//                inTheList = true;
//                int quantity = cart.get().getProducts().get(i).getQuantity();
//                quantity += 1;
//                cart.get().getProducts().get(i).setQuantity(quantity);
//                model.addAttribute("quantity", quantity);
//            }
//        }
        //1.2 a new item
        CartItemBean cartItem = new CartItemBean();
//        cartItem.setId((long) (cart.get().getProducts().size()+1));
        cartItem.setProductId(product.get().getId());
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.get().getPrice() * quantity);

        msCartProxy.addProductToCart(cartId, cartItem);
        model.addAttribute("quantity", quantity);


        // informations about product
        String illustration = product.get().getIllustration();
        String name = product.get().getName();
        Double price = product.get().getPrice();
        String description = product.get().getDescription();


        model.addAttribute("name", name);
        model.addAttribute("illustration", illustration);
        model.addAttribute("description", description);
        model.addAttribute("price", price);
        model.addAttribute("productId", productId);

        // informations about cart
        model.addAttribute("cartId", cartId);


        return "produitDetails";
    }


    @RequestMapping("/cart/return/{cartId}")
    public String returnIndex(Model model, @PathVariable Long cartId) {


        model.addAttribute("cartId", cartId);

        List<ProductBean> products = msProductProxy.list();

        model.addAttribute("products", products);

        return "index";
    }
}