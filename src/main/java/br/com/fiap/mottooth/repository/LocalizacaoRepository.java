package br.com.fiap.mottooth.repository;

import br.com.fiap.mottooth.model.Localizacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {
    Page<Localizacao> findByMotoId(Long motoId, Pageable pageable);
    
    Page<Localizacao> findByPatioId(Long patioId, Pageable pageable);
    
    @Query("SELECT l FROM Localizacao l WHERE " +
           "(:motoId IS NULL OR l.moto.id = :motoId) AND " +
           "(:patioId IS NULL OR l.patio.id = :patioId) AND " +
           "(:dataInicio IS NULL OR l.dataHora >= :dataInicio) AND " +
           "(:dataFim IS NULL OR l.dataHora <= :dataFim)")
    Page<Localizacao> findByFilters(
            @Param("motoId") Long motoId,
            @Param("patioId") Long patioId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            Pageable pageable);
    
    List<Localizacao> findTop1ByMotoIdOrderByDataHoraDesc(Long motoId);
}
