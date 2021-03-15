package br.com.mrit.pessoas.domain.repository;

import br.com.mrit.pessoas.domain.document.Pessoa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PessoaRepository extends MongoRepository<Pessoa, String> {
    Optional<Pessoa> existsByCpf(String cpf);
}