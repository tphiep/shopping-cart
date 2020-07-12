package com.example.web;

import com.example.products.domain.Product;
import com.example.products.domain.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method= RequestMethod.POST)
    public CreateProductResponse create(@RequestBody CreateProductRequest request) {
        Product result = productService.create(request.getInfo());
        return new CreateProductResponse(result.getId());
    }

    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public ResponseEntity<GetProductResponse> find(@PathVariable("id") long id) {
        Optional<Product> result = productService.findById(id);
        return result.map( p -> ResponseEntity.ok().body(GetProductResponse.from(p)) )          //200 OK
                .orElseGet( () -> ResponseEntity.notFound().build() );  //404 Not found
    }
}
