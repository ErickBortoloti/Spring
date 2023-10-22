package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedicos(

        @NotBlank(message = "É necessário preencher seu nome!") //não está branco ou nulo - somente para campos strings
        String nome,
        @NotBlank (message = "É necessário preencher o seu e-mail!")
        @Email (message = "Formato de e-mail incorreto")
        String email,
        @NotBlank(message = "É necessário preencher seu telefone!")
        String telefone,
        @NotBlank(message = "É necessário informar o seu CRM para a conclusão de seu cadastro")
        @Pattern(regexp =  "\\d{4,6}", message = "Formato incorreto, lembre-se que o CRM possui 4 digitos!") // \\d sao digitos e entre {} de 4 a 6 digitios
        String crm,
        @NotNull(message = "Por favor nos informe sua especialidade")
        Especialidade especialidade,

        @NotNull(message = "É necessária informar o seu endereço")
        @Valid DadosEndereco endereco) { //validar outro objeto como um dos atributos (@Valid)

}
