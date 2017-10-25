package com.soso.controller;

import com.soso.models.Client;
import com.soso.service.ClientService;
import com.soso.service.JsonConverter;
import com.soso.service.JsonMapBuilder;
import com.soso.validators.ClientDtoVallidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Garik Kalashyan on 4/23/2017.
 */

@CrossOrigin("*")
@Controller
@RequestMapping("client")
public class ClientRequestController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/clientaccount", params = {"clientId"}, method = RequestMethod.GET)
    public void getClientById(@RequestParam(value = "clientId") Integer clientId, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        Client client = clientService.getClientById(clientId);
        String clientToJsonString = JsonConverter.toJson(new JsonMapBuilder()
                .add("client", client)
                .build());
        response.getWriter().write(clientToJsonString);
    }

    @RequestMapping(value = "/addclient", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addClient(@RequestBody Client client, HttpServletResponse response) throws IOException {
        if(ClientDtoVallidator.validateClientDto(client) == null){
        Integer clientId = clientService.addClient(client);
        response.getWriter().write(JsonConverter.toJson(new JsonMapBuilder()
                .add("clientId", clientId)
                .build()));
        }else {
           response.getWriter().write(JsonConverter.toJson(ClientDtoVallidator.validateClientDto(client).build()));
       }
    }

    @RequestMapping(value = "/clientmaindetails/{clientid}", method = RequestMethod.GET)
    public void getClientMainDetailsById(@PathVariable(value = "clientid") Integer clientId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setCharacterEncoding("UTF-8");
        Client client = clientService.getClientMainDetailsById(clientId);
        String partnerToJsonString = JsonConverter.toJson(new JsonMapBuilder()
                .add("client", client)
                .build());
        response.getWriter().write(partnerToJsonString);
    }

    private boolean isUnauthorizedRequestType(HttpServletRequest request) {
        return request.getRequestURI().equals("/client/signinclient/")
                || request.getRequestURI().equals("/client/addclient/");
    }


}
