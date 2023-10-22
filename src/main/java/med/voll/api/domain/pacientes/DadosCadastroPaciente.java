package med.voll.api.domain.pacientes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(
        @NotBlank (message = "É necessário preencher seu nome!")
        String nome,
        @NotBlank (message = "É necessário preencher seu email!")
        @Email (message = "Formato de email errado")
        String email,
        @NotBlank (message = "É necessário preencher seu telefone!")
        String telefone,
        @NotBlank (message = "É necessário informar o seu CPF")
        @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}", message = "Formato de CPF digitado erroneamente. Lembre-se CPF possui 9 digitos com 3 pontos e 1 travessão no final")
        String cpf,
        @NotNull (message = "É necessária informar o seu endereço")
        @Valid
        DadosEndereco endereco) {
}
