package com.example.ecommerce.services;

import com.example.ecommerce.Repositories.CategorieRepository;
import com.example.ecommerce.Repositories.ProduitRepository;
import com.example.ecommerce.Repositories.StockRepository;
import com.example.ecommerce.Repositories.UserRepository;
import com.example.ecommerce.entities.Categorie;
import com.example.ecommerce.entities.Produit;
import com.example.ecommerce.entities.Stock;
import com.example.ecommerce.entities.Vendeur;
import com.example.ecommerce.entities.dto.ProduitDTO;
import com.example.ecommerce.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProduitServiceImpl implements IProduitService {
    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private StockRepository stockRepository;


    @Override
    @Transactional
    public Produit addProduct(Long vendeurId, Long categoryId, ProduitDTO produitDTO) {
        Vendeur vendeur = (Vendeur) userRepository.findById(vendeurId).orElseThrow(() -> new DataNotFoundException("Vendor not found"));
        Categorie categorie = categorieRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException("Category not found"));

        Produit produit = new Produit();
        produit.setName(produitDTO.getName());
        produit.setDescription(produitDTO.getDescription());
        produit.setPrice(produitDTO.getPrice());
        produit.setReference(produitDTO.getReference());
        produit.setVendeur(vendeur);
        produit.setCategory(categorie);
        produit.setCreatedAt(LocalDateTime.now());

        Produit savedProduit = produitRepository.saveAndFlush(produit);

        Stock stock = new Stock();
        stock.setProduit(savedProduit);
        stock.setAvailable(true);
        stock.setQuantity(produitDTO.getQuantite());
        stockRepository.save(stock);

        savedProduit.setStock(stock);
        produitRepository.save(savedProduit);

        return savedProduit;
    }

    @Override
    public List<Produit> getAllProducts() {
        return produitRepository.findAll();
    }

    @Override
    public List<Produit> getAllProductsByCategory(Long categoryId) {
        return produitRepository.findByCategoryIdCategory(categoryId);
    }

    @Override
    public List<Produit> getAllProductsByVendeur(Long vendeurId) {
        return produitRepository.findByVendeurId(vendeurId);
    }

    @Override
    public Produit updateProduit(Long produitId, ProduitDTO produitDTO) {
        Produit existingProduit = produitRepository.findById(produitId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));


        existingProduit.setName(produitDTO.getName());
        existingProduit.setDescription(produitDTO.getDescription());
        existingProduit.setPrice(produitDTO.getPrice());
        existingProduit.setReference(produitDTO.getReference());

        if (existingProduit.getStock() != null && produitDTO.getQuantite() != existingProduit.getStock().getQuantity()) {
            Stock stock = existingProduit.getStock();
            stock.setQuantity(produitDTO.getQuantite());
            stock.setAvailable(stock.getQuantity() != 0);
            stockRepository.save(stock);
        }
        return produitRepository.save(existingProduit);
    }

    @Override
    @Transactional
    public void deleteProduit(Long produitId) {
        Produit existingProduit = produitRepository.findById(produitId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));

        produitRepository.delete(existingProduit);
    }


}
