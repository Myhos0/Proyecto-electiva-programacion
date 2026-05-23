package com.etitc.smart_network_managment.service;

import com.etitc.smart_network_managment.dto.CiudadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CiudadService {
    private final WebClient webClient;

    public List<CiudadDTO> obtenerCiudades() {

        return webClient
                .get()
                .uri("/City")
                .retrieve()
                .bodyToFlux(CiudadDTO.class)
                .collectList()
                .block();
    }
}