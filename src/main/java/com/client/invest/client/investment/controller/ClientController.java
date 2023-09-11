package com.client.invest.client.investment.controller;

import com.client.invest.client.investment.controller.model.ClientProductResponse;
import com.client.invest.client.investment.controller.model.ClientRequest;
import com.client.invest.client.investment.controller.model.ClientResponse;
import com.client.invest.client.investment.controller.model.ProductResponse;
import com.client.invest.client.investment.service.IClient;
import com.client.invest.client.investment.service.IProduct;
import com.client.invest.client.investment.util.ClientModelMapper;
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
@Tag(name = "Client API")
public class ClientController {

    private final IClient clientService;

    private final IProduct productService;

    public ClientController(IClient clientService, IProduct productService) {
        this.clientService = clientService;
        this.productService = productService;
    }

    @Operation(summary = "fetch client",
            description = "Fetch client using client id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Client not found exception")
    })
    @GetMapping(value = "/client/{clientId}", produces = "application/json")
    public ResponseEntity<ClientResponse> getClient(@Parameter(name = "clientId", description = "Client Id", example = "1")
                                                    @PathVariable Long clientId) {
        ClientResponse response = ClientModelMapper.mapToResponse(clientService.fetchClientById(clientId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "fetch products for client",
            description = "Fetch products linked to client using client id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Client not found exception"),
            @ApiResponse(responseCode = "404", description = "Product not found exception")
    })
    @GetMapping(value = "/client/{clientId}/products", produces = "application/json")
    public ResponseEntity<ClientProductResponse> getClientProducts(@Parameter(name = "clientId", description = "Client Id", example = "1")
                                                                   @PathVariable("clientId") Long clientId) {
        ClientProductResponse client = ClientModelMapper.mapToProductResponse(clientService.fetchClientById(clientId));
        List<ProductResponse> products = ProductModelMapper.mapResponseList(productService.getProductsByClientId(clientId));
        client.setProducts(products);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @Operation(summary = "search for client",
            description = "Search for client using a keyword, will match client first name or last name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GetMapping("/client")
    public ResponseEntity<List<ClientResponse>> getAllClients(@Parameter(name = "keyword", description = "Search keyword", example = "Je")
                                                              @RequestParam(value = "keyword", required = false) String keyword) {
        List<ClientResponse> responses = ClientModelMapper.mapResponseList(clientService.searchClient(keyword));
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @Operation(summary = "update client",
            description = "Update client information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Client not found exception")
    })
    @PutMapping("/client/{clientId}")
    public ResponseEntity<ClientResponse> updateClient(@Parameter(name = "clientId", description = "Client Id", example = "1")
                                                       @PathVariable("clientId") Long clientId,
                                                       @Valid @RequestBody ClientRequest client) {
        ClientResponse response = ClientModelMapper.mapToResponse(clientService.updateClient(clientId, ClientModelMapper.mapToEntity(client)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete client",
            description = "Soft delete client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Client not found exception")
    })
    @DeleteMapping("/client/{clientId}")
    public void deleteClient(@Parameter(name = "clientId", description = "Client Id", example = "1")
                             @PathVariable("clientId") Long clientId) {
        clientService.deleteClient(clientId);
    }
}
