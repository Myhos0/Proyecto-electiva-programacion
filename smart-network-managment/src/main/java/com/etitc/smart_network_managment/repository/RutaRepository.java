package com.etitc.smart_network_managment.repository;

import com.etitc.smart_network_managment.entity.RutaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends JpaRepository<RutaEntity,Long> {

    boolean existsByOrigen_IdAndDestino_IdAndTiempo(
            Integer origenId,
            Integer destinoId,
            Integer tiempo
    );
}
