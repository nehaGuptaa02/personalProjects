package com.springbootcamp.bootloader;


import com.springbootcamp.models.*;
import com.springbootcamp.repos.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryMetadataFieldRepository categoryMetadataFieldRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() < 1) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Role roleAdmin = new Role("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
            Role roleCustomer = new Role("ROLE_CUSTOMER");
            roleRepository.save(roleCustomer);
            Role roleSeller = new Role("ROLE_SELLER");
            roleRepository.save(roleSeller);

            //Adding admin
            User user1 = new User();
            user1.setEmail("neha.guptajune02@gmail.com");
            user1.setFirstName("neha");

            user1.setMiddleName("");
            user1.setLastName("Gupta");
            user1.setPassword(passwordEncoder.encode("pass"));
            user1.setActive(true);
            user1.setDeleted(false);
            user1.setAccountNonLocked(true);
            user1.setFilePath("");
            List<Role> roleList = new ArrayList<>();
            roleList.add(roleAdmin);
            user1.setRoles(roleList);
            userRepository.save(user1);

            //Adding Seller
            Seller seller1 = new Seller();
            seller1.setEmail("sumitkumar@gmail.com");
            seller1.setFirstName("sumit");
            seller1.setMiddleName("");
            seller1.setLastName("kumar");
            seller1.setPassword(passwordEncoder.encode("pass"));
            seller1.setCompanyContact("8445778970L");
            seller1.setActive(true);
            seller1.setDeleted(false);
            seller1.setAccountNonLocked(true);
            seller1.setCompanyName("Comapny Name");
            seller1.setGst("Gst");
            seller1.setFilePath("");
            List<Role> roleListSeller = new ArrayList<>();
            roleListSeller.add(roleSeller);
            seller1.addAddress(new Address("Kanpur", "UttarPradesh", "India", 208001, "Home", "gali no.4"));
            seller1.setRoles(roleListSeller);
            sellerRepository.save(seller1);

            //Adding customer
            Customer customer1 = new Customer();
            customer1.setEmail("shreyamittal@gmail.com");
            customer1.setFirstName("Shreya");
            customer1.setMiddleName("");
            customer1.setLastName("Mittal");
            customer1.setPassword(passwordEncoder.encode("pass"));
            customer1.setActive(true);
            customer1.setDeleted(false);
            customer1.setAccountNonLocked(true);
            customer1.setContact("9719503576L");
            customer1.setFilePath("");
            List<Role> roleListCustomer = new ArrayList<>();
            roleListCustomer.add(roleCustomer);
            customer1.setRoles(roleListCustomer);
            customer1.addAddress(new Address("Muzaffarnagar", "UttarPradesh", "India", 251001, "Home", "Mandir gali"));
            customer1.addAddress(new Address("Greater Noida", "UttarPradesh", "India", 201306, "Office", "ToTheNew"));
            customerRepository.save(customer1);


            Customer customer2 = new Customer();
            customer2.setEmail("sakshigupta@gmail.com");
            customer2.setFirstName("Sakshi");
            customer2.setMiddleName("");
            customer2.setLastName("Gupta");
            customer2.setPassword(passwordEncoder.encode("pass"));
            customer2.setActive(true);
            customer2.setDeleted(false);
            customer2.setAccountNonLocked(true);
            customer2.setContact("9774587591L");
            List<Role> roleListCustomer2 = new ArrayList<>();
            roleListCustomer2.add(roleCustomer);
            customer2.setRoles(roleListCustomer2);
            customer2.setFilePath("");
            customer2.addAddress(new Address("Aligarh", "UttarPradesh", "India", 200201, "Home", "Gular road"));
            customer2.addAddress(new Address("Greater Noida", "UttarPradesh", "India", 201306, "Office", "ToTheNew"));
            customerRepository.save(customer2);
            System.out.println("Total users saved::" + userRepository.count());


            String sizeValues = "XS,S,M,L,XL,XXL";
            CategoryMetadataFieldValues fieldValues1 = new CategoryMetadataFieldValues(sizeValues);
            String colorValues = "red, green, black";
            CategoryMetadataFieldValues fieldValues2 = new CategoryMetadataFieldValues(colorValues);
            CategoryMetadataField sizeField = new CategoryMetadataField("size");
            CategoryMetadataField colorField = new CategoryMetadataField("color");

            //Adding Categories along with subcategories
//            Category womenFashion = new Category("WomenFashion");

            Category clothing = new Category("Clothing");
            Category footwear = new Category("Footwear");
            Category bagsAndClutches = new Category("Bags and clutches");
//            womenFashion.addSubCategory(clothing);
//            womenFashion.addSubCategory(footwear);
//            womenFashion.addSubCategory(bagsAndClutches);

            Category westernWear = new Category("Western Wear");
            Category ethnicWear = new Category("Ethnic Wear");
            Category sportsWear = new Category("sportsWear");
            clothing.addSubCategory(westernWear);
            clothing.addSubCategory(ethnicWear);
            clothing.addSubCategory(sportsWear);

            Category boots = new Category("Boots");
            Category casualShoes = new Category("Casual Shoes");
            Category flats = new Category("Flats");
            footwear.addSubCategory(boots);
            footwear.addSubCategory(casualShoes);
            footwear.addSubCategory(flats);

            Category handBags = new Category("Hand Bags");
            Category clutches = new Category("Clutches");
            Category wallets = new Category("Wallets");
            bagsAndClutches.addSubCategory(handBags);
            bagsAndClutches.addSubCategory(clutches);
            bagsAndClutches.addSubCategory(wallets);
//            categoryRepository.save(womenFashion);
            categoryRepository.save(clothing);
            categoryRepository.save(footwear);
            categoryRepository.save(bagsAndClutches);
            System.out.println("total categories " + categoryRepository.count());

            clothing.addFieldValues(fieldValues1);
            sizeField.addFieldValues(fieldValues1);
            fieldValues1.setCategory(clothing);

            categoryMetadataFieldRepository.save(sizeField);
            clothing.addFieldValues(fieldValues2);
            colorField.addFieldValues(fieldValues2);
            fieldValues2.setCategory(clothing);
            categoryMetadataFieldRepository.save(colorField);
            categoryRepository.save(clothing);
            Category menFashion=new Category("Men Fashion");
            categoryRepository.save(menFashion);




            /*ADDING ALL PRODUCTS AND PRODUCT VARIATIONS*/
            Product product1 = new Product("Women Solid A-Line Dress", " solid knitted V-line dress, has a round neck, short sleeves", "ONLY", false, false, true, false);
            ProductVariation smallSizeDress1 = new ProductVariation(10, 3499L);
            Map<String, Object> variation1 = new HashMap<>();
            variation1.put("color", "green");
            smallSizeDress1.setActive(true);
            smallSizeDress1.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591101563/Product/IMG_20200602_175452_uyidqa.jpg");
            smallSizeDress1.setMetadata(variation1);
            product1.addVariation(smallSizeDress1);

            ProductVariation smallSizeDress2 = new ProductVariation(10, 4499L);
            Map<String, Object> variation2 = new HashMap<>();
            variation2.put("color", "pink");
            smallSizeDress2.setActive(true);
            smallSizeDress2.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591101563/Product/IMG_20200602_175438_judcxi.jpg");
            smallSizeDress2.setMetadata(variation1);
            product1.addVariation(smallSizeDress2);

            product1.setCategory(westernWear);
            seller1.addProduct(product1);
            productRepository.save(product1);

            Product product2 = new Product("Women Solid Straight Kurta", "Solid straight kurta, has a keyhole neck, three-quarter sleeves", "W", false, false, true, false);
            ProductVariation smallSizeKurta1 = new ProductVariation(10, 3499L);
            Map<String, Object> variationKurta1 = new HashMap<>();
            variationKurta1.put("colour", "blue");
            smallSizeKurta1.setActive(true);
            smallSizeKurta1.setMetadata(variation1);
            smallSizeKurta1.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591101566/Product/IMG_20200602_175824_vmsrg4.jpg");
            product2.addVariation(smallSizeKurta1);


            ProductVariation smallSizeKurta2 = new ProductVariation(10, 2499L);
            Map<String, Object> variationKurta2 = new HashMap<>();
            variationKurta2.put("colour", "red");
            smallSizeKurta2.setActive(true);
            smallSizeKurta2.setMetadata(variation1);
            smallSizeKurta2.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591101566/Product/IMG_20200602_175840_fb0ruk.jpg");
            product2.addVariation(smallSizeKurta2);

            product2.setCategory(ethnicWear);
            seller1.addProduct(product2);
            productRepository.save(product2);


            Product product3 = new Product("Women  Melange Solid Round Neck T-shirt", "Get the best of comfort and style with the HRX Women's Athleisure T-shirt.", "HRX", false, false, true, false);
            ProductVariation smallSizeSports1 = new ProductVariation(10, 3499L);
            Map<String, Object> variationSports1 = new HashMap<>();
            variationSports1.put("color", "black");
            smallSizeSports1.setActive(true);
            smallSizeSports1.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591101564/Product/IMG_20200602_180257_g2xkyr.jpg");
            smallSizeSports1.setMetadata(variationSports1);
            product3.addVariation(smallSizeSports1);

            ProductVariation smallSizeSports2 = new ProductVariation(10, 3499L);
            Map<String, Object> variationSports2 = new HashMap<>();
            variationSports2.put("color", "red");
            smallSizeSports2.setActive(true);
            smallSizeSports2.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591101566/Product/IMG_20200602_180305_bt45xo.jpg");
            smallSizeSports2.setMetadata(variationSports2);
            product3.addVariation(smallSizeSports2);

            product3.setCategory(sportsWear);
            seller1.addProduct(product3);
            productRepository.save(product3);

            /*For footwear*/

            Product product4 = new Product("Women  Boots", "A pair of  round toe   boots, has high-top styling, closed back with zip detail.", "Cartlon London", false, false, true, false);
            ProductVariation size4Boots = new ProductVariation(10, 1499L);
            Map<String, Object> variationBoots1 = new HashMap<>();
            variationBoots1.put("color", "Black");
            size4Boots.setActive(true);
            size4Boots.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591101563/Product/IMG_20200602_173703_tvqbzq.jpg");
            size4Boots.setMetadata(variationBoots1);
            product4.addVariation(size4Boots);

            ProductVariation size5Boots = new ProductVariation(10, 1799L);
            Map<String, Object> variationBoots2 = new HashMap<>();
            variationBoots2.put("color", "Blue");
            size5Boots.setActive(true);
            size5Boots.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591101564/Product/IMG_20200602_173731_hmteiq.jpg");
            size5Boots.setMetadata(variationBoots2);
            product4.addVariation(size5Boots);

            product4.setCategory(boots);
            seller1.addProduct(product4);
            productRepository.save(product4);

            Product product5 = new Product("Women Sneakers", " A pair of round-toe  sneakers, has regular styling.", "Catwalk", false, false, true, false);
            ProductVariation size4Casuals = new ProductVariation(10, 1799L);
            Map<String, Object> variationCasuals1 = new HashMap<>();
            variationCasuals1.put("color", "Black");
            size4Casuals.setActive(true);
            size4Casuals.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591101564/Product/IMG_20200602_174751_gk5mfl.jpg");
            size4Casuals.setMetadata(variationCasuals1);
            product5.addVariation(size4Casuals);

            ProductVariation size5Casuals = new ProductVariation(10, 1099L);
            Map<String, Object> variationCasuals2 = new HashMap<>();
            variationCasuals2.put("color", "White");
            size5Casuals.setActive(true);
            size5Casuals.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591101563/Product/IMG_20200602_174759_bnhawx.jpg");
            size5Casuals.setMetadata(variationCasuals2);
            product5.addVariation(size5Casuals);

            product5.setCategory(casualShoes);
            seller1.addProduct(product5);
            productRepository.save(product5);




            Product product6 = new Product("Women Sports Top", " Straight cut Sports Top", "H & M", false, false, true, false);

            ProductVariation sportsTop1 = new ProductVariation(10, 1996L);
            Map<String, Object> sportsTopGreen = new HashMap<>();
            sportsTopGreen.put("color", "Green");
            sportsTop1.setActive(true);
            sportsTop1.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591284864/Product/IMG_20200604_205502_xj4mbo.jpg");
            sportsTop1.setMetadata(sportsTopGreen);
            product6.addVariation(sportsTop1);

            ProductVariation sportsTop2 = new ProductVariation(10, 1996L);
            Map<String, Object> sportsTopGray = new HashMap<>();
            sportsTopGray.put("color", "Gray");
            sportsTop2.setActive(true);
            sportsTop2.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591284865/Product/IMG_20200604_205448_knibat.jpg");
            sportsTop2.setMetadata(sportsTopGray);
            product6.addVariation(sportsTop2);
            product6.setCategory(sportsWear);
            seller1.addProduct(product6);
            productRepository.save(product6);


            Product product7 = new Product("Women Sports BottomWear", " Cool training Tights", "HRX", false, false, true, false);

            ProductVariation sportsTights1 = new ProductVariation(10, 2023L);
            Map<String, Object> sportsTightBlack = new HashMap<>();
            sportsTightBlack.put("color", "Black");
            sportsTights1.setActive(true);
            sportsTights1.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591284864/Product/IMG_20200604_205631_iiddqo.jpg");
            sportsTights1.setMetadata(sportsTightBlack);
            product7.addVariation(sportsTights1);

            ProductVariation sportsTights2 = new ProductVariation(10, 2023L);
            Map<String, Object> sportsTightBlue = new HashMap<>();
            sportsTightBlue.put("color", "Blue");
            sportsTights2.setActive(true);
            sportsTights2.setPrimaryImageName("https://res.cloudinary.com/dbnkpr18k/image/upload/v1591284865/Product/IMG_20200604_205647_x7ebta.jpg");
            sportsTights2.setMetadata(sportsTightBlue);
            product7.addVariation(sportsTights2);




            product7.setCategory(sportsWear);
            seller1.addProduct(product7);
            productRepository.save(product7);

        }
    }
}
