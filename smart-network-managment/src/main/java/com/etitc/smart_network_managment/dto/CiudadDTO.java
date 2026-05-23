package com.etitc.smart_network_managment.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class CiudadDTO {

    private Integer id;

    private String name;

    private String description;

    private Double surface;

    private Integer population;

    private String postalCode;

    private Integer departmentId;
}
