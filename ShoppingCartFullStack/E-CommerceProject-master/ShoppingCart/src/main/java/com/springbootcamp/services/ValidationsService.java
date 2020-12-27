package com.springbootcamp.services;

import com.springbootcamp.models.Category;
import com.springbootcamp.models.Product;
import com.springbootcamp.models.Seller;
import com.springbootcamp.models.User;
import com.springbootcamp.dtos.ProductDto;
import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.repos.CategoryRepository;
import com.springbootcamp.repos.ProductRepository;
import com.springbootcamp.repos.SellerRepository;
import com.springbootcamp.repos.UserRepository;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

import java.util.Optional;

@Service
public class ValidationsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null)
            return true;
        else
            return false;
    }

    boolean passwordExist(String password) {
        User user = userRepository.findByPassword(password);
        if (user != null)
            return true;
        else
            return false;
    }

    public boolean isCompanyNameUnique(String value) {
        Seller seller = sellerRepository.findByCompanyName(value);
        if (seller != null)
            return false;
        else
            return true;
    }
    public boolean isGSTUnique(String gst)
    {
        Seller seller=sellerRepository.findBygst(gst);
        if (seller != null)
            return false;
        else
            return true;
    }

    public boolean isPasswordValid(String password) {
        int digit = 0;
        int special = 0;
        int upCount = 0;
        int loCount = 0;
        boolean flag = false;
        if (password.length() >= 8 && password.length() <= 15) {
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (Character.isUpperCase(c)) {
                    upCount++;
                }
                if (Character.isLowerCase(c)) {
                    loCount++;
                }
                if (Character.isDigit(c)) {
                    digit++;
                }
                if (c >= 33 && c <= 46 || c == 64) {
                    special++;
                }
            }
        }
        if (special >= 1 && loCount >= 1 && upCount >= 1 && digit >= 1)
            flag = true;
        return flag;
    }

    public String isValidProduct(String username, ProductDto productDto) {

        String message;
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
        if (!category.isPresent()) {
//            message = "Category does not exist.";
//            return message;
            throw  new ECommerceException(ErrorCode.NO_CATEGORY_FOUND_WITH_GIVEN_ID);
        }

        Category savedCategory = category.get();
        if (savedCategory.getSubcategories() == null) {
//            message = "Category is not a leaf category.";
//            return message;
            throw  new ECommerceException(ErrorCode.CATEGORY_ID_IS_NOT_OF_LEAF_NODE);
        }
        Product savedProduct = productRepository.findByName(productDto.getName());
//        Optional<Product> product = productRepository.findByName(productDto.getName());
//        Product savedProduct = product.get();
        if(savedProduct!=null)
        {
            throw  new ECommerceException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }
        try {


            if (savedProduct != null) {
                if (savedProduct.getCategory().getId().equals(productDto.getCategoryId())) ;
                {
                    if (savedProduct.getBrand().equalsIgnoreCase(productDto.getBrand())) {
                        if (savedProduct.getSeller().getEmail().equalsIgnoreCase(username)) {

//                            message = "Product with similar details already exists.";
//                            return message;
                            throw  new ECommerceException(ErrorCode.PRODUCT_ALREADY_EXISTS);
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Wrapper exception: " + e);

            // Print the 'actual' exception:
            System.out.println("Underlying exception: " + e.getCause());
        }

        message = "Success";
        return message;
    }
    public boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,9}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public boolean isValidGST(String gst){
        String gstRegex="\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}";
        Pattern pat = Pattern.compile(gstRegex);
        if (gst == null)
            return false;
        return pat.matcher(gst).matches();
    }
}


