package com.etitc.smart_network_managment.controller;

import com.etitc.smart_network_managment.dto.InfoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PingController {
    @GetMapping("/ping")
    public String ping()
    {
        return "servidor funcionando";
    }

    @GetMapping("/info")
    public InfoResponse info()
    {
        return new InfoResponse("Smart-Network-Manage", "1.6");
    }

    @GetMapping("/saludo")
    public String saludo(@RequestParam String nombre)
    {
        return  "Hola " + nombre + ". Bienvenido a la red";
    }
}