package br.com.mrit.pessoas.application.service.impl;

import br.com.mrit.pessoas.application.exception.ApiException;
import br.com.mrit.pessoas.application.model.PessoaModel;
import br.com.mrit.pessoas.application.service.PessoaService;
import br.com.mrit.pessoas.application.util.ApplicationUtil;
import br.com.mrit.pessoas.domain.document.Pessoa;
import br.com.mrit.pessoas.domain.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {

    private final ModelMapper modelMapper;

    private final PessoaRepository repository;

    @Override
    public Pessoa createPessoa(PessoaModel payload) throws ApiException {
        validaPessoa(payload);
        repository.existsByCpf(payload.getCpf())
                    .orElseThrow(() -> ApiException.conflict("Conflito no cadastro",
                                                            "Erro ao cadastrado pessoa, o cpf %s já foi cadastrado."));

        Pessoa pessoa = modelMapper.map(payload, Pessoa.class);
        pessoa.setCpf(pessoa.getCpf().replaceAll("[^0-9]", ""));

        return repository.save(pessoa);
    }

    @Override
    public void updatePessoa(String id, PessoaModel payload) throws ApiException {
        validaPessoa(payload);

        Pessoa pessoa = repository.findById(id).orElseThrow(() -> ApiException.notFound("Cadastro não encontrado.",
                String.format("Não foi possivel atualizar o cadastro de pessoa, o cadastro referente ao id %s não foi encontrado.", id)));

        Pessoa updatePessoa = modelMapper.map(payload, Pessoa.class);

        updatePessoa.setId(pessoa.getId());
        updatePessoa.setCpf(updatePessoa.getCpf().replaceAll("[^0-9]", ""));
        updatePessoa.setDataCadastro(pessoa.getDataCadastro());

        repository.save(updatePessoa);
    }

    private void validaPessoa(PessoaModel pessoa) throws ApiException {
        if(Strings.isNotBlank(pessoa.getEmail())) {
            try {
                new InternetAddress(pessoa.getEmail()).validate();
            } catch (AddressException e) {
                throw  ApiException.badRequest("E-mail inválido", "O campo email deve ter um e-mail válido.");
            }
        }

        if(!ApplicationUtil.isValidCPF(pessoa.getCpf().replaceAll("[^0-9]", ""))) {
            throw ApiException.badRequest("CPF inválido.", "O campo cpf deve ter um valor válido.");
        }
    }

    @Override
    public void deletePessoa(String id) throws ApiException {
        if(!repository.existsById(id)){
            throw ApiException.notFound("Cadastro não encontrado.",
                    String.format("Não foi possivel deletar o cadastro de pessoa, o cadastro referente ao id %s não foi encontrado.", id));
        }

        repository.deleteById(id);
    }

    @Override
    public Pessoa getPessoaById(String id) throws ApiException {
        return repository.findById(id)
                        .orElseThrow(() ->
                                ApiException.notFound("Cadastro não encontrado",
                                        String.format("O cadastro referente ao id %s não foi encontrado.", id)));
    }

    @Override
    public List<Pessoa> getPessoas() throws ApiException {
        List<Pessoa> all = repository.findAll();
        if(Objects.isNull(all) || all.isEmpty()) throw ApiException.notFound("Nenhum cadastro de pessoa encontrado.",
                                                                            "Não foram encontrado cadastros de pessoas.");
        return all;
    }
}