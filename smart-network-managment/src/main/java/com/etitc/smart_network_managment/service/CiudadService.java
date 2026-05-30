package com.etitc.smart_network_managment.service;

import com.etitc.smart_network_managment.dto.CiudadDTO;
import com.etitc.smart_network_managment.entity.CiudadEntity;
import com.etitc.smart_network_managment.repository.CiudadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CiudadService {

    private final CiudadAPIService ciudadAPIService;
    private final CiudadRepository ciudadRepository;

    public CiudadDTO buscarCiudadPorNombre(String nombre)
    {
        List<CiudadDTO> ciudades = ciudadAPIService.obtenerCiudades();

        return ciudades.stream().filter(c -> c.getName().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }

    public CiudadDTO buscarCiudadPorId(Integer id)
    {
        List<CiudadDTO> ciudades = ciudadAPIService.obtenerCiudades();

        return ciudades.stream().filter(ciudad -> ciudad.getId().equals(id)).findFirst().orElse(null);
    }

    public CiudadEntity guardarCiudadSiNoExiste(CiudadDTO ciudadDTO) {

        return ciudadRepository.findById(ciudadDTO.getId()).orElseGet(() -> {
            CiudadEntity ciudad = new CiudadEntity();
            ciudad.setId(ciudadDTO.getId());
            ciudad.setNombre(ciudadDTO.getName());
            return ciudadRepository.save(ciudad);
        });
    }
}