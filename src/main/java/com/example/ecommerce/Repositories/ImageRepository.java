package com.example.ecommerce.Repositories;

import java.util.List;
import java.util.Optional;

import com.example.ecommerce.entities.Image;
import com.example.ecommerce.entities.Produit;
import com.example.ecommerce.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);

    List<Image> findByProduit(Produit produit);
    List<Image> findByUtilisateur(Utilisateur utilisateur);
}
