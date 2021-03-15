package br.com.mrit.pessoas.application.api.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeApiController {
    @RequestMapping(value = "/")
    public String index() {
        return "redirect:/swagger-ui/";
    }
}
