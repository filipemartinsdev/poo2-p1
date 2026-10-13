package com.poo2p1.dto;

import com.poo2p1.model.Cargo;

public class SearchFilters {
    public final String matricula;
    public final Cargo cargo;
    public final String estado;

    public SearchFilters(String matricula, Cargo cargo, String estado) {
        this.matricula = matricula;
        this.cargo = cargo;
        this.estado = estado;
    }
}
