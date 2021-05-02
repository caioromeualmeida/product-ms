package com.uol.productms.service;

import java.util.List;

import com.uol.productms.dto.ProductDetailDto;
import com.uol.productms.dto.ProductDto;
import com.uol.productms.dto.ProductFilterDto;

public interface ProductService {
    ProductDetailDto create(ProductDto productDto);   
    ProductDetailDto update(String productId, ProductDto productDto);
    ProductDetailDto get(String productId);    
    List<ProductDetailDto> getAll(); 
    List<ProductDetailDto> search(ProductFilterDto filters); 
    void delete(String productId);
}
