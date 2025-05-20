package br.com.fiap.mottooth.repository;

import br.com.fiap.mottooth.model.ModeloMoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloMotoRepository extends JpaRepository<ModeloMoto, Long> {
}
