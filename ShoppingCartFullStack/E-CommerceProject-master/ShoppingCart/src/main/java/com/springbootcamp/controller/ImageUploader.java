package com.springbootcamp.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Map;

import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.models.User;
import com.springbootcamp.repos.UserRepository;
import com.springbootcamp.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/image")
public class ImageUploader {
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/userImage")
    public String uploadUserImage(@RequestParam("image") MultipartFile multipartFile, HttpServletRequest httpServletRequest)
            throws IOException {
        Principal principal = httpServletRequest.getUserPrincipal();
        String username = principal.getName();
        User user=userRepository.findByEmail(username);
        Long userId=user.getId();

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name","dbnkpr18k",
                "api_key", "127611299927577",
                "api_secret","t0B-kCU2d9Syr5Zcd8cqrzTNm1Y"));
        File file = Files.createTempFile("temp", multipartFile.getOriginalFilename()).toFile();
        multipartFile.transferTo(file);
        try {
            Map response = cloudinary.uploader().upload(file, ObjectUtils.asMap());
            String imageApi = (String) response.get("url");
             imageService.storeFile(multipartFile, username,imageApi);
             return imageApi;
        } catch (Exception e) {
            throw new ECommerceException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }
    }
    /*
    Method to upload productVariation
    */
    @PostMapping("/setProfileImagePath")
    public String setProfileImagePath(@RequestParam("imageUrl") String url, HttpServletRequest httpServletRequest)
    {
        Principal principal = httpServletRequest.getUserPrincipal();
        String username = principal.getName();
        return imageService.setProfileImagePath(username,url);
    }

}