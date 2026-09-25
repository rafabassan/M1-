package com.example.crud.domain.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RequestProduct(
        @NotBlank(message = "O nome é obrigatório") String name,
        @NotBlank(message = "A categoria é obrigatória") String category,
        @NotNull(message = "O preço é obrigatório") @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero") BigDecimal price,
        @NotNull(message = "O centro de distribuição é obrigatório") DistributionCenter distributionCenter
) {}
