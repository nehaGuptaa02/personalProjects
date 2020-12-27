package com.springbootcamp.controller;

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
import java.util.Locale;

@RestController
@RequestMapping("/admin")
public class ProductAdminController
{
    @Autowired
    private ProductService productService;
    @GetMapping("/viewProduct/{id}")
    public ProductDto viewProductAdmin(@PathVariable Long id, HttpServletRequest httpServletRequest){
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return productService.viewProductAdmin(username,id);
    }
    @GetMapping("/viewAllProducts")
    public List<ProductDto> viewAllProductsAdmin(@PageableDefault(page = 0, value = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                                    Pageable pageable, HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return productService.viewAllProductsByAdmin(username,pageable);
    }
    @PutMapping("/activateDeactivateProduct/{id}")
    public String activateProduct(@PathVariable Long id,@RequestParam("choice") String choice,
                                  HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return productService.activateDeactivateProduct(username,id,choice,locale);
    }


}
