package com.etitc.smart_network_managment.service;

import com.etitc.smart_network_managment.dto.CiudadDTO;
import com.etitc.smart_network_managment.model.Ciudad;
import com.etitc.smart_network_managment.repository.CiudadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CiudadService {
    private final CiudadRepository ciudadRepository;

    public List<Ciudad> ObtenerTodos()
    {
        return ciudadRepository.findAll();
    }

    public Ciudad ObtenerPorId(Long id)
    {
        return ciudadRepository.findById(id).orElseThrow(() -> new RuntimeException("La ciudad no se encuentra con el Id"));
    }

    public Ciudad Crear(CiudadDTO ciudadDTO)
    {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(ciudadDTO.getNombre());
        ciudad.setPais(ciudadDTO.getPais());
        ciudad.setPoblacion(ciudadDTO.getPoblacion());

        return ciudadRepository.save(ciudad);
    }

    public void Borrar(Long id)
    {
        ciudadRepository.deleteById(id);
        System.out.println("Se borro correctamente la ciudad");
    }
}