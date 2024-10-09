package com.example.ordem_servico.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/ordem-servico")
public class OrdemServicoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listarOrdemServico() {
        return "{\"message\":\"Ordem de serviço listada com sucesso!\"}";
    }
}
