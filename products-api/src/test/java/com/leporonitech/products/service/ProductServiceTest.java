package com.leporonitech.products.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.leporonitech.products.model.Product;
import com.leporonitech.products.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProductService.class})
@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    /**
     * Method under test: {@link ProductService#addProducts(List)}
     */
    @Test
    void testAddProducts() {
        ArrayList<Product> products = new ArrayList<>();
        productService.addProducts(products);
        assertEquals(products, productService.getAllProducts());
    }
}

