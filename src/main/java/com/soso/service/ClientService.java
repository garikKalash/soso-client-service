package com.soso.service;

import com.soso.dto.ClientDAO;
import com.soso.models.Client;
import com.soso.service.authentication.AuthenticationTokenService;
import com.soso.service.eventListener.EventListenerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Garik Kalashyan on 3/8/2017.
 */

@Repository
public class ClientService extends BaseRestClient {
    private final Integer selfId = 1;


    private final AuthenticationTokenService authenticationTokenService = new AuthenticationTokenService(3);
    private final EventListenerClient eventListenerClient = new EventListenerClient(6);

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    public ClientService(@Value("${clientservice.id}") Integer defaultId) {
        super(defaultId);
    }

    public boolean isValidToken(Integer itemId, String token) {
        return authenticationTokenService.isValidToken(selfId, itemId, token);
    }

    public Client getClientById(Integer clientId) {
        return clientDAO.getClientById(clientId);
    }

    public Integer addClient(Client client) {
        List<Client> clientListViaTelephone = clientDAO.getClientByTelephone(client.getTelephone());

        // this case shows us that we have already user with the same telephone
        // and we need not to add new one client
        if(!clientListViaTelephone.isEmpty()){
            return  clientListViaTelephone.get(0).getId();
        }

        return clientDAO.addClient(client);
    }

    public Integer signinClient(String telephone, String password) {
        return clientDAO.signinClient(telephone, password);
    }

    public Client getClientMainDetailsById(Integer clientId) {
        return clientDAO.getClientById(clientId);
    }

    public List<Client> getClientByTelephone(String telephone) {
        return clientDAO.getClientByTelephone(telephone);
    }
}
