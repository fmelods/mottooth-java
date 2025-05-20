package br.com.fiap.mottooth.repository;

import br.com.fiap.mottooth.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByMotoId(Long motoId);
    List<Movimentacao> findByUsuarioId(Long usuarioId);
    List<Movimentacao> findByTipoMovimentacaoId(Long tipoMovimentacaoId);
    List<Movimentacao> findByDataMovimentacaoBetween(LocalDateTime inicio, LocalDateTime fim);
}
