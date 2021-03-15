package br.com.mrit.pessoas.domain.repository;

import br.com.mrit.pessoas.domain.entity.Pessoa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PessoaRepository extends MongoRepository<Pessoa, Long> {
}