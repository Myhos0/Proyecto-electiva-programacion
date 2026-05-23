package com.etitc.smart_network_managment.service;

import com.etitc.smart_network_managment.dto.CiudadDTO;
import com.etitc.smart_network_managment.dto.CiudadRutaDTO;
import com.etitc.smart_network_managment.dto.RutaDTO;
import com.etitc.smart_network_managment.grafo.Grafo;
import com.etitc.smart_network_managment.model.Ruta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GrafoService {

    private final CiudadService ciudadService;

    public Grafo construirGrafo() {
        List<CiudadDTO> ciudades = ciudadService.obtenerCiudades().stream().limit(10).toList();

        Grafo grafo = new Grafo();

        for (CiudadDTO ciudad : ciudades) {
            grafo.agregarCiudad(ciudad);
        }

        for (int i = 0; i < ciudades.size() - 1; i++) {

            CiudadDTO origen = ciudades.get(i);

            CiudadDTO destino = ciudades.get(i + 1);

            int tiempo = (int)(Math.random() * 10) + 1;

            grafo.agregarRuta(origen,destino, tiempo);
        }

        return grafo;
    }

    public List<CiudadRutaDTO> ObtenerGrafo()
    {
        Grafo grafo = construirGrafo();

        List<CiudadRutaDTO> respuesta = new ArrayList<>();

        for(CiudadDTO ciudadDTO: grafo.getGrafo().keySet()) {
            List<RutaDTO> rutas = new ArrayList<>();

            for (Ruta ruta : grafo.getGrafo().get(ciudadDTO)) {
                rutas.add(new RutaDTO(ruta.getDestino().getName(), ruta.getTiempo()));
            }

            respuesta.add(new CiudadRutaDTO(ciudadDTO.getName(),rutas));
        }

        return respuesta;
    }
}