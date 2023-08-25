package com.healthcare.techlink.medlink.infrastructure.rest;

import com.healthcare.techlink.medlink.core.domain.HistoricoPaciente;
import com.healthcare.techlink.medlink.core.repository.HistoricoPacienteRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/historico")
public class HistoricoController {

    @GetMapping("/{id_paciente}")
    @ApiOperation(value = "Histórico médico do paciente")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Retorna todo o histórico do paciente"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Erro interno do servidor"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Paciente não encontrador")
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
