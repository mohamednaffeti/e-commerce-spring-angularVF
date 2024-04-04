package com.example.ecommerce.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SommeResponseDTO {
    private final String message = "La somme de la commande est :";
    private double somme;
}
