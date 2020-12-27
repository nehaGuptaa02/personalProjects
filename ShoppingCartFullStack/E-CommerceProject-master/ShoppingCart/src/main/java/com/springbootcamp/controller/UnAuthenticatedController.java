package com.springbootcamp.controller;

import com.springbootcamp.dtos.ProductDto;
import com.springbootcamp.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/unAuth")
public class UnAuthenticatedController
{
@Autowired
private HomeService homeService;
    @GetMapping("/getAllProducts")
    public List<ProductDto> getAllProducts(@PageableDefault(page = 0, value = 15, sort = {"id"}, direction = Sort.Direction.ASC)
                                                   Pageable pageable) {
        return homeService.getAllProducts(pageable);
    }
}
