package com.etitc.smart_network_managment.grafo;

import com.etitc.smart_network_managment.dto.CiudadDTO;
import com.etitc.smart_network_managment.dto.RutaMinimaDTO;
import com.etitc.smart_network_managment.model.Ruta;
import com.etitc.smart_network_managment.service.GrafoService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
public class Grafo {

    private Map<CiudadDTO, List<Ruta>> grafo = new HashMap<>();

    public void agregarCiudad(CiudadDTO ciudad)
    {
        grafo.putIfAbsent(ciudad, new ArrayList<>());
    }

    public void agregarRuta(CiudadDTO origen,CiudadDTO destino,int tiempo)
    {
        agregarCiudad(origen);

        agregarCiudad(destino);

        grafo.get(origen).add(new Ruta(destino,tiempo));

        grafo.get(destino).add(new Ruta(origen,tiempo));
    }

    public RutaMinimaDTO dijkstra(CiudadDTO origen, CiudadDTO destino) {

        Map<CiudadDTO, Integer> distancias = new HashMap<>();

        Map<CiudadDTO, CiudadDTO> anteriores = new HashMap<>();

        for (CiudadDTO ciudad : grafo.keySet())
        {
            distancias.put(ciudad, Integer.MAX_VALUE);
        }

        distancias.put(origen, 0);

        PriorityQueue<RutaDijkstra> cola = new PriorityQueue<>(Comparator.comparingInt(RutaDijkstra::getDistancia));

        cola.add(new RutaDijkstra(origen, 0));

        while (!cola.isEmpty()) {

            RutaDijkstra actual = cola.poll();

            CiudadDTO ciudadActual = actual.getCiudad();

            if (ciudadActual.equals(destino))
            {
                break;
            }

            for (Ruta ruta : grafo.get(ciudadActual)) {

                CiudadDTO vecino = ruta.getDestino();

                int nuevaDistancia = distancias.get(ciudadActual) + ruta.getTiempo();

                if (nuevaDistancia < distancias.get(vecino)) {

                    distancias.put(vecino, nuevaDistancia);

                    anteriores.put(vecino, ciudadActual);

                    cola.add(new RutaDijkstra(vecino, nuevaDistancia));
                }
            }
        }

        if (distancias.get(destino) == Integer.MAX_VALUE) {

            throw new RuntimeException("No existe ruta entre las ciudades");
        }

        List<String> camino = reconstruirCamino(anteriores, origen, destino);

        return new RutaMinimaDTO(
                origen.getName(),
                destino.getName(),
                distancias.get(destino),
                camino);
    }

    private List<String> reconstruirCamino(Map<CiudadDTO, CiudadDTO> anteriores, CiudadDTO origen, CiudadDTO destino) {

        List<String> camino = new ArrayList<>();

        CiudadDTO actual = destino;

        while (actual != null) {

            camino.add(actual.getName());

            actual = anteriores.get(actual);
        }

        Collections.reverse(camino);

        return camino;
    }
}
