package com.etitc.smart_network_managment.grafo;

import com.etitc.smart_network_managment.dto.CiudadDTO;
import com.etitc.smart_network_managment.model.Ruta;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Grafo {

    private Map<CiudadDTO, List<Ruta>> grafo = new HashMap<>();

    public void agregarCiudad(CiudadDTO ciudad)
    {
        grafo.putIfAbsent(ciudad, new ArrayList<>());
    }

    public void agregarRuta(CiudadDTO origen,CiudadDTO destino,int tiempo)
    {
        grafo.get(origen).add(new Ruta(destino,tiempo));

        grafo.get(destino).add(new Ruta(origen,tiempo));
    }

}
