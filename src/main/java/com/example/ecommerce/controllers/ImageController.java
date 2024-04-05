package com.example.ecommerce.controllers;

import com.example.ecommerce.Repositories.ImageRepository;
import com.example.ecommerce.Repositories.ProduitRepository;
import com.example.ecommerce.Repositories.UserRepository;
import com.example.ecommerce.entities.Image;
import com.example.ecommerce.entities.Produit;
import com.example.ecommerce.entities.Utilisateur;
import com.example.ecommerce.entities.dto.ImageUploadResponse;
import com.example.ecommerce.util.ImageUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImageController {

    final ImageRepository imageRepository;
    final ProduitRepository produitRepository;
    final UserRepository userRepository;

    public ImageController(ImageRepository imageRepository, ProduitRepository produitRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.produitRepository = produitRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/upload/imageProduct/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageUploadResponse> uploadImageProduct(@RequestParam("image") MultipartFile file,
                                                           @PathVariable Long productId)
            throws IOException {


        Optional<Produit> optionalProduit = produitRepository.findById(productId);
        if (optionalProduit.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ImageUploadResponse("Product not found"));
        }
        imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtility.compressImage(file.getBytes()))
                .produit(optionalProduit.get())
                .build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));
    }

    @PostMapping(value = "/upload/imageUser/{idUser}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageUploadResponse> uploadImageUser(@RequestParam("image") MultipartFile file,
                                                           @PathVariable Long idUser)
            throws IOException {


        Optional<Utilisateur> optionalUser = userRepository.findById(idUser);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ImageUploadResponse("Product not found"));
        }
        imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtility.compressImage(file.getBytes()))
                .utilisateur(optionalUser.get())
                .build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));
    }



@GetMapping("/get/imagesProduct/{productId}")
public ResponseEntity<List<Image>> getImagesByProductId(@PathVariable Long productId) {
    Optional<Produit> optionalProduit = produitRepository.findById(productId);
    if (optionalProduit.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    List<Image> images = imageRepository.findByProduit(optionalProduit.get());
    return ResponseEntity.ok(images);
}

    @GetMapping("/get/imageUser/{userId}")
    public ResponseEntity<List<Image>> getImagesByUserId(@PathVariable Long userId) {
        Optional<Utilisateur> optionalUtilisateur = userRepository.findById(userId);
        if (optionalUtilisateur.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Image> images = imageRepository.findByUtilisateur(optionalUtilisateur.get());
        return ResponseEntity.ok(images);
    }


    @GetMapping(path = {"/get/image/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {

        final Optional<Image> dbImage = imageRepository.findByName(name);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(ImageUtility.decompressImage(dbImage.get().getImage()));
    }







    @DeleteMapping("/delete/image/{imageName}")
    public ResponseEntity<Void> deleteImageByName(@PathVariable String imageName) {
        Optional<Image> optionalImage = imageRepository.findByName(imageName);
        if (optionalImage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        imageRepository.delete(optionalImage.get());
        return ResponseEntity.noContent().build();
    }

}