package com.etitc.smart_network_managment.controller;

import com.etitc.smart_network_managment.dto.CiudadDTO;
import com.etitc.smart_network_managment.service.CiudadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CiudadController {

    private final CiudadService ciudadService;

    @GetMapping("/ciudades")
    public List<CiudadDTO> obtenerCiudades() {

        return ciudadService.obtenerCiudades();
    }
}