package com.example.ecommerce.entities;

import com.example.ecommerce.entities.enumerations.EtatStatus;
import com.example.ecommerce.entities.enumerations.TypePaiement;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PanierDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPanierDetails;
    private LocalDateTime dateCommande;
    @Enumerated(value = EnumType.STRING)
    private EtatStatus etatCommande;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produit")
    private Produit produit;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;


    @ManyToOne(fetch = FetchType.EAGER)
    private Vendeur vendeur;

    private int quantite;

}
