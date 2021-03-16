package br.com.mrit.pessoas.application.api.impl;

import br.com.mrit.pessoas.application.api.PessoasApi;
import br.com.mrit.pessoas.application.exception.ApiException;
import br.com.mrit.pessoas.application.model.ErrorResponse;
import br.com.mrit.pessoas.application.model.PessoaModel;
import br.com.mrit.pessoas.application.service.PessoaService;
import br.com.mrit.pessoas.domain.document.Pessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class PessoasApiController implements PessoasApi {

    private final PessoaService service;

    public ResponseEntity createPessoa(@Valid @RequestBody PessoaModel payload) {
        ResponseEntity result;
        try {
            Pessoa pessoa = service.createPessoa(payload);
            result = ResponseEntity.created(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(pessoa.getId())
                    .toUri())
                    .body(pessoa);
        } catch (ApiException e) {
            result = ResponseEntity.status(e.getStatusCode())
                    .body(ErrorResponse
                            .builder()
                            .code(e.getCode())
                            .description(e.getReason())
                            .message(e.getMessage())
                            .build());
        }
        return result;
    }

    public ResponseEntity deletePessoa(@PathVariable("id") String id) {
        try {
            service.deletePessoa(id);
            return ResponseEntity.noContent().build();
        } catch (ApiException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(ErrorResponse
                            .builder()
                            .code(e.getCode())
                            .description(e.getReason())
                            .message(e.getMessage())
                            .build());
        }
    }

    public ResponseEntity getPessoaById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(service.getPessoaById(id));
        } catch (ApiException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(ErrorResponse
                            .builder()
                            .code(e.getCode())
                            .description(e.getReason())
                            .message(e.getMessage())
                            .build());
        }
    }

    public ResponseEntity getPessoas() {
        try {
            return ResponseEntity.ok(service.getPessoas());
        } catch (ApiException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(ErrorResponse
                            .builder()
                            .code(e.getCode())
                            .description(e.getReason())
                            .message(e.getMessage())
                            .build());
        }
    }

    public ResponseEntity updatePessoa(@PathVariable("id") String id, @Valid @RequestBody PessoaModel payload) throws URISyntaxException{
        try {
            service.updatePessoa(id, payload);
            return ResponseEntity.noContent().location(new URI(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())).build();
        } catch (ApiException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(ErrorResponse
                            .builder()
                            .code(e.getCode())
                            .description(e.getReason())
                            .message(e.getMessage())
                            .build());
        }
    }
}