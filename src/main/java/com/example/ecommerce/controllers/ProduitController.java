package com.example.ecommerce.controllers;

import com.example.ecommerce.entities.Produit;
import com.example.ecommerce.entities.dto.ProduitDTO;
import com.example.ecommerce.services.IProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    private IProduitService produitService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/images";

    @PostMapping(value = "/{vendeurId}/{categoryId}")
    public ResponseEntity<Produit> addProduct(@PathVariable Long vendeurId,@PathVariable Long categoryId,@RequestBody ProduitDTO produitDTO) throws IOException {


        Produit createdProduit = produitService.addProduct(vendeurId, categoryId, produitDTO);
        if (createdProduit != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduit);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Produit>> getAllProducts() {
        List<Produit> produits = produitService.getAllProducts();
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/getByCategory/{categoryId}")
    public ResponseEntity<List<Produit>> getAllProductsByCategory(@PathVariable Long categoryId) {
        List<Produit> produits = produitService.getAllProductsByCategory(categoryId);
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/getByVendeur/{vendeurId}")
    public ResponseEntity<List<Produit>> getAllProductsByVendeur(@PathVariable Long vendeurId) {
        List<Produit> produits = produitService.getAllProductsByVendeur(vendeurId);
        return ResponseEntity.ok(produits);
    }

    @PutMapping("/update/{produitId}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long produitId, @RequestBody ProduitDTO produitDTO) {
        Produit updatedProduit = produitService.updateProduit(produitId, produitDTO);
        return ResponseEntity.ok(updatedProduit);
    }

    @DeleteMapping("/delete/{produitId}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long produitId) {
        produitService.deleteProduit(produitId);
        return ResponseEntity.noContent().build();
    }
}