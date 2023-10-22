package med.voll.api.infra.excepetion;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity erro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity erro400(MethodArgumentNotValidException erro) { //utilizar o MethodArgumentNotValidExcepetion para capturar a exceção em si e conseguir exibir na pagina web
        var erros = erro.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(DadosErros404::new).toList()); //utilização do BOdy para devolver um corpo no site
    }


    private record DadosErros404(String campo, String mensagem) {
        public DadosErros404(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());

        }

    }
}
