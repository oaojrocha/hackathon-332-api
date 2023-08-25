package com.healthcare.techlink.medlink.infrastructure.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MedLinkController {

    @GetMapping
    @ApiOperation(value = "")
    @ApiResponses(value = {
            @ApiResponse(code = HttpStatus.SC_OK, message = "Sucesso")
    })
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().build();
    }

}
