package com.example.crud.domain.product;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(length = 36, nullable = false)
    private String id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 80)
    private String category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "distribution_center", nullable = false, length = 40)
    private DistributionCenter distributionCenter;

    public Product() {}

    public Product(String id, String name, String category, BigDecimal price, Boolean active, DistributionCenter distributionCenter) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.active = active;
        this.distributionCenter = distributionCenter;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public DistributionCenter getDistributionCenter() { return distributionCenter; }
    public void setDistributionCenter(DistributionCenter distributionCenter) { this.distributionCenter = distributionCenter; }
}
