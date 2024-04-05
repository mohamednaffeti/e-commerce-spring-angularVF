package com.example.ecommerce.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data

@NoArgsConstructor
@Builder
public class ProduitDTO {
    private String name;
    private String description;
    private double price;
    private String reference;
    private int quantite;




    public ProduitDTO(String name, String description, double price, String reference, int quantite) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.reference = reference;
        this.quantite = quantite;

    }
}
