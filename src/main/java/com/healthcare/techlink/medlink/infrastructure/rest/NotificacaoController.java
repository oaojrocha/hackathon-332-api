package com.healthcare.techlink.medlink.infrastructure.rest;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.techlink.medlink.core.repository.AgendaRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    @PostMapping("/lembrete-consulta/{id_agenda}")
    @ApiOperation(value = "Envia um lembrete para o paciente sobre a consulta")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Notificação envidada com sucesso"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Erro interno do servidor"),
            @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Agendamento não encontrado")
    })
    public ResponseEntity<?> enviarLembrete(@PathVariable(value = "id_agenda") long idAgenda) {
        return AgendaRepository.dados.stream().filter(d -> d.getId() == idAgenda).findFirst().isPresent()
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

}
