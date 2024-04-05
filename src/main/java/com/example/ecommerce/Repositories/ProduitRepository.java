package com.example.ecommerce.Repositories;

import com.example.ecommerce.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit,Long> {
    List<Produit> findByVendeurId(Long vendor);
}
