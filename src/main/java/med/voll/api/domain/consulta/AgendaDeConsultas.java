package med.voll.api.domain.consulta;


import med.voll.api.domain.ValidacaoExcepetion;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository MedicoRepository;
    @Autowired
    private PacienteRepository PacienteRepository;

    public void agendar(AgendamentoDaConsulta dados) {
        if (!PacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoExcepetion("ID do Paciente informado inválido");
        }

        if (dados.idMedico() != null && !MedicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoExcepetion("ID do Médico informado");
        }

        var paciente = PacienteRepository.findById(dados.idPaciente()).get();
        var medico = escolherMedico(dados);
        var consulta = new consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);
    }

    private Medico escolherMedico(AgendamentoDaConsulta dados) {
        if (dados.idMedico() != null) {
            return MedicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoExcepetion("Por favor, selecionar uma especialidade ou escolher um médico!");
        }

        return MedicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if(!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoExcepetion("Id da consulta inválido!");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

}
