package br.com.mrit.pessoas.application.api

import br.com.mrit.pessoas.application.api.impl.SourceApiController
import org.springframework.http.HttpStatus
import spock.lang.Specification

class SourceApiControllerSpec extends Specification {

    def controller = new SourceApiController()

    def "Responde status 200 ao buscar os repositorios"() {
        when:
            def response = controller.getSource()
        then:
            response.getStatusCode() == HttpStatus.OK
            response.getBody().getBackend() == backend
            response.getBody().getFrontend() == frontend
        where:
            backend = "https://github.com/marrodrigues/mrit-pessoas-api"
            frontend = "https://github.com/marrodrigues/mrit-pessoas"
    }
}
