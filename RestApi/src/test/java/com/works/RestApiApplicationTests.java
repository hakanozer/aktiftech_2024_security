package com.works;

import com.works.entities.Product;
import com.works.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestApiApplicationTests {

    @Autowired
    ProductService productService;

    public RestApiApplicationTests() {
        //System.out.println("this line call");
    }

    @Test
    @Order(1)
    void productListCountControl() {
        List<Product> ls = productService.list();
        Assertions.assertTrue(ls.size() > 0);
    }

    @Test
    @Order(2)
    void productSaveControl() {
        Product p = new Product();
        p.setTitle("Prod-1");
        p.setPrice(new BigDecimal("100.00"));
        p.setDescription("Product-1 Description");
        try {
            productService.save(p);
            Assertions.assertTrue(true);
        }catch (Exception e) {
            Assertions.assertTrue(false);
        }
    }



}
