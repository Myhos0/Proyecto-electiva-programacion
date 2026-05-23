package com.etitc.smart_network_managment.model;

import com.etitc.smart_network_managment.dto.CiudadDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ruta {

    private CiudadDTO destino;

    private int tiempo;
}