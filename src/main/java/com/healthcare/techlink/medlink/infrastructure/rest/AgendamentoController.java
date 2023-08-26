package com.healthcare.techlink.medlink.infrastructure.rest;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.techlink.medlink.core.domain.Agenda;
import com.healthcare.techlink.medlink.core.domain.HistoricoPaciente;
import com.healthcare.techlink.medlink.core.repository.AgendaRepository;
import com.healthcare.techlink.medlink.core.repository.MedicoRepository;
import com.healthcare.techlink.medlink.core.repository.PacienteRepository;
import com.healthcare.techlink.medlink.infrastructure.rest.dto.AgendaDTO;
import com.healthcare.techlink.medlink.infrastructure.rest.dto.AgendaUpdateDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "agenda", description = "APIs de agendamentos")
@RequestMapping("/agenda")
public class AgendamentoController {

    @GetMapping(value = "/medico/{id_medico}", produces = { "application/json" })
    @Operation(summary =  "Agenda do médico", description = "Retorna lista de agendas do médico", tags = { "agenda" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista de agendas do médico", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Agenda.class)))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content),
            @ApiResponse(responseCode = "404", description = "Médico ou agenda não encontrado", content = @Content)
    })
    public ResponseEntity<List<Agenda>> getByMedico(@PathVariable(value = "id_medico") long idMedico) {

        return Optional.of(AgendaRepository.dados.stream()
                .filter(h -> h.getMedico().getId() == idMedico)
                .collect(Collectors.toList()))
                .filter(body -> !body.isEmpty())
                .map(body -> ResponseEntity.ok(body))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping(value = "/paciente/{id_paciente}", produces = { "application/json" })
    @Operation(summary =  "Agenda do paciente", description = "Retorna lista de agenda paciente", tags = { "agenda" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista de agenda do paciente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Agenda.class)))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente ou agenda não encontrado", content = @Content)
    })
    public ResponseEntity<List<Agenda>> getByPaciente(@PathVariable(value = "id_paciente") long idPaciente) {

        return Optional.of(AgendaRepository.dados.stream()
                .filter(h -> h.getPaciente().getId() == idPaciente)
                .collect(Collectors.toList()))
                .filter(body -> !body.isEmpty())
                .map(body -> ResponseEntity.ok(body))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping(produces = { "application/json" })
    @Operation(summary =  "Todas agendas", description = "Retorna lista de todas agendas", tags = { "agenda" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista de agenda", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Agenda.class)))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content),
            @ApiResponse(responseCode = "404", description = "Agenda não encontrada", content = @Content)
    })
    public ResponseEntity<List<Agenda>> getAll() {

        return Optional.of(AgendaRepository.dados.stream()
                .collect(Collectors.toList()))
                .filter(body -> !body.isEmpty())
                .map(body -> ResponseEntity.ok(body))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping(value = "/{id}", produces = { "application/json" })
    @Operation(summary =  "Agenda por id", description = "Retorna a agenda através do id", tags = { "agenda" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna agenda especifica", content = @Content(schema = @Schema(implementation = Agenda.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content),
            @ApiResponse(responseCode = "404", description = "Agenda não encontrada", content = @Content)
    })
    public ResponseEntity<Agenda> getById(@PathVariable(value = "id") long id) {

        return AgendaRepository.dados.stream()
                .filter(h -> h.getId() == id)
                .findFirst()
                .filter(body -> Objects.nonNull(body))
                .map(body -> ResponseEntity.ok(body))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping(produces = { "application/json" })
    @Operation(summary =  "Cadatrar agenda", tags = { "agenda" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastra agenda", content = @Content(schema = @Schema(implementation = Agenda.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente ou médico não encontrado", content = @Content)
    })
    public ResponseEntity<Agenda> create(@RequestBody AgendaDTO payload) {

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

        return new ResponseEntity<Agenda>(a, org.springframework.http.HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary =  "Atualizar agenda", tags = { "agenda" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualiza a agenda"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
            @ApiResponse(responseCode = "404", description = "Agenda não encontrada")
    })
    public ResponseEntity<Void> update(@PathVariable(value = "id") long id, @RequestBody AgendaUpdateDTO payload) {

        try {
            AgendaRepository.dados.stream()
                    .filter(d -> d.getId() == id)
                    .findFirst()
                    .ifPresentOrElse(d -> d.setDataConsulta(payload.getDataConsulta()),
                            () -> {
                                throw new RuntimeException("");
                            });

            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary =  "Deletar agenda", tags = { "agenda" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleta agenda"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
            @ApiResponse(responseCode = "404", description = "Agenda não encontrada")
    })
    public ResponseEntity<Void> delete(@PathVariable(value = "id") long id) {
        return AgendaRepository.dados.removeIf(d -> d.getId() == id) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

}
