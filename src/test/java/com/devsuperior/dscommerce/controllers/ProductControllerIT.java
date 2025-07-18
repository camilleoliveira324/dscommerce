package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.util.Factory;
import com.devsuperior.dscommerce.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ProductController controller;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;

    @Autowired
    private ObjectMapper objectMapper;
    private ProductDTO dto;

    private String productName;

    private String adminToken, clientToke, invalidToken;


    @BeforeEach
    void setUp() throws Exception {
        productName = "Macbook";

        adminToken = tokenUtil.obtainAccessToken(mockMvc, "alex@gmail.com", "123456");
        clientToke = tokenUtil.obtainAccessToken(mockMvc, "maria@gmail.com", "123456");
        invalidToken = adminToken + "invalidToken";

        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;

        dto = Factory.createProductDTO();

    }

    @Test
    public void pageableFilterByName() throws Exception {

        mockMvc.perform(get("/products?name={producName}", productName).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Macbook Pro"))
                .andExpect(jsonPath("$.content[0].id").value(3))
                .andExpect(jsonPath("$.content[0].price").value(1250.0))
                .andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));

    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
        String json = objectMapper.writeValueAsString(dto);

        String expectedName = dto.getName();

        mockMvc.perform(put("/products/{id}", existingId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.name").value(expectedName));
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExistis() throws Exception {
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/products/{id}", nonExistingId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void insertProductWhenUserIsAdmin() throws Exception {
        var p = new ProductDTO("url", 1000.0, "descfurureuruieruruiur", "name", null);
        p.getCategories().add(new CategoryDTO(1L, "Livros"));

        var json = objectMapper.writeValueAsString(p);

        mockMvc.perform(post("/products")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
