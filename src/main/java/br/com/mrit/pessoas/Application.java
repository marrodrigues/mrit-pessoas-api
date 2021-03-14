package br.com.mrit.pessoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.mrit.pessoas")
public class Application {
    public static void main(String[] args){
        new SpringApplication(Application.class).run(args);
    }
}