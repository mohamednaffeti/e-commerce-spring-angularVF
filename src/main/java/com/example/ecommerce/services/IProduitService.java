package com.example.ecommerce.services;

import com.example.ecommerce.entities.Produit;
import com.example.ecommerce.entities.dto.ProduitDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProduitService {
    Produit addProduct(Long vendeurId, Long categoryId, ProduitDTO produit);
    List<Produit> getAllProducts();

    List<Produit> getAllProductsByCategory(Long categoryId);
    List<Produit> getAllProductsByVendeur(Long vendeurId);

    Produit updateProduit(Long produitId, ProduitDTO produitDTO);

    void deleteProduit(Long produitId);

}
