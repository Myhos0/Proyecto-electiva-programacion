package com.etitc.smart_network_managment.controller;

import com.etitc.smart_network_managment.dto.RutaDTO;
import com.etitc.smart_network_managment.service.GrafoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/grafo")
@RequiredArgsConstructor
public class GrafoController {

    private final GrafoService grafoService;

    @GetMapping("/aleatorio")
    public ResponseEntity<?> obtenerGrafoAleatorio() {

       return ResponseEntity.ok(grafoService.obtenerGrafoAleatorioDTO());
    }

    @GetMapping("/bd")
    public ResponseEntity<?> obtenerGrafoDesdeBD()
    {
        try
        {
            return ResponseEntity.ok(grafoService.obtenerGrafoBDDTO());
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/ruta")
    public ResponseEntity<?> crearRuta(@RequestBody RutaDTO rutaDTO)
    {
        if (rutaDTO.getOrigen().equals(rutaDTO.getDestino()))
        {
            return ResponseEntity.badRequest().body("La ciudad origen no puede ser igual al destino");
        }

        try {
            grafoService.crearRuta(rutaDTO);

            return ResponseEntity.ok("Ruta creada correctamente");
        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> obtenerGrafoActual()
    {
        return ResponseEntity.ok(grafoService.obtenerGrafoActualDTO());
    }
}