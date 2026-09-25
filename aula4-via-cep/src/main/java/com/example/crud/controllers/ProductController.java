package com.example.crud.controllers;

import com.example.crud.domain.product.*;
import com.example.crud.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) { this.service = service; }

    @GetMapping
    public List<Product> list() { return service.listActive(); }

    @GetMapping("/{id}")
    public Product findById(@PathVariable String id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody RequestProduct request,
                                          @RequestHeader(value = "X-Request-Id", required = false) String requestId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request, requestId));
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable String id, @Valid @RequestBody RequestProduct request,
                          @RequestHeader(value = "X-Request-Id", required = false) String requestId) {
        return service.update(id, request, requestId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inactivate(@PathVariable String id) {
        service.inactivate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(params = "category")
    public List<Product> byCategory(@RequestParam String category) { return service.byCategory(category); }

    @GetMapping("/distribution-center/{distributionCenter}")
    public List<Product> byDistributionCenter(@PathVariable DistributionCenter distributionCenter) { return service.byCenter(distributionCenter); }

    @GetMapping("/distribution-center/{distributionCenter}/category")
    public List<Product> byDistributionCenterAndCategory(@PathVariable DistributionCenter distributionCenter, @RequestParam String category) {
        return service.byCenterAndCategory(distributionCenter, category);
    }

    @GetMapping("/distribution-center/count")
    public List<DistributionCenterCount> countByDistributionCenter() { return service.countByCenter(); }

    @GetMapping("/price/greater-than")
    public List<Product> byMinPrice(@RequestParam BigDecimal price) { return service.byMinPrice(price); }

    @GetMapping("/name")
    public List<Product> byName(@RequestParam String name) { return service.byName(name); }

    @GetMapping("/top-expensive")
    public List<TopExpensiveProducts> topExpensive() { return service.topExpensive(); }

    @GetMapping("/{id}/availability")
    public ProductService.AvailabilityResponse checkAvailability(@PathVariable String id, @RequestParam String cep) {
        return service.checkAvailability(id, cep);
    }
}
