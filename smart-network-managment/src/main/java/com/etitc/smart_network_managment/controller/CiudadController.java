package com.etitc.smart_network_managment.controller;

import com.etitc.smart_network_managment.dto.CiudadDTO;
import com.etitc.smart_network_managment.model.Ciudad;
import com.etitc.smart_network_managment.service.CiudadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
@RequiredArgsConstructor
public class CiudadController {

    private final CiudadService ciudadService;

    @GetMapping("/todas")
    public List<Ciudad> ObtnerTodas()
    {
        return ciudadService.ObtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ciudad> ObtenerPorId(@PathVariable Long id)
    {
        return ResponseEntity.ok(ciudadService.ObtenerPorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<Ciudad> Crear(@RequestBody CiudadDTO ciudadDTO)
    {
        return ResponseEntity.ok(ciudadService.Crear(ciudadDTO));
    }

    @DeleteMapping("/borrar/{id}")
    public void Borrar(@PathVariable Long id)
    {
        ciudadService.Borrar(id);
    }
}
