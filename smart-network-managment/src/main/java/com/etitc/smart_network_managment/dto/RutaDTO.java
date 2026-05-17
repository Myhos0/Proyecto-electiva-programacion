package com.etitc.smart_network_managment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RutaDTO {
    private String inicio;
    private String destino;
    private Integer duracionRecorrido;
}
