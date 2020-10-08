package com.sebag.productapp.controllers;

import com.sebag.productapp.dtos.ProductDTO;
import com.sebag.productapp.entities.Product;
import com.sebag.productapp.repositories.ProductRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductController(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Gets all non deleted products.
     *
     * @return all non deleted products
     */
    @GetMapping
    @ApiOperation(value = "Get all non-deleted products")
    public List<ProductDTO> getAllProducts() {
        return productRepository
                .getAllProducts().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Add/Update a product.
     *
     * @param product the product
     * @return the product dto
     */
    @PostMapping
    @ApiOperation(value = "Add or update a product")
    public ProductDTO postProduct(@RequestBody ProductDTO product) {
        log.info(product.toString());
        Product productToStore = modelMapper.map(product, Product.class);
        return modelMapper.map(productRepository.save(productToStore), ProductDTO.class);
    }

    /**
     * Soft delete a product.
     *
     * @param sku the sku of the product
     * @return a boolean indicating the success of the operation
     */
    @DeleteMapping("{sku}")
    @ApiOperation(value = "Soft-delete a product")
    public Boolean deleteProduct(@PathVariable String sku) {
        Optional<Product> product = productRepository.findById(sku);
        if (product.isPresent()) {
            product.get().setDeleted(true);
            productRepository.save(product.get());
            return true;
        }
        return false;
    }
}
