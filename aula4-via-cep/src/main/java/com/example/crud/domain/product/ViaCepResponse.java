package com.example.crud.domain.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ViaCepResponse(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        @JsonProperty("uf") String uf,
        @JsonProperty("erro") Boolean erro
) {}
