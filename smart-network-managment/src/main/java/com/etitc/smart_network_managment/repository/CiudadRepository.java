package com.etitc.smart_network_managment.repository;

import com.etitc.smart_network_managment.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad,Long> {

}