package com.springbootcamp.services;

import com.springbootcamp.dtos.*;
import com.springbootcamp.models.Category;
import com.springbootcamp.models.Product;
import com.springbootcamp.repos.CategoryRepository;
import com.springbootcamp.repos.ProductRepository;
import com.springbootcamp.transformers.CategoryMetadataFieldTransformer;
import com.springbootcamp.transformers.CategoryTransformer;
import com.springbootcamp.transformers.ProductTransformer;
import com.springbootcamp.transformers.ProductVariationTransformer;
import com.springbootcamp.utils.BaseVO;
import com.springbootcamp.utils.ErrorVO;
import com.springbootcamp.utils.ResponseVO;
import com.springbootcamp.utils.StringToSetParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HomeService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    public List<ParentCategoryResponseDto> getAllParentCategories(String username)
    {
        List<Category> parentCategories=categoryRepository.findByParentIdIsNull();
        List<ParentCategoryResponseDto> parentCategoryResponseDtos=new ArrayList<>();

        parentCategories.forEach((category) -> {
            ParentCategoryResponseDto parentCategoryResponseDto=new ParentCategoryResponseDto();
            parentCategoryResponseDto.setId(category.getId());
            parentCategoryResponseDto.setCategoryName(category.getName());
       parentCategoryResponseDtos.add(parentCategoryResponseDto);

        });
        return parentCategoryResponseDtos;
    }

    public ResponseEntity<BaseVO> getAllCategories() {
        BaseVO response;
        List<Category> categories = categoryRepository.findAll();
        List<CategoryAdminResponseDto> categoryDtos = new ArrayList<>();

        categories.forEach((category) -> {
            categoryDtos.add(toCategoryAdminResponseDto(category));
        });

        response = new ResponseVO<List>(categoryDtos, null, new Date());
        return new ResponseEntity<BaseVO>(response, HttpStatus.OK);
    }
    public CategoryAdminResponseDto toCategoryAdminResponseDto(Category category) {

        CategoryAdminResponseDto categoryAdminResponseDto = new CategoryAdminResponseDto();

        // get actual category with all parent tree
        CategoryTransformer categoryTransformer = new CategoryTransformer();
        CategoryDto categoryDto = categoryTransformer.toDto(category);
        categoryAdminResponseDto.setCategoryDto(categoryDto);
        // get child categories
        Set<CategoryDto> subCategories;
        if (category.getSubcategories() != null) {
            subCategories = new HashSet<>();

            category.getSubcategories().forEach((e) -> {
                CategoryDto categoryDto1 = categoryTransformer.toDto(category);
                categoryDto1.setParentId(category.getId());
                subCategories.add(categoryTransformer.toDto(e));
            });
            categoryAdminResponseDto.setSubCategories(subCategories);
        }

        // get the possible metadata fields and values
        Set<CategoryMetadataFieldDto> fieldValues;
        if (category.getCategoryMetadataFieldValuesSet() != null) {
            fieldValues = new HashSet<>();

            category.getCategoryMetadataFieldValuesSet().forEach((e) -> {
                CategoryMetadataFieldTransformer categoryMetadataFieldTransformer = new CategoryMetadataFieldTransformer();
                CategoryMetadataFieldDto categoryMetadataFieldDto = categoryMetadataFieldTransformer.toDto(e.getCategoryMetadataField());
                categoryMetadataFieldDto.setValues(StringToSetParser.toSetOfValues(e.getValue()));
                fieldValues.add(categoryMetadataFieldDto);
            });
            categoryAdminResponseDto.setCategoryMetadataFieldDtoSet(fieldValues);
        }
        return categoryAdminResponseDto;
    }

    public ResponseEntity<BaseVO> getAllChildCategories(Long categoryId)
    {
        BaseVO response;
        if (categoryId == null) {
            response = new ErrorVO("Not Found", "Please enter the category Id.", new Date());
            return new ResponseEntity<BaseVO>(response, HttpStatus.NOT_FOUND);
        }

        Optional<Category> preStored = categoryRepository.findById(categoryId);
        if (!preStored.isPresent()) {
            response = new ErrorVO("Not Found", "Category with given id does not exist.", new Date());
            return new ResponseEntity<BaseVO>(response, HttpStatus.NOT_FOUND);
        }

        CategoryAdminResponseDto categoryAdminResponseDto = toAllChildCategories(preStored.get());

        response = new ResponseVO<CategoryAdminResponseDto>(categoryAdminResponseDto, null, new Date());
        return new ResponseEntity<BaseVO>(response, HttpStatus.OK);

    }

    private CategoryAdminResponseDto toAllChildCategories(Category category)
    {
        CategoryAdminResponseDto categoryAdminResponseDto = new CategoryAdminResponseDto();

        // get actual category with all parent tree
        CategoryTransformer categoryTransformer = new CategoryTransformer();
        CategoryDto categoryDto = categoryTransformer.toDto(category);
        categoryAdminResponseDto.setCategoryDto(categoryDto);
        // get child categories
        Set<CategoryDto> subCategories;
        if (category.getSubcategories() != null) {
            subCategories = new HashSet<>();

            category.getSubcategories().forEach((e) -> {
                CategoryDto categoryDto1 = categoryTransformer.toDto(category);
                categoryDto1.setParentId(category.getId());
                subCategories.add(categoryTransformer.toDto(e));
            });
            categoryAdminResponseDto.setSubCategories(subCategories);
        }

        // get the possible metadata fields and values
        Set<CategoryMetadataFieldDto> fieldValues;
        if (category.getCategoryMetadataFieldValuesSet() != null) {
            fieldValues = new HashSet<>();

            category.getCategoryMetadataFieldValuesSet().forEach((e) -> {
                CategoryMetadataFieldTransformer categoryMetadataFieldTransformer = new CategoryMetadataFieldTransformer();
                CategoryMetadataFieldDto categoryMetadataFieldDto = categoryMetadataFieldTransformer.toDto(e.getCategoryMetadataField());
                categoryMetadataFieldDto.setValues(StringToSetParser.toSetOfValues(e.getValue()));
                fieldValues.add(categoryMetadataFieldDto);
            });
            categoryAdminResponseDto.setCategoryMetadataFieldDtoSet(fieldValues);
        }
        return categoryAdminResponseDto;
    }

    public List<ProductDto> getAllProducts(Pageable pageable)
    {
        List<Product> products = productRepository.findAll(pageable);
        List<ProductDto> productDtoList = new ArrayList<>();
        ProductTransformer productTransformer = new ProductTransformer();
        CategoryTransformer categoryTransformer = new CategoryTransformer();
        ProductVariationTransformer productVariationTransformer = new ProductVariationTransformer();


            products.forEach(product ->
                    {
                        ProductDto productDto = productTransformer.toDto(product);
                        productDto.setCategoryDto(categoryTransformer.toDto(product.getCategory()));

                        if (product.getProductVariations() != null && product.isActive()) {

                            Set<ProductVariationDto> productVariationDtoSet = new HashSet<>();
                            product.getProductVariations().forEach(productVariation -> {
                                productVariationDtoSet.add(productVariationTransformer.toDto(productVariation));
                                productDto.setProductVariationDtoSet(productVariationDtoSet);
                            });


                        }
                        if(productDto.isActive())
                        {
                            productDtoList.add(productDto);
                        }


                    }

            );

        return productDtoList;
    }

//    public List<String> getAllSubCategories(String categoryName,String username)
//    {
//        Category category=categoryRepository.findByName(categoryName);
//        List<Category> subCategories=
//    }
}
