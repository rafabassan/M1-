package com.example.crud.domain.product;

import java.math.BigDecimal;

public record TopExpensiveProducts(String id, String name, BigDecimal price, String category, DistributionCenter distributionCenter) {}
