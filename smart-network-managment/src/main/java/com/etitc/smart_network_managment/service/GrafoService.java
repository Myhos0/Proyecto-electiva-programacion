package com.etitc.smart_network_managment.service;

import com.etitc.smart_network_managment.dto.CiudadDTO;
import com.etitc.smart_network_managment.dto.CiudadGrafoDTO;
import com.etitc.smart_network_managment.dto.ConexionDTO;
import com.etitc.smart_network_managment.dto.RutaDTO;
import com.etitc.smart_network_managment.entity.RutaEntity;
import com.etitc.smart_network_managment.grafo.Grafo;
import com.etitc.smart_network_managment.model.Ruta;
import com.etitc.smart_network_managment.repository.RutaRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
public class GrafoService {

    private final CiudadService ciudadService;
    private final RutaRepository rutaRepository;

    private Grafo grafo;

    @PostConstruct
    public void init() {
        try {

            grafo = construirGrafoBD();

        } catch (RuntimeException e) {

            System.out.println("No existen rutas guardadas. Se creará un grafo aleatorio.");

            grafo = crearGrafoAleatorio();
        }
    }

    public Grafo construirGrafoBD() {
        List<RutaEntity> rutas = rutaRepository.findAll();

        if (rutas.isEmpty()) {
            throw new RuntimeException("No existen rutas para crear el grafo");
        }

        Grafo grafo = new Grafo();

        List<CiudadDTO> ciudades = ciudadService.obtenerCiudades();

        for (RutaEntity ruta : rutas) {
            CiudadDTO origen = buscarCiudadPorId(ciudades, ruta.getOrigenId());
            CiudadDTO destino = buscarCiudadPorId(ciudades, ruta.getDestinoId());

            if (origen != null) {
                grafo.agregarCiudad(origen);
            }

            if (destino != null) {
                grafo.agregarCiudad(destino);
            }

            if (origen != null && destino != null) {

                grafo.agregarRuta(
                        origen,
                        destino,
                        ruta.getTiempo()
                );
            }

        }

        return grafo;
    }

    public Grafo crearGrafoAleatorio() {

        List<CiudadDTO> ciudades = ciudadService.obtenerCiudades();

        Collections.shuffle(ciudades);

        ciudades = ciudades.stream().limit(10).toList();

        Grafo grafo = new Grafo();

        for (CiudadDTO ciudad : ciudades) {
            grafo.agregarCiudad(ciudad);
        }

        for (int i = 0; i < ciudades.size() - 1; i++) {

            CiudadDTO origen = ciudades.get(i);

            CiudadDTO destino = ciudades.get(i + 1);

            int tiempo = (int) (Math.random() * 10) + 1;

            grafo.agregarRuta(origen, destino, tiempo);
        }

        return grafo;
    }

    public void crearRuta(RutaDTO rutaDTO) {
        CiudadDTO origen = buscarCiudadPorNombre(rutaDTO.getOrigen());

        CiudadDTO destino = buscarCiudadPorNombre(rutaDTO.getDestino());

        if (origen == null || destino == null) {
            throw new RuntimeException("Ciudad no encontrada");
        }

        boolean existeDirecta = rutaRepository.existsByOrigenIdAndDestinoIdAndTiempo(origen.getId(), destino.getId(), rutaDTO.getTiempo());
        boolean existeInversa = rutaRepository.existsByOrigenIdAndDestinoIdAndTiempo(destino.getId(), origen.getId(), rutaDTO.getTiempo());

        if (existeDirecta || existeInversa) {
            throw new RuntimeException("La ruta ya existe");
        }

        grafo.agregarRuta(
                origen,
                destino,
                rutaDTO.getTiempo()
        );

        RutaEntity entity = new RutaEntity();

        entity.setOrigenId(origen.getId());

        entity.setDestinoId(destino.getId());

        entity.setTiempo(rutaDTO.getTiempo());

        rutaRepository.save(entity);
    }

    public List<CiudadGrafoDTO> convertirGrafoDTO(Grafo grafo) {
        List<CiudadGrafoDTO> respuesta = new ArrayList<>();

        for (CiudadDTO ciudad : grafo.getGrafo().keySet()) {
            List<ConexionDTO> conexiones = new ArrayList<>();

            for (Ruta ruta : grafo.getGrafo().get(ciudad)) {
                conexiones.add(new ConexionDTO(ruta.getDestino(), ruta.getTiempo()));
            }

            respuesta.add(new CiudadGrafoDTO(ciudad, conexiones));
        }

        return respuesta;
    }

    public List<CiudadGrafoDTO> obtenerGrafoAleatorioDTO() {
        Grafo grafoAleatorio = crearGrafoAleatorio();

        return convertirGrafoDTO(grafoAleatorio);
    }

    public List<CiudadGrafoDTO> obtenerGrafoBDDTO() {
        Grafo grafoBD = construirGrafoBD();

        return convertirGrafoDTO(grafoBD);
    }

    public List<CiudadGrafoDTO> obtenerGrafoActualDTO() {
        return convertirGrafoDTO(grafo);
    }

    public CiudadDTO buscarCiudadPorId(List<CiudadDTO> ciudades, Integer id) {
        return ciudades.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    private CiudadDTO buscarCiudadPorNombre(String nombre) {
        List<CiudadDTO> ciudades = ciudadService.obtenerCiudades();

        return ciudades.stream().filter(c -> c.getName().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }
}