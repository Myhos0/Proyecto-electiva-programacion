package com.etitc.smart_network_managment.controller;

import com.etitc.smart_network_managment.dto.CiudadRutaDTO;
import com.etitc.smart_network_managment.service.GrafoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GrafoController {

    private final GrafoService grafoService;

    @GetMapping("/grafo")
    public List<CiudadRutaDTO> obtenerGrafo() {

        return grafoService.ObtenerGrafo();
    }
}
