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

    private Integer origenId;
    private Integer destinoId;
    private Integer tiempo;
}
