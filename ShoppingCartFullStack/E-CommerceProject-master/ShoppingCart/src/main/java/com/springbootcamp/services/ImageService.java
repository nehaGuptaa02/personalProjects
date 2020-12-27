package com.springbootcamp.services;


import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.models.User;
import com.springbootcamp.exceptions.FileStorageException;
import com.springbootcamp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Service
public class ImageService {

    @Autowired
    private UserRepository userRepository;

    public User storeFile(MultipartFile file, String username,String imageApi) {
        // Normalize file name
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        String fileName = date + file.getOriginalFilename();
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            User user=userRepository.findByEmail(username);
            user.setFileName(fileName);
            user.setFileType(file.getContentType());
//            String filePath="/home/neha/ProjectImages/"+fileName;
            user.setFilePath(imageApi);
            return userRepository.save(user);
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public User getFile(String fileName) {

        Optional<User> user = userRepository.findByFileName(fileName);
        User savedUser= user.get();
        if (user == null) {
            throw new ECommerceException(ErrorCode.FILE_NOT_FOUND_EXCEPTION);
        }
        return savedUser;
    }

    public String setProfileImagePath(String username, String url) {
        User user=userRepository.findByEmail(username);
        if(user==null)
        {
            throw new ECommerceException(ErrorCode.NO_USER_FOUND_WITH_GIVEN_EMAIL);

        }
        if(url==null||url.isEmpty()||url.trim().length()==0)
        {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }
        user.setFilePath(url);
        userRepository.save(user);
        return "Your Profile picture has been saved successfully";
    }

//    public void write(MultipartFile file, String fileType, String fileName) throws IOException {
//        String folderPath = "home\\neha\\Downloads\\ProjectImagesUpload";
//        String filePath = folderPath + "/" + fileName;
//        byte[] bytes = file.getBytes();
//        Path path = Paths.get(folderPath + file.getOriginalFilename());
//        Files.write(path, bytes);
////        return filePath;
//    }
}


