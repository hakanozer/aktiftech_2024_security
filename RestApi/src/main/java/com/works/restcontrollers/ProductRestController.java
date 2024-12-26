package com.works.restcontrollers;

import com.works.entities.Product;
import com.works.services.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
@SecurityRequirement(name = "basicAuth")
public class ProductRestController {

     final ProductService productService;

     @PostMapping("save")
     public Product save(@RequestBody Product product) {
         return productService.save(product);
     }

     @GetMapping("list")
     public List<Product> list() {
         return productService.list();
     }
}
