package com.etitc.smart_network_managment.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rutas")
@Data
public class RutaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origen_id")
    private CiudadEntity origen;

    @ManyToOne
    @JoinColumn(name = "destino_id")
    private CiudadEntity destino;

    private Integer tiempo;
}
