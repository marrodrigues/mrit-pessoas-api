package br.com.mrit.pessoas.domain.document;

import br.com.mrit.pessoas.domain.document.enumeration.SexoEnumeration;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Validated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pessoa")
@ApiModel(description="Todos os detalhes sobre o documento pessoa.")
public class Pessoa {
    @Id
    @ApiModelProperty(example = "1", notes="Identificador da pessoa.")
    private String id;
    @ApiModelProperty(example = "Mario Rodrigues", required = true, notes="Nome da pessoa.")
    private String nome;
    @ApiModelProperty(example = "M", notes="Sexo da pessoa.")
    private SexoEnumeration sexo;
    @ApiModelProperty(example = "user.test@gmail.com", notes="E-mail da pessoa.")
    private String email;
    @ApiModelProperty(example = "1990-10-09", required = true, notes="Data de nascimento da pessoa.")
    private LocalDate dataNascimento;
    @ApiModelProperty(example = "Rio de janeiro", notes="Cidade onde a pessoa nasceu.")
    private String naturalidade;
    @ApiModelProperty(example = "brasileiro", notes="Nacionalidade da pessoa.")
    private String nacionalidade;
    @ApiModelProperty(example = "12345678912", required = true, notes="Numeros do cpf da pessoa.")
    private String cpf;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(example = "2021-01-29 09:38:00", notes="Data da criação do cadastro de pessoa.")
    private LocalDateTime dataCadastro;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(example = "2021-02-14 16:21:10", notes="Data da ultima atualização do cadastro de pessoa.")
    private LocalDateTime dataAtualizacao;
}
