package com.example.ordem_servico.controller;

import com.example.ordem_servico.dto.OrdemServicoDTO;
import com.example.ordem_servico.model.OrdemServico;
import com.example.ordem_servico.service.OrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemServicoService;

    @PostMapping
    public ResponseEntity<OrdemServico> criarOrdemServico(@RequestBody OrdemServicoDTO dto) {
        OrdemServico novaOrdem = ordemServicoService.criarOrdemServico(dto);
        return ResponseEntity.ok(novaOrdem);
    }
}
