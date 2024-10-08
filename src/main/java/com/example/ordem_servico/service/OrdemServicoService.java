package com.example.ordem_servico.service;

import com.example.ordem_servico.dto.OrdemServicoDTO;
import com.example.ordem_servico.model.OrdemServico;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.stereotype.Service;

@Service
public class OrdemServicoService {

    // Método para criar e enviar uma nova ordem de serviço para o Gestão Click
    public OrdemServico criarOrdemServico(OrdemServicoDTO dto) {
        // Mapear o DTO para a entidade OrdemServico
        OrdemServico ordem = mapearDtoParaEntidade(dto);

        // Enviar a ordem de serviço para o Gestão Click
        enviarParaGestaoClick(ordem);

        // Retornar a ordem criada
        return ordem;
    }

    // Método para mapear o DTO para a entidade OrdemServico
    private OrdemServico mapearDtoParaEntidade(OrdemServicoDTO dto) {
        OrdemServico ordem = new OrdemServico();
        ordem.setNumero(dto.getNumero());
        ordem.setCliente(dto.getCliente());
        ordem.setSituacao(dto.getSituacao());
        ordem.setDataEntrada(dto.getDataEntrada());
        ordem.setHoraEntrada(dto.getHoraEntrada());
        ordem.setDataSaida(dto.getDataSaida());
        ordem.setCanalVenda(dto.getCanalVenda());
        ordem.setCentroCusto(dto.getCentroCusto());
        ordem.setPrioridade(dto.getPrioridade());
        ordem.setVendedor(dto.getVendedor());
        ordem.setTecnico(dto.getTecnico());
        ordem.setPotencia(dto.getPotencia());
        ordem.setTensaoEntrada(dto.getTensaoEntrada());
        ordem.setTensaoSaida(dto.getTensaoSaida());
        ordem.setEquipamento(dto.getEquipamento());
        ordem.setMarca(dto.getMarca());
        ordem.setModelo(dto.getModelo());
        ordem.setSerie(dto.getSerie());
        ordem.setCondicoes(dto.getCondicoes());
        ordem.setDefeitos(dto.getDefeitos());
        ordem.setAcessorios(dto.getAcessorios());

        return ordem;
    }

    // Método para enviar a ordem de serviço para a API do Gestão Click
    private void enviarParaGestaoClick(OrdemServico ordem) {
        Client client = ClientBuilder.newClient();

        // Criar o payload (JSON) com base nos dados da ordem de serviço
        String payload = String.format(
                "{ \"cliente_id\": \"%s\", \"vendedor_id\": \"45\", \"nome_vendedor\": \"%s\", \"data_entrada\": \"%s\"," +
                        " \"situacao_id\": \"3150\", \"nome_situacao\": \"%s\", \"condicao_pagamento\": \"parcelado\", \"produtos\":" +
                        " [{ \"produto\": { \"produto_id\": \"22\", \"nome_produto\": \"%s\", \"quantidade\": \"1\", \"valor_venda\": \"60.00\" }}] }",
                ordem.getCliente(), ordem.getVendedor(), ordem.getDataEntrada(), ordem.getSituacao(), ordem.getEquipamento()
        );

        // Fazer a requisição POST para a API externa
        Response response = client.target("https://api.beteltecnologia.com/ordens_servicos")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("access-token", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                .header("secret-access-token", "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYY")
                .post(Entity.json(payload));

        // Processar a resposta da API
        int statusCode = response.getStatus();
        String body = response.readEntity(String.class);

        System.out.println("Status: " + statusCode);
        System.out.println("Body: " + body);

        // Fechar o cliente
        client.close();
    }
}
