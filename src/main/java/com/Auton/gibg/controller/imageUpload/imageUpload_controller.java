package com.Auton.gibg.controller.imageUpload;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.Auton.gibg.response.ImageRespone.ImageUploadResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/api/admin")
public class imageUpload_controller {

    @Value("${upload.dir}")
    private String uploadDir;


//    @PostMapping("/user/profile/upload")
//    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
//        ImageUploadResponse response = new ImageUploadResponse();
//
//        if (imageFile.isEmpty()) {
//            response.setMessage("Image file is required");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            // Generate a random name for the uploaded image
//            String fileExtension = StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
//            String randomFileName = UUID.randomUUID().toString() + "." + fileExtension;
//
//            // Check if the file extension is allowed (jpg, png, svg)
//            if (!fileExtension.matches("jpg|png|svg")) {
//                response.setMessage("Only JPG, PNG, and SVG files are allowed");
//                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//            }
//
//            // Construct the path to save the image within the project's directory
//            String filePath = ResourceUtils.getFile(uploadDir).getAbsolutePath() + File.separator + randomFileName;
//
//            // Create the upload directory if it doesn't exist
//            File uploadDirFile = new File(uploadDir);
//            if (!uploadDirFile.exists()) {
//                uploadDirFile.mkdirs();
//            }
//
//            // Save the uploaded image to the specified path
//            File destFile = new File(filePath);
//            imageFile.transferTo(destFile);
//
//            response.setMessage("Image uploaded successfully");
//            response.setUrlPath(filePath);
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (IOException e) {
//            e.printStackTrace();
//            response.setMessage("Error uploading image");
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/user/profile/upload")
    public ResponseEntity<ImageUploadResponse> uploadImageToCloudinary(@RequestParam("imageFile") MultipartFile imageFile) {
        ImageUploadResponse response = new ImageUploadResponse();

        if (imageFile.isEmpty()) {
            response.setMessage("Image file is required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
// Initialize Cloudinary instance
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "ds639zn4t",
                    "api_key", "297389628948212",
                    "api_secret", "gyTMSMSLKyKjVG6A_IC3bhrHUvE"
            ));

// Upload image to Cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());

// Get the Cloudinary URL of the uploaded image
            String imageUrl = (String) uploadResult.get("secure_url");

            response.setMessage("Image uploaded successfully");
            response.setUrlPath(imageUrl);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            response.setMessage("Error uploading image");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/user/profile/delete")
    public ResponseEntity<ImageUploadResponse> deleteFile(@RequestParam("filePath") String filePath) {
        ImageUploadResponse response = new ImageUploadResponse();

        try {
            Path path = Paths.get(filePath);

            // Check if the file exists
            if (Files.exists(path)) {
                Files.delete(path);
                response.setMessage("File deleted successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage("File not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.setMessage("Error deleting file");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



