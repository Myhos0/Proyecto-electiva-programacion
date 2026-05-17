package com.etitc.smart_network_managment.service;

import com.etitc.smart_network_managment.dto.RutaDTO;
import com.etitc.smart_network_managment.model.Ruta;
import com.etitc.smart_network_managment.repository.RutaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RutaService {
    private final RutaRepository rutaRepository;

    public List<Ruta> ObtenerTodos()
    {
        return rutaRepository.findAll();
    }

    public Ruta ObtnerPorId(Long Id)
    {
        return rutaRepository.findById(Id).orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
    }

    public Ruta Crear(RutaDTO rutaDTO)
    {
        Ruta ruta = new Ruta();

        ruta.setInicio(rutaDTO.getInicio());
        ruta.setDestino(rutaDTO.getDestino());
        ruta.setDuracionRecorrido(rutaDTO.getDuracionRecorrido());

        return rutaRepository.save(ruta);
    }

    public void Borrar(Long id)
    {
        rutaRepository.deleteById(id);

        System.out.println("Ruta eliminiada correctamente");
    }
}
