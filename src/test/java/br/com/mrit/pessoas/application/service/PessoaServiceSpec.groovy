package br.com.mrit.pessoas.application.service

import br.com.mrit.pessoas.application.exception.ApiException
import br.com.mrit.pessoas.application.model.PessoaModel
import br.com.mrit.pessoas.application.service.impl.PessoaServiceImpl
import br.com.mrit.pessoas.domain.document.Pessoa
import br.com.mrit.pessoas.domain.document.enumeration.SexoEnumeration
import br.com.mrit.pessoas.domain.repository.PessoaRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalDateTime

class PessoaServiceSpec extends Specification {

    def repository = Mock(PessoaRepository)

    def service = new PessoaServiceImpl(new ModelMapper(), repository)

    def "Retorna lista de todas as pessoas cadastradas"(){
        setup:
            repository.findAll() >> list
        when:
            def resul = service.getPessoas()
        then:
            resul == list
        where:
            list = [Stub(Pessoa)]
    }

    def "Retorna ApiException quando a lista de pessoas cadastradas está vazia"(){
        setup:
        repository.findAll() >> list
        when:
             service.getPessoas()
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == HttpStatus.NOT_FOUND.value()
        where:
            list = new ArrayList()
    }

    def "Retorna pessoa na busca de pessoa por id"(){
        setup:
            repository.findById(id) >> pessoa
        when:
            def resul = service.getPessoaById(id)
        then:
            resul == pessoa.get()
        where:
            id = "1"
            pessoa = Optional.of(Stub(Pessoa))
    }

    def "Retorna ApiException quando a busca de pessoa por id está vazia"(){
        setup:
            repository.findById(id) >> pessoa
        when:
            service.getPessoaById(id)
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == HttpStatus.NOT_FOUND.value()
        where:
            id = "1"
            pessoa = Optional.empty()
    }

    def "Exclui uma pessoa cadastrada por id"(){
        setup:
            repository.existsById(id) >> true
        when:
            service.deletePessoa(id)
        then:
            noExceptionThrown()
        where:
            id = "1"
            pessoa = Optional.of(Stub(Pessoa))
    }

    def "Retorna ApiException quando tenta excluir um pessoa por id com valor nulo"(){
        setup:
            repository.findById(id) >> pessoa
        when:
            service.deletePessoa(id)
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == HttpStatus.NOT_FOUND.value()
        where:
            id = "1"
            pessoa = Optional.empty()
    }

    def "Executa metodo de save do repositorio ao cadastrar uma pessoa"() {
        setup:
            repository.findByCpf(pessoaModelMock.getCpf()) >> pessoa
        when:
            service.createPessoa(pessoaModelMock)
        then:
            1 * repository.save(_ as Pessoa)
        where:
            pessoaModelMock = getDefaultPessoaModel()
            pessoa = Optional.empty()
    }

    def"Erro de conflio ao tenta cadastrar a pessoa"(){
        setup:
            repository.findByCpf(pessoaModelMock.getCpf()) >> pessoa
        when:
            service.createPessoa(pessoaModelMock)
        then:
            ApiException apiException = thrown()
            apiException.getStatusCode() == HttpStatus.CONFLICT.value()
            0 * repository.save(_ as Pessoa)
        where:
            pessoaModelMock = getDefaultPessoaModel()
            pessoa = Optional.of(Stub(Pessoa))
    }

    @Unroll("Erro #error ao tenta cadastrar a pessoa")
    def"Erro na requisição ao tenta cadastrar a pessoa"(){
        when:
        service.createPessoa(pessoaModelMock)
        then:
        ApiException apiException = thrown()
        apiException.getStatusCode() == statusCode
        0 * repository.existsByCpf(_ as String)
        0 * repository.save(_ as Pessoa)
        where:
        error             | pessoaModelMock                  | statusCode
        "CPF invalido"    | getPessoaModelWithInvalidCPF()   | HttpStatus.BAD_REQUEST.value()
        "e-mail invalido" | getPessoaModelWithInvalidEmail() | HttpStatus.BAD_REQUEST.value()
    }

    def "Executa metodo de save do repositorio ao atualizar uma pessoa"() {
        setup:
        repository.findById(id) >> optionalPessoa
        when:
        service.updatePessoa(id, pessoaModelMock)
        then:
        1 * repository.save(_ as Pessoa)
        where:
        id = "1"
        pessoaModelMock = getDefaultPessoaModel()
        optionalPessoa = Optional.of(getDefaultPessoa())
    }

    def "Erro ao buscar pessoa por id ao atualizar uma pessoa"() {
        setup:
        repository.findById(id) >> optionalPessoa
        when:
        service.updatePessoa(id, pessoaModelMock)
        then:
        ApiException apiException = thrown()
        apiException.getStatusCode() == HttpStatus.NOT_FOUND.value()
        0 * repository.save(_ as Pessoa)
        where:
        id = "1"
        pessoaModelMock = getDefaultPessoaModel()
        optionalPessoa = Optional.empty()
    }

    @Unroll("Erro #error ao tenta atualizar a pessoa")
    def"Erro na requisição ao tenta atualizar a pessoa"(){
        when:
        service.updatePessoa(id, pessoaModelMock)
        then:
        ApiException apiException = thrown()
        apiException.getStatusCode() == statusCode
        0 * repository.existsByCpf(_ as String)
        0 * repository.save(_ as Pessoa)
        where:
        id  | error             | pessoaModelMock                  | statusCode
        "1" | "CPF invalido"    | getPessoaModelWithInvalidCPF()   | HttpStatus.BAD_REQUEST.value()
        "1" | "e-mail invalido" | getPessoaModelWithInvalidEmail() | HttpStatus.BAD_REQUEST.value()
    }

    def getDefaultPessoa(){
        return Pessoa.builder()
                .id("1")
                .nome("Fulano1")
                .dataNascimento(LocalDate.of(1990, 10,9))
                .cpf("000.313.480-68")
                .email("user.test@test.com")
                .sexo(SexoEnumeration.M)
                .naturalidade("Rio de Janeiro")
                .nacionalidade("brasileiro")
                .dataCadastro(LocalDateTime.now())
                .build()
    }

    def getDefaultPessoaModel(){
        return PessoaModel.builder()
                .nome("Fulano1")
                .dataNascimento(LocalDate.of(1990, 10,9))
                .cpf("000.313.480-68")
                .email("user.test@test.com")
                .sexo(SexoEnumeration.M)
                .naturalidade("Rio de Janeiro")
                .nacionalidade("brasileiro")
                .build()
    }

    def getPessoaModelWithInvalidCPF() {
        def pessoa = getDefaultPessoaModel()
        pessoa.setCpf("000.000.000-00")
        return pessoa
    }

    def getPessoaModelWithInvalidEmail() {
        def pessoa = getDefaultPessoaModel()
        pessoa.setEmail("user.test")
        return pessoa
    }
}