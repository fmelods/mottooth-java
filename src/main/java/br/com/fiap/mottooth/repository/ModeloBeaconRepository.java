package br.com.fiap.mottooth.repository;

import br.com.fiap.mottooth.model.ModeloBeacon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloBeaconRepository extends JpaRepository<ModeloBeacon, Long> {
}
