package com.etitc.smart_network_managment.grafo;

import com.etitc.smart_network_managment.dto.CiudadDTO;

public class Nodo {
    CiudadDTO ciudad;
    Nodo siguiente;

    public Nodo(CiudadDTO ciudad)
    {
        this.ciudad = ciudad;
        this.siguiente = null;
    }
}
