package com.soso.validators;

import com.soso.models.Client;
import com.soso.service.JsonMapBuilder;
/**
 * Created by Garik Kalashyan on 4/26/2017.
 */


public final class ClientDtoVallidator {


    public static  JsonMapBuilder validateClientDto(Client client){
      JsonMapBuilder errors = new JsonMapBuilder();
      boolean isThereValidationErrors = false;
        if (client.getTelephone() == null || client.getTelephone().isEmpty()) {
            errors.add("isShortTelephone", Boolean.TRUE.toString());
            isThereValidationErrors = true;
        }
        if (client.getName() == null || client.getName().isEmpty() || client.getName().length() < 3) {
            errors.add("isInvalidName", Boolean.TRUE.toString());
            isThereValidationErrors = true;
        }
        return isThereValidationErrors ? errors : null ;
    }




}
