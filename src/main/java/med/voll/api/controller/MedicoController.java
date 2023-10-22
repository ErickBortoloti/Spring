package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")

public class MedicoController {
    @Autowired
    private MedicoRepository repository;
	
    @PostMapping
    @Transactional

    //código 201 = requisicao processada e novo recurso criado
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicos dados, UriComponentsBuilder EnderecoSite) {
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = EnderecoSite.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));

    }

@GetMapping
    public ResponseEntity <Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) { //colocar parametro para paginacao do spring - fazer sobrecarga no findall4
        //pageabledeafault = caso o parametro NAO esteja vindo da URL, ele usára as o que foi definido acima.
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //Conversao dos dados de Medico para Listagem medicos para lista.
        return ResponseEntity.ok(page);

}
    @PutMapping
    @Transactional
    public ResponseEntity AtualizarDadosMedicos(@RequestBody @Valid AtualizarCadastroMedico dados) {
        var medico = repository.getReferenceById(dados.id()); //repository responsável por acessar o banco de dados
        medico.atualizarDados(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); //devolver um DTO pq n eh recomendando devolver uma entidade JPA

    }

    @DeleteMapping("/{id}") // {} = parametro dinâmico
    @Transactional //responsável por fazer escritas, no caso do GET N usa pois só mostra.
    public ResponseEntity excluirMedico(@PathVariable Long id) { //para receber o DeleteMapping com o ID selecionado pelo o usuario, necessário colocar o @PathVariable
        // ^^ classe do spring para selecionar qual resposta vai ter (n sendo mais 200)
        var medico = repository.getReferenceById(id);
        medico.excluir();
        //noContent cria um objeto, e o .build constroi o objeto - NoContent = n tem conteudo na resposta.
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}") // {} = parametro dinâmico
    public ResponseEntity detalharMedico(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }

}


