package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Vehicle(@JsonAlias("Valor") String valor, @JsonAlias("Marca") String marca,
        @JsonAlias("Modelo") String modelo, @JsonAlias("AnoModelo") String anoModelo,
        @JsonAlias("Combustivel") String tipoCombustivel) {

}
