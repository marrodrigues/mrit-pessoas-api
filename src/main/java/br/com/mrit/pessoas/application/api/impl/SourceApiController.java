package br.com.mrit.pessoas.application.api.impl;

import br.com.mrit.pessoas.application.api.SourceApi;
import br.com.mrit.pessoas.application.model.Source;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SourceApiController implements SourceApi {

    @Value("${application.source.back-end}")
    private String BACK_END;
    @Value("${application.source.front-end}")
    private String FRONT_END;

    @GetMapping("/source")
    public ResponseEntity<Source> getSource() {
        return ResponseEntity.ok(Source.builder().backend(BACK_END).frontend(FRONT_END).build());
    }
}
