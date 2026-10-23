package com.poo2p1.dto;

import com.poo2p1.model.Cargo;

public class SearchFilters {
    public final String matricula;
    public final Cargo cargo;
    public final String estado;
    public final Double salarioMin;
    public final Double salarioMax;

    public SearchFilters(String matricula, Cargo cargo, String estado, Double salarioMin, Double salarioMax) {
        this.matricula = matricula;
        this.cargo = cargo;
        this.estado = estado;
        this.salarioMin = salarioMin;
        this.salarioMax = salarioMax;
    }
}
