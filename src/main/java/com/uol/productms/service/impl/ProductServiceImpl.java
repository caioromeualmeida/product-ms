package com.uol.productms.service.impl;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.uol.productms.dto.ProductDetailDto;
import com.uol.productms.dto.ProductDto;
import com.uol.productms.dto.ProductFilterDto;
import com.uol.productms.exception.ProductNotFoundException;
import com.uol.productms.mapper.ProductMapper;
import com.uol.productms.model.Product;
import com.uol.productms.repository.ProductRepository;
import com.uol.productms.service.ProductService;
import com.uol.productms.specification.ProductSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;
    
    private ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ProductDetailDto create(ProductDto productDto) {
        Product product = productRepository.save(productMapper.fromDto(productDto));
        return productMapper.toDetailDto(product);
    }
    
    @Override
    public ProductDetailDto update(String productId, ProductDto productDto) throws ProductNotFoundException {
        try {
            Optional<Product> product = productRepository.findById(productId);    
            product.get().setName(productDto.getName());
            product.get().setDescription(productDto.getDescription());
            product.get().setPrice(productDto.getPrice());
            return productMapper.toDetailDto(productRepository.save(product.get()));
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Product id " + productId + " not found");
        }
    }

    @Override
    public ProductDetailDto get(String productId) throws ProductNotFoundException {
        try {
            Optional<Product> product = productRepository.findById(productId);
            return productMapper.toDetailDto(product.get());
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Product id " + productId + " not found");
        }
    }

    @Override
    public List<ProductDetailDto> getAll() {
        List<Product> products = productRepository.findAll();
        return productMapper.toDetailsDto(products);     
    }

    @Override
    public List<ProductDetailDto> search(ProductFilterDto filters) {
        List<Product> products = productRepository.findAll(
            where(ProductSpecification.minPrice(filters.getMin_price()))
            .and(ProductSpecification.maxPrice(filters.getMax_price()))
            .and(ProductSpecification.name(filters.getQ()).or(ProductSpecification.description(filters.getQ())))
        );
        return productMapper.toDetailsDto(products);
    }

    @Override
    public void delete(String productId) {
        try {
            Optional<Product> product = productRepository.findById(productId);    
            productRepository.delete(product.get());
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Product id " + productId + " not found");
        }
    }    
}
