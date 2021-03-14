package br.com.mrit.pessoas.application.model;

import br.com.mrit.pessoas.application.model.enumeration.SexoEnumeration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Validated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description="Todos os detalhes sobre uma pessoa.")
public class Pessoa {
    @JsonProperty("id")
    @ApiModelProperty(example = "1", notes="Identificador da pessoa.")
    private Long id;
    @JsonProperty("nome")
    @ApiModelProperty(example = "Mario Rodrigues", required = true, notes="Nome da pessoa.")
    private String nome;
    @JsonProperty("sexo")
    @ApiModelProperty(example = "M", notes="Sexo da pessoa.")
    private SexoEnumeration sexo;
    @JsonProperty("email")
    @ApiModelProperty(example = "user.test@gmail.com", notes="E-mail da pessoa.")
    private String email;
    @JsonProperty("dataNascimento")
    @ApiModelProperty(example = "1990-10-09", required = true, notes="Data de nascimento da pessoa.")
    private LocalDate dataNascimento;
    @JsonProperty("naturalidade")
    @ApiModelProperty(example = "Rio de janeiro", notes="Cidade onde a pessoa nasceu.")
    private String naturalidade;
    @JsonProperty("nacionalidade")
    @ApiModelProperty(example = "brasileiro", notes="Nacionalidade da pessoa.")
    private String nacionalidade;
    @JsonProperty("cpf")
    @ApiModelProperty(example = "12345678912", required = true, notes="Numeros do cpf da pessoa.")
    private String cpf;
    @JsonProperty("dataCadastro")
    @ApiModelProperty(example = "2021-01-29 09:38:00", notes="Data da criação do cadastro de pessoa.")
    private LocalDateTime dataCadastro;
    @JsonProperty("dataAtualizacao")
    @ApiModelProperty(example = "2021-02-14 16:21:10", notes="Data da ultima atualização do cadastro de pessoa.")
    private LocalDateTime dataAtualizacao;
}
