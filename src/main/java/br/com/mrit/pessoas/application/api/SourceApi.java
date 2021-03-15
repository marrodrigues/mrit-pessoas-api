package br.com.mrit.pessoas.application.api;

import br.com.mrit.pessoas.application.model.Source;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(tags = "Source")
@RequestMapping("/api/v1")
public interface SourceApi {

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca informações dos repositorios dos códigos",
            description = "Busca informações dos repositorios dos códigos",
            security = {@SecurityRequirement(name = "basicAuth")}, tags={ "Source" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Resultado da busca com sucesso.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Source.class)))) })
    @GetMapping(value = "/source", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Source> getSource();
}
