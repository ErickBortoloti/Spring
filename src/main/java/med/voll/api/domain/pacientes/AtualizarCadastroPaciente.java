package med.voll.api.domain.pacientes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

public record AtualizarCadastroPaciente(    @NotNull
                                            Long id,
                                            String nome,
                                            String telefone,
                                          @Valid DadosEndereco endereco
) {
}
