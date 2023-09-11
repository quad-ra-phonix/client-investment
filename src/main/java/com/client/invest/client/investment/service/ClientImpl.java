package com.client.invest.client.investment.service;

import com.client.invest.client.investment.enums.ClientStatus;
import com.client.invest.client.investment.exception.ClientNotFoundException;
import com.client.invest.client.investment.model.Client;
import com.client.invest.client.investment.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientImpl implements IClient {

    private final ClientRepository clientRepository;

    public ClientImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client fetchClientById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
    }

    @Override
    public Client updateClient(Long clientId, Client client) {
        Client clientSaved = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        clientSaved.setFirstName(client.getFirstName());
        clientSaved.setLastName(client.getLastName());
        clientSaved.setEmailAddress(client.getEmailAddress());
        clientSaved.setMobileNumber(client.getMobileNumber());
        clientSaved.setDateOfBirth(client.getDateOfBirth());
        clientSaved.setPhysicalAddress(client.getPhysicalAddress());
        return clientRepository.save(clientSaved);
    }

    @Override
    public List<Client> searchClient(String keyword) {
        if (keyword == null) {
            return clientRepository.findAll();
        }
        return clientRepository.search(keyword);
    }

    @Override
    public void deleteClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        client.setStatus(ClientStatus.DELETED);
        clientRepository.save(client);
    }
}
