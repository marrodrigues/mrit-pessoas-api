package br.com.mrit.pessoas.application.api;

import br.com.mrit.pessoas.application.exception.ApiException;
import br.com.mrit.pessoas.application.model.PessoaModel;
import br.com.mrit.pessoas.domain.document.Pessoa;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Pessoas")
@RequestMapping("/api")
public interface PessoasApi {

    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adiciona pesssoa",
            description = "Adiciona um cadastro de pessoa no sistema.",
            security = {@SecurityRequirement(name = "basicAuth")}, tags={ "Pessoas" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Pessoa criada", content = @Content(schema = @Schema(implementation = Pessoa.class))),
        @ApiResponse(responseCode = "400", description = "Erro nos dados enviados para cadastro"),
        @ApiResponse(responseCode = "409", description = "Conflito de cadastro") })
    @PostMapping(value = "/v1/pessoas", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Pessoa> createPessoa(@Parameter(in = ParameterIn.DEFAULT,
                                                        description = "Objeto com informações da pessoa a ser adicionada.",
                                                        required=true,
                                                        schema=@Schema())
                                            @RequestBody PessoaModel body) throws ApiException;

    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclusão de pesssoa",
            description = "Exclusão de um cadastro de pessoa no sistema.",
            security = {@SecurityRequirement(name = "basicAuth")}, tags={ "Pessoas" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Exclusão concluida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
    @DeleteMapping(value = "/v1/pessoas/{id}")
    ResponseEntity deletePessoa(@Parameter(in = ParameterIn.PATH,
                                                description = "Identificador da pessoa",
                                                required=true,
                                                schema=@Schema())
                                @PathVariable("id") String id) throws ApiException;

    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca de pessoa cadastrada",
            description = "Busca de uma pessoa especifica cadastrada no sistema ",
            security = {@SecurityRequirement(name = "basicAuth")}, tags={ "Pessoas" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200",
                description = "Resultado da busca com sucesso.",
                content = @Content(schema = @Schema(implementation = Pessoa.class))) })
    @GetMapping(value = "/v1/pessoas/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Pessoa> getPessoaById(@Parameter(in = ParameterIn.PATH,
                                                            description = "Identificador da pessoa",
                                                            required=true,
                                                            schema=@Schema())
                                            @PathVariable("id") String id) throws ApiException;

    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista pessoas cadastradas",
            description = "Busca de todas as pessoas cadastradas no sistema ",
            security = {@SecurityRequirement(name = "basicAuth")}, tags={ "Pessoas" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200",
                description = "Resultado da busca com sucesso.",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pessoa.class)))) })
    @GetMapping(value = "/v1/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Pessoa>> getPessoas() throws ApiException;

    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualização de um cadastro de pesssoa",
            description = "Atualização de um cadastro de pessoa no sistema.",
            security = {@SecurityRequirement(name = "basicAuth")}, tags={"Pessoas"})
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Atualização concluida com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro nos dados enviados para cadastro"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
    @PutMapping(value = "/v1/pessoas/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity updatePessoa(@Parameter(in = ParameterIn.PATH,
                                            description = "Identificador da pessoa",
                                            required=true,
                                            schema=@Schema())
                                    @PathVariable("id") String id,
                                @Parameter(in = ParameterIn.DEFAULT,
                                            required=true,
                                            description = "Objeto com informações da pessoa a ser atualizada.",
                                            schema=@Schema())
                                   @RequestBody PessoaModel body) throws ApiException;
}