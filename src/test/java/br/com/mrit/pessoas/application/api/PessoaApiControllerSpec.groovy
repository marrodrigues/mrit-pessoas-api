package br.com.mrit.pessoas.application.api

import br.com.mrit.pessoas.application.api.impl.PessoasApiController
import br.com.mrit.pessoas.application.exception.ApiException
import br.com.mrit.pessoas.application.model.PessoaModel
import br.com.mrit.pessoas.application.service.PessoaService
import br.com.mrit.pessoas.domain.document.Pessoa
import br.com.mrit.pessoas.domain.document.enumeration.SexoEnumeration
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class PessoaApiControllerSpec extends Specification {

    def service = Mock(PessoaService)
    def controller = new PessoasApiController(service)
    def request = Mock(MockHttpServletRequest)

    def "Responde status 200 ao buscar todos os registros da collection pessoas"() {
        setup:
            service.getPessoas() >> list
        when:
            def response = controller.getPessoas()
        then:
            response.getStatusCode() == HttpStatus.OK
            response.getBody() == list
        where:
            list = [Stub(Pessoa)]
    }

    @Unroll("Responde status #statusCode.value() ao erro #thrown.code")
    def "Responde erros ao buscar todos os registros da collection pessoas"() {
        setup:
            service.getPessoas() >> { throw thrown }
        when:
            def response = controller.getPessoas()
        then:
            response.getStatusCode() == statusCode
        where:
            statusCode                       | thrown
            HttpStatus.NOT_FOUND             | ApiException.notFound("Cadastro não encontrado.", String.format("O cadastro referente ao id %s não foi encontrado.", "1"))
            HttpStatus.INTERNAL_SERVER_ERROR | ApiException.internalError("Erro interno.", "Erro interno.")
    }

    def "Responde status 200 ao buscar uma pessoa por id"() {
        setup:
            service.getPessoaById(id) >> pessoaMock
        when:
            def response = controller.getPessoaById(id)
        then:
            response.getStatusCode() == HttpStatus.OK
            response.getBody() == pessoaMock
        where:
            id  = "1"
            pessoaMock = Stub(Pessoa)
    }

    @Unroll("Responde status #statusCode.value() ao erro #thrown.code")
    def "Responde erros ao buscar uma pessoa por id"() {
        setup:
            service.getPessoaById(id) >> { throw thrown }
        when:
            def response = controller.getPessoaById(id)
        then:
            response.getStatusCode() == statusCode
        where:
            id  | statusCode                       | thrown
            "1" | HttpStatus.NOT_FOUND             | ApiException.notFound("Cadastro não encontrado.", String.format("O cadastro referente ao id %s não foi encontrado.", "1"))
    }

    def "Responde status 204 ao deletar uma pessoa por id"() {
        when:
            def response = controller.deletePessoa(id)
        then:
            response.getStatusCode() == HttpStatus.NO_CONTENT
        where:
            id  | pessoaMock
            "1" | Stub(Pessoa)
    }

    @Unroll("Responde status #statusCode.value() ao erro #thrown.code")
    def "Responde erros ao deletar uma pessoa por id"() {
        setup:
            service.deletePessoa(id) >> { throw thrown }
        when:
            def response = controller.deletePessoa(id)
        then:
            response.getStatusCode() == statusCode
        where:
            id  | statusCode                       | thrown
            "1" | HttpStatus.NOT_FOUND             | ApiException.notFound("Cadastro não encontrado.", String.format("O cadastro referente ao id %s não foi encontrado.", "1"))
    }

    def "Responde status 201 ao cadastrar uma pessoa"() {
        setup:
            service.createPessoa(pessoaModelMock) >> pessoaMock
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when:
            def response = controller.createPessoa(pessoaModelMock)
        then:
            response.getStatusCode() == HttpStatus.CREATED
            response.getBody() == pessoaMock
        where:
            pessoaMock   | pessoaModelMock
            Stub(Pessoa) | getDefaultPessoaModel()
    }

    @Unroll("Responde status #statusCode.value() ao erro #thrown.reason")
    def "Responde erros ao tentar criar um cadastro pessoa com dados inválidos"() {
        setup:
            service.createPessoa(pessoaModelMock) >> { throw thrown }
        when:
            def response = controller.createPessoa(pessoaModelMock)
        then:
            response.getStatusCode() == statusCode
        where:
            pessoaModelMock                  | statusCode             | thrown
            getPessoaModelWithInvalidCPF()   | HttpStatus.BAD_REQUEST | ApiException.badRequest("CPF inválido.", "O campo cpf deve ter um valor válido.")
            getPessoaModelWithInvalidEmail() | HttpStatus.BAD_REQUEST | ApiException.badRequest("E-mail inválido", "O campo email deve ter um e-mail válido.")
            getDefaultPessoaModel()          | HttpStatus.CONFLICT    | ApiException.conflict("Conflito no cadastro", "Erro ao cadastrado pessoa, o cpf %s já foi cadastrado.")
    }

    def "Responde status 204 ao atualizar uma pessoa"() {
        setup:
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when:
            def response = controller.updatePessoa(id, pessoaModelMock)
        then:
            response.getStatusCode() == HttpStatus.NO_CONTENT
        where:
            id  | pessoaMock   | pessoaModelMock
            "1" | Stub(Pessoa) | getDefaultPessoaModel()
    }

    @Unroll("Responde status #statusCode.value() ao erro #thrown.reason")
    def "Responde erros ao tentar atualizar um pessoa com dados inválidos"() {
        setup:
            service.updatePessoa(id, pessoaModelMock) >> { throw thrown }
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when:
            def response = controller.updatePessoa(id, pessoaModelMock)
        then:
            response.getStatusCode() == statusCode
        where:
            id  | pessoaModelMock                  | statusCode             | thrown
            "1" | getPessoaModelWithInvalidCPF()   | HttpStatus.BAD_REQUEST | ApiException.badRequest("CPF inválido.", "O campo cpf deve ter um valor válido.")
            "1" | getPessoaModelWithInvalidEmail() | HttpStatus.BAD_REQUEST | ApiException.badRequest("E-mail inválido", "O campo email deve ter um e-mail válido.")
            "1" | getDefaultPessoaModel()          | HttpStatus.NOT_FOUND   | ApiException.notFound("Cadastro não encontrado.", String.format("Não foi possivel atualizar o cadastro de pessoa, o cadastro referente ao id %s não foi encontrado.", id))
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