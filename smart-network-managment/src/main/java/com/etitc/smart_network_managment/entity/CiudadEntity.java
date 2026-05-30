package com.etitc.smart_network_managment.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ciudades")
@Data
public class CiudadEntity {
    @Id
    private Integer id;
    private String nombre;
}
