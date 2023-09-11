package com.client.invest.client.investment.util;

import com.client.invest.client.investment.controller.model.ClientProductResponse;
import com.client.invest.client.investment.controller.model.ClientRequest;
import com.client.invest.client.investment.controller.model.ClientResponse;
import com.client.invest.client.investment.model.Client;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientModelMapper {

    public static Client mapToEntity(ClientRequest request){
        if (request == null){
            return null;
        }

        Client response = new Client();
        response.setFirstName(request.getFirstName());
        response.setLastName(request.getLastName());
        response.setDateOfBirth(request.getDateOfBirth());
        response.setEmailAddress(request.getEmailAddress());
        response.setMobileNumber(request.getMobileNumber());
        response.setPhysicalAddress(request.getPhysicalAddress());
        return response;
    }

    public static ClientResponse mapToResponse(Client client){
        if (client == null){
            return null;
        }

        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setFirstName(client.getFirstName());
        response.setLastName(client.getLastName());
        response.setDateOfBirth(client.getDateOfBirth());
        response.setEmailAddress(client.getEmailAddress());
        response.setMobileNumber(client.getMobileNumber());
        response.setPhysicalAddress(client.getPhysicalAddress());
        return response;
    }

    public static ClientProductResponse mapToProductResponse(Client client){
        if (client == null){
            return null;
        }

        ClientProductResponse response = new ClientProductResponse();
        response.setId(client.getId());
        response.setFirstName(client.getFirstName());
        response.setLastName(client.getLastName());
        response.setDateOfBirth(client.getDateOfBirth());
        response.setEmailAddress(client.getEmailAddress());
        response.setMobileNumber(client.getMobileNumber());
        response.setPhysicalAddress(client.getPhysicalAddress());
        return response;
    }

    public static List<ClientResponse> mapResponseList(List<Client> clients) {
        List<ClientResponse> responses = new ArrayList<>();
        clients.forEach(c -> {
            responses.add(mapToResponse(c));
        });
        return responses;
    }
}
