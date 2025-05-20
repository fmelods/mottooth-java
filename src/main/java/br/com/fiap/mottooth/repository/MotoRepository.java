package br.com.fiap.mottooth.repository;

import br.com.fiap.mottooth.model.Moto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
    Optional<Moto> findByPlaca(String placa);
    
    Page<Moto> findByPlacaContaining(String placa, Pageable pageable);
    
    Page<Moto> findByClienteId(Long clienteId, Pageable pageable);
    
    Page<Moto> findByModeloMotoId(Long modeloMotoId, Pageable pageable);
    
    @Query("SELECT m FROM Moto m WHERE " +
           "(:placa IS NULL OR m.placa LIKE %:placa%) AND " +
           "(:clienteId IS NULL OR m.cliente.id = :clienteId) AND " +
           "(:modeloId IS NULL OR m.modeloMoto.id = :modeloId)")
    Page<Moto> findByFilters(
            @Param("placa") String placa,
            @Param("clienteId") Long clienteId,
            @Param("modeloId") Long modeloId,
            Pageable pageable);
}
