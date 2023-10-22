package med.voll.api.domain.usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //modo de dizer ao Spring que essa classe é um serviço, um componente.
//implementação da interfação UserDetailsService para o spring enteder que essa classe é de autenticação
public class Autenticacao implements UserDetailsService {

 @Autowired //injentar a dependencia na classe
   private UsuarioRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }
}
