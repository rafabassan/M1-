package com.example.crud.services;

import com.example.crud.domain.product.*;
import com.example.crud.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final ViaCepService viaCepService;

    public ProductService(ProductRepository repository, ViaCepService viaCepService) {
        this.repository = repository;
        this.viaCepService = viaCepService;
    }

    public List<Product> listActive() { return repository.findAllByActiveTrue(); }

    public Product findById(String id) {
        return repository.findById(id)
                .filter(p -> Boolean.TRUE.equals(p.getActive()))
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado: " + id));
    }

    @Transactional
    public Product create(RequestProduct request, String requestId) {
        Product product = new Product();
        product.setId(java.util.UUID.randomUUID().toString());
        product.setName(request.name().trim());
        product.setCategory(request.category().trim());
        product.setPrice(request.price());
        product.setDistributionCenter(request.distributionCenter());
        product.setActive(true);
        return repository.save(product);
    }

    @Transactional
    public Product update(String id, RequestProduct request, String requestId) {
        Product product = findById(id);
        product.setName(request.name().trim());
        product.setCategory(request.category().trim());
        product.setPrice(request.price());
        product.setDistributionCenter(request.distributionCenter());
        return repository.save(product);
    }

    @Transactional
    public void inactivate(String id) {
        Product product = findById(id);
        product.setActive(false);
        repository.save(product);
    }

    public List<Product> byCategory(String category) { return repository.findAllByCategoryIgnoreCaseAndActiveTrue(category); }
    public List<Product> byCenter(DistributionCenter center) { return repository.findAllByDistributionCenterAndActiveTrue(center); }
    public List<Product> byCenterAndCategory(DistributionCenter center, String category) { return repository.findAllByDistributionCenterAndCategoryIgnoreCaseAndActiveTrue(center, category); }
    public List<Product> byMinPrice(java.math.BigDecimal price) { return repository.findAllByPriceGreaterThanAndActiveTrue(price); }
    public List<Product> byName(String name) { return repository.findByNameContainsIgnoreCaseAndActiveTrue(name); }

    public List<DistributionCenterCount> countByCenter() {
        return java.util.Arrays.stream(DistributionCenter.values())
                .map(c -> new DistributionCenterCount(c, repository.countByDistributionCenterAndActiveTrue(c)))
                .toList();
    }

    public List<TopExpensiveProducts> topExpensive() {
        return repository.findAllByActiveTrue().stream()
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .limit(5)
                .map(p -> new TopExpensiveProducts(p.getId(), p.getName(), p.getPrice(), p.getCategory(), p.getDistributionCenter()))
                .toList();
    }

    public AvailabilityResponse checkAvailability(String id, String cep) {
        Product product = findById(id);
        ViaCepResponse address = viaCepService.consultar(cep);
        boolean available = product.getDistributionCenter().getCity().equalsIgnoreCase(address.localidade());
        return new AvailabilityResponse(product.getId(), product.getName(), product.getDistributionCenter().getCity(), address.localidade(), address.cep(), available);
    }

    public record AvailabilityResponse(String productId, String productName, String distributionCenter, String cityFromCep, String cep, boolean available) {}
}
