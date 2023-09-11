package com.client.invest.client.investment.controller;

import com.client.invest.client.investment.controller.model.ProductRequest;
import com.client.invest.client.investment.controller.model.ProductResponse;
import com.client.invest.client.investment.service.IProduct;
import com.client.invest.client.investment.util.ProductModelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Investment Products API")
public class ProductController {

    private final IProduct productService;


    public ProductController(IProduct productService) {
        this.productService = productService;
    }

    @Operation(summary = "search for products",
            description = "Search for products using a keyword, will match product name or product Type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> fetchAllProducts(@Parameter(name = "keyword", description = "Search keyword", example = "Je")
                                                                  @RequestParam(value = "keyword", required = false) String keyword) {
        return new ResponseEntity<>(ProductModelMapper.mapResponseList(productService.searchProducts(keyword)), HttpStatus.OK);
    }

    @Operation(summary = "fetch product by id",
            description = "Fetch product using product id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Product not found exception")
    })
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> fetchProductById(@Parameter(name = "productId", description = "product id", example = "1")
                                                            @PathVariable("productId") Long productId) {
        return new ResponseEntity<>(ProductModelMapper.mapToResponse(productService.getProductById(productId)), HttpStatus.OK);
    }

    @Operation(summary = "Create products for client",
            description = "Create an investment product for a client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Client not found exception")
    })
    @PostMapping("/products/{clientId}")
    public ResponseEntity<ProductResponse> createProduct(@PathVariable("clientId") Long clientId,
                                                         @Valid @RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(ProductModelMapper.mapToResponse(
                productService.addProduct(clientId, ProductModelMapper.mapToEntity(productRequest))), HttpStatus.OK);
    }

    @Operation(summary = "Update products",
            description = "Update investment product information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Product not found exception")
    })
    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("productId") Long productId,
                                                         @Valid @RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(ProductModelMapper.mapToResponse(
                productService.updateProduct(productId, ProductModelMapper.mapToEntity(productRequest))), HttpStatus.OK);
    }


}
