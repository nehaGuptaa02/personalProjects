//package com.springbootcamp.controller;
//
//
//import com.springbootcamp.services.OrderCartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/order")
//public class OrderController {
//    @Autowired
//    private OrderCartService orderService;
//    @PostMapping
//    public String addOrderInCart(@RequestParam("productId") Long productId,
//                                 @RequestParam("variationId") Long variationId,
//                                 @RequestParam("name") String name,
//                                 @RequestParam("price") String price,
//                                 @RequestParam("image") String image) {
//        return orderService.addOrderInCart(productId,variationId,name,price,image);
//
//    }
//}
