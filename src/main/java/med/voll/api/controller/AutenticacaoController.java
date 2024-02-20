package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    @Autowired //injetar o parametro
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenservice;

    public AutenticacaoController(TokenService tokenservice) {
        this.tokenservice = tokenservice;
    }

    @PostMapping
    public ResponseEntity logar(@RequestBody @Valid DadosAutenticacao dados) {
        try {
            var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            var autenticacao = manager.authenticate(token);

            var TokenJWT = tokenservice.gerarToken((usuario) autenticacao.getPrincipal());

            return ResponseEntity.ok(new DadosTokenJWT(TokenJWT)); }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
