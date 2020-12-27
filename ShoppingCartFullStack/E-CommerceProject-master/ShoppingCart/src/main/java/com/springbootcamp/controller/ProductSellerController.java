package com.springbootcamp.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.springbootcamp.dtos.ProductDto;
import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.models.ProductVariation;
import com.springbootcamp.repos.ProductVariationRepository;
import com.springbootcamp.services.ProductService;
import com.springbootcamp.dtos.ProductVariationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/seller")
public class ProductSellerController
{
    @Autowired
    private ProductService productService;
    @Autowired

    private ProductVariationRepository productVariationRepository;
    @PostMapping("/addProduct")
    public ProductDto addNewProduct(@RequestBody ProductDto productDto, HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return productService.addNewProduct(username,productDto);
    }
    @PostMapping("/addProductVariation")
    public ProductVariationDto addProductVariation(@RequestBody ProductVariationDto productVariationDto,HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return productService.addNewProductVariation(username,productVariationDto);
    }

    @GetMapping("/viewProduct/{id}")
    public ProductDto viewProduct(@PathVariable Long id,HttpServletRequest httpServletRequest){
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return productService.viewProductSeller(username,id);
    }
    @GetMapping("/viewProductVariation/{id}")
    public ProductVariationDto viewProductVariation(@PathVariable Long id, HttpServletRequest httpServletRequest){
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return productService.viewProductVariation(username,id);
    }
    @GetMapping("/viewAllProducts")
    public List<ProductDto> viewAllProducts(@PageableDefault(page = 0, value = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                                     Pageable pageable,HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return productService.viewAllProductsBySeller(username,pageable);
    }
    @GetMapping("/viewAllProductVariations/{id}")
    public List<ProductVariationDto> viewAllProductVariations(@PageableDefault(page = 0, value = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                                    Pageable pageable,HttpServletRequest httpServletRequest,@PathVariable Long id)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return productService.viewAllProductVariationsBySeller(username,id,pageable);
    }
    @DeleteMapping("/deleteProduct/{id}")
    public String deleteProductById(HttpServletRequest httpServletRequest,@PathVariable Long id)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return productService.deleteProductById(username,id,locale);
    }

    @PatchMapping("updateProduct/{id}")
    public  @ResponseBody ProductDto updateProduct(@PathVariable Long id,@RequestBody ProductDto productDto,HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return productService.updateProduct(id,productDto,username,locale);

    }
    @PatchMapping("updateProductVariation/{id}")
    public  @ResponseBody ProductVariationDto updateProductVariation(@PathVariable Long id,@RequestBody ProductVariationDto productVariationDto,HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return productService.updateProductVariation(id,productVariationDto,username,locale);

    }
    @PostMapping("/uploadProductVariationImage")
    public String uploadProductVariationImage(@RequestParam("image") MultipartFile multipartFile, @RequestParam("Variation Id") Long variationId)
            throws IOException {
        if(variationId==null)
        {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }
        Optional<ProductVariation> productVariation=productVariationRepository.findById(variationId);
        if(!productVariation.isPresent())
        {
            throw new ECommerceException(ErrorCode.NO_PRODUCT_VARIATION_FOUND);
        }
        ProductVariation savedProductVariation=productVariation.get();
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name","dbnkpr18k",
                "api_key","127611299927577",
                "api_secret","t0B-kCU2d9Syr5Zcd8cqrzTNm1Y"));

        File file = Files.createTempFile("temp", multipartFile.getOriginalFilename()).toFile();
        multipartFile.transferTo(file);

        try {
            Map response = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                    "public_id", multipartFile.getOriginalFilename(),
                    "folder", "/Home/Product"));

            String imageApi = (String) response.get("url");
            savedProductVariation.setPrimaryImageName(imageApi);
            return imageApi;

        } catch (Exception e) {
            throw new ECommerceException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }
    }



}
