package com.soso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;


/**
 * Created by Garik.Kalashyan on 12/31/2016.
 */
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/")
public class HomeDummyController {
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public void datas()
    {
        System.out.println(" Server is running. Request type GET.");
    }


}
