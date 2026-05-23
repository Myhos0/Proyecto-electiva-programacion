package com.etitc.smart_network_managment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CiudadRutaDTO {

    private String ciudad;

    private List<RutaDTO> rutas;
}
