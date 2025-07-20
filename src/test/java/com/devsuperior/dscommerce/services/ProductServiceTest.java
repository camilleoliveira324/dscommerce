package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscommerce.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;

    private PageImpl<Product> page;
    private Product product;

    private ProductDTO dto;


    @Mock
    private CategoryService categoryService;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 100L;
        dependentId = 3L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));
        dto = Factory.createProductDTO();
        categoryDTO = new CategoryDTO(product.getCategories().stream().findFirst().get());

        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.when(repository.existsById(dependentId)).thenReturn(true);

        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        Mockito.when(repository.searchByName(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class))).thenReturn(page);

        Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
    }

    @Test
    public void updateShouldReturnExceptionWhenIdNotExistis() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, dto);
        });
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExistis() {
        ProductDTO dtoResult = service.update(existingId, dto);

        Assertions.assertEquals(dto.getId(), dtoResult.getId());
    }

    @Test
    public void findByIdShouldReturnExceptionWhenIdNotExistis() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExistis() {
        ProductDTO dtoResult = service.findById(existingId);

        Assertions.assertEquals(dto.getId(), dtoResult.getId());
        Mockito.verify(repository, times(1)).findById(existingId);
    }

    @Test
    public void findAllPagedeShouldRetournPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductMinDTO> result = service.findAll((categoryDTO.getName()), pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(repository, times(1)).searchByName(categoryDTO.getName(), pageable);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExistis() {

        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository, times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowsExceptionWhenNonExistisId() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        Mockito.verify(repository, Mockito.never()).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowsExceptionWhenDependentId() {

        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });

        Mockito.verify(repository, times(1)).deleteById(dependentId);
    }
}
