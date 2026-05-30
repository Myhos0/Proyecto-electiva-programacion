package com.etitc.smart_network_managment.grafo;

import com.etitc.smart_network_managment.dto.CiudadDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RutaDijkstra {
    private CiudadDTO ciudad;

    private Integer distancia;
}
