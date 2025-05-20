package br.com.fiap.mottooth.repository;

import br.com.fiap.mottooth.model.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {
    TipoUsuario findByDescricao(String descricao);
}
