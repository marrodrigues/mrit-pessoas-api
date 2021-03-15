package br.com.mrit.pessoas.application.service;

import br.com.mrit.pessoas.application.exception.ApiException;
import br.com.mrit.pessoas.domain.entity.Pessoa;

import java.util.List;

public interface PessoaService {
    Pessoa createPessoa(Pessoa payload) throws ApiException;
    void updatePessoa(Long id, Pessoa payload) throws ApiException;
    void deletePessoa(Long id) throws ApiException;
    Pessoa getPessoaById(Long id) throws ApiException;
    List<Pessoa> getPessoas() throws ApiException;
}
