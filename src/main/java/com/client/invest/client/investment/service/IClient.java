package com.client.invest.client.investment.service;

import com.client.invest.client.investment.model.Client;

import java.util.List;

public interface IClient {
    Client createClient(Client client);

    Client fetchClientById(Long clientId);

    Client updateClient(Long clientId, Client client);

    List<Client> searchClient(String keyword);

    void deleteClient(Long clientId);
}
