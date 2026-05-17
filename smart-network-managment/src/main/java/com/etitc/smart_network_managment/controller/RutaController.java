package com.etitc.smart_network_managment.controller;

import com.etitc.smart_network_managment.dto.CiudadDTO;
import com.etitc.smart_network_managment.dto.RutaDTO;
import com.etitc.smart_network_managment.model.Ciudad;
import com.etitc.smart_network_managment.model.Ruta;
import com.etitc.smart_network_managment.service.CiudadService;
import com.etitc.smart_network_managment.service.RutaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rutas")
@RequiredArgsConstructor
public class RutaController {

    private final RutaService rutaService;
    private final CiudadService ciudadService;

    @GetMapping("/todas")
    public List<Ruta> ObtnerTodas(){ return rutaService.ObtenerTodos(); }

    @GetMapping("/{id}")
    public ResponseEntity ObtenerPorId(@PathVariable Long id)
    {
        return ResponseEntity.ok(rutaService.ObtnerPorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity Crear(@RequestBody RutaDTO rutaDTO)
    {
        List<Ciudad> ciudades = ciudadService.ObtenerTodos();

        boolean inicioExiste = ciudades.stream().anyMatch(c -> c.getNombre().equals(rutaDTO.getInicio()));
        boolean destinoExiste = ciudades.stream().anyMatch(c -> c.getNombre().equals(rutaDTO.getDestino()));

        if (!inicioExiste || !destinoExiste)
        {
            return ResponseEntity.badRequest().body("Ciudad no encontrada");
        }

        if(rutaDTO.getInicio().equals(rutaDTO.getDestino()))
        {
         return  ResponseEntity.badRequest().body("El destino no puede ser igual al inicio");
        }

        return ResponseEntity.ok( rutaService.Crear(rutaDTO));
    }

    @DeleteMapping("/Borrar")
    public void Borrar(@PathVariable Long id)
    {
        rutaService.Borrar(id);
    }
}
