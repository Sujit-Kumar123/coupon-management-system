package io.sujit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    Product getProduct(@PathVariable String id){
        return productRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        Product oldProduct = productRepository.findById(id).orElse(null);
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        return productRepository.save(oldProduct);
    }

    @DeleteMapping("/{id}")
    String deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
        return id;
    }
}
