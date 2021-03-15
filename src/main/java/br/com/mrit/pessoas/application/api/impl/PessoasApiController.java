package br.com.mrit.pessoas.application.api.impl;

import br.com.mrit.pessoas.application.api.PessoasApi;
import br.com.mrit.pessoas.application.exception.ApiException;
import br.com.mrit.pessoas.application.model.PessoaModel;
import br.com.mrit.pessoas.application.service.PessoaService;
import br.com.mrit.pessoas.domain.document.Pessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class PessoasApiController implements PessoasApi {

    private final PessoaService service;

    public ResponseEntity<Pessoa> createPessoa(@Valid @RequestBody PessoaModel payload) throws ApiException {
        Pessoa pessoa = service.createPessoa(payload);
        return ResponseEntity.created(ServletUriComponentsBuilder
                                        .fromCurrentRequest()
                                        .path("/{id}")
                                        .buildAndExpand(pessoa.getId())
                                        .toUri())
                                .body(pessoa);
    }

    public ResponseEntity<Void> deletePessoa(@PathVariable("id") String id) throws ApiException {
        service.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Pessoa> getPessoaById(@PathVariable("id") String id) throws ApiException {
        return ResponseEntity.ok(service.getPessoaById(id));
    }

    public ResponseEntity<List<Pessoa>> getPessoas() throws ApiException {
        return ResponseEntity.ok(service.getPessoas());
    }

    public ResponseEntity<Void> updatePessoa(@PathVariable("id") String id, @Valid @RequestBody PessoaModel payload) throws ApiException {
        service.updatePessoa(id, payload);
        return ResponseEntity.noContent().build();
    }
}