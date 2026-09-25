package com.example.crud.domain.product;

import jakarta.validation.constraints.NotBlank;

public record RequestCategory(@NotBlank(message = "A categoria é obrigatória") String category) {}
