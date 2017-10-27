package com.soso.controller;

import com.soso.models.Client;
import com.soso.service.ClientService;
import com.soso.service.JsonConverter;
import com.soso.service.JsonMapBuilder;
import com.soso.validators.ClientDtoVallidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Garik Kalashyan on 4/23/2017.
 */

@CrossOrigin("*")
@Controller
@RequestMapping("client")
public class ClientRequestController {

    @Autowired
    private ClientService clientService;


    @RequestMapping(value="/clientaccount/{clientid}",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public ResponseEntity<Client> getClientById(@PathVariable(value = "clientid") Integer clientId){
        Client client = clientService.getClientById(clientId);
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @RequestMapping(value = "/addclient", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> addClient(@RequestBody Client client){
        return new ResponseEntity<Map>(clientService.addClient(client).build(),HttpStatus.OK);
    }

    @RequestMapping(value = "/clientmaindetails/{clientid}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> getClientMainDetailsById(@PathVariable(value = "clientid") Integer clientId){
        Client client = clientService.getClientMainDetailsById(clientId);
        return new ResponseEntity<Client>(client,HttpStatus.OK);
    }
}
