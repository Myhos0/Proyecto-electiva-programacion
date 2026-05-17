package com.etitc.smart_network_managment.repository;

import com.etitc.smart_network_managment.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends JpaRepository<Ruta,Long> {
}
