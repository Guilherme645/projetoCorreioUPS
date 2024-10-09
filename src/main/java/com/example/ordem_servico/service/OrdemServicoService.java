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

    private static final String API_URL = "https://api.gestaoclick.com/ordens_servicos?&loja=178825";
    private static final String ACCESS_TOKEN = "9a106afa8b428596d32d5902311222c57a17c40c";
    private static final String SECRET_ACCESS_TOKEN = "60a593e9311993d94b833f181a677b3348c436bf";

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

    // Atualização do payload para refletir as mudanças
    private void enviarParaGestaoClick(OrdemServico ordem) {
        Client client = ClientBuilder.newClient();

        // Criar o payload (JSON) com base nos dados da ordem de serviço
        String payload = String.format(
                "{ \"cliente_id\": \"%s\", \"vendedor_id\": \"%s\", \"nome_vendedor\": \"%s\", \"data_entrada\": \"%s\", " +
                        "\"situacao_id\": \"%s\", \"nome_situacao\": \"%s\", \"condicao_pagamento\": \"%s\", " +
                        "\"produtos\": [{ \"produto\": { \"produto_id\": \"%s\", \"nome_produto\": \"%s\", \"quantidade\": \"1\", \"valor_venda\": \"%s\" }}], " +
                        "\"servicos\": [{ \"servico_id\": \"%s\", \"nome_servico\": \"%s\", \"quantidade\": \"%s\", \"valor_venda\": \"%s\" }], " +
                        "\"pagamentos\": [{ \"data_vencimento\": \"%s\", \"valor\": \"%s\", \"forma_pagamento_id\": \"%s\" }] }",
                ordem.getCliente(),
                ordem.getVendedor(),
                ordem.getNomeSituacao(),
                ordem.getDataEntrada(),
                ordem.getSituacao(),
                ordem.getNomeSituacao(),
                ordem.getCondicaoPagamento(),
                ordem.getEquipamento(),

                ordem.getDataVencimento(),
                ordem.getValorPagamento(),
                ordem.getFormaPagamentoId()
        );

        System.out.println("Payload enviado: " + payload);

        // Fazer a requisição POST para a API externa
        Response response = client.target(API_URL)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("access-token", ACCESS_TOKEN)
                .header("secret-access-token", SECRET_ACCESS_TOKEN)
                .post(Entity.json(payload));

        // Processar a resposta da API
        int statusCode = response.getStatus();
        if (statusCode == 200) {
            String body = response.readEntity(String.class);
            System.out.println("Ordem de serviço enviada com sucesso!");
            System.out.println("Resposta da API: " + body);
        } else {
            System.out.println("Erro ao enviar ordem de serviço. Status: " + statusCode);
            String responseBody = response.readEntity(String.class);
            System.out.println("Erro da API: " + responseBody);
        }

        // Fechar o cliente
        client.close();
    }
}

