package com.etitc.smart_network_managment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CiudadDTO {
    private String nombre;
    private String pais;
    private Integer poblacion;
}
