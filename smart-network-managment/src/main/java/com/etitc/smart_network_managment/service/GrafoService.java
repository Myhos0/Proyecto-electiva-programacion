package com.etitc.smart_network_managment.service;

import com.etitc.smart_network_managment.dto.*;
import com.etitc.smart_network_managment.entity.CiudadEntity;
import com.etitc.smart_network_managment.entity.RutaEntity;
import com.etitc.smart_network_managment.grafo.Grafo;
import com.etitc.smart_network_managment.model.Ruta;
import com.etitc.smart_network_managment.repository.RutaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GrafoService
{
    private final CiudadService ciudadService;
    private final RutaRepository rutaRepository;
    private final CiudadAPIService ciudadAPIService;

    private Grafo grafo;

    @PostConstruct
    public void init()
    {
        List<RutaEntity> rutas = rutaRepository.findAll();

        if(rutas.isEmpty())
        {
            grafo = crearGrafoAleatorio();
        }else
        {
            grafo = construirGrafo();
        }
    }

    private Grafo crearGrafoAleatorio()
    {
        List<CiudadDTO> ciudades = ciudadAPIService.obtenerCiudades();

        Collections.shuffle(ciudades);

        ciudades = ciudades.stream().limit(10).toList();

        Grafo nuevoGrafo = new Grafo();

        ciudades.forEach(nuevoGrafo::agregarCiudad);

        for (int i = 0; i < ciudades.size() - 1; i++)
        {
            int tiempo = (int)(Math.random() * 10) + 1;

            nuevoGrafo.agregarRuta(
                    ciudades.get(i),
                    ciudades.get(i + 1),
                    tiempo
            );
        }

        return nuevoGrafo;
    }

    private Grafo construirGrafo()
    {
        List<RutaEntity> rutas = rutaRepository.findAll();

        if(rutas.isEmpty())
        {
            throw new RuntimeException("No existen rutas");
        }

        Grafo nuevoGrafo =
                new Grafo();

        for (RutaEntity ruta : rutas) {

            CiudadDTO origen = ciudadService.buscarCiudadPorId(ruta.getOrigen().getId());

            CiudadDTO destino = ciudadService.buscarCiudadPorId(ruta.getDestino().getId());

            if (origen != null)
            {
                nuevoGrafo.agregarCiudad(origen);
            }

            if (destino != null)
            {
                nuevoGrafo.agregarCiudad(destino);
            }

            if (origen != null && destino != null)
            {
                nuevoGrafo.agregarRuta(
                        origen,
                        destino,
                        ruta.getTiempo());
            }
        }

        return nuevoGrafo;
    }

    public void crearRuta(RutaDTO dto) {

        CiudadDTO origen = ciudadService.buscarCiudadPorNombre(dto.getOrigen());

        CiudadDTO destino = ciudadService.buscarCiudadPorNombre(dto.getDestino());

        if (origen == null || destino == null)
        {
            throw new RuntimeException("Ciudad no encontrada");
        }

        boolean existeDirecta = rutaRepository.existsByOrigen_IdAndDestino_IdAndTiempo(origen.getId(), destino.getId(), dto.getTiempo());

        boolean existeInversa = rutaRepository.existsByOrigen_IdAndDestino_IdAndTiempo(destino.getId(), origen.getId(), dto.getTiempo());

        if (existeDirecta || existeInversa)
        {
            throw new RuntimeException("La ruta ya existe");
        }

        CiudadEntity ciudadOrigen = ciudadService.guardarCiudadSiNoExiste(origen);

        CiudadEntity ciudadDestino = ciudadService.guardarCiudadSiNoExiste(destino);

        RutaEntity ruta = new RutaEntity();

        ruta.setOrigen(ciudadOrigen);
        ruta.setDestino(ciudadDestino);
        ruta.setTiempo(dto.getTiempo());

        rutaRepository.save(ruta);

        grafo.agregarCiudad(origen);
        grafo.agregarCiudad(destino);
        grafo.agregarRuta(
                origen,
                destino,
                dto.getTiempo());
    }

    public List<CiudadGrafoDTO> obtenerGrafoActualDTO()
    {
        return convertirDTO(grafo);
    }

    private List<CiudadGrafoDTO> convertirDTO(Grafo grafo) {

        List<CiudadGrafoDTO> respuesta = new ArrayList<>();

        for (CiudadDTO ciudad : grafo.getGrafo().keySet())
        {
            List<ConexionDTO> conexiones = new ArrayList<>();

            for (Ruta ruta : grafo.getGrafo().get(ciudad))
            {
                conexiones.add(new ConexionDTO(
                        ruta.getDestino(),
                        ruta.getTiempo()));
            }

            respuesta.add(new CiudadGrafoDTO(
                    ciudad,
                    conexiones));
        }

        return respuesta;
    }

    public List<CiudadGrafoDTO> obtenerGrafoAleatorioDTO() {

        Grafo grafoAleatorio = crearGrafoAleatorio();

        return convertirDTO(grafoAleatorio);
    }
    public List<CiudadGrafoDTO> obtenerGrafoBDDTO() {

        Grafo grafoBD = construirGrafo();

        return convertirDTO(grafoBD);
    }

    public RutaMinimaDTO calcularRutaMinima(String nombreOrigen, String nombreDestino) {

        CiudadDTO origen = ciudadService.buscarCiudadPorNombre(nombreOrigen);

        CiudadDTO destino = ciudadService.buscarCiudadPorNombre(nombreDestino);

        if (origen == null)
        {
            throw new RuntimeException("Ciudad origen no encontrada");
        }

        if (destino == null) {
            throw new RuntimeException("Ciudad destino no encontrada");
        }

        return grafo.dijkstra(
                origen,
                destino);
    }
}