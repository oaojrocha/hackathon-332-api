package com.healthcare.techlink.medlink.infrastructure.rest;

import com.healthcare.techlink.medlink.core.domain.Agenda;
import com.healthcare.techlink.medlink.core.domain.HistoricoPaciente;
import com.healthcare.techlink.medlink.core.domain.Medico;
import com.healthcare.techlink.medlink.core.repository.AgendaRepository;
import com.healthcare.techlink.medlink.core.repository.HistoricoPacienteRepository;
import com.healthcare.techlink.medlink.core.repository.MedicoRepository;
import com.healthcare.techlink.medlink.core.repository.PacienteRepository;
import com.healthcare.techlink.medlink.infrastructure.rest.dto.AgendaDTO;
import com.healthcare.techlink.medlink.infrastructure.rest.dto.AgendaUpdateDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/agenda")
public class AgendamentoController {

    @GetMapping("/medico/{id_medico}")
    @ApiOperation(value = "Histórico médico do paciente")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Retorna todo o histórico do paciente"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Erro interno do servidor"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Paciente não encontrador")
    })
    public ResponseEntity<?> getByMedico(@PathVariable(value = "id_medico") long idMedico) {

        return Optional.of(AgendaRepository.dados.stream()
                .filter(h -> h.getMedico().getId() == idMedico)
                .collect(Collectors.toList()))
                .filter(body -> !body.isEmpty())
                .map(body -> ResponseEntity.ok(body))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/paciente/{id_paciente}")
    @ApiOperation(value = "Histórico médico do paciente")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Retorna todo o histórico do paciente"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Erro interno do servidor"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Paciente não encontrador")
    })
    public ResponseEntity<?> getByPaciente(@PathVariable(value = "id_paciente") long idPaciente) {

        return Optional.of(AgendaRepository.dados.stream()
                .filter(h -> h.getPaciente().getId() == idPaciente)
                .collect(Collectors.toList()))
                .filter(body -> !body.isEmpty())
                .map(body -> ResponseEntity.ok(body))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping
    @ApiOperation(value = "Histórico médico do paciente")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Retorna todo o histórico do paciente"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Erro interno do servidor"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Paciente não encontrador")
    })
    public ResponseEntity<?> getAll() {

        return Optional.of(AgendaRepository.dados.stream()
                .collect(Collectors.toList()))
                .filter(body -> !body.isEmpty())
                .map(body -> ResponseEntity.ok(body))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Histórico médico do paciente")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Retorna todo o histórico do paciente"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Erro interno do servidor"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Paciente não encontrador")
    })
    public ResponseEntity<?> getById(@PathVariable(value = "id") long id) {

        return Optional.of(AgendaRepository.dados.stream()
                .filter(h -> h.getId() == id)
                .collect(Collectors.toList()))
                .filter(body -> !body.isEmpty())
                .map(body -> ResponseEntity.ok(body))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    @ApiOperation(value = "Histórico médico do paciente")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_CREATED, message = "Retorna todo o histórico do paciente"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Erro interno do servidor"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Paciente não encontrador")
    })
    public ResponseEntity<?> create(@RequestBody AgendaDTO payload) {

        Agenda a = new Agenda();
        a.setId(AgendaRepository.dados.stream().max(Comparator.comparing(v -> v.getId())).get().getId() + 1);
        a.setDataConsulta(payload.getDataConsulta());

        Optional.ofNullable(payload)
                .ifPresent(p -> {
                    MedicoRepository.dados.stream()
                            .filter(m -> m.getId() == payload.getIdMedico())
                            .findFirst()
                            .ifPresent(m -> a.setMedico(m));

                    PacienteRepository.dados.stream()
                            .filter(d -> d.getId() == payload.getIdPaciente())
                            .findFirst()
                            .ifPresent(d -> a.setPaciente(d));
                });

        if (Objects.isNull(a.getMedico()) || Objects.isNull(a.getPaciente())) {
            return ResponseEntity.badRequest().build();
        }

        AgendaRepository.dados.add(a);

        return ResponseEntity.ok().build();

    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Histórico médico do paciente")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_CREATED, message = "Retorna todo o histórico do paciente"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Erro interno do servidor"),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Paciente não encontrador")
    })
    public ResponseEntity<?> update(@PathVariable(value = "id") long id,  @RequestBody AgendaUpdateDTO payload) {

        AgendaRepository.dados.stream()
            .filter(d -> d.getId() == id)
            .findFirst()
            .ifPresent(d -> d.setDataConsulta(payload.getDataConsulta()));

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Histórico médico do paciente")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_CREATED, message = "Retorna todo o histórico do paciente"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Erro interno do servidor"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Paciente não encontrador")
    })
    public ResponseEntity<?> delete(@PathVariable(value = "id") long id) {
        return AgendaRepository.dados.removeIf(d -> d.getId() == id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();

    }

}
