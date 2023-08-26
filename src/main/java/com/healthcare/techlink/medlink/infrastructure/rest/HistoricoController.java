package com.healthcare.techlink.medlink.infrastructure.rest;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.techlink.medlink.core.repository.HistoricoPacienteRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "historico", description = "APIs de histórico médico")
@RequestMapping("/historico")
public class HistoricoController {

    @GetMapping("/{id_paciente}")
    @Operation(summary = "Histórico médico do paciente", description = "Retorna todo o histórico de todas as consultas do paciente", tags = { "historico" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todo o histórico do paciente"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
            @ApiResponse(responseCode = "404", description = "Paciente ou histórico não encontrado")
    })
    public ResponseEntity<?> get(@PathVariable(value = "id_paciente") long idPaciente) {

        return Optional.of(HistoricoPacienteRepository.dados.stream()
                .filter(h -> h.getPaciente().getId() == idPaciente)
                .collect(Collectors.toList()))
            .filter(body -> !body.isEmpty())
            .map(body -> ResponseEntity.ok(body))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
