package br.com.fiap.mottooth.repository;

import br.com.fiap.mottooth.model.Beacon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeaconRepository extends JpaRepository<Beacon, Long> {
    Optional<Beacon> findByUuid(String uuid);
    
    Page<Beacon> findByUuidContaining(String uuid, Pageable pageable);
    
    Page<Beacon> findByMotoId(Long motoId, Pageable pageable);
    
    Page<Beacon> findByModeloBeaconId(Long modeloBeaconId, Pageable pageable);
    
    @Query("SELECT b FROM Beacon b WHERE " +
           "(:uuid IS NULL OR b.uuid LIKE %:uuid%) AND " +
           "(:motoId IS NULL OR b.moto.id = :motoId) AND " +
           "(:modeloId IS NULL OR b.modeloBeacon.id = :modeloId)")
    Page<Beacon> findByFilters(
            @Param("uuid") String uuid,
            @Param("motoId") Long motoId,
            @Param("modeloId") Long modeloId,
            Pageable pageable);
}
