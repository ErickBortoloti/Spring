package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);
    @Query(value = "SELECT * FROM medicos m " +
            "WHERE m.ativo = 1 " +
            "AND m.especialidade = :especialidade " +
            "AND m.id NOT IN (SELECT c.medico_id FROM consultas c WHERE c.data = :data) " +
            "ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);
}
