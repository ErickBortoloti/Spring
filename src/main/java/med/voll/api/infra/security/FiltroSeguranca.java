package med.voll.api.infra.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class FiltroSeguranca extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenservice;
    @Autowired
    private UsuarioRepository repositoryusuario;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { //validar apenas uma vez por request
        var token = recuperaToken(request);
        if (token != null) {
            var login = tokenservice.getSubject(token);
            var usuario = repositoryusuario.findByLogin(login);

            var autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autenticacao);

        }

        filterChain.doFilter(request, response);

    }

    private String recuperaToken(HttpServletRequest request) {
        var autorizacao = request.getHeader("Authorization");
        if(autorizacao != null) {
            return autorizacao.replace("Bearer ", "");
        }

        return null;
    }
}