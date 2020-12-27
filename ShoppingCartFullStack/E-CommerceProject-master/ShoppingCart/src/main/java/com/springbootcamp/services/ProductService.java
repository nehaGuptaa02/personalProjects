package com.springbootcamp.services;

import com.springbootcamp.dtos.ProductDetailsDto;
import com.springbootcamp.dtos.ProductDto;
import com.springbootcamp.dtos.ProductVariationDto;
import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.models.Category;
import com.springbootcamp.models.Product;
import com.springbootcamp.models.ProductVariation;
import com.springbootcamp.models.Seller;
import com.springbootcamp.repos.CategoryRepository;
import com.springbootcamp.repos.ProductRepository;
import com.springbootcamp.repos.ProductVariationRepository;
import com.springbootcamp.repos.SellerRepository;
import com.springbootcamp.transformers.CategoryTransformer;
import com.springbootcamp.transformers.ProductTransformer;
import com.springbootcamp.transformers.ProductVariationTransformer;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ValidationsService validationsService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ProductVariationRepository productVariationRepository;
    @Autowired
    private MessageSource messageSource;
    private static final String adminUsername = "neha.guptajune02@gmail.com";


    //For adding new product
    public ProductDto addNewProduct(String username, ProductDto productDto) {
        String status = validationsService.isValidProduct(username, productDto);
        if (!status.equals("Success")) {
            throw new ECommerceException(ErrorCode.VALIDATION_FAILS);
        }

        ProductTransformer productTransformer = new ProductTransformer();
        Product product = productTransformer.fromDto(productDto);
        Seller seller = sellerRepository.findByEmail(username);
        if (seller == null)
            throw new ECommerceException(ErrorCode.SELLER_NOT_FOUND);
        product.setSeller(seller);
        Category category = categoryRepository.findAllById(productDto.getCategoryId());
        product.setCategory(category);
        productRepository.save(product);
        String subject = "Product created";
        String message = "A product with following details has been registered : \n" +
                "Name : " + product.getName() + "\n" +
                "Category : " + product.getCategory().getName() + "\n" +
                "Brand : " + product.getBrand() + "\n" +
                "Description : " + product.getDescription();
        emailService.sendEmail(adminUsername, subject, message);
        ProductDto newproductDto = productTransformer.toDto(product);
        return newproductDto;

    }

    //To add a productVariation
    public ProductVariationDto addNewProductVariation(String username, ProductVariationDto productVariationDto) {

        Optional<Product> product = productRepository.findById(productVariationDto.getProductId());
        if (!product.isPresent()) {
            throw new ECommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        Product savedProduct = product.get();
        if (productVariationDto.getQuantityAvailable() != null) {
            if (productVariationDto.getQuantityAvailable() < 0)
                throw new ECommerceException(ErrorCode.INVALID_QUANTITY);
        }
        if (productVariationDto.getPrice() != null) {
            if (productVariationDto.getPrice() < 0)
                throw new ECommerceException(ErrorCode.INVALID_PRICE);
        }
        String ext = FilenameUtils.getExtension(productVariationDto.getPrimaryImageName());
        if (ext.equalsIgnoreCase("jpg") && ext.equalsIgnoreCase("jpeg") &&
                ext.equalsIgnoreCase("png") && ext.equalsIgnoreCase("bmp")) {

            throw new ECommerceException(ErrorCode.INVALID_IMAGE);
        }

        //isActive k lie bhi check lagega
        if (savedProduct.isDeleted())
            throw new ECommerceException(ErrorCode.INVALID_PRODUCT);
        /*Three validations are left to be checked*/
        ProductVariationTransformer productVariationTransformer = new ProductVariationTransformer();
        ProductVariation productVariation = productVariationTransformer.fromDto(productVariationDto);
        productVariation.setProduct(savedProduct);
        productVariationRepository.save(productVariation);
        ProductVariationDto newproductVariationDto = productVariationTransformer.toDto(productVariation);
        return newproductVariationDto;


    }

    //To view Product by seller
    public ProductDto viewProductSeller(String username, Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ECommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        Product savedProduct = product.get();
        Seller seller = savedProduct.getSeller();
        if (!username.equalsIgnoreCase(seller.getEmail())) {
            throw new ECommerceException(ErrorCode.PRODUCT_NOT_MATCHES_WITH_CREATOR);
        }
        if (savedProduct.isDeleted()) {
            throw new ECommerceException(ErrorCode.PRODUCT_IS_DELETED);

        }
        ProductTransformer productTransformer = new ProductTransformer();
        ProductDto productDto = productTransformer.toDto(savedProduct);
        CategoryTransformer categoryTransformer = new CategoryTransformer();
        productDto.setCategoryDto(categoryTransformer.toDto(savedProduct.getCategory()));
        return productDto;
    }


    //To view a productVariationById
    public ProductVariationDto viewProductVariation(String username, Long id) {
        Optional<ProductVariation> productVariation = productVariationRepository.findById(id);
        if (!productVariation.isPresent()) {
            throw new ECommerceException(ErrorCode.NO_PRODUCT_VARIATION_FOUND);
        }
        ProductVariation savedProductVariation = productVariation.get();
        Seller seller = savedProductVariation.getProduct().getSeller();
        if (!username.equalsIgnoreCase(seller.getEmail())) {
            throw new ECommerceException(ErrorCode.PRODUCT_NOT_MATCHES_WITH_CREATOR);
        }
        if (savedProductVariation.getProduct().isDeleted()) {
            throw new ECommerceException(ErrorCode.PRODUCT_IS_DELETED);

        }
        //Getting the product information from productVariation
        ProductVariationTransformer productVariationTransformer = new ProductVariationTransformer();
        ProductVariationDto productVariationDto = productVariationTransformer.toDto(savedProductVariation);
        ProductTransformer productTransformer = new ProductTransformer();
        ProductDto productDto = productTransformer.toDto(savedProductVariation.getProduct());
        CategoryTransformer categoryTransformer = new CategoryTransformer();
        productDto.setCategoryDto(categoryTransformer.toDto(savedProductVariation.getProduct().getCategory()));
        productVariationDto.setProductDto(productDto);
        return productVariationDto;
    }

    //To get list of all products of a logged-in user.
    public List<ProductDto> viewAllProductsBySeller(String username, Pageable pageable) {
        Seller seller = sellerRepository.findByEmail(username);
        List<Product> products = productRepository.findAllBySellerId(seller.getId(), pageable);
        List<ProductDto> productDtoList = new ArrayList<>();
        ProductTransformer productTransformer = new ProductTransformer();
        CategoryTransformer categoryTransformer = new CategoryTransformer();
        products.forEach(product ->
                {
                    if (!product.isDeleted()) {
                        ProductDto productDto = productTransformer.toDto(product);
                        productDto.setCategoryDto(categoryTransformer.toDto(product.getCategory()));
                        productDtoList.add(productDto);
                    }

                }

        );
        return productDtoList;

    }

    //For Seller to view All ProductVariations
    public List<ProductVariationDto> viewAllProductVariationsBySeller(String username, Long id, Pageable pageable) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ECommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        Product savedProduct = product.get();
        Seller seller = savedProduct.getSeller();
        if (!username.equalsIgnoreCase(seller.getEmail())) {
            throw new ECommerceException(ErrorCode.PRODUCT_NOT_MATCHES_WITH_CREATOR);
        }
        if (savedProduct.isDeleted()) {
            throw new ECommerceException(ErrorCode.PRODUCT_IS_DELETED);

        }
        List<ProductVariation> productVariations = productVariationRepository.findAllByProductId(id, pageable);
        List<ProductVariationDto> productVariationDtoList = new ArrayList<>();
        ProductVariationTransformer productVariationTransformer = new ProductVariationTransformer();
        productVariations.forEach(productVariation -> {
            productVariationDtoList.add(productVariationTransformer.toDto(productVariation));
        });
        return productVariationDtoList;

    }

    //Seller deleting product by id
    public String deleteProductById(String username, Long id, Locale locale) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ECommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        Product savedProduct = product.get();
        Seller seller = savedProduct.getSeller();
        if (!username.equalsIgnoreCase(seller.getEmail())) {
            throw new ECommerceException(ErrorCode.PRODUCT_NOT_MATCHES_WITH_CREATOR);
        }
        productRepository.delete(savedProduct);
        return messageSource.getMessage("message.deleteProduct", null, locale);
    }

    //SELLER:-updating a product
    public ProductDto updateProduct(Long id, ProductDto productDto, String username, Locale locale) {
        if (productDto.getName().trim().length() == 0 && productDto.getBrand().trim().length() == 0 &&
                productDto.getDescription().trim().length() == 0 && productDto.isCancellable() == null && productDto.isReturnable()==null
                || (productDto.getName().isEmpty() && productDto.getBrand().isEmpty() && productDto.getDescription().isEmpty())) {
            throw new ECommerceException(ErrorCode.INVALID_UPDATE);
        }
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ECommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        Product savedProduct = product.get();
        Seller seller = savedProduct.getSeller();
        if (!username.equalsIgnoreCase(seller.getEmail())) {
            throw new ECommerceException(ErrorCode.PRODUCT_NOT_MATCHES_WITH_CREATOR);
        }
        Product savedProduct1 = productRepository.findByName(productDto.getName());
//        Optional<Product> product = productRepository.findByName(productDto.getName());
//        Product savedProduct = product.get();
        if(savedProduct1!=null)
        {
            throw  new ECommerceException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }
        try {

            if (savedProduct != null) {
                if (savedProduct.getCategory().getId() == productDto.getCategoryId()) ;
                {
                    if (savedProduct.getBrand().equalsIgnoreCase(productDto.getBrand())) {
                        if (savedProduct.getSeller().getEmail().equalsIgnoreCase(username)) {
                            throw new ECommerceException(ErrorCode.PRODUCT_ALREADY_EXISTS);
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Wrapper exception: " + e);
            // Print the 'actual' exception:
            System.out.println("Underlying exception: " + e.getCause());
        }


        if (productDto.getName() != null)
            savedProduct.setName(productDto.getName());
        if (productDto.getDescription() != null)
            savedProduct.setDescription(productDto.getDescription());
        if (productDto.getBrand() != null)
            savedProduct.setBrand(productDto.getBrand());
        if (productDto.isCancellable() != null)
            savedProduct.setCancellable(productDto.isCancellable());
        if (productDto.isReturnable() != null)
            savedProduct.setReturnable(productDto.isReturnable());
        productRepository.save(savedProduct);
        ProductTransformer productTransformer = new ProductTransformer();
        return productTransformer.toDto(savedProduct);
    }

    //Seller:-Updating Product Variation
    public ProductVariationDto updateProductVariation(Long id, ProductVariationDto productVariationDto, String username, Locale locale) {
        if (productVariationDto.getQuantityAvailable()==null&& productVariationDto.getPrice()==null && productVariationDto.getPrimaryImageName()==null) {
            throw new ECommerceException(ErrorCode.INVALID_UPDATE);
        }
        if(productVariationDto.getId()==null)
        {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }
        Optional<ProductVariation> productVariation = productVariationRepository.findById(id);
        if (!productVariation.isPresent())
            throw new ECommerceException(ErrorCode.NO_PRODUCT_VARIATION_FOUND);
        ProductVariation savedProductVariation = productVariation.get();
        Seller seller = savedProductVariation.getProduct().getSeller();
//        Product savedProduct = product.get();
        if (productVariationDto.getQuantityAvailable() != null) {
            if (productVariationDto.getQuantityAvailable() < 0)
                throw new ECommerceException(ErrorCode.INVALID_QUANTITY);
        }
        if (productVariationDto.getPrice() != null) {
            if (productVariationDto.getPrice() < 0)
                throw new ECommerceException(ErrorCode.INVALID_PRICE);
        }
        String ext = FilenameUtils.getExtension(productVariationDto.getPrimaryImageName());
        if (ext.equalsIgnoreCase("jpg") && ext.equalsIgnoreCase("jpeg") &&
                ext.equalsIgnoreCase("png") && ext.equalsIgnoreCase("bmp")) {

            throw new ECommerceException(ErrorCode.INVALID_IMAGE);
        }

        if (!username.equalsIgnoreCase(seller.getEmail())) {
            throw new ECommerceException(ErrorCode.PRODUCT_NOT_MATCHES_WITH_CREATOR);
        }
        if (savedProductVariation.getProduct().isDeleted()) {
            throw new ECommerceException(ErrorCode.PRODUCT_IS_DELETED);

        }
        /*Metadata wala validation create karna hai*/
        if (productVariationDto.getQuantityAvailable() != null)
            savedProductVariation.setQuantityAvailable(productVariationDto.getQuantityAvailable());
        if (productVariationDto.getPrice() != null)
            savedProductVariation.setPrice(productVariationDto.getPrice());
        if (productVariationDto.getPrimaryImageName() != null)
            savedProductVariation.setPrimaryImageName(productVariationDto.getPrimaryImageName());
        if (productVariationDto.getMetadata() != null)
            savedProductVariation.setMetadata(productVariationDto.getMetadata());
        if (productVariationDto.isActive() != null)
            savedProductVariation.setActive(productVariationDto.isActive());
        /*for secondary images list*/
        productVariationRepository.save(savedProductVariation);
        ProductVariationTransformer productVariationTransformer = new ProductVariationTransformer();
        return productVariationTransformer.toDto(savedProductVariation);

    }
    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>       CUSTOMER      >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    public ProductDto viewProductCustomer(String username, Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ECommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        Product savedProduct = product.get();
        if (savedProduct.isDeleted() == true || savedProduct.isActive() == false) {
            throw new ECommerceException(ErrorCode.PRODUCT_IS_DELETED);

        }
        if (savedProduct.getProductVariations() == null || savedProduct.getProductVariations().isEmpty())
            throw new ECommerceException(ErrorCode.NO_PRODUCT_VARIATION_FOUND);
        ProductTransformer productTransformer = new ProductTransformer();
        ProductDto productDto = productTransformer.toDto(savedProduct);
        CategoryTransformer categoryTransformer = new CategoryTransformer();
        productDto.setCategoryDto(categoryTransformer.toDto(savedProduct.getCategory()));
        ProductVariationTransformer productVariationTransformer = new ProductVariationTransformer();
        Set<ProductVariationDto> productVariationDtoSet = new HashSet<>();
        savedProduct.getProductVariations().forEach(productVariation -> {
            productVariationDtoSet.add(productVariationTransformer.toDto(productVariation));
        });
        productDto.setProductVariationDtoSet(productVariationDtoSet);
        return productDto;
    }

    public List<ProductDto> viewAllProductsByCustomer(Long id, Pageable pageable) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent())
            throw new ECommerceException(ErrorCode.NO_CATEGORY_FOUND_WITH_GIVEN_ID);
        Category savedCategory = category.get();
        ProductTransformer productTransformer = new ProductTransformer();
        List<ProductDto> productDtoList = new ArrayList<>();
        if (savedCategory.getSubcategories() == null || savedCategory.getSubcategories().isEmpty()) {
            List<Product> productList = productRepository.findByCategoryId(id, pageable);
            for (Product product : productList) {
                if ((product.isActive() == true) && (product.isDeleted() == false)) {

                    ProductVariationTransformer productVariationTransformer = new ProductVariationTransformer();
                    Set<ProductVariationDto> productVariationDtoSet = new HashSet<>();
                    product.getProductVariations().forEach(productVariation -> {
                        productVariationDtoSet.add(productVariationTransformer.toDto(productVariation));
                    });
                    ProductDto productDto = productTransformer.toDto(product);
                    productDto.setProductVariationDtoSet(productVariationDtoSet);
                    productDtoList.add(productDto);
                }


            }
        }
//        else {
//            for (Category childCategory : savedCategory.getSubcategories()) {
//                Long categoryId=childCategory.getId();
//                List<Product> productList = productRepository.findByCategoryId(categoryId, pageable);
//                for (Product product : productList) {
//                    if ((product.isActive() == true) && (product.isDeleted() == false) && (product.getProductVariations() != null)) {
//                        productDtoList.addAll( viewAllProductsByCustomer(childCategory.getId(),pageable));
//                    }
//                }
//
//            }
//        }
        else {
            for (Category childCategory : savedCategory.getSubcategories()) {
                productDtoList.addAll(viewAllProductsByCustomer(childCategory.getId(), pageable));
            }

        }
        return productDtoList;
    }


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>       ADMIN       >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //ADMIN:-to view product
    public ProductDto viewProductAdmin(String username, Long id) {
//        Optional<Product> product = productRepository.findById(id);
//        if (!product.isPresent()) {
//            throw new ECommerceException(ErrorCode.NO_PRODUCT_FOUND);
//        }
//        Product savedProduct = product.get();
//        ProductTransformer productTransformer = new ProductTransformer();
//        ProductDto productDto = productTransformer.toDto(savedProduct);
//        CategoryTransformer categoryTransformer = new CategoryTransformer();
//        productDto.setCategoryDto(categoryTransformer.toDto(savedProduct.getCategory()));
//
//
//        /*Here we have to return all the productvariation primary image*/
//
////        ProductVariationTransformer productVariationTransformer=new ProductVariationTransformer();
////        Set<ProductVariation> productVariationSet=productDto.getProductVariations();
////        HashMap<Long,String> hm=new HashMap<Long, String>();
////
////        productVariationSet.forEach(productVariation -> {
////            hm.put(productVariation.getId(),productVariation.getPrimaryImageName());
////        });
//
//        return productDto;
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ECommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        Product savedProduct = product.get();
        if (savedProduct.isDeleted() == true || savedProduct.isActive() == false) {
            throw new ECommerceException(ErrorCode.PRODUCT_IS_DELETED);

        }
        if (savedProduct.getProductVariations() == null || savedProduct.getProductVariations().isEmpty())
            throw new ECommerceException(ErrorCode.NO_PRODUCT_VARIATION_FOUND);
        ProductTransformer productTransformer = new ProductTransformer();
        ProductDto productDto = productTransformer.toDto(savedProduct);
        CategoryTransformer categoryTransformer = new CategoryTransformer();
        productDto.setCategoryDto(categoryTransformer.toDto(savedProduct.getCategory()));
        ProductVariationTransformer productVariationTransformer = new ProductVariationTransformer();
        Set<ProductVariationDto> productVariationDtoSet = new HashSet<>();
        savedProduct.getProductVariations().forEach(productVariation -> {
            productVariationDtoSet.add(productVariationTransformer.toDto(productVariation));
        });
        productDto.setProductVariationDtoSet(productVariationDtoSet);
        return productDto;

    }

    //ADMIN:-to view all products
    public List<ProductDto> viewAllProductsByAdmin(String username, Pageable pageable) {
        List<Product> products = productRepository.findAll(pageable);
        List<ProductDto> productDtoList = new ArrayList<>();
        ProductTransformer productTransformer = new ProductTransformer();
        CategoryTransformer categoryTransformer = new CategoryTransformer();
        ProductVariationTransformer productVariationTransformer = new ProductVariationTransformer();

        products.forEach(product ->
                {

                    ProductDto productDto = productTransformer.toDto(product);
                    productDto.setCategoryDto(categoryTransformer.toDto(product.getCategory()));
                    if (product.getProductVariations() != null) {
                        Set<ProductVariationDto> productVariationDtoSet = new HashSet<>();
                        product.getProductVariations().forEach(productVariation -> {
                            productVariationDtoSet.add(productVariationTransformer.toDto(productVariation));
                            productDto.setProductVariationDtoSet(productVariationDtoSet);
                        });

                        productDtoList.add(productDto);
                    }

                }

        );
        return productDtoList;
        /*All variation primary images are left*/
    }

    //ADMIN:-activating and deactivating product
    public String activateDeactivateProduct(String username, Long id, String choice, Locale locale) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent())
            throw new ECommerceException(ErrorCode.NO_PRODUCT_FOUND);

        Product savedProduct = product.get();
        if (choice.equalsIgnoreCase("activate")) {
            if (savedProduct.isActive())
                throw new ECommerceException(ErrorCode.PRODUCT_ALREADY_ACTIVE);
            savedProduct.setActive(true);
            productRepository.save(savedProduct);
            String message = "Your registered product " + savedProduct.getName() + " with id : " + savedProduct.getId() + " has been activated successfully";
            emailService.sendEmail(username, "Product Activation", message);
            return messageSource.getMessage("message.productActivation", null, locale);

        }
        if (choice.equalsIgnoreCase("deactivate")) {
            if (!savedProduct.isActive())
                throw new ECommerceException(ErrorCode.PRODUCT_ALREADY_INACTIVE);
            savedProduct.setActive(false);
            productRepository.save(savedProduct);
            String message = "Your registered product " + savedProduct.getName() + " with id : " + savedProduct.getId() + " has been deactivated successfully";
            emailService.sendEmail(username, "Product Deactivation", message);

        }
        return messageSource.getMessage("message.productDeActivation", null, locale);

    }

    public ProductDetailsDto viewProductDetail(Long productId, Long variationId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ECommerceException(ErrorCode.NO_PRODUCT_FOUND);
        }
        Product savedProduct = product.get();
        if (savedProduct.isDeleted() == true || savedProduct.isActive() == false) {
            throw new ECommerceException(ErrorCode.PRODUCT_IS_DELETED);

        }
        if (savedProduct.getProductVariations() == null || savedProduct.getProductVariations().isEmpty())
            throw new ECommerceException(ErrorCode.NO_PRODUCT_VARIATION_FOUND);
        ProductDetailsDto productDetailsDto = new ProductDetailsDto();
        ProductTransformer productTransformer = new ProductTransformer();
        ProductDto productDto = productTransformer.toDto(savedProduct);
        productDetailsDto.setProductDto(productDto);
        Optional<ProductVariation> productVariation = productVariationRepository.findById(variationId);
        ProductVariation savedProductVariation = productVariation.get();
        ProductVariationTransformer productVariationTransformer = new ProductVariationTransformer();
        ProductVariationDto productVariationDto = productVariationTransformer.toDto(savedProductVariation);
        productDetailsDto.setProductVariationDto(productVariationDto);
        return productDetailsDto;

    }

}
