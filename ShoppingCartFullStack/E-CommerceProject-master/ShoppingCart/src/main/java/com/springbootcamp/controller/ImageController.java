//package com.springbootcamp.controller;
//
//
//import com.springbootcamp.models.User;
//import com.springbootcamp.payload.ImageResponse;
//import com.springbootcamp.services.ImageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import com.cloudinary.Cloudinary;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.net.MalformedURLException;
//import java.net.URLConnection;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.security.Principal;
//
//
//@RestController
//@RequestMapping("/image")
//public class ImageController {
//
//    @Autowired
//    private ImageService imageService;
//
//    //    @PostMapping("/upload")
////    public ImageResponse uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) throws IOException {
////        Principal principal = httpServletRequest.getUserPrincipal();
////        String username = principal.getName();
////        User user = imageService.storeFile(file, username);
////        imageService.write(file, file.getContentType(), user.getFileName());
////        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
////        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
////                .path("home\\Downloads\\ProjectImagesUpload\\")
////                .path(fileName)
////                .toUriString();
////        user.setFilePath(fileDownloadUri);
////        return new ImageResponse(user.getFileName(), fileDownloadUri,
////                file.getContentType(), file.getSize());
////    }
//    @PostMapping("/upload")
//    public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) throws IOException {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        Principal principal = httpServletRequest.getUserPrincipal();
//        String username = principal.getName();
//        User user = imageService.storeFile(file, username);
//        Path path = Paths.get("/home/neha/ProjectImages/" + fileName);
//        try {
//            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/home/neha/ProjectImages/")
//                .path(fileName)
//                .toUriString();
//        return ResponseEntity.ok(fileDownloadUri);
//    }
//    @RequestMapping("download/{fileName:.+}")
//    public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
//                                    @PathVariable("fileName") String fileName) throws IOException {
//        final String EXTERNAL_FILE_PATH = "/home/neha/ProjectImages/";
//        File file = new File(EXTERNAL_FILE_PATH + fileName);
//        if (file.exists()) {
//
//            //get the mimetype
//            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
//            if (mimeType == null) {
//                //unknown mimetype so set the mimetype to application/octet-stream
//                mimeType = "application/octet-stream";
//            }
//
//            response.setContentType(mimeType);
//            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
//
//            response.setContentLength((int) file.length());
//
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//
//            FileCopyUtils.copy(inputStream, response.getOutputStream());
//
//        }
//    }
////    @GetMapping("/download/{fileName:.+}")
////    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
////        Path path = Paths.get("/home/neha/ProjectImages/"+ fileName);
////        Resource resource = null;
////        try {
////            resource = new UrlResource(path.toUri());
////        } catch (MalformedURLException e) {
////            e.printStackTrace();
////        }
////        return ResponseEntity.ok()
////                .contentType(MediaType.parseMediaType(contentType))
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
////                .body(resource);
////    }
//}
//
//
//
