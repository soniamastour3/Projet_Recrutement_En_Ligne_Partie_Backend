package com.example.entrepriseservice.Utils;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
public class StoresService {
    private final Path rootLocation = Paths.get("C:\\Users\\ahmed\\Desktop\\Projet Bootcamp\\Backend\\recrutement_en_ligne\\entrepriseService\\upload");

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    public String store(MultipartFile file) {
        try {

            String fileName = Integer.toString(new Random().nextInt(10000));
            //extention
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'),
                    file.getOriginalFilename().length());
            //le nom de l'image
            String name  = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
            String original = name + fileName + ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            return original;


        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }
//public String store(MultipartFile file) {
//    try {
//        if (file == null || file.isEmpty()) {
//            throw new RuntimeException("Le fichier est vide.");
//        }
//
//        // Ensure the storage directory exists
//        if (!Files.exists(this.rootLocation)) {
//            Files.createDirectories(this.rootLocation);
//        }
//
//        // Log the file information
//        System.out.println("Uploading file: " + file.getOriginalFilename() + " Size: " + file.getSize());
//
//        // Get the file extension
//        String originalFilename = file.getOriginalFilename();
//        if (originalFilename == null || !originalFilename.contains(".")) {
//            throw new IllegalArgumentException("Nom de fichier invalide: " + originalFilename);
//        }
//        String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
//
//        // Generate a unique file name
//        String uniqueFileName = UUID.randomUUID().toString() + ext;
//
//        // Define the destination path
//        Path destination = this.rootLocation.resolve(uniqueFileName);
//
//        // Copy the file to the destination
//        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
//
//        // Log the stored file path
//        System.out.println("File successfully stored at: " + destination.toString());
//
//        // Return the relative file name or path
//        return uniqueFileName;
//
//    } catch (Exception e) {
//        throw new RuntimeException("Échec du téléchargement du fichier: " + e.getMessage(), e);
//    }
//}

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found or unreadable: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to load file: " + filename, e);
        }
    }
}
