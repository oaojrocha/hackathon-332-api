package com.healthcare.techlink.medlink.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.techlink.medlink.core.repository.AgendaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "notificacao", description = "APis de notificações e lembretes")
@RequestMapping("/notificacao")
public class NotificacaoController {

    @PostMapping("/lembrete/{id_agenda}")
    @Operation(summary = "Enviar lembrete", description = "Envia um lembrete para o paciente sobre a consulta", tags = { "notificacao" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificação envidada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    public ResponseEntity<?> enviarLembrete(@PathVariable(value = "id_agenda") long idAgenda) {
        return AgendaRepository.dados.stream().filter(d -> d.getId() == idAgenda).findFirst().isPresent()
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

}
