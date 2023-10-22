package med.voll.api.domain.pacientes;

public record DadosListagemPaciente(Long id, String nome, String email, String CPF, String telefone) {

    public DadosListagemPaciente(Paciente Paciente) {
        this(Paciente.getId(), Paciente.getNome(), Paciente.getEmail(), Paciente.getCpf(), Paciente.getTelefone());
    };



}
