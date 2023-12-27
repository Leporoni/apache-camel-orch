package com.leporonitech.products.service;

import com.leporonitech.products.model.Product;
import com.leporonitech.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ProductService.class})
@ExtendWith(SpringExtension.class)
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    void testGetAllProducts() {
        ProductRepository productRepositoryMock = Mockito.mock(ProductRepository.class);
        ProductService productService = new ProductService();
        ReflectionTestUtils.setField(productService, "productRepository", productRepositoryMock);

        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product());
        expectedProducts.add(new Product());

        Mockito.when(productRepositoryMock.findAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getAllProducts();

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Name");
        product.setPrice(10.0d);
        Optional<Product> ofResult = Optional.of(product);
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Product> actualProductById = productService.getProductById(1L);
        assertSame(ofResult, actualProductById);
        assertTrue(actualProductById.isPresent());
        verify(productRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Name");
        product.setPrice(10.0d);
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product);

        Product product2 = new Product();
        product2.setId(1L);
        product2.setName("Name");
        product2.setPrice(10.0d);
        assertSame(product, productService.createProduct(product2));
        verify(productRepository).save(Mockito.<Product>any());
    }

    @Test
    void testAddProducts() {
        ArrayList<Product> products = new ArrayList<>();
        productService.addProducts(products);
        assertEquals(products, productService.getAllProducts());
    }

    @Test
    void testAddProducts2() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Name");
        product.setPrice(10.0d);
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product);

        Product product2 = new Product();
        product2.setId(1L);
        product2.setName("Name");
        product2.setPrice(10.0d);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product2);
        productService.addProducts(products);
        verify(productRepository).save(Mockito.<Product>any());
        assertTrue(productService.getAllProducts().isEmpty());
    }

    @Test
    void testAddProducts3() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Name");
        product.setPrice(10.0d);
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product);

        Product product2 = new Product();
        product2.setId(1L);
        product2.setName("Name");
        product2.setPrice(10.0d);

        Product product3 = new Product();
        product3.setId(2L);
        product3.setName(String.join("", "com.", System.getProperty("user.name"), "tech.products.model.Product"));
        product3.setPrice(0.5d);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product3);
        products.add(product2);
        productService.addProducts(products);
        verify(productRepository, atLeast(1)).save(Mockito.<Product>any());
        assertTrue(productService.getAllProducts().isEmpty());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(Mockito.<Long>any());
        productService.deleteProduct(1L);
        verify(productRepository).deleteById(Mockito.<Long>any());
        assertTrue(productService.getAllProducts().isEmpty());
    }

    @Test
    void testDeleteAllProducts() {
        doNothing().when(productRepository).deleteAll();
        productService.deleteAllProducts();
        verify(productRepository).deleteAll();
        assertTrue(productService.getAllProducts().isEmpty());
    }
}