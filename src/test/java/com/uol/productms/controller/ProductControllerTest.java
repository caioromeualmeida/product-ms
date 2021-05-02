package com.uol.productms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uol.productms.dto.ProductDetailDto;
import com.uol.productms.dto.ProductDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class ProductControllerTest {
        
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private ProductDto productDto;

    private static final String DEFAULT_PATH = "/products/";
    
    private String createProduct() throws Exception{
        String productDtoJson = mapper.writeValueAsString(productDto);

        return mockMvc.perform(MockMvcRequestBuilders.post(DEFAULT_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(productDtoJson))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();
    }

    private void getProduct(String productJsonString) throws Exception {
        //converte pata buscar o id
        ProductDetailDto productDetailDto = mapper.readValue(productJsonString, ProductDetailDto.class);

        //busca o usuário
        mockMvc.perform(MockMvcRequestBuilders.get(DEFAULT_PATH + productDetailDto.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(productJsonString))
        .andExpect(status().isOk());
    }

	private void removeProduct(String productJsonString) throws Exception {        
        ProductDetailDto productDetailDto = mapper.readValue(productJsonString, ProductDetailDto.class);

        //remove o usuário
        mockMvc.perform(MockMvcRequestBuilders.delete(DEFAULT_PATH + productDetailDto.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    }
    
	public void updateProduct(String productJsonString) throws Exception {
        //Gera um novo nome para o usuario ja cadastrado
        ProductDetailDto productDetailDto = mapper.readValue(productJsonString, ProductDetailDto.class);
        String newName = UUID.randomUUID().toString();
        productDetailDto.setName(newName);
        String updatedDtoJson = mapper.writeValueAsString(productDetailDto);

        //atualiza o usuario
        String updatedJsonString = mockMvc.perform(MockMvcRequestBuilders.put(DEFAULT_PATH + productDetailDto.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedDtoJson))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();
        
        ProductDetailDto updatedDetailDto = mapper.readValue(updatedJsonString, ProductDetailDto.class);
        assertEquals(newName, updatedDetailDto.getName());
    }
    
    @Test
	public void createProductTest() throws Exception {
        productDto = new ProductDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Math.random());
        this.createProduct();
    }
    
    @Test
	public void getProductTest() throws Exception {
        productDto = new ProductDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Math.random());
        String productJsonString = this.createProduct();
        this.getProduct(productJsonString);
    }
    
    @Test
	public void removeProductTest() throws Exception {
        productDto = new ProductDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Math.random());
        String productJsonString = this.createProduct();
        this.removeProduct(productJsonString);
    }
    
    @Test
	public void updateProductTest() throws Exception {
        productDto = new ProductDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Math.random());
        String productJsonString = this.createProduct();
        this.updateProduct(productJsonString);
    }
    
    @Test
	public void getAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(DEFAULT_PATH)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    public void createInvalidNameProductTest() throws Exception {
        productDto = new ProductDto(null, UUID.randomUUID().toString(), Math.random());

        String productDtoJson = mapper.writeValueAsString(productDto);

        mockMvc.perform(MockMvcRequestBuilders.post(DEFAULT_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(productDtoJson))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();
    }

    @Test
    public void createInvalidDescriptionProductTest() throws Exception {
        productDto = new ProductDto(UUID.randomUUID().toString(), null, Math.random());

        String productDtoJson = mapper.writeValueAsString(productDto);

        mockMvc.perform(MockMvcRequestBuilders.post(DEFAULT_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(productDtoJson))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();
    }

    @Test
    public void createInvalidPriceProductTest() throws Exception {
        productDto = new ProductDto(UUID.randomUUID().toString(), null, 0D);

        String productDtoJson = mapper.writeValueAsString(productDto);

        mockMvc.perform(MockMvcRequestBuilders.post(DEFAULT_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(productDtoJson))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();
    }

    @Test
	public void getAllSearchTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(DEFAULT_PATH + "/search")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }    
}
