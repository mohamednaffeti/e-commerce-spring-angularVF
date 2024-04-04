package com.example.ecommerce.Repositories;

import com.example.ecommerce.entities.PanierDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PanierDetailsRepository extends JpaRepository<PanierDetails,Long> {
    List<PanierDetails> findByClientId(Long idUser);
    List<PanierDetails> findByVendeurId(Long idUser);
    List<PanierDetails> findByDateCommande(LocalDateTime dateCommande);
    void deleteByDateCommande(LocalDateTime dateCommande);
}
