package br.com.mrit.pessoas.application.api;

import br.com.mrit.pessoas.application.model.Pessoa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PessoasApiController implements PessoasApi {

    public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa body) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    public ResponseEntity<Void> deletePessoa(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    public ResponseEntity<Pessoa> getPessoaById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    public ResponseEntity<List<Pessoa>> getPessoas() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    public ResponseEntity<Void> updatePessoa(@PathVariable("id") Long id, @RequestBody Pessoa body) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}