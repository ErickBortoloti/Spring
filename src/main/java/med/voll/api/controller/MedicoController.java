package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medicos")

public class MedicoController {
    @Autowired
    private MedicoRepository repository;
	
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedicos dados) {
        repository.save(new Medico(dados));

    }

@GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) { //colocar parametro para paginacao do spring - fazer sobrecarga no findall4
        //pageabledeafault = caso o parametro NAO esteja vindo da URL, ele usára as o que foi definido acima.
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //Conversao dos dados de Medico para Listagem medicos para lista.


}
    @PutMapping
    @Transactional
    public void AtualizarDadosMedicos(@RequestBody @Valid AtualizarCadastroMedico dados) {
        var medico = repository.getReferenceById(dados.id()); //repository responsável por acessar o banco de dados
        medico.atualizarDados(dados);

    }

    @DeleteMapping("/{id}") // {} = parametro dinâmico
    @Transactional //responsável por fazer escritas, no caso do GET N usa pois só mostra.
    public void excluirMedico(@PathVariable Long id) { //para receber o DeleteMapping com o ID selecionado pelo o usuario, necessário colocar o @PathVariable
        var medico = repository.getReferenceById(id);
        medico.excluir();

    }

}
