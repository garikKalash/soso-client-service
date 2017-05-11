package com.soso.controller;

import com.soso.models.Client;
import com.soso.service.ClientService;
import com.soso.service.JsonConverter;
import com.soso.service.JsonMapBuilder;
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

    /*  @ModelAttribute
     public void checkRequiredDataFromRequest(@RequestHeader("token") String token,@RequestHeader("clientId") Integer itemId, HttpServletRequest request) {
         if (!isUnauthorizedRequestType(request)) {
             if(token.isEmpty() && itemId != null && clientService.isValidToken(itemId,token)){
                 System.out.println("Pre Requesting state is fully processed.");
             }else {
                 try {
                     throw new Exception("The token: "+ token +" is not found in the stored tokens.");
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         }
     }
 */
    @RequestMapping(value = "/clientaccount", params = {"clientId"}, method = RequestMethod.GET)
    public void getPartnerById(@RequestParam(value = "clientId") Integer clientId, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        Client client = clientService.getClientById(clientId);
        String clientToJsonString = JsonConverter.toJson(new JsonMapBuilder()
                .add("client", client)
                .build());
        response.getWriter().write(clientToJsonString);
    }

    @RequestMapping(value = "/addclient", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addFollowerToPartner(@RequestBody Client client, HttpServletResponse response) throws IOException {
        boolean thereIsValidation = false;
        JsonMapBuilder errorJsonMapBuilder = new JsonMapBuilder();
        if (client.getTelephone() == null || client.getTelephone().isEmpty()) {
            errorJsonMapBuilder.add("isShortTelephone", Boolean.TRUE.toString());
            thereIsValidation = true;
        }else if (!clientService.getClientByTelephone(client.getTelephone()).isEmpty()) {
                errorJsonMapBuilder.add("isUniqueTelephoneError", Boolean.TRUE.toString());
                thereIsValidation = true;

        }
        if (client.getName() == null || client.getName().isEmpty() || client.getPassword().length() < 3) {
            errorJsonMapBuilder.add("isInvalidName", Boolean.TRUE.toString());
            thereIsValidation = true;
        }
        if (client.getPassword() == null || client.getPassword().isEmpty() || client.getPassword().length() < 3) {
            errorJsonMapBuilder.add("isInvalidPassword", Boolean.TRUE.toString());
            thereIsValidation = true;
        }


        if(!thereIsValidation){
        Integer newClientId = clientService.addClient(client);
        response.getWriter().write(JsonConverter.toJson(new JsonMapBuilder()
                .add("newClientId", newClientId)
                .build()));
        }else {
           response.getWriter().write(JsonConverter.toJson(errorJsonMapBuilder.build()));
       }
    }

    @RequestMapping(value = "/signinclient", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void signin(@RequestBody Client client, HttpServletResponse response) throws IOException {
        boolean thereIsValidation = false;
        JsonMapBuilder errorJsonMapBuilder = new JsonMapBuilder();
        if (client.getTelephone() == null || client.getTelephone().isEmpty()) {
            errorJsonMapBuilder.add("isShortTelephone", Boolean.TRUE.toString());
            thereIsValidation = true;
        }
        if (client.getPassword() == null || client.getPassword().isEmpty() || client.getPassword().length() < 3) {
            errorJsonMapBuilder.add("isInvalidPassword", Boolean.TRUE.toString());
            thereIsValidation = true;
        }

        if (!thereIsValidation) {
            Integer newClientId = clientService.signinClient(client.getTelephone(), client.getPassword());
            if (newClientId != null) {
                response.getWriter().write(JsonConverter.toJson(new JsonMapBuilder()
                        .add("clientId", newClientId).add("isInvalidPassword", Boolean.FALSE.toString()).add("isShortTelephone", Boolean.FALSE.toString()).add("wrongclient", Boolean.FALSE.toString())
                        .build()));
            }else{
                response.getWriter().write(JsonConverter.toJson(new JsonMapBuilder()
                        .add("wrongclient", Boolean.TRUE.toString())
                        .build()));
            }
        }else{
            response.getWriter().write(JsonConverter.toJson(errorJsonMapBuilder.build()));
        }
    }

    @RequestMapping(value = "/clientmaindetails/{clientid}", method = RequestMethod.GET)
    public void getPartnerMainDetailsById(@PathVariable(value = "clientid") Integer clientId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setCharacterEncoding("UTF-8");
        Client partner = clientService.getClientMainDetailsById(clientId);
        String partnerToJsonString = JsonConverter.toJson(new JsonMapBuilder()
                .add("client", partner)
                .build());
        response.getWriter().write(partnerToJsonString);
    }

    private boolean isUnauthorizedRequestType(HttpServletRequest request) {
        return request.getRequestURI().equals("/client/signinclient/")
                || request.getRequestURI().equals("/client/addclient/");
    }


}
