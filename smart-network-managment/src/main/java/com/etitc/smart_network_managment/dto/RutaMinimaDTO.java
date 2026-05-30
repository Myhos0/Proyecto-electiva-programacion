package com.etitc.smart_network_managment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaMinimaDTO {

    private String origen;

    private String destino;

    private Integer distanciaTotal;

    private List<String> ruta;
}