package com.springbootcamp.controller;

import com.springbootcamp.dtos.ProductDetailsDto;
import com.springbootcamp.dtos.ProductDto;
import com.springbootcamp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class ProductCustomerController
{
    @Autowired
    private ProductService productService;
    @GetMapping("/viewProduct/{id}")
    public ProductDto viewProductCustomer(@PathVariable Long id, HttpServletRequest httpServletRequest){
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return productService.viewProductCustomer(username,id);
    }
    @GetMapping("/viewAllProducts/{id}")
    public List<ProductDto> viewAllProductsCustomer(@PageableDefault(page = 0, value = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                                         Pageable pageable,@PathVariable Long id, HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return productService.viewAllProductsByCustomer(id,pageable);
    }
    @GetMapping("/viewProductDetail")
    public ProductDetailsDto viewProductDetail(@RequestParam("productId") Long productId,
                                               @RequestParam("variationId")Long variationId, HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return productService.viewProductDetail(productId,variationId);
    }

}
