package com.uol.productms.controller;

import java.util.List;

import javax.validation.Valid;

import com.uol.productms.dto.ProductDetailDto;
import com.uol.productms.dto.ProductDto;
import com.uol.productms.dto.ProductFilterDto;
import com.uol.productms.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    private ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDetailDto> create(@Valid @RequestBody ProductDto productDto){
        ProductDetailDto response = productService.create(productDto);
        return new ResponseEntity<ProductDetailDto>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{productId}")
    public ResponseEntity<ProductDetailDto> update(@PathVariable String productId, @Valid @RequestBody ProductDto productDto){
        return new ResponseEntity<ProductDetailDto>(productService.update(productId, productDto), HttpStatus.OK);    
    }    

    @GetMapping(path = "/{productId}")
    public ResponseEntity<ProductDetailDto> get(@PathVariable String productId){
        return new ResponseEntity<ProductDetailDto>(productService.get(productId), HttpStatus.OK);    
    }    

    @GetMapping
    public ResponseEntity<List<ProductDetailDto>> getAll(){
        List<ProductDetailDto> response = productService.getAll();
        return new ResponseEntity<List<ProductDetailDto>>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<ProductDetailDto>> search(ProductFilterDto filters){
        List<ProductDetailDto> response = productService.search(filters);
        return new ResponseEntity<List<ProductDetailDto>>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<Void> delete(@PathVariable String productId){
        productService.delete(productId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }    
}
