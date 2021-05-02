package com.uol.productms.mapper;

import java.util.List;

import com.uol.productms.dto.ProductDetailDto;
import com.uol.productms.dto.ProductDto;
import com.uol.productms.model.Product;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toDto(Product product);
    Product fromDto(ProductDto productDto);
    ProductDetailDto toDetailDto(Product product);
    List<ProductDetailDto> toDetailsDto(List<Product> products);
}
