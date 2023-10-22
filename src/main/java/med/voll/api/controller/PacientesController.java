package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.pacientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacientesController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder EnderecoSite) {
        var paciente = new Paciente(dados);
        repository.save(paciente);

        var uri = EnderecoSite.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();


        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));

    }

@GetMapping

public ResponseEntity <Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
    var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);

    return ResponseEntity.ok(page);

}

@PutMapping
@Transactional

    public ResponseEntity AtualizarCadastroPaciente(@RequestBody @Valid AtualizarCadastroPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarDados(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));

}

@DeleteMapping("/{id}")
@Transactional

    public ResponseEntity ApagarCliente(@PathVariable Long id) {
    var paciente = repository.getReferenceById(id);
    paciente.excluir();

    return ResponseEntity.noContent().build();
    }

@GetMapping("/{id}")

public ResponseEntity detalharPaciente(@PathVariable Long id) {
    var paciente = repository.getReferenceById(id);

    return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));

    }
}
