package br.com.mrit.pessoas.application.service;

import br.com.mrit.pessoas.application.exception.ApiException;
import br.com.mrit.pessoas.application.model.PessoaModel;
import br.com.mrit.pessoas.domain.document.Pessoa;

import java.util.List;

public interface PessoaService {
    Pessoa createPessoa(PessoaModel payload) throws ApiException;
    void updatePessoa(String id, PessoaModel payload) throws ApiException;
    void deletePessoa(String id) throws ApiException;
    Pessoa getPessoaById(String id) throws ApiException;
    List<Pessoa> getPessoas() throws ApiException;
}
